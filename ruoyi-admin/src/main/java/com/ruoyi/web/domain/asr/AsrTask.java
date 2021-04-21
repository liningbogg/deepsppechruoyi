package com.ruoyi.web.domain.asr;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 单次任务对象 asr_task
 * 
 * @author ruoyi
 * @date 2021-04-17
 */
public class AsrTask extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 任务ID */
    @Excel(name = "任务ID")
    private Long taskId;

    /** 任务集合ID */
    @Excel(name = "任务集合ID")
    private Long tasksetId;

    /** 任务名称 */
    @Excel(name = "任务名称")
    private String taskName;

    /** 文件路径 */
    @Excel(name = "文件路径")
    private String filepath;

    /** 转写结果 */
    @Excel(name = "转写结果")
    private String result;

    /** 状态（0就绪 1进行中 2完成 3失败） */
    @Excel(name = "状态", readConverterExp = "0=就绪,1=进行中,2=完成,3=失败")
    private String status;

    public void setTaskId(Long taskId) 
    {
        this.taskId = taskId;
    }

    public Long getTaskId() 
    {
        return taskId;
    }
    public void setTasksetId(Long tasksetId) 
    {
        this.tasksetId = tasksetId;
    }

    public Long getTasksetId() 
    {
        return tasksetId;
    }
    public void setTaskName(String taskName) 
    {
        this.taskName = taskName;
    }

    public String getTaskName() 
    {
        return taskName;
    }
    public void setFilepath(String filepath) 
    {
        this.filepath = filepath;
    }

    public String getFilepath() 
    {
        return filepath;
    }
    public void setResult(String result) 
    {
        this.result = result;
    }

    public String getResult() 
    {
        return result;
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
            .append("taskId", getTaskId())
            .append("tasksetId", getTasksetId())
            .append("taskName", getTaskName())
            .append("filepath", getFilepath())
            .append("result", getResult())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
