import request from '@/utils/request'

export function queryPage(page, size, data) {
  return request({
    url: '/role/' + page + '/' + size,
    method: 'post',
    data
  })
}

export function getAllRole() {
  return request({
    url: '/role/',
    method: 'get'
  })
}

export function getOwnedRole(params) {
  return request({
    url: '/role/own',
    method: 'get',
    params
  })
}

export function grantRole(userId, data) {
  return request({
    url: '/role/grant/' + userId,
    method: 'post',
    data
  })
}

export function getRoleById(roleId) {
  return request({
    url: '/role/' + roleId,
    method: 'get'
  })
}

export function updateRole(data) {
  return request({
    url: '/role',
    method: 'put',
    data
  })
}

export function insertRole(data) {
  return request({
    url: '/role',
    method: 'post',
    data
  })
}

export function deleteRole(data) {
  return request({
    url: '/role',
    method: 'delete',
    data
  })
}