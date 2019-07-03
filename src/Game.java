import java.time.LocalDate;
import java.util.ArrayList;

public class Game {

    private ArrayList<Player> players;
    private boolean isRanked;
    private LocalDate date;
    private ArrayList<Integer> rankingChanges;

    public Game(ArrayList<Player> players, LocalDate date, boolean isRanked) {
        this.players = players;
        this.date = date;
        this.isRanked = isRanked;
        this.rankingChanges = new ArrayList<>();
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public LocalDate getDate() {
        return date;
    }

    public boolean isRanked() {
        return isRanked;
    }

    public void setRankingChanges(ArrayList<Integer> rankingChanges) {
        this.rankingChanges = rankingChanges;
    }

    @Override
    public String toString() {
        String rank = (isRanked) ? stringOfRankingChanges() : "gra towarzyska.";

        return "(" + date + ") " +
                 stringOfPlayersList() +
                " | " +
                 rank;
    }

    private String stringOfRankingChanges(){
        StringBuilder rtgChanges = new StringBuilder("(");
        for (Integer rankingChange : rankingChanges) {
            if (rankingChange>0){
                rtgChanges.append("+");
            }
            rtgChanges.append(rankingChange);
            rtgChanges.append(", ");
        }
        rtgChanges.delete(rtgChanges.length()-3,rtgChanges.length()-1);
        rtgChanges.append(")");
        return rtgChanges.toString();
    }

    private String stringOfPlayersList(){

        StringBuilder playersList = new StringBuilder();
        for (Player player : players) {
            playersList.append(player.getFirstName());
            playersList.append(' ');
            playersList.append(player.getLastName().charAt(0));
            playersList.append('.');
            playersList.append(", ");

        }
        playersList.delete(playersList.length()-3, playersList.length()-1);
    return playersList.toString();
    }
}
