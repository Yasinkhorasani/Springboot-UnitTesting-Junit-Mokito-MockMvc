package springboot.Junit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import springboot.Junit.model.Movie;
import springboot.Junit.service.MovieService;

import java.time.LocalDate;
import java.time.Month;

import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.mockito.ArgumentMatchers.any;
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
}
