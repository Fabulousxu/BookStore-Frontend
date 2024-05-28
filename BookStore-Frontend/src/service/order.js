import { apiURL, post, get } from './util';

export async function getOrder() {
  let res = await get(`${apiURL}/order`),
    orderList = []
  res.forEach(items => {
    items.items.forEach(item => {
      let datetime = new Date(items.createdAt),
        y = datetime.getFullYear(),
        m = datetime.getMonth() + 1,
        d = datetime.getDate(),
        time = y + "-" + (m < 10 ? "0" + m : m) + "-" + (d < 10 ? "0" + d : d) + " " + datetime.toTimeString().substr(0, 8);
      orderList.push({
        id: item.book.id,
        cover: item.book.cover,
        title: item.book.title,
        author: item.book.author,
        price: item.book.price,
        receiver: items.receiver,
        tel: items.tel,
        address: items.address,
        time: time,
        number: item.number
      })
    })
  })
  return orderList
}

export async function placeOrder(receiver, tel, address, itemIds) {
  let res = await post(`${apiURL}/order`, { receiver, tel, address, itemIds })
  if (res.ok) return
  else throw res.message
}