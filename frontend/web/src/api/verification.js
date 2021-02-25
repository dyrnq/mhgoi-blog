import request from '@/axios'

export function getEmailCheckCode (data) {
  return request({
    url: '/verification/email',
    method: 'post',
    data
  })
}

export function getMobileCheckCode (data) {
  return request({
    url: '/verification/mobile',
    method: 'post',
    data
  })
}
