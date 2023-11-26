import { get, post, response } from '../utils/RequestUtils'
const urlPrefix = '/book'

type bookListParams = {
  page: number
  num: number
  name: String
  sort: number
  sortType: number
}

export type bookInfo = {
  id: number
  bookname: string
  categoryName: string
  pic_url: string
  categoryId: number
  author: string
  bookDesc: string
  borrow: number
  borrowId: number
  createTime: string
  updatedTime: string
}

export function bookList(
  params: bookListParams
): Promise<response<{ total: number; books: bookInfo[] }>> {
  return get(urlPrefix + '/getBooks', params)
}
export function update(data: bookInfo): Promise<response<string>> {
  return post(urlPrefix + '/updateBook', data)
}
export function inster(data: bookInfo): Promise<response<string>> {
  return post(urlPrefix + '/insterBook', data)
}
export function remove(data: { ids: number[] }): Promise<response<string>> {
  return post(urlPrefix + '/removeBooks', data.ids)
}

export async function selectToLikeName(params: {
  bookName: string
}): Promise<response<bookInfo[]>> {
  return await get(urlPrefix + '/getBookInfos', params)
}
