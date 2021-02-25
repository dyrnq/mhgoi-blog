import request from '@/utils/request'

export function getNotice() {
    return request({
        url: '/notice',
        method: 'get'
    })
}

export function updateNotice(data) {
    return request({
      url: '/notice',
      method: 'put',
      data
    })
  }
  
  export function insertNotice(data) {
    return request({
      url: '/notice',
      method: 'post',
      data
    })
  }
  
  export function deleteNotice(data) {
    return request({
      url: '/notice',
      method: 'delete',
      data
    })
  }