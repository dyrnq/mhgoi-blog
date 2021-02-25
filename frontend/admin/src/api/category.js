import request from '@/utils/request'

export function getCategory() {
  return request({
    url: '/category',
    method: 'get'
  })
}

export function getCategoryById(id) {
  return request({
    url: '/category/' + id,
    method: 'get'
  })
}

export function updateCategory(data) {
  return request({
    url: '/category',
    method: 'put',
    data
  })
}

export function insertCategory(data) {
  return request({
    url: '/category',
    method: 'post',
    data
  })
}

export function deleteCategory(data) {
  return request({
    url: '/category',
    method: 'delete',
    data
  })
}