import axios from 'axios'
import store from './store'
import router from './router'
import { getToken, parseToken, removeToken, isTokenExpired } from '@/utils/auth'
import { Message } from 'element-ui'
import { showLoading, tryHideLoading } from '@/utils/loading'

const defaultAxios = axios.create()
// axios 配置
defaultAxios.defaults.timeout = 4000// 超时时间单位毫秒
defaultAxios.defaults.headers.post['Content-Type'] = 'application/json'

defaultAxios.interceptors.request.use(
  async config => {
    config.baseURL = '/api'
    config.withCredentials = true // 允许携带token ,这个是解决跨域产生的相关问题
    config.timeout = 4000
    let token = getToken()
    if (token) {
      if (parseToken(token) && isTokenExpired(token)) {
        token = await store.dispatch('refreshToken')
      }
      config.headers = {
        Authorization: token,
        'Content-Type': 'application/json'
      }
    }
    showLoading()
    return config
  },
  error => {
    return Promise.reject(error)
  }
)

// http response 拦截器
defaultAxios.interceptors.response.use(
  res => {
    switch (res.data.code) {
      case 20007: // 令牌过期
        removeToken()
        router.replace({
          path: '/login'
        })
        break
      case 20009: // 用户失效
        removeToken()
        Message.warning('账号已冻结')
        router.push({
          path: '/login'
        })
        break
      case 20003: // 权限不足
        Message.warning('权限不足')
        break
      case 20013: // 用户信息变更
        Message.warning('用户信息变更')
        removeToken()
        router.push({
          path: '/login'
        })
        break
    }
    tryHideLoading()
    return res.data
  },
  error => {
    return Promise.reject(error)
  }
)

export default defaultAxios
