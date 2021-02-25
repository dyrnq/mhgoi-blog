import request from '@/utils/request'

export function getSocial() {
  return request({
    url: '/social',
    method: 'get'
  })
}

export function getSocialById(socialId) {
  return request({
    url: '/social/' + socialId,
    method: 'get'
  })
}

export function updateSocial(data) {
  return request({
    url: '/social',
    method: 'put',
    data
  })
}

export function insertSocial(data) {
  return request({
    url: '/social',
    method: 'post',
    data
  })
}

export function deleteSocial(data) {
  return request({
    url: '/social',
    method: 'delete',
    data
  })
}