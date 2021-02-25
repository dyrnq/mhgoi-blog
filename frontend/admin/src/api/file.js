import request from '@/utils/request'

export function getUpToken() {
    return request({
        url: '/file/token',
        method: 'get'
    })
}
