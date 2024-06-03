package com.example.bookstore.service.impl;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.example.bookstore.entity.Book;
import com.example.bookstore.entity.Comment;
import com.example.bookstore.entity.User;
import com.example.bookstore.repository.BookRepository;
import com.example.bookstore.repository.CommentRepository;
import com.example.bookstore.repository.UserRepository;
import com.example.bookstore.service.BookService;
import com.example.bookstore.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {
  @Autowired private BookRepository bookRepository;
  @Autowired private CommentRepository commentRepository;
  @Autowired private UserRepository userRepository;

  @Override
  public JSONObject searchBooks(String keyword, int pageIndex, int pageSize) {
    JSONObject res = new JSONObject();
    Page<Book> bookPage =
        bookRepository.findByTitleContainsOrAuthorContainsOrDescriptionContainsOrIsbnContains(
            keyword, keyword, keyword, keyword, PageRequest.of(pageIndex, pageSize));
    res.put("totalNumber", bookPage.getTotalElements());
    res.put("totalPage", bookPage.getTotalPages());
    JSONArray items = new JSONArray();
    for (Book book : bookPage) items.add(book.toJson());
    res.put("items", items);
    return res;
  }

  @Override
  public JSONObject getBookInfo(long bookId) {
    Book book = bookRepository.findById(bookId).orElse(null);
    return book == null ? null : book.toJson();
  }

  @Override
  public JSONObject getBookComments(
      long bookId, int pageIndex, int pageSize, String sort, long userId) {
    JSONObject res = new JSONObject();
    Page<Comment> commentPage =
        commentRepository.findByBook_BookIdOrderByCreatedAtDesc(
            bookId, PageRequest.of(pageIndex, pageSize));
    res.put("totalNumber", commentPage.getTotalElements());
    res.put("totalPage", commentPage.getTotalPages());
    JSONArray items = new JSONArray();
    for (Comment comment : commentPage)
      items.add(comment.toJson(userRepository.findById(userId).get()));
    res.put("items", items);
    return res;
  }

  @Override
  public JSONObject postComment(long bookId, long userId, String content) {
    Book book = bookRepository.findById(bookId).orElse(null);
    User user = userRepository.findById(userId).orElse(null);
    if (book == null) return Util.errorResponseJson("书籍不存在");
    if (user == null) return Util.errorResponseJson("用户不存在");
    book.getComments().add(new Comment(user, book, content));
    bookRepository.save(book);
    return Util.successResponseJson("评论成功");
  }

  @Override
  public JSONObject setBookInfo(
      long bookId,
      String title,
      String author,
      String isbn,
      String cover,
      String description,
      int price,
      int sales,
      int repertory) {
    Book book = bookRepository.findById(bookId).orElse(null);
    if (book == null) return Util.errorResponseJson("书籍不存在");
    book.setTitle(title);
    book.setAuthor(author);
    book.setIsbn(isbn);
    book.setCover(cover);
    book.setDescription(description);
    book.setPrice(price);
    book.setSales(sales);
    book.setRepertory(repertory);
    bookRepository.save(book);
    return Util.successResponseJson("修改成功");
  }

  @Override
  public JSONObject addBook(
      String title,
      String author,
      String isbn,
      String cover,
      String description,
      int price,
      int sales,
      int repertory) {
    bookRepository.save(new Book(title, author, isbn, cover, description, price, sales, repertory));
    return Util.successResponseJson("添加成功");
  }

  @Override
  public JSONObject deleteBook(long bookId) {
    Book book = bookRepository.findById(bookId).orElse(null);
    if (book == null) return Util.errorResponseJson("书籍不存在");
    bookRepository.delete(book);
    return Util.successResponseJson("删除成功");
  }
}
