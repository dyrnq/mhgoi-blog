import request from '@/utils/request'

export function getSiteFriend() {
  return request({
    url: '/friend',
    method: 'get'
  })
}

export function updateSiteFriend(data) {
  return request({
    url: '/friend',
    method: 'put',
    data
  })
}

export function insertSiteFriend(data) {
  return request({
    url: '/friend',
    method: 'post',
    data
  })
}

export function deleteSiteFriend(data) {
  return request({
    url: '/friend',
    method: 'delete',
    data
  })
}