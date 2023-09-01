package miles.service;

import miles.pojo.Book;
import miles.pojo.Page;

import java.sql.Connection;
import java.util.List;

/**
 * @author by Miles
 * @date 2023/8/24
 */
public interface BookService {
    void addBook(Book book);

    int deleteBookById(Integer id);

    int updateBook(Book book);

    Book queryBookById(Integer id);

    List<Book> queryBooks();

    Page<Book> page(Integer pageNo, Integer pageSize);

    Page<Book> pageByPrice(int pageNo, int pageSize, int min, int max);

}
