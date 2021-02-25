import request from '@/utils/request'

export function queryPage(page, size, data) {
  return request({
    url: '/comment/' + page + '/' + size,
    method: 'post',
    data
  })
}

export function getCommetnsByArticleId(articleId) {
  return request({
    url: '/comment/article/' + articleId,
    method: 'get'
  })
}

export function deleteComment(articleId, data) {
  return request({
    url: '/comment/' + articleId,
    method: 'delete',
    data
  })
}