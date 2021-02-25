import axios from 'axios'

const request = axios.create()
request.defaults.timeout = 10000

request.interceptors.request.use(
  config => {
    return config
  },
  error => {
    return Promise.reject(error)
  }
)
// http response 拦截器
request.interceptors.response.use(
  res => {
    return res.data
  },
  error => {
    return Promise.reject(error)
  }
)
export default request
