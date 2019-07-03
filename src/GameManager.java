import java.time.LocalDate;
import java.util.ArrayList;

public class GameManager {
    private static final int[] K_VALUES={9,21,27,15};
    private static final int BUFFER_RATING = 1300;
    private static final int BUFFER_GAMES_NUMBER = 10;
    private static final int PREMIUM_K_MULTIPLIER = 2;

    private ArrayList<Game> games;

    public GameManager() {
        this.games = new ArrayList<>();
    }

    public void addGame(Game game) {
        games.add(game);
    }

    public ArrayList<Game> getGames() {
        return games;
    }

    public ArrayList<Integer> calculateRatingChanges(Game game) {
        ArrayList<Integer> rtgChanges = new ArrayList<>();
        ArrayList<Player> gamePlayers = game.getPlayers();
        int ratingSum = 0;
        for (Player player : gamePlayers) {
            ratingSum += player.getRating();
        }
        for (int i = 0; i < gamePlayers.size(); i++) {
            double result = 1 - ((double) i / (gamePlayers.size() - 1));
            double averageOpponentsRating =
                    (double) (ratingSum - gamePlayers.get(i).getRating()) / (gamePlayers.size() - 1);
            double expected =
                    1 / (1 + Math.pow(10, (averageOpponentsRating - gamePlayers.get(i).getRating()) / 400));
            int rtgChange = (int) Math.round(getKValue(gamePlayers) * (result - expected));
            if (gamePlayers.get(i).getRating()<BUFFER_RATING) {
                rtgChange = Math.max(rtgChange, gamePlayers.size()-i);
            }
            if (gamePlayers.get(i).getGames().size()<= BUFFER_GAMES_NUMBER) {
                rtgChange *= PREMIUM_K_MULTIPLIER;
            }
            rtgChanges.add(rtgChange);
        }
        return rtgChanges;
    }

    private int getKValue(ArrayList<Player> players){
        return K_VALUES[players.size()-1];
    }

    @Override
    public String toString() {
        StringBuilder stringGamesList = new StringBuilder();
        for (Game game : games) {
            stringGamesList.append(game.toString());
            stringGamesList.append('\n');
        }
        return stringGamesList.toString();
    }
}
