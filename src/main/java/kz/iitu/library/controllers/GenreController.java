package kz.iitu.library.controllers;

import io.swagger.annotations.ApiOperation;
import kz.iitu.library.models.Book;
import kz.iitu.library.models.Genre;
import kz.iitu.library.services.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@ApiOperation(  value = "Genre controller",
        notes = "",
        response = Book.class)
@RequestMapping("/genre")
public class GenreController {
    @Autowired
    private GenreService genreService;

    @GetMapping("/{genrename}")
    @ApiOperation(  value = "Find genre by name",
            notes = "Write genre's name and get genre",
            response = Book.class)
    public Genre findGenreByName(@PathVariable String genrename) {
        return genreService.findGenreByName(genrename);
    }

    @GetMapping
    @ApiOperation(  value = "Find all genres",
            notes = "Get all genre's",
            response = Book.class)

    public List<Genre> findAll() {
        return genreService.findAll();
    }

    @PostMapping("/addGenre")
    @ApiOperation(  value = "Add genre",
            notes = "Write properties of genre and add to DB",
            response = Book.class)
    public String addGenre(@RequestBody Genre genre) {
        if (genreService.addGenre(genre)) {
            return (genre + "Жанр добавлен");
        }
        return ("Такой жанр уще существует");
    }

    @DeleteMapping("/deleteGenre/{genrename}")
    @ApiOperation(  value = "Delete genre by name",
            notes = "Write genre's name and delete genre from DB",
            response = Book.class)

    public boolean deleteGenreByName(@PathVariable String genrename) {
        if (genreService.deleteByGenreName(genrename) > 0) {
            return true;
        }
        return false;
    }

    @DeleteMapping("/cleanGenres")
    @ApiOperation(  value = "Clean all genres from DB",
            notes = "DELETE ALL GENRES FROM DB",
            response = Book.class)
    public void clear() {
        genreService.clear();
    }
}
