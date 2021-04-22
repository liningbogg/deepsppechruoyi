import request from '@/utils/request'
import { praseStrEmpty } from "@/utils/ruoyi";

// 查询用户列表
export function listTaskset(query) {
  return request({
    url: '/asr/taskset/list',
    method: 'get',
    params: query
  })
}

// 下载转写结果
export function tasksetResultDownload(query) {
  return request({
    url: '/asr/taskset/download',
    method: 'get',
    params: query
  })
}