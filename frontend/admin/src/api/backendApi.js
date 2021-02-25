import request from '@/utils/request'

export function queryPage(page, size, data) {
    return request({
        url: '/backendApi/' + page + '/' + size,
        method: 'post',
        data
    })
}

export function getApiById(apiId) {
    return request({
        url: '/backendApi/' + apiId,
        method: 'get'
    })
}

export function updateApi(data) {
    return request({
        url: '/backendApi',
        method: 'put',
        data
    })
}

export function insertApi(data) {
    return request({
        url: '/backendApi',
        method: 'post',
        data
    })
}

export function deleteApi(data) {
    return request({
        url: '/backendApi',
        method: 'delete',
        data
    })
}

export function getOwnedApi(params) {
    return request({
        url: '/backendApi/own',
        method: 'get',
        params
    })
}

export function getModuledApi() {
    return request({
        url: '/backendApi/module',
        method: 'get'
    })
}

export function getModules() {
    return request({
        url: '/backendApi/modules',
        method: 'get'
    })
}


export function grantApi(roleId, data) {
    return request({
        url: '/backendApi/grant/' + roleId,
        method: 'post',
        data
    })
}