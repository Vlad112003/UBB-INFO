package ro.ubbcluj.cs.map;

import ro.ubbcluj.cs.map.domain.Movie;
import ro.ubbcluj.cs.map.repository.MovieDbRepository;
import ro.ubbcluj.cs.map.repository.Repository;

public class Main {
    public static void main(String[] args) {
        Repository<Long, Movie> repo = new MovieDbRepository("jdbc:postgresql://localhost:5432/movies", "postgres", "piupiu");
        //System.out.println(repo.findOne(1l));
        Movie m = new Movie("abc","dir1",2000);
        //repo.save(m);
        //System.out.println(repo.findAll());
        repo.delete(2l);
        System.out.println(repo.findAll());


    }
}
