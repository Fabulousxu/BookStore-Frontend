DROP DATABASE IF EXISTS bookstore;
CREATE DATABASE bookstore;
USE bookstore;

# 账户密码表
CREATE TABLE account
(
    user_id  BIGINT PRIMARY KEY AUTO_INCREMENT, # 用户id
    nickname VARCHAR(16) NOT NULL,              # 用户名
    password VARCHAR(16) NOT NULL               # 密码
);

# 用户信息表
CREATE TABLE user
(
    user_id BIGINT PRIMARY KEY AUTO_INCREMENT, # 用户id
    balance BIGINT DEFAULT 0,                  # 余额
    FOREIGN KEY (user_id) REFERENCES account (user_id) ON DELETE CASCADE ON UPDATE CASCADE
);

# 书籍信息表
CREATE TABLE book
(
    book_id     BIGINT PRIMARY KEY AUTO_INCREMENT, # 书籍id
    title       TEXT NOT NULL,                     # 书名
    author      TEXT NOT NULL,                     # 作者
    description TEXT NOT NULL,                     # 描述
    price       INT  NOT NULL,                     # 价格
    cover       TEXT NOT NULL,                     # 封面URL
    sales       INT DEFAULT 0                      # 销量
);

# 订单表
CREATE TABLE `order`
(
    order_id   BIGINT PRIMARY KEY AUTO_INCREMENT,   # 订单id
    user_id    BIGINT      NOT NULL,                # 用户id
    receiver   VARCHAR(32) NOT NULL,                # 收件人
    address    TEXT        NOT NULL,                # 地址
    tel        VARCHAR(16) NOT NULL,                # 电话
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, # 创建时间
    FOREIGN KEY (user_id) REFERENCES account (user_id) ON DELETE CASCADE ON UPDATE CASCADE
);

# 订单项目表
CREATE TABLE order_item
(
    order_item_id BIGINT PRIMARY KEY AUTO_INCREMENT, # 订单项目id
    order_id      BIGINT NOT NULL,                   # 订单id
    book_id       BIGINT NOT NULL,                   # 书籍id
    number        INT DEFAULT 1,                     # 数量
    FOREIGN KEY (order_id) REFERENCES `order` (order_id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (book_id) REFERENCES book (book_id) ON DELETE CASCADE ON UPDATE CASCADE
);

# 购物车表
CREATE TABLE cart_item
(
    cart_item_id BIGINT PRIMARY KEY AUTO_INCREMENT, # 购物车项目id
    user_id      BIGINT NOT NULL,                   # 用户id
    book_id      BIGINT NOT NULL,                   # 书籍id
    number       INT DEFAULT 1,                     # 数量
    FOREIGN KEY (user_id) REFERENCES account (user_id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (book_id) REFERENCES book (book_id) ON DELETE CASCADE ON UPDATE CASCADE
);

# 评论表
CREATE TABLE comment
(
    comment_id BIGINT PRIMARY KEY AUTO_INCREMENT,   # 评论id
    user_id    BIGINT NOT NULL,                     # 用户id
    book_id    BIGINT NOT NULL,                     # 书籍id
    content    TEXT   NOT NULL,                     # 内容
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, # 创建时间
    `like`     INT       DEFAULT 0,                 # 点赞数
    FOREIGN KEY (user_id) REFERENCES account (user_id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (book_id) REFERENCES book (book_id) ON DELETE CASCADE ON UPDATE CASCADE
);

# 评论点赞表
CREATE TABLE comment_like
(
    comment_id BIGINT NOT NULL, # 评论id
    user_id    BIGINT NOT NULL, # 用户id
    PRIMARY KEY (comment_id, user_id),
    FOREIGN KEY (comment_id) REFERENCES comment (comment_id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (user_id) REFERENCES account (user_id) ON DELETE CASCADE ON UPDATE CASCADE
);