export const apiURL = process.env.REACT_APP_MAIN_URL
export const wsURL = process.env.REACT_APP_WS_URL
export const gatewayURL = process.env.REACT_APP_GATEWAY_URL
export const functionURL = process.env.REACT_APP_FUNCTION_URL
export const enableFunctionApi = process.env.REACT_APP_ENABLE_FUNCTION_API === 'true'
export const graphQLURL = process.env.REACT_APP_GRAPHQL_URL

export async function get(url) {
  let res = await fetch(url, {
    method: 'GET',
    credentials: 'include'
  })
  if (!res.ok) throw res.status
  return res.json();
}

export async function post(url, data) {
  let res = await fetch(url, {
    method: 'POST',
    body: JSON.stringify(data),
    headers: {
      'Content-Type': 'application/json',
    },
    credentials: 'include'
  })
  return res.json();
}

export async function put(url, data) {
  let res = await fetch(url, {
    method: 'PUT',
    body: JSON.stringify(data),
    headers: {
      'Content-Type': 'application/json',
    },
    credentials: 'include'
  })
  return res.json();
}

export async function del(url, data) {
  let res = await fetch(url, {
    method: 'DELETE',
    body: JSON.stringify(data),
    headers: {
      'Content-Type': 'application/json',
    },
    credentials: 'include'
  })
  return res.json();
}

export function errorHandle(err, navigate) {
  if (err === 400 || err === 401) {
    alert('登录已失效，请重新登录！')
    navigate('/login')
  } else alert(err)
}