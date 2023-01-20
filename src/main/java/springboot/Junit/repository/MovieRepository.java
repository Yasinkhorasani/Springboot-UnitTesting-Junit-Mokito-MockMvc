package springboot.Junit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springboot.Junit.model.Movie;

import java.util.List;


public interface MovieRepository extends JpaRepository <Movie,Long> {

    List<Movie> findByGenera(String genera);
}

