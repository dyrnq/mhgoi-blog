import request from '@/utils/request'

export function login(data) {
  return request({
    url: '/login',
    method: 'post',
    data
  })
}

export function thirdLogin(providerId, params) {
  return request({
      url: '/auth/' + providerId,
      method: 'get',
      params
  })
}


export function getInfo(token) {
  return request({
    url: '/user/info',
    method: 'get',
    params: { token }
  })
}

export function queryPage(page, size, data) {
  return request({
    url: '/user/' + page +'/' + size,
    method: 'post',
    data
  })
}

export function getUserById(userId) {
  return request({
    url: '/user/' + userId,
    method: 'get'
  })
}

export function updateUser(data) {
  return request({
    url: '/user',
    method: 'put',
    data
  })
}

export function insertUser(data) {
  return request({
    url: '/user',
    method: 'post',
    data
  })
}

export function deleteUser(data) {
  return request({
    url: '/user',
    method: 'delete',
    data
  })
}