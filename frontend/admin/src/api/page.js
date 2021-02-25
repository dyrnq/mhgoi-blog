import request from '@/utils/request'

export function queryPage(page, size, data) {
  return request({
    url: '/page/' + page +'/' + size,
    method: 'post',
    data
  })
}



export function getPageById(id) {
  return request({
    url: '/page/' + id,
    method: 'get'
  })
}

export function updatePage(data) {
  return request({
    url: '/page',
    method: 'put',
    data
  })
}

export function insertPage(data) {
  return request({
    url: '/page',
    method: 'post',
    data
  })
}

export function deletePage(data) {
  return request({
    url: '/page',
    method: 'delete',
    data
  })
}