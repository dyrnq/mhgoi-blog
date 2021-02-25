import request from '@/axios'

export function getTags (params) {
  return request({
    url: '/tag',
    method: 'get',
    params
  })
}
