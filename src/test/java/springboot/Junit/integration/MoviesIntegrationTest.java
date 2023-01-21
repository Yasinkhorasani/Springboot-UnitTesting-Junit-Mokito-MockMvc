package springboot.Junit.integration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.client.RestTemplate;
import springboot.Junit.repository.MovieRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MoviesIntegrationTest {

    @LocalServerPort
    private int port;

    private String baseURL= "http://localhost";

    private static RestTemplate restTemplate;

    @Autowired
    private MovieRepository movieRepository;
    @BeforeAll
    public static void init(){
        restTemplate = new RestTemplate();
    }
    @BeforeEach
    public void beforeSetup(){
        baseURL = baseURL + ":" + port + "/movies";
    }
    @AfterEach
    public void afterSetup(){
        movieRepository.deleteAll();
    }
}
