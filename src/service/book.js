import { apiURL, post, get } from "./util"

export async function searchBooks(keyword, pageIndex, pageSize) {
  let res = await get(`${apiURL}/books?keyword=${keyword}&pageIndex=${pageIndex}&pageSize=${pageSize}`),
    bookList = []
  res.items.forEach((item, index) => {
    bookList.push({
      id: item.id,
      title: item.title,
      author: item.author,
      price: (item.price / 100).toFixed(2),
      cover: item.cover,
      isbn: item.isbn,
      sales: item.sales,
      repertory: item.repertory,
      description: item.description,
      index: index
    })
  })
  return {
    totalNumber: res.totalNumber,
    totalPage: res.totalPage,
    list: bookList
  }
}

export async function getBooks(pageIndex, pageSize) {
  return await searchBooks('', pageIndex, pageSize)
}

export async function getBookInfo(id) {
  let res = await get(`${apiURL}/book/${id}`)
  return {
    id: res.id,
    title: res.title,
    author: res.author,
    price: (res.price / 100).toFixed(2),
    intro: res.description,
    cover: res.cover,
    isbn: res.isbn,
    sales: res.sales,
    repertory: res.repertory,
  }
}

export async function getBookComments(id, pageIndex, pageSize, sort) {
  let res = await get(`${apiURL}/book/${id}/comments?pageIndex=${pageIndex}&pageSize=${pageSize}&sort=${sort}`),
    commentList = []
  res.items.forEach(item => {
    let datetime = new Date(item.createdAt),
      y = datetime.getFullYear(),
      m = datetime.getMonth() + 1,
      d = datetime.getDate(),
      time = y + "-" + (m < 10 ? "0" + m : m) + "-" + (d < 10 ? "0" + d : d) + " " + datetime.toTimeString().substr(0, 8);
    commentList.push({
      id: item.id,
      username: item.username,
      text: item.content,
      time: time,
      like: item.like,
      liked: item.liked
    })
  })
  return {
    totalNumber: res.totalNumber,
    totalPage: res.totalPage,
    list: commentList
  }
}

export async function comment(id, content) {
  let res = await post(`${apiURL}/book/${id}/comments`, { content })
  if (!res.ok) throw res.message
}