package com.ruoyi.web.domain.asr;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 单次任务集合对象 asr_taskset
 * 
 * @author ruoyi
 * @date 2021-04-16
 */
public class AsrTaskset extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 任务集合ID */
    private Long tasksetId;

    /** 任务集合名称 */
    @Excel(name = "任务集合名称")
    private String tasksetName;

    /** 状态（0就绪 1进行中 2完成 3失败） */
    @Excel(name = "状态", readConverterExp = "0=就绪,1=进行中,2=完成,3=失败")
    private String status;

    public void setTasksetId(Long tasksetId) 
    {
        this.tasksetId = tasksetId;
    }

    public Long getTasksetId() 
    {
        return tasksetId;
    }
    public void setTasksetName(String tasksetName) 
    {
        this.tasksetName = tasksetName;
    }

    public String getTasksetName() 
    {
        return tasksetName;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("tasksetId", getTasksetId())
            .append("tasksetName", getTasksetName())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
