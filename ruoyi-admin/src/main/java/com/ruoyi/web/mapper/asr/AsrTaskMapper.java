package com.ruoyi.web.mapper.asr;

import java.util.List;
import com.ruoyi.web.domain.asr.AsrTask;

/**
 * 单次任务Mapper接口
 * 
 * @author ruoyi
 * @date 2021-04-17
 */
public interface AsrTaskMapper 
{
    /**
     * 查询单次任务
     * 
     * @param taskId 单次任务ID
     * @return 单次任务
     */
    public AsrTask selectAsrTaskById(Long taskId);

    /**
     * 查询单次任务列表
     * 
     * @param asrTask 单次任务
     * @return 单次任务集合
     */
    public List<AsrTask> selectAsrTaskList(AsrTask asrTask);

    /**
     * 新增单次任务
     * 
     * @param asrTask 单次任务
     * @return 结果
     */
    public int insertAsrTask(AsrTask asrTask);

    /**
     * 修改单次任务
     * 
     * @param asrTask 单次任务
     * @return 结果
     */
    public int updateAsrTask(AsrTask asrTask);

    /**
     * 删除单次任务
     * 
     * @param taskId 单次任务ID
     * @return 结果
     */
    public int deleteAsrTaskById(Long taskId);

    /**
     * 批量删除单次任务
     * 
     * @param taskIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteAsrTaskByIds(Long[] taskIds);
}
