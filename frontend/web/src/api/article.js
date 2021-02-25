import request from '@/axios'

export function queryPage (page, size, data) {
  return request({
    url: '/article/' + page + '/' + size,
    method: 'post',
    data
  })
}

export function getArticles (params) {
  return request({
    url: '/article',
    method: 'get',
    params
  })
}

export function getArchives () {
  return request({
    url: '/article/archive',
    method: 'get'
  })
}

export function getArticleById (id) {
  return request({
    url: '/article/' + id,
    method: 'get'
  })
}

export function getArticleBySlug (slug) {
  return request({
    url: '/article/slug/' + slug,
    method: 'get'
  })
}

export function visitArticle (id) {
  return request({
    url: '/article/visit/' + id,
    method: 'put'
  })
}
