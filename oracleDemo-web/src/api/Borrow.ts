import { get, post, response } from '../utils/RequestUtils'
const urlPrefix = '/student'

export type selectBorrow = {
  current: number
  pageSize: number
  bookName: string
  studentId: number
  categoryName: string
}

export type borrowInfo = {
  id: number
  bookId: number
  bookName: string
  categoryName: string
  student: {
    bookCard: number
    username: string
    classname: string
    studentId: number
    sex: number
  }
}

export type bookBorrowInfo = {
  id: number
  bookCard: number
  bookInfoId: number
}

export async function getBorrows(params: selectBorrow): Promise<
  response<{
    list: borrowInfo[]
    total: number
  }>
> {
  return await get(urlPrefix + '/getStudentBorrows', params)
}

export async function remove(data: number[]): Promise<response<string>> {
  return await post(urlPrefix + '/removeByIds', data)
}

export async function insterOrUpdate(
  data: bookBorrowInfo
): Promise<response<string>> {
  return await post(urlPrefix + '/insterOrUpdate', data)
}
export type studentInfo = {
  bookCard: number
  username: string
  classname: string
  studentId: number
  sex: string
}
export async function selectToLikeName(params: {
  studentId: string
  current?: number
  pageSize?: number
}): Promise<
  response<{
    total: number
    list: studentInfo[]
  }>
> {
  return await get(urlPrefix + '/getStudents', params)
}

export async function insterOrUpdateStudent(
  data: studentInfo
): Promise<response<string>> {
  return await post(urlPrefix + '/insterOrUpdateStudent', data)
}

export async function removeStudent(data: number[]): Promise<response<string>> {
  return await post(urlPrefix + '/removeStudent', data)
}
