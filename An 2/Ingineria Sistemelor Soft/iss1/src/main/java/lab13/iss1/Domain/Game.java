package lab13.iss1.Domain;

import java.util.Objects;
import java.util.UUID;


public class Game {
    private UUID id;
    private String name;
    private GenreType genre;
    private int playtime;
    private GameStatus status;
    private float price;

    public Game(String name, GenreType genre, int playtime, GameStatus status, float price) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.genre = genre;
        this.playtime = playtime;
        this.status = status;
        this.price = price;
    }

    public Game(UUID id, String name, GenreType genre, int playtime, GameStatus status, float price) {
        this.id = id;
        this.name = name;
        this.genre = genre;
        this.playtime = playtime;
        this.status = status;
        this.price = price;
    }

    public Game (String name, GenreType genre, int playtime, float price) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.genre = genre;
        this.playtime = playtime;
        this.status = GameStatus.NOT_INSTALLED;
        this.price = price;
    }

    public Game (String name, GenreType genre, float price) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.genre = genre;
        this.playtime = 0;
        this.status = GameStatus.NOT_INSTALLED;
        this.price = price;
    }

    public Game(String name, String genre, float price) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.genre = GenreType.valueOf(genre);
        this.playtime = 0;
        this.status = GameStatus.NOT_INSTALLED;
        this.price = price;
    }




    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GenreType getGenre() {
        return genre;
    }

    public void setGenre(GenreType genre) {
        this.genre = genre;
    }

    public int getPlaytime() {
        return playtime;
    }

    public void setPlaytime(int playtime) {
        this.playtime = playtime;
    }

    public GameStatus getStatus() {
        return status;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return playtime == game.playtime && Float.compare(price, game.price) == 0 && Objects.equals(id, game.id) && Objects.equals(name, game.name) && genre == game.genre && status == game.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, genre, playtime, status, price);
    }
}
