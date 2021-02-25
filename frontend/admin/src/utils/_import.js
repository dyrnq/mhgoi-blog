export default name => {
    return map[name] || null
}

const map = {
    Menu: () => import('@/views/sys/menu'),
    User: () => import('@/views/sys/user'),
    Role: () => import('@/views/sys/role'),
    Api: () => import('@/views/sys/api'),
    SiteInfo: () => import('@/views/site/info'),
    FriendSite: () => import('@/views/site/friend'),
    Social: () => import('@/views/site/social'),
    Category: () => import('@/views/article/category'),
    Tag: () => import('@/views/article/tag'),
    Article: () => import('@/views/article/article'),
    Notice: () => import('@/views/site/notice'),
    Page: () => import('@/views/site/page'),
}