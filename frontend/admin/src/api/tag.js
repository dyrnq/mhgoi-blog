import request from '@/utils/request'

export function queryPage(page, size, data) {
  return request({
    url: '/tag/' + page +'/' + size,
    method: 'post',
    data
  })
}

export function getTag() {
  return request({
    url: '/tag',
    method: 'get'
  })
}

export function getTagById(id) {
  return request({
    url: '/tag/' + id,
    method: 'get'
  })
}

export function updateTag(data) {
  return request({
    url: '/tag',
    method: 'put',
    data
  })
}

export function insertTag(data) {
  return request({
    url: '/tag',
    method: 'post',
    data
  })
}

export function deleteTag(data) {
  return request({
    url: '/tag',
    method: 'delete',
    data
  })
}