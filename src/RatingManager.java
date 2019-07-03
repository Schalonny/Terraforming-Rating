import java.util.ArrayList;
import java.util.HashMap;

public class RatingManager {
    private HashMap<Integer, Player> ratingList;

    public RatingManager() {
        this.ratingList = new HashMap<>();
    }

    public HashMap<Integer, Player> getRatingList() { //TODO sortowanie ?
        return ratingList;
    }

    public void applyRatingChanges(ArrayList<Player> players, ArrayList<Integer> rtgChanges){
        for (int i = 0; i < players.size(); i++) {
            ratingList
                    .get(players.get(i).hashCode())
                    .changeRating(rtgChanges.get(i));
        }
    }

    public void updatePlayersGames(Game game){
        for (Player player : game.getPlayers()) {
            ratingList.get(player.hashCode()).addGame(game);
        }
    }
}
