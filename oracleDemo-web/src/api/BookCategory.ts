import { get, post, response } from '../utils/RequestUtils'
const urlPrefix = '/category'

 type bookListParams = {
  page: number
  num: number
}

export type bookCategory = {
  id: number
  name: string
  sort: number
  createTime: string
}

export function categoryList(): Promise<
  response<{ total: number; list: bookCategory[] }>
> {
  return get(urlPrefix + '/getCategorys')
}
export function update(data: bookCategory): Promise<response<string>> {
  return post(urlPrefix + '/updateCategory', data)
}
export function inster(data: bookCategory): Promise<response<string>> {
  return post(urlPrefix + '/insterCategory', data)
}
export function remove(data: { ids: number[] }): Promise<response<string>> {
  return post(urlPrefix + '/removeCategory', data.ids)
}
