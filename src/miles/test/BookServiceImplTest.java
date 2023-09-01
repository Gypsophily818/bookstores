package miles.test;

import miles.dao.impl.BookDaoImpl;
import miles.pojo.Book;
import miles.pojo.Page;
import miles.service.BookService;
import miles.service.impl.BookServiceImpl;
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
public class BookServiceImplTest {
    private BookService bookService = new BookServiceImpl();
    private Connection conn = JdbcUtils.getConnection();

    @Test
    public void addBook() {
        Book book = new Book(null, "仙仙拉屎记", "安安", new BigDecimal("9.9"), 100, 23, null);
        bookService.addBook(book);
    }

    @Test
    public void deleteBookById() {
        bookService.deleteBookById(25);
    }

    @Test
    public void updateBook() {
        Book book = new Book(26, "小仙拉屎记", "小仙", new BigDecimal("29"), 9, 9, null);
        bookService.updateBook(book);
    }

    @Test
    public void queryBookById() {
        Book book = bookService.queryBookById(8);
        System.out.println(book);
    }

    @Test
    public void queryBooks() {
        List<Book> books = bookService.queryBooks();
        books.forEach(System.out::println);
    }

    @Test
    public void queryPage() {
        Page<Book> page = bookService.page(1, 5);
        page.getItems().forEach(System.out::println);
    }

    @Test
    public void page() {
        Page<Book> page = bookService.page(2, 5);
        System.out.println(page);
    }
}