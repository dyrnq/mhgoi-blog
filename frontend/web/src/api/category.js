import request from '@/axios'

export function getCategories (params) {
  return request({
    url: '/category',
    method: 'get',
    params
  })
}
