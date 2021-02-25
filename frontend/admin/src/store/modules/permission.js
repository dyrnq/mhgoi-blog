import { constantRoutes } from '@/router'
import Layout from '@/layout'
import _import from '@/utils/_import'
import store from '../index'

function castMenuToRoute(menus) {
  const accessedRoutes = []
  menus.forEach(e => {
    doGenerateRoute(accessedRoutes, e)
  })
  if (store.getters.roles.indexOf('SUPER_ADMIN') > -1) {
    accessedRoutes.push({
      path: '/edit',
      component: Layout,
      children: [{
        path: 'article',
        name: 'ArticleEdit',
        component: () => import('@/views/article/article/edit'),
        meta: { title: '文章编辑', icon: 'dashboard' },
        hidden: true
      },{
        path: 'page',
        name: 'PageEdit',
        component: () => import('@/views/site/page/edit'),
        meta: { title: '页面编辑', icon: 'dashboard' },
        hidden: true
      }
      ],
      hidden: true
    })
  }
  accessedRoutes.push({ path: '*', redirect: '/404', hidden: true })
  return accessedRoutes
}

function doGenerateRoute(routeList, currentMenu) {
  let newRoute = { path: currentMenu.menuUrl }
  if (currentMenu.pid) {
    newRoute.component = _import(currentMenu.routeName)
    newRoute.name = currentMenu.routeName
  } else {
    newRoute.component = Layout
  }
  newRoute.meta = { icon: currentMenu.menuIcon, title: currentMenu.menuName }
  if (currentMenu.children) {
    newRoute.children = []
    currentMenu.children.forEach(e => doGenerateRoute(newRoute.children, e))
  }
  routeList.push(newRoute)
}

const state = {
  routes: [],
  addRoutes: []
}

const mutations = {
  SET_ROUTES: (state, routes) => {
    state.addRoutes = routes
    state.routes = constantRoutes.concat(routes)
  }
}

const actions = {
  generateRoutes({ commit }, menus) {
    return new Promise(resolve => {
      let accessedRoutes = castMenuToRoute(menus)
      commit('SET_ROUTES', accessedRoutes)
      resolve(accessedRoutes)
    })
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
