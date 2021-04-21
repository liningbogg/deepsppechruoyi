package com.ruoyi.web.service.asr.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.web.mapper.asr.AsrTasksetMapper;
import com.ruoyi.web.domain.asr.AsrTaskset;
import com.ruoyi.web.service.asr.IAsrTasksetService;

/**
 * 单次任务集合Service业务层处理
 * 
 * @author ruoyi
 * @date 2021-04-16
 */
@Service
public class AsrTasksetServiceImpl implements IAsrTasksetService 
{
    @Autowired
    private AsrTasksetMapper asrTasksetMapper;

    /**
     * 查询单次任务集合
     * 
     * @param tasksetId 单次任务集合ID
     * @return 单次任务集合
     */
    @Override
    public AsrTaskset selectAsrTasksetById(Long tasksetId)
    {
        return asrTasksetMapper.selectAsrTasksetById(tasksetId);
    }

    /**
     * 查询单次任务集合列表
     * 
     * @param asrTaskset 单次任务集合
     * @return 单次任务集合
     */
    @Override
    public List<AsrTaskset> selectAsrTasksetList(AsrTaskset asrTaskset)
    {
        return asrTasksetMapper.selectAsrTasksetList(asrTaskset);
    }

    /**
     * 新增单次任务集合
     * 
     * @param asrTaskset 单次任务集合
     * @return 结果
     */
    @Override
    public int insertAsrTaskset(AsrTaskset asrTaskset)
    {
        asrTaskset.setCreateTime(DateUtils.getNowDate());
        return asrTasksetMapper.insertAsrTaskset(asrTaskset);
    }

    /**
     * 修改单次任务集合
     * 
     * @param asrTaskset 单次任务集合
     * @return 结果
     */
    @Override
    public int updateAsrTaskset(AsrTaskset asrTaskset)
    {
        asrTaskset.setUpdateTime(DateUtils.getNowDate());
        return asrTasksetMapper.updateAsrTaskset(asrTaskset);
    }

    /**
     * 批量删除单次任务集合
     * 
     * @param tasksetIds 需要删除的单次任务集合ID
     * @return 结果
     */
    @Override
    public int deleteAsrTasksetByIds(Long[] tasksetIds)
    {
        return asrTasksetMapper.deleteAsrTasksetByIds(tasksetIds);
    }

    /**
     * 删除单次任务集合信息
     * 
     * @param tasksetId 单次任务集合ID
     * @return 结果
     */
    @Override
    public int deleteAsrTasksetById(Long tasksetId)
    {
        return asrTasksetMapper.deleteAsrTasksetById(tasksetId);
    }
}
