import {apiURL, post, get, put, del, functionURL} from './util'

export async function getCart() {
  let res = await get(`${apiURL}/cart`),
    cartList = []
  res.forEach(item => {
    let book = item.book
    cartList.push({
      itemId: item.id,
      id: book.id,
      cover: book.cover,
      title: book.title,
      author: book.author,
      price: (book.price / 100).toFixed(2),
      number: item.number
    })
  })
  return cartList
}

export async function addIntoCart(bookId) {
  let res = await put(`${apiURL}/cart?bookId=${bookId}`)
  if (!res.ok) throw res.message
}

export async function setCartItemNumber(itemId, number) {
  let res = await put(`${apiURL}/cart/${itemId}?number=${number}`)
  if (!res.ok) throw res.message
}

export async function removeFromCart(itemId) {
  let res = await del(`${apiURL}/cart/${itemId}`)
  if (!res.ok) throw res.message
}

export async function calSingleTotalPrice(price, number) {
  let res = await fetch(`${functionURL}/calSingleTotalPrice`, {
    method: 'POST',
    body: `${price * 100}, ${number}`
  })
  let array = await res.json()
  return array[0] / 100;
}

export async function calAllTotalPrice(priceList) {
  let res = await fetch(`${functionURL}/calAllTotalPrice`, {
    method: 'POST',
    body: priceList.map(price => price * 100).join(',')
  })
  return res.json()[0] / 100
}
