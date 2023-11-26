import { get, post, response } from '../utils/RequestUtils'
const urlPrefix = '/user'

export async function login(username: string, password: string):Promise<response<any>> {
  return await post(urlPrefix + '/login', { username, password })
}

export async function userSession(): Promise<response<any>> {
  return await get(urlPrefix + '/getUser')
}
