import request from '@/axios'

export function getPageBySlug (slug) {
  return request({
    url: '/page/slug/' + slug,
    method: 'get'
  })
}
