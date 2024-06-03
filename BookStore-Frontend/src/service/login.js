import { apiURL, post } from './util';
import { setMode } from '../components/menu-bar';

export async function login(username, password) {
  let res = await post(`${apiURL}/login`, { username, password })
  if (res.ok) setMode(res.data.admin ? 'admin' : 'user')
  else throw res.message
}