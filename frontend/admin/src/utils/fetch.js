import axios from 'axios'

const service = axios.create({
    timeout: 4000 // request timeout
})

service.interceptors.response.use(
    res => {
        return res.data;
    },
    error => {
        return Promise.reject(error);
    }
)
export default service;