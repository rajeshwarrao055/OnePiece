package com.personal.OnePiece.graphdb;

import com.personal.OnePiece.graphdb.data.MovieEntity;
import com.personal.OnePiece.graphdb.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {
    @Autowired
    private MovieRepository movieRepository;

    @GetMapping("/by-title")
    public ResponseEntity<MovieEntity> fetchMovieEntity(@RequestParam String title) {
        return ResponseEntity.ok(movieRepository.findOneByTitle(title));
    }

    @GetMapping("/all")
    public ResponseEntity<List<MovieEntity>> fetchAllMovies() {
        return ResponseEntity.ok(movieRepository.findAll());
    }

    @GetMapping("/by-director")
    public ResponseEntity<List<MovieEntity>> fetchMoviesByDirector(@RequestParam String directorName) {
        //loses context of actors and directors with custom query -- need to fix this
        return ResponseEntity.ok(movieRepository.moviesDirectedBy(directorName));
    }

    // does not use custom query
    @GetMapping("/by-director-name")
    public ResponseEntity<List<MovieEntity>> fetchMoviesByDirectorName(@RequestParam String directorName) {
        return ResponseEntity.ok(movieRepository.findByDirectorsName(directorName));
    }
}
