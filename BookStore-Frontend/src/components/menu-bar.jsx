import { useNavigate } from 'react-router-dom'
import '../css/menu-bar.css'
import cartImg from '../images/cart.png'
import accountImg from '../images/account.png'

export default function MenuBar(props) {
  const menuList = [
    { text: "首页", url: "/home" },
    { text: "订单", url: "/order" },
    { text: "统计", url: "/stat" },
    { text: "星空", url: "/sky"}
  ]
  const navigate = useNavigate()
  let username = 'xupeigong'

  return (
    <div id='MenuBar'>
      <h1 id='MenuBar-title'>星空书店</h1>
      <ul id='MenuBar-menu'>
        {
          menuList.map((item, index) =>
            <li
              key={index}
              className={props.index === index ? 'MenuBar-menuList MenuBar-menuList-active' : 'MenuBar-menuList'}
              onClick={() => navigate(item.url)}
            >{item.text}</li>
          )
        }
        <li
          key={menuList.length}
          id='MenuBar-cart'
          className={props.index === menuList.length ? 'MenuBar-menuList MenuBar-menuList-active' : 'MenuBar-menuList'}
          onClick={() => navigate('/cart')}
        >
          <img id='MenuBar-cart-image' src={cartImg} alt='购物车' />
        </li>
        <li
          key={menuList.length + 1}
          id="MenuBar-account"
          className={props.index === menuList.length + 1 ? 'MenuBar-menuList MenuBar-menuList-active' : 'MenuBar-menuList'}
          onClick={() => navigate('/account')}
        >
          <img id='MenuBar-account-image' src={accountImg} alt='账户' />
          <span id="MenuBar-username">{username}</span>
        </li>
      </ul>

    </div>
  )
}