import request from '@/axios'

export function login (data) {
  return request({
    url: '/login',
    method: 'post',
    data
  })
}



export function getUserInfo (token) {
  return request({
    url: '/user/info',
    method: 'get',
    params: { token }
  })
}


