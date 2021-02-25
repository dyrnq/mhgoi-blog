import axios from 'axios'
import { MessageBox, Message } from 'element-ui'
import store from '@/store'
import { getToken } from '@/utils/auth'


const _axios = axios.create({
    timeout: 5000
})

export const uploadURL = process.env.VUE_APP_BASE_API+'/upload'
export const myURL = '/pic'

export async function uploadImg(file) {
    //let token = await store.dispatch('user/getUpToken')
    let config = {
        headers: { 
            'Content-Type': 'multipart/form-data',
            'Authorization': getToken(),
        }
    };
    let data = new FormData();
    // //data.append('token', token);
    data.append('file', file);    
    return new Promise(resolve => {
        _axios.post(uploadURL, data, config).then(res => {
            resolve(res.data);
        })
    })
}