package kz.iitu.library.controllers;

import io.swagger.annotations.ApiOperation;
import kz.iitu.library.models.Author;
import kz.iitu.library.models.Book;
import kz.iitu.library.models.User;
import kz.iitu.library.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@ApiOperation(  value = "User controller",
        notes = "",
        response = Book.class)
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> findAllUsers(){
        return userService.findAllUsers();
    }

    @GetMapping("/author={authorname}")
    @ApiOperation(  value = "Find author by name",
            notes = "Write name of author and find author",
            response = Book.class)

    public Author findAuthorByName(@PathVariable String authorname){
        return userService.findAuthorByName(authorname);
    }

    @GetMapping("/user={username}")
    @ApiOperation(  value = "Find user by name",
            notes = "Write name of user and find user",
            response = Book.class)
    public User findUserByName(@PathVariable String username){
        return userService.findUserByName(username);
    }

    //add user
    @GetMapping("/create")
    @ApiOperation(  value = "Create user by username and password",
            notes = "Write username and password of user and create user",
            response = Book.class)
    public void createUserByUsernamePassword(String username, String password){
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        userService.addUser(user);
    }
    @PostMapping
    @ApiOperation(  value = "Create user",
            notes = "Write properties of user and create user",
            response = Book.class)

    public String addUser(@RequestBody User user){
        if(userService.addUser(user)) {
            return ("Пользователь "+user+" добавлен");
        }
        return (user + " Уже есть");
    }

    @PostMapping("/author")
    @ApiOperation(  value = "Create author",
            notes = "Write properties of author and create user",
            response = Book.class)
    public String addAuthor(@RequestBody Author author){
        if(userService.addAuthor(author)) {
            return(author + "Автор добавлен");

        }
        return (author + " Уже существует");
    }

    @PostMapping("/{userid}/edit")
    @ApiOperation(  value = "Edit user by user id",
            notes = "Write properties of user and edit user",
            response = Book.class)
    public String editUser(@PathVariable Long userid,@RequestBody User user){
        if(userService.findUserById(userid) == null) {
            return ("Нету такого юзера "+user);
        }
        userService.saveUser(user);
        return (user +" изменен");
    }

    @PostMapping("/addBook")
    @ApiOperation(  value = "Request book by user",
            notes = "Write book's id and user's id and request book",
            response = Book.class)
    public boolean addBook(@RequestParam Long userId,@RequestParam Long bookId){
        return userService.addBook(userId, bookId);
    }

    @DeleteMapping("/deleteUser/{username}")
    @ApiOperation(  value = "Delete user by username",
            notes = "Write username of user and Delete user",
            response = Book.class)
    public boolean deleteUserByName(@PathVariable String username) {
        if (userService.deleteUserByName(username) > 0) {
            return true;
        }
        return false;
    }

    @DeleteMapping("/deleteBook/{authorname}")
    @ApiOperation(  value = "Delete author by name",
            notes = "Write name of author and Delete author",
            response = Book.class)
    public boolean deleteAuthorByName(@PathVariable String authorname) {
        if (userService.deleteAuthorByName(authorname) > 0) {
            return true;
        }
        return false;
    }
    @DeleteMapping("/cleanAuthorsAndUsers")
    @ApiOperation(  value = "Clear authors and users",
            notes = "Clear authors and users",
            response = Book.class)
    public void clear() {
        userService.clear();
    }
}
