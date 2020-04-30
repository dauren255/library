package kz.iitu.library.controllers;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import kz.iitu.library.models.*;
import kz.iitu.library.services.BookService;
import kz.iitu.library.services.GenreService;
import kz.iitu.library.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RequestMapping("/book")
@ApiOperation(  value = "Book controller",
        notes = "",
        response = Book.class)
@RestController
public class BookController {

    @Autowired
    private BookService bookService;
    @Autowired
    private UserService userService;
    @Autowired
    private GenreService genreService;

    @GetMapping
    @ApiOperation(  value = "Find all books",
                    notes = "",
                    response = Book.class)
    public List<Book> allBooks(){
        return bookService.findAll();
    }
    @GetMapping("/status={status}")
    @ApiOperation(  value = "Find books by status",
            notes = "Write status and find books",
            response = Book.class)
    public List<Book> findAllByStatus(@ApiParam(value = "value for status", required = true) @PathVariable Status status){
       return bookService.findAllByStatus(status);
    }
    @GetMapping("/{id}")
    @ApiOperation(  value = "Find books by name",
            notes = "Write name of book and find books",
            response = Book.class)
    public Book findBookByName(@PathVariable Long id) {
        return bookService.findBookById(id);
    }
    @GetMapping("/author={authorname}")
    public List<Book> findBookByAuthor(@PathVariable String authorname){
        return bookService.findAllByAuthor(userService.findAuthorByName(authorname));
    }

    @PostMapping
    @ApiOperation(  value = "Add book to DB",
            notes = "Write properties of book and add book",
            response = Book.class)
    public String addBook(@RequestBody Book book) {
        if (bookService.addBook(book)) {
            return ("Книга " + book + " добавлена");
        }
        return (book + " Такая книга уже есть");

    }

    @PutMapping("/{id}")
    @ApiOperation(  value = "Edit book in DB",
            notes = "Write id of book and edit books authors and genres",
            response = Book.class)
    public String editBook(@PathVariable Long id, @RequestParam String authorname, @RequestParam String genrename) {
        Book book = bookService.findBookById(id);
        List<Genre> genres = new ArrayList<Genre>();
        List<Author> authors = new ArrayList<Author>();
        authors.add(userService.findAuthorByName(authorname));
        genres.add(genreService.findGenreByName(genrename));
        book.setGenres(genres);
        book.setAuthors(authors);
        bookService.save(book);
        return ("Книга "+ book + " переработано");
    }

    @PostMapping("/addBook")
    @ApiOperation(  value = "Add book to user",
            notes = "Write user's id and book's id and add book to user but before user should request book",
            response = Book.class)
    public String addBookToUser(@RequestParam Long userId,@RequestParam Long bookId) {
        if (bookService.addBookToUser(userId, bookId)) {
            bookService.findBookById(bookId).notifyMe();
            return ("Книга добавлена пользователю " + userId);
        }
        return ("Выходить ошибка книга уже кому то присвоена или ее юзер не запрашивал");
    }

    @PostMapping("/returnBook")
    @ApiOperation(  value = "Return book from user",
            notes = "Write user's id and book's id and return book from user",
            response = Book.class)
    public String returnBookFromUser(@RequestParam Long userId,@RequestParam Long bookId) {
        if (bookService.returnBookFromUser(userId, bookId)) {
            bookService.findBookById(bookId).notifyMe();
            return ("Книга вернулась с пользователя " + userId);
        }
        return ("Ошибка книга уже свободна");
    }

    @DeleteMapping("/deleteBook/{bookname}")
    @ApiOperation(  value = "Delete book by name",
            notes = "Write book's name and delete book from DB",
            response = Book.class)
    public boolean deleteBookByName(@PathVariable String bookName) {
        if (bookService.deleteBookByName(bookName) > 0) {
            return true;
        }
        return false;
    }

    @DeleteMapping("/cleanBooks")
    @ApiOperation(  value = "Clean all books from DB",
            notes = "DELETE ALL BOOKS FROM DB",
            response = Book.class)
    public void clear() {
        bookService.clear();
    }
}
