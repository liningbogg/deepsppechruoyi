package com.ruoyi.web.controller.asr;

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
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.web.domain.asr.AsrTask;
import com.ruoyi.web.domain.asr.AsrTaskset;
import com.ruoyi.web.service.asr.IAsrTasksetService;
import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.common.utils.file.AudioUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.utils.sign.Base64;
import com.ruoyi.framework.web.service.TokenService;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 单次任务集合Controller
 * 
 * @author ruoyi
 * @date 2021-04-16
 */
@RestController
@RequestMapping("/asr/taskset")
public class AsrTasksetController extends BaseController
{
    @Autowired
    private IAsrTasksetService asrTasksetService;
    
    @Autowired
    private TokenService tokenService;

    /**
     * 查询任务集合列表
     */
    @DataScope(userAlias = "ts")
    @PreAuthorize("@ss.hasPermi('asr:taskset:list')")
    @GetMapping("/list")
    public TableDataInfo list(AsrTaskset asrTaskset)
    {
    	//LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        //String username = loginUser.getUsername();
        startPage();
        //asrTaskset.setCreateBy(username);
        List<AsrTaskset> list = asrTasksetService.selectAsrTasksetList(asrTaskset);
        return getDataTable(list);
    }

    /**
     * 导出单次任务集合列表
     */
    @PreAuthorize("@ss.hasPermi('asr:taskset:export')")
    @Log(title = "单次任务集合", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(AsrTaskset asrTaskset)
    {
        List<AsrTaskset> list = asrTasksetService.selectAsrTasksetList(asrTaskset);
        ExcelUtil<AsrTaskset> util = new ExcelUtil<AsrTaskset>(AsrTaskset.class);
        return util.exportExcel(list, "taskset");
    }
    

    /**
     * 获取单次任务集合详细信息
     */
    @PreAuthorize("@ss.hasPermi('asr:taskset:query')")
    @GetMapping(value = "/{tasksetId}")
    public AjaxResult getInfo(@PathVariable("tasksetId") Long tasksetId)
    {
        return AjaxResult.success(asrTasksetService.selectAsrTasksetById(tasksetId));
    }

    /**
     * 新增单次任务集合
     */
    @PreAuthorize("@ss.hasPermi('asr:taskset:add')")
    @Log(title = "单次任务集合", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AsrTaskset asrTaskset)
    {
        return toAjax(asrTasksetService.insertAsrTaskset(asrTaskset));
    }

    /**
     * 修改单次任务集合
     */
    @PreAuthorize("@ss.hasPermi('asr:taskset:edit')")
    @Log(title = "单次任务集合", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AsrTaskset asrTaskset)
    {
        return toAjax(asrTasksetService.updateAsrTaskset(asrTaskset));
    }

    /**
     * 删除单次任务集合
     */
    @PreAuthorize("@ss.hasPermi('asr:taskset:remove')")
    @Log(title = "单次任务集合", businessType = BusinessType.DELETE)
	@DeleteMapping("/{tasksetIds}")
    public AjaxResult remove(@PathVariable Long[] tasksetIds)
    {
        return toAjax(asrTasksetService.deleteAsrTasksetByIds(tasksetIds));
    }
}
