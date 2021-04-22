import request from '@/utils/request'
import { praseStrEmpty } from "@/utils/ruoyi";

// 查询用户列表
export function listTask(query) {
  return request({
    url: '/asr/task/list',
    method: 'get',
    params: query
  })
}

/** achieveWav*/
export function achieveWav(query) {
  return request({
    url: '/asr/task/achieveWav',
    method: 'get',
    params: query
  })
}

// 删除task
export function delTask(taskId) {
  return request({
    url: '/asr/task/' + taskId,
    method: 'delete'
  })
}

// 导出用户
export function exportTask(query) {
  return request({
    url: '/asr/task/export',
    method: 'get',
    params: query
  })
}