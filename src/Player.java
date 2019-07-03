import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

public class Player {
    private String firstName;
    private String lastName;
    private String nickName;
    private int rating;
    private ArrayList<Game> games;

    // Konstruktor do stworzenia nowego gracza
    public Player(String firstName, String lastName, String nickName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.nickName = nickName;
        this.rating = 1200;
        this.games = new ArrayList<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return firstName.equals(player.firstName) &&
                lastName.equals(player.lastName) &&
                Objects.equals(nickName, player.nickName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, nickName);
    }

    @Override
    public String toString() {
        String result = firstName + " ";
        if (nickName.length() != 0) {
            result += "'" + nickName + "' ";
        }
        return result +
                lastName + " - " + rating;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void changeRating(int ratingChange) {
        this.rating += ratingChange;
    }

    public LocalDate getLastPlayed() {
        return games.get(games.size()-1).getDate();
    }

    public ArrayList<Game> getGames() {
        return games;
    }

    public void addGame(Game game) {
        this.games.add(game);
    }
}
