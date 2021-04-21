<template>
  <div class="app-container display">
    <el-row :gutter="20">
      <!--任务集数据-->
      <el-col :span="20" :xs="24">
        <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
          <el-form-item label="名称" prop="tasksetName" >
            <el-input
              v-model="queryParams.tasksetName"
              placeholder="请输入任务集名称"
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
          <el-form-item label="创建时间">
            <el-date-picker
              v-model="dateRange"
              size="small"
              style="width: 240px"
              value-format="yyyy-MM-dd"
              type="daterange"
              range-separator="-"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
            ></el-date-picker>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
          </el-form-item>
        </el-form>
        <el-table v-loading="loading" :data="tasksetList" @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="50" align="center" />
          <el-table-column label="任务集编号" align="center" key="tasksetId" prop="tasksetId" v-if="columns[0].visible" :show-overflow-tooltip="true" />
          <el-table-column label="任务集名称" align="center" key="tasksetName" prop="tasksetName" v-if="columns[1].visible" :show-overflow-tooltip="true" />
          <el-table-column label="用户名" align="center" key="createBy" prop="createBy" v-if="columns[2].visible" :show-overflow-tooltip="true" />
          <el-table-column label="创建时间" align="center" key="createTime" prop="createTime" v-if="columns[3].visible" />
          <el-table-column label="完成状态" align="center" key="status" v-if="columns[4].visible">
            <template slot-scope="scope">
              <el-switch
                v-model="scope.row.status"
                active-value="2"
                inactive-value="0"
                @change="handleStatusChange(scope.row)"
              ></el-switch>
            </template>
          </el-table-column>
          <el-table-column
            label="操作"
            align="center"
            width="160"
            class-name="small-padding fixed-width"
          >
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="text"
                icon="el-icon-show"
                @click="handleTasklist(scope.row.tasksetId)"
                v-hasPermi="['asr:task:list']"
              >详情</el-button>
            </template>
          </el-table-column>
        </el-table>

        <pagination
          v-show="total>0"
          :total="total"
          :page.sync="queryParams.pageNum"
          :limit.sync="queryParams.pageSize"
          @pagination="getTasksetList"
        />
      </el-col>  
    </el-row>
  </div>
</template>

<script>
import { listTaskset } from "@/api/asr/taskset";

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
      // 列信息
      columns: [
        { key: 0, label: `任务集编号`, visible: true },
        { key: 1, label: `任务集名称`, visible: true },
        { key: 2, label: `用户名`, visible: true },
        { key: 3, label: `创建时间`, visible: true },
        { key: 4, label: `状态`, visible: true },
      ],
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
        tasksetName: undefined,
        status: undefined,
      },
      // 日期范围
      dateRange: [],
      // taskset列表
      tasksetList: null,
    };
  },
  created() {
    this.getTasksetList();
  },
  methods: {
    goTarget(href) {
      window.open(href, "_blank");
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.dateRange = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.page = 1;
      this.getTasksetList();
    },
    /** 查询tasklist */
    getTasksetList() {
      this.loading = true;
      listTaskset(this.addDateRange(this.queryParams, this.dateRange)).then(response => {
          this.tasksetList = response.rows;
          this.total = response.total;
          this.loading = false;
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
    
  },
};
</script>