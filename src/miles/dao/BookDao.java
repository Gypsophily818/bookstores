package miles.dao;

import miles.pojo.Book;

import java.sql.Connection;
import java.util.Collections;
import java.util.List;

/**
 * @author by Miles
 * @date 2023/8/24
 */
public interface BookDao {

    int addBook(Book book);

    int deleteBookById(Integer id);

    int updateBook(Book book);

    Book queryBookById(Integer id);

    List<Book> queryBooks();

    Integer queryForPageTotalCount();

    List<Book> queryForPageItems(Integer begin, Integer pageSize);

    Integer queryForPageTotalCountByPrice(int min, int max);

    List<Book> queryForPageItemsByPrice(int begin, int pageSize, int min, int max);
}
