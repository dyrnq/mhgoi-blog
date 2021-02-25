import request from '@/utils/request'

//获取菜单
export function getMenus() {
    return request({
        url: '/menu',
        method: 'get'
    })
}
//添加菜单
export function insertMenu(data) {
    return request({
        url: '/menu',
        method: 'post',
        data
    })
}
//修改菜单
export function updateMenu(data) {
    return request({
        url: '/menu',
        method: 'put',
        data
    })
}
//删除菜单
export function deleteMenu(data) {
    return request({
        url: '/menu',
        method: 'delete',
        data
    })
}

export function getOwnedMenu(params) {
    return request({
        url: '/menu/own',
        method: 'get',
        params
    })
}

export function grantMenu(roleId, data) {
    return request({
        url: '/menu/grant/' + roleId,
        method: 'post',
        data
    })
}
