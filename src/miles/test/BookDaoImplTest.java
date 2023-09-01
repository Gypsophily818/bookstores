package miles.test;

import miles.dao.impl.BookDaoImpl;
import miles.pojo.Book;
import miles.utils.JdbcUtils;
import org.junit.Test;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author by Miles
 * @date 2023/8/24
 */
public class BookDaoImplTest {
    private BookDaoImpl bookDao = new BookDaoImpl();
    private Connection conn = JdbcUtils.getConnection();

    @Test
    public void addBook() {
        Book book = new Book(null, "始小仙拉屎记", "始小仙", new BigDecimal("99.99"), 999, 8, null);
        bookDao.addBook(book);
    }

    @Test
    public void deleteBookById() {
        bookDao.deleteBookById(23);
    }

    @Test
    public void updateBook() {
        Book book = new Book(22, "始仙拉屎记", "始仙", new BigDecimal("99.99"), 99, 2, null);
        bookDao.updateBook(book);
    }

    @Test
    public void queryBookById() {
        Book book = bookDao.queryBookById(12);
        System.out.println(book);
    }

    @Test
    public void queryBooks() {
        List<Book> books = bookDao.queryBooks();
        books.forEach(System.out::println);
    }

    @Test
    public void queryForGetPageTotalCount() {
        int count = bookDao.queryForPageTotalCount();
        System.out.println(count);
    }

    @Test
    public void queryForPageLimit() {
        List<Book> books = bookDao.queryForPageItems(5, 5);
        books.forEach(System.out::println);
    }

    @Test
    public void queryForPageTotalCountByPrice() {
        Integer integer = bookDao.queryForPageTotalCountByPrice(29, 100);
        System.out.println(integer);
    }

    @Test
    public void queryForPageItemsByPrice() {
        List<Book> books = bookDao.queryForPageItemsByPrice(1, 4, 29, 100);
        books.forEach(System.out::println);
    }
}