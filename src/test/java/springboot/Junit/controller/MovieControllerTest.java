package springboot.Junit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import springboot.Junit.model.Movie;
import springboot.Junit.service.MovieService;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.hamcrest.CoreMatchers.is;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@WebMvcTest
public class MovieControllerTest {

    @MockBean
    private MovieService movieService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("test create new Movie Object")
    public void shouldCreateNewMovie() throws Exception {
        Movie avatarMovie = new Movie();
        avatarMovie.setId(1L);
        avatarMovie.setName("Avatar");
        avatarMovie.setGenera("Action");
        avatarMovie.setReleaseDate(LocalDate.of(2000, Month.APRIL,22));

        when(movieService.save(any(Movie.class))).thenReturn(avatarMovie);
        this.mockMvc.perform(post("/movies").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(avatarMovie)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name",is(avatarMovie.getName())))
                .andExpect(jsonPath("$.genera",is(avatarMovie.getGenera())))
                .andExpect(jsonPath("$.releaseDate",is(avatarMovie.getReleaseDate().toString())));
}

/////////////////////////////////////////////////////////////////////////////////////
    @Test
    @DisplayName("Test Controller for get All Movie Objects")
    public void shouldFetchAllMovies() throws Exception {
        Movie avatarMovie = new Movie();
        avatarMovie.setId(1L);
        avatarMovie.setName("Avatar");
        avatarMovie.setGenera("Action");
        avatarMovie.setReleaseDate(LocalDate.of(2000, Month.APRIL,22));

        Movie titanicMovie = new Movie();
        avatarMovie.setId(2L);
        titanicMovie.setName("Titanic");
        titanicMovie.setGenera("Romantic");
        titanicMovie.setReleaseDate(LocalDate.of(2003, Month.MARCH,12));

        List<Movie> movieList = new ArrayList<>();
        movieList.add(avatarMovie);
        movieList.add(titanicMovie);

        when(movieService.getAllMovies()).thenReturn(movieList);

        this.mockMvc.perform(get("/movies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()",is(movieList.size())));
    }

    /////////////////////////////////////////////////////////////////////////////////////
    @Test
    @DisplayName("Test Controller for return One Movie By Id")
    public void shouldFetchOneMovieById() throws Exception {
        Movie avatarMovie = new Movie();
        avatarMovie.setId(1L);
        avatarMovie.setName("Avatar");
        avatarMovie.setGenera("Action");
        avatarMovie.setReleaseDate(LocalDate.of(2000, Month.APRIL, 22));

        when(movieService.getMovieById(anyLong())).thenReturn(avatarMovie);

        this.mockMvc.perform(get("/movies/{id}",1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name",is(avatarMovie.getName())))
                .andExpect(jsonPath("$.genera", is(avatarMovie.getGenera())));
    }

    /////////////////////////////////////////////////////////////////////////////////////
    @Test
    @DisplayName("Test Controller for Delete Movie 'By Id'")
    public void shouldDeleteMovie() throws Exception {
        Movie avatarMovie = new Movie();
        avatarMovie.setId(1L);
        avatarMovie.setName("Avatar");
        avatarMovie.setGenera("Action");
        avatarMovie.setReleaseDate(LocalDate.of(2000, Month.APRIL, 22));

        doNothing().when(movieService).deleteMovie(anyLong());

        this.mockMvc.perform(delete("/movies/{id}",1L))
                .andExpect(status().isNoContent());
    }

    /////////////////////////////////////////////////////////////////////////////////////
    @Test
    @DisplayName("Test Controller for update Movie 'By Id'")
    public void shouldUpdateMovie() throws Exception {
        Movie avatarMovie = new Movie();
        avatarMovie.setId(1L);
        avatarMovie.setName("Avatar");
        avatarMovie.setGenera("Action");
        avatarMovie.setReleaseDate(LocalDate.of(2000, Month.APRIL, 22));

        when(movieService.updateMovie(any(Movie.class),anyLong())).thenReturn(avatarMovie);

        this.mockMvc.perform(put("/movies/{id}",1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(avatarMovie)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name",is(avatarMovie.getName())))
                .andExpect(jsonPath("$.genera",is(avatarMovie.getGenera())));
    }
    }
