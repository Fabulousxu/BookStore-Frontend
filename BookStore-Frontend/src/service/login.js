import { apiURL, post } from './util';

export async function login(username, password) {
  let res = await post(`${apiURL}/login`, { username, password })
  if (!res.ok) throw res.message  
}