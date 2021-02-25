import request from '@/utils/request'

export function getSiteInfo() {
  return request({
    url: '/siteInfo',
    method: 'get'
  })
}

export function getRuntime() {
  return request({
    url: '/siteInfo/runtime',
    method: 'get'
  })
}


export function getThread() {
  return request({
    url: '/siteInfo/thread',
    method: 'get'
  })
}

export function updateSiteInfo(data) {
  return request({
    url: '/siteInfo',
    method: 'put',
    data
  })
}
