import { useRef, useState, useEffect } from 'react'
import { useNavigate } from 'react-router-dom'
import { Checkbox } from "antd"
import MenuBar from '../components/menu-bar'
import { mode } from '../App'
import '../css/manage.css'

let type = 'book'

export default function ManagePage() {
  const bookManage = useRef(null),
    userManage = useRef(null),
    orderManage = useRef(null),
    [placeholder, setPlaceholder] = useState('请输入书籍名、作者名等关键词搜索相关书籍'),
    [currPage, setCurrPage] = useState(1),
    [totalPage, setTotalPage] = useState(0),
    [totalCount, setTotalCount] = useState(0),
    [listName, setListName] = useState('站内书籍'),
    [showBookModal, setShowBookModal] = useState(false),
    [showUserModal, setShowUserModal] = useState(false),
    bookList = useRef(null),
    userList = useRef(null),
    orderList = useRef(null),
    onManageBook = () => {
      bookManage.current.className = 'ManageType-button ManageType-button-select'
      userManage.current.className = 'ManageType-button'
      orderManage.current.className = 'ManageType-button'
      setPlaceholder('请输入书籍名、作者名等关键词搜索相关书籍')
      type = 'book'
      setListName('站内书籍')

      setBookList([{
        cover: '',
        title: '深入理解计算机系统',
        author: 'Randal E. Bryant / David R. O\'Hallaron',
        price: 100,
        isbn: '9787115410780',
        sale: 100,
        repertory: 100
      }, {
        cover: '',
        title: '深入理解计算机系统',
        author: 'Randal E. Bryant / David R. O\'Hallaron',
        price: 100,
        isbn: '9787115410780',
        sale: 100,
        repertory: 100
      }])
    },
    onManageUser = () => {
      bookManage.current.className = 'ManageType-button'
      userManage.current.className = 'ManageType-button ManageType-button-select'
      orderManage.current.className = 'ManageType-button'
      setPlaceholder('请输入用户名关键词搜索相关用户')
      type = 'user'
      setListName('站内用户')

      setUserList([{
        username: 'u1',
        email: 'u1@bookstore.com',
        password: '123',
        silence: false
      }, {
        username: 'u2',
        email: 'u2@bookstore.com',
        password: '123',
        silence: true
      }])
    },
    onManageOrder = () => {
      bookManage.current.className = 'ManageType-button'
      userManage.current.className = 'ManageType-button'
      orderManage.current.className = 'ManageType-button ManageType-button-select'
      setPlaceholder('请输入订单号、用户名、下单时间、书籍信息等关键词搜索相关订单')
      type = 'order'
      setListName('站内订单')

      setOrderList([{
        cover: '',
        title: '深入理解计算机系统',
        author: 'Randal E. Bryant / David R. O\'Hallaron',
        price: 100,
        receiver: '徐培公',
        tel: '15043253467',
        address: '上海市闵行区江川路街道东川路800号上海交通大学学生公寓东27号楼311',
        user: 'u1',
        time: '2024-12-21 12:00:00',
        number: 1
      }])
    },
    onSearch = () => {
    },
    onNextPage = () => {
    },
    onLastPage = () => {
    },
    setBookList = (list = [{ cover: '', title: '-', author: '-', price: '', isbn: '', sale: 0, repertory: 0 }]) => {
      bookList.current.style.display = 'flex'
      userList.current.style.display = 'none'
      orderList.current.style.display = 'none'
      let lis = bookList.current.children
      for (let li of lis) li.style.visibility = 'hidden'
      list.forEach((item, index) => {
        if (index < lis.length) {
          let li = lis[index],
            info = li.children[0].children,
            info1 = info[1].children,
            info2 = info[2].children
          li.style.visibility = 'visible'
          info[0].src = item.cover
          info1[0].innerHTML = item.title
          info1[1].innerHTML = item.author
          info1[2].innerHTML = '¥' + item.price
          info2[0].innerHTML = 'ISBN ' + item.isbn
          info2[1].innerHTML = '销量' + item.sale + '件'
          info2[2].innerHTML = '库存' + item.repertory + '件'
        }
      })
    },
    setUserList = (list = [{ username: '-', email: '-', password: '-', silence: false }]) => {
      bookList.current.style.display = 'none'
      userList.current.style.display = 'flex'
      orderList.current.style.display = 'none'
      let lis = userList.current.children
      for (let li of lis) li.style.visibility = 'hidden'
      list.forEach((item, index) => {
        if (index < lis.length) {
          let li = lis[index],
            info = li.children[0].children
          li.style.visibility = 'visible'
          info[0].innerHTML = item.username
          info[1].innerHTML = item.email
          info[2].innerHTML = '密码: ' + item.password
          info[4].innerHTML = item.silence ? '解禁' : '禁用'
          info[4].color = item.silence ? 'green' : 'red'
        }
      })
    },
    setOrderList = (list = [{ cover: '-', title: '-', author: '-', price: '', receiver: '-', tel: '', address: '-', user: '', time: '-', number: '-' }]) => {
      bookList.current.style.display = 'none'
      userList.current.style.display = 'none'
      orderList.current.style.display = 'flex'
      let lis = orderList.current.children
      for (let li of lis) li.style.visibility = 'hidden'
      list.forEach((item, index) => {
        if (index < lis.length) {
          let li = lis[index],
            info = li.children[0].children,
            info1 = info[1].children,
            info2 = info[2].children,
            info3 = info[3].children
          li.style.visibility = 'visible'
          info[0].src = item.cover
          info1[0].innerHTML = item.title
          info1[1].innerHTML = item.author
          info1[2].innerHTML = '¥' + item.price
          info2[0].innerHTML = item.receiver
          info2[1].innerHTML = '☏' + item.tel
          info2[2].innerHTML = item.address
          info3[0].innerHTML = '@' + item.user
          info3[1].innerHTML = item.time
          info3[2].innerHTML = '共' + item.number + '件'
          info3[3].innerHTML = '¥' + item.price * item.number
        }
      })

    }

  useEffect(() => {
    setBookList([{
      cover: '',
      title: '深入理解计算机系统',
      author: 'Randal E. Bryant / David R. O\'Hallaron',
      price: 100,
      isbn: '9787115410780',
      sale: 100,
      repertory: 100
    }, {
      cover: '',
      title: '深入理解计算机系统',
      author: 'Randal E. Bryant / David R. O\'Hallaron',
      price: 100,
      isbn: '9787115410780',
      sale: 100,
      repertory: 100
    }])
  }, [])

  return (
    <div>
      <MenuBar index={4} mode={mode} />
      <div id='Manage'>
        <div id='ManageType'>
          <button
            ref={bookManage}
            className='ManageType-button ManageType-button-select'
            onClick={onManageBook}>书籍管理</button>
          <button ref={userManage} className='ManageType-button' onClick={onManageUser}>用户管理</button>
          <button ref={orderManage} className='ManageType-button' onClick={onManageOrder}>订单管理</button>
        </div>
        <div id='ManageDisplay'>
          <div id='Manage-SearchBar'>
            <input id='Manage-SearchBar-input' type="text" placeholder={placeholder} />
            <button id='Manage-SearchBar-submit' onClick={onSearch}></button>
          </div>
          <h1 id='ManageList-title'>{listName}，共{totalCount}条</h1>
          <div className='Manage-line' />
          <ul ref={bookList} id='Manage-ul'>
            {
              [1, 2, 3, 4, 5, 6, 7, 8, 9, 10].map((item, index) =>
                <li key={index} className='Manage-li' onClick={() => { }}>
                  <div className='ManageBook-bookInfo'>
                    <img className='ManageBook-cover' src='' alt='book-image' />
                    <div className='ManageBook-li-flex1'>
                      <span className='ManageBook-title'>-</span>
                      <span className='ManageBook-author'>-</span>
                      <div className='ManageBook-price'>¥</div>
                    </div>
                    <div className='ManageBook-li-flex2'>
                      <span className='ManageBook-isbn'>ISBN </span>
                      <span className='ManageBook-sale'>销量 件</span>
                      <div className='ManageBook-repertory'>库存 件</div>
                    </div>
                    <button
                      className='ManageBook-setting'
                      onClick={
                        e => {
                          e.stopPropagation()
                          setShowBookModal(true)
                        }
                      }
                    >修改信息</button>
                    <button
                      className='ManageBook-remove'
                      onClick={
                        e => {
                          e.stopPropagation()
                        }
                      }
                    >删除书籍</button>
                    <Checkbox onClick={e => e.stopPropagation()} />
                  </div>
                  <div className='Manage-line' />
                </li>
              )
            }
          </ul>
          <ul ref={userList} id='Manage-ul'>
            {
              [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 18, 18, 19, 20].map((item, index) =>
                <li key={index} className='Manage-li' onClick={() => { }}>
                  <div className='ManageUser-userInfo'>
                    <span className='ManageUser-username'>-</span>
                    <span className='ManageUser-email'>-</span>
                    <span className='ManageUser-password'>密码: </span>
                    <button
                      className='ManageUser-setting'
                      onClick={
                        e => {
                          e.stopPropagation()
                          setShowUserModal(true)
                        }
                      }
                    >修改信息</button>
                    <button
                      className='ManageUser-slience'
                      onClick={
                        e => {
                          e.stopPropagation()
                        }
                      }
                    >禁用</button>
                    <button
                      className='ManageUser-remove'
                      onClick={
                        e => {
                          e.stopPropagation()
                        }
                      }
                    >注销</button>
                    <Checkbox onClick={e => e.stopPropagation()} />
                  </div>
                  <div className='Manage-line' />
                </li>
              )
            }
          </ul>
          <ul ref={orderList} id='Manage-ul'>
            {
              [1, 2, 3, 4, 5, 6, 7, 8, 9, 10].map((item, index) =>
                <li key={index} className='Manage-li' onClick={() => { }}>
                  <div className='ManageOrder-info'>
                    <img className='ManageOrder-cover' src='' alt='book-image' />
                    <div className='ManageOrder-li-flex1'>
                      <span className='ManageOrder-title'>-深入理解计算机系统</span>
                      <span className='ManageOrder-author'>-Randal E. Bryant / David R. O\'Hallaron</span>
                      <div className='ManageOrder-price'>¥100.00</div>
                    </div>
                    <div className='ManageOrder-li-flex2'>
                      <div className='ManageOrder-receiver'>-徐培公</div>
                      <div className='ManageOrder-tel'>☏15043253467</div>
                      <div className='ManageOrder-address'>-上海市闵行区江川路街道东川路800号上海交通大学学生公寓东27号楼311</div>
                    </div>
                    <div className='ManageOrder-li-flex3'>
                      <div className='ManageOrder-user'>@u1</div>
                      <div className='ManageOrder-datetime'>2024-12-21 12:00:00</div>
                      <div className='ManageOrder-number'>共 件</div>
                      <div className='ManageOrder-total-price'>¥</div>
                    </div>
                    <button
                      className='ManageOrder-remove'
                      onClick={
                        e => {
                          e.stopPropagation()
                        }
                      }
                    >删除记录</button>
                    <Checkbox onClick={e => e.stopPropagation()} />
                  </div>
                  <div className='Order-line' />
                </li>
              )
            }
          </ul>
          <div id='Manage-pageShift-select'>
            <Checkbox><font color='white' fontSize='15px'>全部全选</font></Checkbox>
            <div id='Manage-pageShift'>
              <button className='Manage-pageShift-button' onClick={onLastPage}>上一页</button>
              <span id='Manage-pageShift-text'>第 {currPage} 页 / 共 {totalPage} 页</span>
              <button className='Manage-pageShift-button' onClick={onNextPage}>下一页</button>
            </div>
            <Checkbox> <font color='white' fontSize='15px'>本页全选</font></Checkbox>
          </div>
        </div>
      </div>
      {
        showBookModal &&
        <div className='Manage-modal' onClick={() => setShowBookModal(false)}>
          <div id='ManageBook-modal-content' onClick={e => e.stopPropagation()}>
            <div id='ManageBook-modal-flex1'>
              <image id='ManageBook-modal-cover' src='' alt='book-cover' />
              <div id='ManageBook-modal-flex2'>
                <div className='Manage-modal-label-input'>
                  <div className='Manage-modal-label'>书名</div>
                  <input className='Manage-modal-input' />
                </div>
                <div className='Manage-modal-label-input'>
                  <div className='Manage-modal-label'>作者</div>
                  <input className='Manage-modal-input' />
                </div>
                <div className='Manage-modal-label-input'>
                  <div className='Manage-modal-label'>ISBN</div>
                  <input className='Manage-modal-input' />
                </div>
                <div className='Manage-modal-label-input'>
                  <div className='Manage-modal-label'>价格</div>
                  <input className='Manage-modal-input' />
                </div>
                <div className='Manage-modal-label-input'>
                  <div className='Manage-modal-label'>销量</div>
                  <input className='Manage-modal-input' />
                </div>
                <div className='Manage-modal-label-input'>
                  <div className='Manage-modal-label'>库存</div>
                  <input className='Manage-modal-input' />
                </div>
              </div>
            </div>
            <textarea id='ManageBook-modal-intro' placeholder='书籍简介' />
            <div className='Manage-modal-operation'>
              <button className='Manage-modal-submit'>修改</button>
              <button className='Manage-modal-cancle' onClick={() => setShowBookModal(false)}>取消</button>
            </div>
          </div>
        </div>
      }
      {
        showUserModal &&
        <div className='Manage-modal' onClick={() => setShowUserModal(false)}>
          <div id='ManageUser-modal-content' onClick={e => e.stopPropagation()}>
            <div id='ManageUser-modal-flex'>
              <div className='Manage-modal-label-input'>
                <div className='Manage-modal-label'>用户名</div>
                <input className='Manage-modal-input' />
              </div>
              <div className='Manage-modal-label-input'>
                <div className='Manage-modal-label'>邮箱</div>
                <input className='Manage-modal-input' />
              </div>
              <div className='Manage-modal-label-input'>
                <div className='Manage-modal-label'>密码</div>
                <input className='Manage-modal-input' />
              </div>
            </div>
            <div className='Manage-modal-operation'>
              <button className='Manage-modal-submit'>修改</button>
              <button className='Manage-modal-cancle' onClick={() => setShowUserModal(false)}>取消</button>
            </div>
          </div>
        </div>
      }
    </div>
  )
}