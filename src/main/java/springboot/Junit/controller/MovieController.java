package springboot.Junit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import springboot.Junit.model.Movie;
import springboot.Junit.service.MovieService;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {
    @Autowired
    private MovieService movieService;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Movie create(@RequestBody Movie movie){
        return movieService.save(movie);
    }
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Movie> read(){
       return movieService.getAllMovies();
    }
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Movie read(@PathVariable Long id){
        return movieService.getMovieById(id);
    }
    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public Movie update(@PathVariable Long id, @RequestBody Movie movie){
        return movieService.updateMovie(movie,id);
    }
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        movieService.deleteMovie(id);
    }

}
