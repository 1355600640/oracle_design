import { Message } from '@arco-design/web-vue'
import axios from 'axios'
import router from '../router'
const instance = axios.create({
  withCredentials: false,
  timeout: 15000,
})

// 请求拦截器
instance.interceptors.request.use(
  (config) => {
    config.url = '/api' + config.url
    if (localStorage.getItem('oracle_token')) {
      config.headers.Authorization =
        'Bearer ' + localStorage.getItem('oracle_token')
    }
    return config
  },
  (error) => {
    return Promise.reject(error)
  }
)

// 响应拦截器
instance.interceptors.response.use(
  (response) => {
    if (response.status === 200) {
      if (response.data.status !== 200) {
        Message.clear()
        Message.error(response.data.message || '参数错误')
        if (response.data.status === 405) {
          localStorage.removeItem('oracle_token')
          // 跳转到登录页
          router.push('/login')
        }
      }
    }
    return response
  },
  (error) => {
    Message.clear()
    Message.error(error.data.message)
  }
)

export default instance
