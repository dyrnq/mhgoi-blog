import Vue from 'vue'
import VueRouter from 'vue-router'
//import { validateToken } from '@/utils/auth'
import store from '../store'

Vue.use(VueRouter)

const originalPush = VueRouter.prototype.push
VueRouter.prototype.push = function push (location) {
  return originalPush.call(this, location).catch(err => err)
}

const routes = [
  // {
  //   path: '/auth-redirect',
  //   component: () => import('@/views/Login/auth-redirect'),
  //   hidden: true
  // },
  {
    path: '/',
    name: 'index',
    component: () => import('@/views/index'),
    children: [
      {
        path: 'home',
        name: 'home',
        component: () => import('@/views/Home'),
        alias: '/',
        meta: {
          keepAlive: true,
          stopScroll: true,
          title: '测试'
        }
      },
      {
        path: 'category/:slug',
        name: 'categoryBySlug',
        component: () => import('@/views/Home'),
        meta: { title: '类别' }
      },
      {
        path: '/article/:id',
        name: 'article',
        component: () => import('@/views/Article'),
        meta: { title: '文章' }
      },
      {
        path: '/post/:slug',
        name: 'articleBySlug',
        component: () => import('@/views/Article'),
        meta: { title: '文章' }
      },
      {
        path: '/about',
        name: 'about',
        component: () => import('@/views/About'),
        meta: { title: '关于' }
      },
      {
        path: '/friend',
        name: 'friend',
        component: () => import('@/views/Friend'),
        meta: { title: '友站' }
      },
      {
        path: '/archives',
        name: 'archives',
        component: () => import('@/views/Archives'),
        meta: { title: '归档', keepAlive: true }
      },
      {
        path: '/tags/',
        name: 'tags',
        component: () => import('@/views/Tags'),
        meta: { title: '标签', keepAlive: true }
      },
      {
        path: '/tags/:id',
        name: 'tagsByTagName',
        component: () => import('@/views/Tags/tagName'),
        meta: { title: '标签', keepAlive: true }
      }
      // {
      //   path: '/file',
      //   name: 'file',
      //   component: () => import('@/views/File'),
      //   meta: { title: '文件', keepAlive: true }
      // },
      // {
      //   path: '/user/info',
      //   name: 'userinfo',
      //   component: () => import('@/views/Personal'),
      //   meta: { title: '个人中心', keepAlive: true }
      // },
    ],
    meta: { title: '首页' }
  },
  // {
  //   path: '/register',
  //   name: 'register',
  //   component: () => import('@/views/Register'),
  //   meta: { title: '注册页' }
  // },
  // {
  //   path: '/login',
  //   name: 'login',
  //   component: () => import('@/views/Login/index'),
  //   meta: { title: '登录页' }
  // },
  {
    path: '*',
    name: '404',
    component: () => import('@/views/404')
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes,
  scrollBehavior (to, from, savedPosition) {
    if (savedPosition && to.meta.keepAlive) {
      return savedPosition
    }
    if (!to.meta.stopScroll) return { x: 0, y: 0 }
  }
})
// 路由守卫
router.beforeEach(async (to, from, next) => {
  store.dispatch('setLoading', true)
  // const hasToken = await validateToken()
  // if (hasToken) {
  //   if (to.path === '/login') {
  //     next('/')
  //   } else {
  //     if (store.state.isLogin) {
  //       next()
  //     } else {
  //       await store.dispatch('getUserInfo')
  //       next()
  //     }
  //   }
  // } else {
  //   if (to.meta.requireLogin) {
  //     next('/login')
  //   } else {
  //     next()
  //   }
  // }
  next()
  // console.log(to)
})

router.afterEach(() => {
  store.dispatch('setLoading', false)
})
export default router
