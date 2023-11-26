/**
 * 格式化data类型
 * @param dateCode 待转换参数
 * @param model all 显示全部时间
 * @returns
 */
export function stringToDate(
  dateCode: string | number,
  model: 'all' | 'date'
): string {
  if (!dateCode) return ''
  const date = new Date(dateCode)
  let dateString =
    date.getFullYear() +
    '-' +
    ((date.getMonth() + 1 >= 10 ? '' : '0') + (date.getMonth() + 1)) +
    '-' +
    ((date.getDay() >= 10 ? '' : '0') + date.getDay())
  if (model == 'all') {
    dateString +=
      ' ' +
      ((date.getHours() >= 10 ? '' : '0') + date.getHours()) +
      ':' +
      ((date.getMinutes() >= 10 ? '' : '0') + date.getMinutes()) +
      ':' +
      ((date.getSeconds() >= 10 ? '' : '0') + date.getSeconds())
  }
  return dateString
}

/**
 * 数字转换以万为单位，保留小鼠
 * @param number 待转换的数字
 * @param norm 保留位数
 * @returns
 */
export function normNumber(number: number, norm: number): string {
  if (norm > 4) norm = 0
  let str = ''
  str = parseInt(number / Math.pow(10, 4 - norm) + '') / 10 + ''
  return str + '万'
}
export function numberToCapital(str: string): string {
  if (!str) return str
  const capital = [
    '零',
    '一',
    '二',
    '三',
    '四',
    '五',
    '六',
    '七',
    '八',
    '九',
    '十',
    '百',
    '千',
    '万',
  ]
  const patter = /(?<=第)(?:\d*)/
  let number: string = patter.exec(str) ? (patter.exec(str) as any)[0] : ''
  if (number !== '') {
    let capitalNumber = ''
    for (let i = number.length - 1; i >= 0; i--) {
      let unit = ''
      if (i < number.length - 1) {
        unit = capital[10 + number.length - i - 2]
      }
      let num = parseInt(number.charAt(i))
      capitalNumber =
        (i == number.length - 2 && num == 1 ? '' : capital[num]) +
        unit +
        capitalNumber
    }

    str = str.replace('第' + number, '第' + capitalNumber)
    // console.log(str)
  } else if (str.indexOf('第') != 0 && str.includes('章')) {
    str = '第' + str
  }
  // console.log(str)
  return str
}

/**
 * 计算日期到现在的具体天数,向上取整
 * @param date
 */
export function dateToNum(date: string): number {
  const nowDate = Date.now()
  const lastDate = new Date(date).valueOf()
  let day = Math.ceil((nowDate - lastDate) / 60 / 60 / 60 / 24)
  return day
}
