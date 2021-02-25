import request from '@/axios'

export function getSiteFriend () {
  return request({
    url: '/friend',
    method: 'get'
  })
}
