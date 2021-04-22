<template>
  <div class="app-container display">
    <el-row :gutter="20">
      <!--任务集数据-->
      <el-col :span="24" :xs="24">
        <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
          <el-form-item label="任务名称" prop="taskName" >
            <el-input
              v-model="queryParams.taskName"
              placeholder="请输入任务名称"
              clearable
              size="small"
              style="width: 200px"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="完成情况" prop="status" >
          	<el-select
              v-model="queryParams.status"
              placeholder="设置状态"
              clearable
              size="small"
              style="width: 240px"
            >
              <el-option
                v-for="dict in statusOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              />
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
          </el-form-item>
        </el-form>
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button
              type="danger"
              plain
              icon="el-icon-delete"
              size="mini"
              :disabled="multiple"
              @click="handleDelete"
              v-hasPermi="['asr:task:remove']"
            >删除</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="warning"
              plain
              icon="el-icon-download"
              size="mini"
              @click="handleExport"
              v-hasPermi="['asr:task:export']"
            >导出</el-button>
          </el-col>
          <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
        </el-row>
        
        <el-table v-loading="loading" :data="taskList" @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="50" align="center" min-width="5%"/>
          <el-table-column label="语音编号" align="center" key="taskId" prop="taskId" min-width="10%" v-if="columns[0].visible" :show-overflow-tooltip="true" />
          <el-table-column label="语音名称" align="center" key="taskName" prop="taskName" min-width="20%" v-if="columns[1].visible" :show-overflow-tooltip="true" />
          <el-table-column label="转写结果" 
          		align="center" 
          		key="result" 
          		prop="result" 
          		v-if="columns[2].visible" 
          		:show-overflow-tooltip="true" 
          		min-width="30%"
          		>
          </el-table-column>
          <el-table-column label="创建时间" align="center" key="createTime" prop="createTime" min-width="15%" v-if="columns[3].visible" />
          <el-table-column label="完成状态" align="center" key="status" v-if="columns[4].visible" min-width="10%">
            <template slot-scope="scope">
              <el-tag color="#00ff00" v-if='scope.row.status=="2"'>完成</el-tag>
              <el-tag color="#ff0000" v-if='scope.row.status=="0"'>未完成</el-tag>
            </template>
          </el-table-column>
          <el-table-column
            label="操作"
            key="operator"
            align="center"
            width="160"
            min-width="10%"
            v-hasPermi="['asr:task:list']"
            class-name="small-padding fixed-width"
          >
            <template slot-scope="scope">
              <el-button type="text" @click="onPlay(scope.row, scope.$index)" 
              		icon="el-icon-video-play" 
              		v-if="scope.row.playstatusReady">播放</el-button>
              <el-button type="text" @click="onStopplay" icon="el-icon-video-pause" v-else>停止</el-button>
            </template>
          </el-table-column>
        </el-table>

        <pagination
          v-show="total>0"
          :total="total"
          :page.sync="queryParams.pageNum"
          :limit.sync="queryParams.pageSize"
          @pagination="getTaskList"
        />
      </el-col>  
    </el-row>
  </div>
</template>

<script>
import { listTask } from "@/api/asr/task";
import { achieveWav , delTask, exportTask} from "@/api/asr/task";
export default {
  name: "ListTaskset",
  data() {
    return {
      // 版本号
      version: "0.0.1",
      // 显示搜索条件
      showSearch: true,
      // 当前状态名称
      statusName: "",
      // 非多个禁用
      multiple: true,
      // 非单个禁用
      single: true,
      // 选中的task
	  taskids : [], 
      // 列信息
      columns: [
        { key: 0, label: `语音编号`, visible: true },
        { key: 1, label: `语音名称`, visible: true },
        { key: 2, label: `转写结果`, visible: true },
        { key: 3, label: `创建时间`, visible: true },
        { key: 4, label: `状态`, visible: true },
      ],
      // 日期范围
      dateRange: [],
      statusOptions: [
		{
          "dictValue": undefined,
          "dictLabel": "所有"
        },
		{
          "dictValue": "0",
          "dictLabel": "未完成"
        },
		{
          "dictValue": "2",
          "dictLabel": "完成"
        },
      ],
      defaultProps: {
        children: "children",
        label: "label"
      },
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        createBy: undefined,
        tasksetId: undefined,
        taskName: undefined, 
        status: undefined,
      },
      queryParamsWav: {
      	taskId: null,
      },
      // taskset列表
      taskList: null,
    };
  },
  created() {
    this.queryParams.tasksetId = this.$route.query.tasksetId;
    this.getTaskList();
    
    //let url= window.URL.createObjectURL(blobwav);  // 获取音频blob url
    this.phrase=new Audio();  // 音乐片段播放器
    this.phrase.controls=false;  // 设置显示播放控件
    this.phrase.autoplay="autoplay";
    this.phrase.addEventListener(
        'ended',
        () => {
             //this.playstatusReady=true;
             for(let index in this.taskList){
				let task = this.taskList[index];
				if(task.playstatusReady==false){
					task.playstatusReady=true;
					this.$set(this.taskList, index, task);
				}
             }
         },
        false
    );
  },
  methods: {
    goTarget(href) {
      window.open(href, "_blank");
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.page = 1;
      this.getTaskList();
    },
    /** 查询tasklist */
    getTaskList() {
      this.loading = true;
      listTask(this.addDateRange(this.queryParams, this.dateRange)).then(response => {
          this.taskList = response.rows;
          for(let task of this.taskList){
          	task.playstatusReady = true;
          }
          this.total = response.total;
          this.loading = false;
          console.log(this.loading);
        }
      );
    },
    /** 处理任务显示详情 */
    handleTasklist(tasksetId){
        this.$router.push({
            path:'/display/task',
            query:{
                tasksetId: tasksetId,
            }
        });
    },
    /** 播放语音 */
    onPlay(rowdata, index){
    	this.queryParamsWav.taskId = rowdata.taskId;
    	achieveWav(this.queryParamsWav).then(response => {
            let blobwav = response.blob;
            this.phrase.load();  // 未证实加载是否有效 2019-01-31 09:43:25
            //this.phrase.src = url;
            this.phrase.src= "data:audio/wav;base64," + blobwav;
            for(let indexitem in this.taskList){
				let task = this.taskList[indexitem];
				if(index==indexitem){
					task.playstatusReady=false;
					this.$set(this.taskList, indexitem, task);
				}else{
					task.playstatusReady=true;
					this.$set(this.taskList, indexitem, task);
				}
             }
            this.$set(this.taskList, index, rowdata);
            console.log(this.taskList);
        });
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.taskids = selection.map(item => item.taskId);
      this.single = selection.length != 1;
      this.multiple = !selection.length;
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const taskIds = row.taskId || this.taskids;
      this.$confirm('是否确认删除编号为"' + taskIds + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delTask(taskIds);
        }).then(() => {
          this.getTaskList();
          this.msgSuccess("删除成功");
        })
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有用户数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportTask(queryParams);
        }).then(response => {
          this.download(response.msg);
        })
    },
  },
  
};
</script>