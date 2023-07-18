package com.personal.OnePiece.graphdb.repository;

import com.personal.OnePiece.graphdb.data.MovieEntity;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MovieRepository extends Neo4jRepository<MovieEntity, String> {
    MovieEntity findOneByTitle(String title);

    @Query("MATCH (movie: Movie)<-[:DIRECTED]-(director:Person), (movie)<-[:ACTED_IN]-(actor:Person) WHERE director.name=$directorName " +
            "RETURN movie, collect(director) as directors, collect(actor) as actors")
    List<MovieEntity> moviesDirectedBy(@Param("directorName") String directorName);

    List<MovieEntity> findByDirectorsName(String directorName);
}
