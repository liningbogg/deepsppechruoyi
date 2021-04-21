package com.ruoyi.web.mapper.asr;

import java.util.List;
import com.ruoyi.web.domain.asr.AsrTaskset;

/**
 * 单次任务集合Mapper接口
 * 
 * @author ruoyi
 * @date 2021-04-16
 */
public interface AsrTasksetMapper 
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
     * 删除单次任务集合
     * 
     * @param tasksetId 单次任务集合ID
     * @return 结果
     */
    public int deleteAsrTasksetById(Long tasksetId);

    /**
     * 批量删除单次任务集合
     * 
     * @param tasksetIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteAsrTasksetByIds(Long[] tasksetIds);
}
