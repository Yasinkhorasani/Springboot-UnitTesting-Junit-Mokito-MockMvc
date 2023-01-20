package springboot.Junit.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import springboot.Junit.model.Movie;
import springboot.Junit.repository.MovieRepository;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MovieServiceTest {

    @InjectMocks
    private MovieService movieService;
    @Mock
    private MovieRepository movieRepository;

    private Movie avatarMovie;
    private Movie titanicMovie;

    @BeforeEach
    public void init(){
        avatarMovie = new Movie();
        avatarMovie.setId(1L);
        avatarMovie.setName("Avatar");
        avatarMovie.setGenera("Action");
        avatarMovie.setReleaseDate(LocalDate.of(2000, Month.APRIL,22));

        titanicMovie = new Movie();
        titanicMovie.setId(2L);
        titanicMovie.setName("Titanic");
        titanicMovie.setGenera("Romantic");
        titanicMovie.setReleaseDate(LocalDate.of(2003, Month.MARCH,12));
    }
    @Test
    @DisplayName("Save the Movie object to database")
    public void save(){
        when(movieRepository.save(any(Movie.class))).thenReturn(avatarMovie);

        Movie newMovie = movieService.save(avatarMovie);

        assertNotNull(newMovie);
        assertThat(newMovie.getName()).isEqualTo("Avatar");
    }
    ////////////////////////////////////////////////////////////////////////////////////
    @Test
    @DisplayName("Return list of Movies with size 2")
    public void getAllMovies(){
        List<Movie> movieList = new ArrayList<>();
        movieList.add(avatarMovie);
        movieList.add(titanicMovie);

        when(movieRepository.findAll()).thenReturn(movieList);

        List<Movie> movies = movieService.getAllMovies();

        assertNotNull(movies);
        assertEquals(2,movies.size());
    }

    /////////////////////////////////////////////////////////////////////////////////////
    @Test
    @DisplayName("Return Movie Object")
    public void getMovieById(){

        when(movieRepository.findById(anyLong())).thenReturn(Optional.of(avatarMovie));

       Movie existingMovie = movieService.getMovieById(1L);
       assertNotNull(existingMovie);
       assertThat(existingMovie.getId()).isEqualTo(1L);
    }
    @Test
    @DisplayName("Throw the Exception")
    public void getMovieByIdForException(){

        when(movieRepository.findById(1L)).thenReturn(Optional.of(avatarMovie));

        assertThrows(RuntimeException.class, ()->{
            movieService.getMovieById(2L);
        });
    }

    //////////////////////////////////////////////////////////////////////////////////////
    @Test
    @DisplayName("update the Movie into database")
    public void updateMovie(){

        when(movieRepository.findById(anyLong())).thenReturn(Optional.of(avatarMovie));
        when(movieRepository.save(any(Movie.class))).thenReturn(avatarMovie);
        avatarMovie.setGenera("Fantasy");

        Movie updateMovie = movieService.updateMovie(avatarMovie, 1L);

        assertNotNull(updateMovie);
        assertEquals("Fantasy",updateMovie.getGenera());
    }

    /////////////////////////////////////////////////////////////////////////////////////////
    @Test
    @DisplayName("Return delete the Movie from database")
    public void deleteMovie(){

        when(movieRepository.findById(anyLong())).thenReturn(Optional.of(avatarMovie));
        doNothing().when(movieRepository).delete(any(Movie.class));

        movieService.deleteMovie(1L);

        verify(movieRepository,times(1)).delete(avatarMovie);

    }
}
