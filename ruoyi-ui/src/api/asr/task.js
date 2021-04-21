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