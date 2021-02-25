import request from '@/axios'

export function getNotice () {
  return request({
    url: '/notice',
    method: 'get'
  })
}
