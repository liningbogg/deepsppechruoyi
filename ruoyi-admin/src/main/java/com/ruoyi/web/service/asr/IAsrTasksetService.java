package com.ruoyi.web.service.asr;

import java.util.List;
import com.ruoyi.web.domain.asr.AsrTaskset;

/**
 * 单次任务集合Service接口
 * 
 * @author ruoyi
 * @date 2021-04-16
 */
public interface IAsrTasksetService 
{
    /**
     * 查询单次任务集合
     * 
     * @param tasksetId 单次任务集合ID
     * @return 单次任务集合
     */
    public AsrTaskset selectAsrTasksetById(Long tasksetId);

    /**
     * 查询单次任务集合列表
     * 
     * @param asrTaskset 单次任务集合
     * @return 单次任务集合集合
     */
    public List<AsrTaskset> selectAsrTasksetList(AsrTaskset asrTaskset);

    /**
     * 新增单次任务集合
     * 
     * @param asrTaskset 单次任务集合
     * @return 结果
     */
    public int insertAsrTaskset(AsrTaskset asrTaskset);

    /**
     * 修改单次任务集合
     * 
     * @param asrTaskset 单次任务集合
     * @return 结果
     */
    public int updateAsrTaskset(AsrTaskset asrTaskset);

    /**
     * 批量删除单次任务集合
     * 
     * @param tasksetIds 需要删除的单次任务集合ID
     * @return 结果
     */
    public int deleteAsrTasksetByIds(Long[] tasksetIds);

    /**
     * 删除单次任务集合信息
     * 
     * @param tasksetId 单次任务集合ID
     * @return 结果
     */
    public int deleteAsrTasksetById(Long tasksetId);
}
