package com.ruoyi.web.service.asr.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.web.mapper.asr.AsrTaskMapper;
import com.ruoyi.web.domain.asr.AsrTask;
import com.ruoyi.web.service.asr.IAsrTaskService;

/**
 * 单次任务Service业务层处理
 * 
 * @author ruoyi
 * @date 2021-04-17
 */
@Service
public class AsrTaskServiceImpl implements IAsrTaskService 
{
    @Autowired
    private AsrTaskMapper asrTaskMapper;

    /**
     * 查询单次任务
     * 
     * @param taskId 单次任务ID
     * @return 单次任务
     */
    @Override
    public AsrTask selectAsrTaskById(Long taskId)
    {
        return asrTaskMapper.selectAsrTaskById(taskId);
    }

    /**
     * 查询单次任务列表
     * 
     * @param asrTask 单次任务
     * @return 单次任务
     */
    @Override
    public List<AsrTask> selectAsrTaskList(AsrTask asrTask)
    {
        return asrTaskMapper.selectAsrTaskList(asrTask);
    }

    /**
     * 新增单次任务
     * 
     * @param asrTask 单次任务
     * @return 结果
     */
    @Override
    public int insertAsrTask(AsrTask asrTask)
    {
        asrTask.setCreateTime(DateUtils.getNowDate());
        return asrTaskMapper.insertAsrTask(asrTask);
    }

    /**
     * 修改单次任务
     * 
     * @param asrTask 单次任务
     * @return 结果
     */
    @Override
    public int updateAsrTask(AsrTask asrTask)
    {
        asrTask.setUpdateTime(DateUtils.getNowDate());
        return asrTaskMapper.updateAsrTask(asrTask);
    }

    /**
     * 批量删除单次任务
     * 
     * @param taskIds 需要删除的单次任务ID
     * @return 结果
     */
    @Override
    public int deleteAsrTaskByIds(Long[] taskIds)
    {
        return asrTaskMapper.deleteAsrTaskByIds(taskIds);
    }

    /**
     * 删除单次任务信息
     * 
     * @param taskId 单次任务ID
     * @return 结果
     */
    @Override
    public int deleteAsrTaskById(Long taskId)
    {
        return asrTaskMapper.deleteAsrTaskById(taskId);
    }
}
