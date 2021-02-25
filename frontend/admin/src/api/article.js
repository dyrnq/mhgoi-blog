import request from '@/utils/request'

export function queryPage(page, size, data) {
  return request({
    url: '/article/' + page +'/' + size,
    method: 'post',
    data
  })
}

export function getArticle() {
  return request({
    url: '/article',
    method: 'get'
  })
}

export function getArticleById(id) {
  return request({
    url: '/article/' + id,
    method: 'get'
  })
}

export function updateArticle(data) {
  return request({
    url: '/article',
    method: 'put',
    data
  })
}

export function insertArticle(data) {
  return request({
    url: '/article',
    method: 'post',
    data
  })
}

export function deleteArticle(data) {
  return request({
    url: '/article',
    method: 'delete',
    data
  })
}