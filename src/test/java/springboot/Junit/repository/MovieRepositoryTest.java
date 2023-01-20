package springboot.Junit.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import springboot.Junit.model.Movie;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
public class MovieRepositoryTest {
    @Autowired
    private MovieRepository movieRepository;

    private Movie avatarMovie;
    private Movie titanicMovie;

// Code Refactor
    @BeforeEach
    public void init(){
        avatarMovie = new Movie();
        avatarMovie.setName("Avatar");
        avatarMovie.setGenera("Action");
        avatarMovie.setReleaseDate(LocalDate.of(2000, Month.APRIL,22));

        titanicMovie = new Movie();
        titanicMovie.setName("Titanic");
        titanicMovie.setGenera("Romantic");
        titanicMovie.setReleaseDate(LocalDate.of(2003, Month.MARCH,12));
    }
    @Test
    @DisplayName("save the movie to the database")
    public void save(){
        //Arrange
   /*     Movie avatarMovie = new Movie();
        avatarMovie.setName("Avatar");
        avatarMovie.setGenera("Action");
        avatarMovie.setReleaseDate(LocalDate.of(2000, Month.APRIL,22));
   */
        //Act(when)
        Movie newMovie = movieRepository.save(avatarMovie);

        //Assert(then)
        assertNotNull(newMovie);
        assertThat(newMovie.getId()).isNotEqualTo(null);
    }

    //////////////////////////////////////////////////////////////////////////////////////
    @Test
    @DisplayName("Return MoviesList size of 2")
    void getAll(){
        //Arrange
   /*     Movie avatarMovie = new Movie();
        avatarMovie.setName("Avatar");
        avatarMovie.setGenera("Action");
        avatarMovie.setReleaseDate(LocalDate.of(2000, Month.APRIL,22));

    */
        movieRepository.save(avatarMovie);

   /*     Movie titanicMovie = new Movie();
        avatarMovie.setName("Titanic");
        avatarMovie.setGenera("Romantic");
        avatarMovie.setReleaseDate(LocalDate.of(2003, Month.MARCH,12));

    */
        movieRepository.save(titanicMovie);

        //Act(when)
        List<Movie> movieList = movieRepository.findAll();

        //Assert(then)
        assertNotNull(movieList);
        assertThat(movieList).isNotNull();
        assertEquals(2,movieList.size());
    }

    /////////////////////////////////////////////////////////////////////////////////////
    @Test
    @DisplayName("Return Movie By Id")
    void getMovieById(){

        //Arrange
       /*Movie avatarMovie = new Movie();
        avatarMovie.setName("Avatar");
        avatarMovie.setGenera("Action");
        avatarMovie.setReleaseDate(LocalDate.of(2000, Month.APRIL,22));
        */
        movieRepository.save(avatarMovie);

        //Act(when)
        Movie movieById =movieRepository.findById(avatarMovie.getId()).get();

        //Assert(then)
        assertNotNull(movieById);
        assertEquals("Action",movieById.getGenera());
        assertThat(avatarMovie.getReleaseDate()).isBefore(LocalDate.of(2000,Month.APRIL,23));
    }

    /////////////////////////////////////////////////////////////////////////////////////////
    @Test
    @DisplayName("Return update the Movie with genera FANTASY")
    void updateMovie(){

        //Arrange
       /* Movie avatarMovie = new Movie();
        avatarMovie.setName("Avatar");
        avatarMovie.setGenera("Action");
        avatarMovie.setReleaseDate(LocalDate.of(2000, Month.APRIL,22));
        */
        movieRepository.save(avatarMovie);

        Movie existingMovie =movieRepository.findById(avatarMovie.getId()).get();

        //Act(when)
        existingMovie.setGenera("fantasy");
        Movie newMovie = movieRepository.save(existingMovie);

        //Assert(then)
        assertEquals("fantasy",newMovie.getGenera());
        assertEquals("Avatar",newMovie.getName());
    }

    /////////////////////////////////////////////////////////////////////////////////////////
    @Test
    @DisplayName("Delete the existing Movie")
    void delete(){

        //Arrange
    /*    Movie avatarMovie = new Movie();
        avatarMovie.setName("Avatar");
        avatarMovie.setGenera("Action");
        avatarMovie.setReleaseDate(LocalDate.of(2000, Month.APRIL,22));
     */
        movieRepository.save(avatarMovie);
        Long id = avatarMovie.getId();

   /*   Movie titanicMovie = new Movie();
        avatarMovie.setName("Titanic");
        avatarMovie.setGenera("Romantic");
        avatarMovie.setReleaseDate(LocalDate.of(2003, Month.MARCH,12));
    */
        movieRepository.save(titanicMovie);

        //Act(when)
        movieRepository.delete(avatarMovie);
        Optional<Movie> existingMovie = movieRepository.findById(id);
        List<Movie> movieList =movieRepository.findAll();

        //Assert(then)
        assertEquals(1,movieList.size());
        assertThat(existingMovie).isEmpty();
    }

    //////////////////////////////////////////////////////////////////////////////////////////
    @Test
    @DisplayName("return movies list with genera ROMANCE")
    public void getMovieByGenera(){
        //Arrange
  /*      Movie avatarMovie = new Movie();
        avatarMovie.setName("Avatar");
        avatarMovie.setGenera("Action");
        avatarMovie.setReleaseDate(LocalDate.of(2000, Month.APRIL,22));
   */
        movieRepository.save(avatarMovie);

  /*      Movie titanicMovie = new Movie();
        avatarMovie.setName("Titanic");
        avatarMovie.setGenera("Romantic");
        avatarMovie.setReleaseDate(LocalDate.of(2003, Month.MARCH,12));
   */
        movieRepository.save(titanicMovie);

        //Act(when)
        List<Movie> movieList = movieRepository.findByGenera("Romantic");

        //Assert(then)
        assertNotNull(movieList);
        assertThat(movieList.size()).isEqualTo(1);
    }
}

