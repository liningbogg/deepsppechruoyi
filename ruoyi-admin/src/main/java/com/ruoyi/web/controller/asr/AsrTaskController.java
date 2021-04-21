package com.ruoyi.web.controller.asr;

import java.io.File;
import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ruoyi.common.annotation.DataScope;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.web.domain.asr.AsrTask;
import com.ruoyi.web.service.asr.IAsrTaskService;
import com.ruoyi.common.utils.file.AudioUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.utils.sign.Base64;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 单次任务Controller
 * 
 * @author ruoyi
 * @date 2021-04-17
 */
@RestController
@RequestMapping("/asr/task")
public class AsrTaskController extends BaseController
{
    @Autowired
    private IAsrTaskService asrTaskService;

    /**
     * 查询单次任务列表
     */
    @DataScope(userAlias = "t")
    @PreAuthorize("@ss.hasPermi('asr:task:list')")
    @GetMapping("/list")
    public TableDataInfo list(AsrTask asrTask)
    {
        startPage();
        System.out.println(asrTask);
        List<AsrTask> list = asrTaskService.selectAsrTaskList(asrTask);
        return getDataTable(list);
    }

    /**
     * 导出单次任务列表
     */
    @PreAuthorize("@ss.hasPermi('asr:task:export')")
    @Log(title = "单次任务", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(AsrTask asrTask)
    {
        List<AsrTask> list = asrTaskService.selectAsrTaskList(asrTask);
        ExcelUtil<AsrTask> util = new ExcelUtil<AsrTask>(AsrTask.class);
        return util.exportExcel(list, "task");
    }

    /**
     * 获取单次任务详细信息
     */
    @PreAuthorize("@ss.hasPermi('asr:task:query')")
    @GetMapping(value = "/{taskId}")
    public AjaxResult getInfo(@PathVariable("taskId") Long taskId)
    {
        return AjaxResult.success(asrTaskService.selectAsrTaskById(taskId));
    }
    
    
    /**
     * 获取wav语音
     */
    @DataScope(userAlias = "t")
    @PreAuthorize("@ss.hasPermi('asr:task:query')")
    @GetMapping("/achieveWav")
    public AjaxResult achieveWav(Long taskId)
    {
    	AsrTask task = asrTaskService.selectAsrTaskById(taskId);
    	AjaxResult ajax = AjaxResult.success();
    	String filename = task.getFilepath();
    	ajax.put("blob", Base64.encode(AudioUtils.getAudio(filename)));
        ajax.put("fileName", filename);
        return ajax;
    } 

    /**
     * 新增单次任务
     */
    @PreAuthorize("@ss.hasPermi('asr:task:add')")
    @Log(title = "单次任务", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AsrTask asrTask)
    {
        return toAjax(asrTaskService.insertAsrTask(asrTask));
    }

    /**
     * 修改单次任务
     */
    @PreAuthorize("@ss.hasPermi('asr:task:edit')")
    @Log(title = "单次任务", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AsrTask asrTask)
    {
        return toAjax(asrTaskService.updateAsrTask(asrTask));
    }

    /**
     * 删除单次任务
     */
    @PreAuthorize("@ss.hasPermi('asr:task:remove')")
    @Log(title = "单次任务", businessType = BusinessType.DELETE)
	@DeleteMapping("/{taskIds}")
    public AjaxResult remove(@PathVariable Long[] taskIds)
    {
        return toAjax(asrTaskService.deleteAsrTaskByIds(taskIds));
    }
}
