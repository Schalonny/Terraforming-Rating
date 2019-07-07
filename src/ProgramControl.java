import java.time.LocalDate;
import java.util.ArrayList;
import java.util.InputMismatchException;

public class ProgramControl {

    private ConsolePrinter printer = new ConsolePrinter();
    private DataReader dataReader = new DataReader(printer);
    private FileManager fileManager;
    private GameManager gameManager;
    private RatingManager ratingManager;

    // konstruktor - w momencie wywołania ProgramControl importujemy dane i inicjujemy menagery
/*    public ProgramControl() {
        this.fileManager = new FileManagerBuilder(printer, dataReader).build();
        this.gameManager = new GameManager();
        this.ratingManager = new RatingManager();
        try {
            ratingList = fileManager.importData();
            if (ratingList == null) {
                throw new FileNotFoundException();
            }
            printer.printLine("Zaimportowane dane z pliku");
        } catch (FileNotFoundException e) {
            printer.printLine("Zainicjowano nową bazę.");
            ratingList = new RatingList();
        }
    }*/

    private Option getOption() {
        Option option = null;
        boolean optionOk = false;
        while (!optionOk) {
            try {
                option = Option.createFromInt(dataReader.getInt());
                optionOk = true;
            } catch (ArrayIndexOutOfBoundsException e) {
                printer.printLine("Nie ma opcji o wskazanym numerze");
            } catch (InputMismatchException e) {
                printer.printLine("Nie podano liczby");
            }
        }
        return option;
    }

    void controlLoop() {
        Option option = null;
        while (option != Option.EXIT) {
            printOptions();
            option = getOption();
            switch (option) {
                case RATINGS:
                    showRatings();
                    break;
                case ADD_PLAYER:
                    addPlayer();
                    break;
                case EDIT_PLAYER:
                    // TODO
                    break;
                case ADD_GAME:
                    addGame();
                    break;
                case REMOVE_PLAYER:
                    //TODO usuwanie gracza
                    break;
                case REMOVE_GAME:
                    //TODO usuwanie gry
                    break;
                case GAMES:
                    showGames();
                    break;
                case EXIT:
                    exit();
                    break;
            }
        }

    }

    private void addGame() {
        Game gameToAdd = dataReader.readAndAddGame(ratingList); //TODO wczytywanie danych

        gameManager.addGame(gameToAdd);
        ratingManager.updatePlayersGames(gameToAdd);

        if (gameToAdd.isRanked()) {
            ArrayList<Integer> rtgChanges =
                    gameManager.calculateRatingChanges(gameToAdd);
            gameToAdd.setRankingChanges(rtgChanges);
            ratingManager.applyRatingChanges(gameToAdd.getPlayers(), rtgChanges);
        }

    }

    private void showGames() {
        printer.printLine(gameManager.toString());
    }

    private void addPlayer() {
        Player player = dataReader.readAndCreatePlayer(ratingList);
        ratingManager.addPlayer(player);
    }

/*    private void editPlayer() {
        String who = dataReader.readAndSearchByName();
        Player player = ratingList.players.get(who);
        printer.printLine("1 - imię, 2 - nazwisko, 3 - nick, 4 - ranking");
        int edit = getWhatToEdit();
        switch (edit) {
            case 1:
                printer.printLine("Ustaw nowe imię: ");
                player.setFirstName(dataReader.getString());
                break;
            case 2:
                printer.printLine("Ustaw nowe nazwisko: ");
                player.setLastName(dataReader.getString());
                break;
            case 3:
                printer.printLine("Ustaw nowy nick: ");
                player.setNickName(dataReader.getString());
                break;
            case 4:
                printer.printLine("Podaj ręcznie nowy ranking: ");
                try {
                    player.setRating(dataReader.getInt());
                } catch (InputMismatchException e) {
                    printer.printLine("Podano nieprawidłowy ranking");
                }
                break;
            default:
                break;
        }
    }

    private int getWhatToEdit() {
        int option = 0;
        boolean optionOk = false;
        while (!optionOk) {
            try {
                option = dataReader.getInt();
                if (option < 0 || option > 4) {
                    throw new ArrayIndexOutOfBoundsException();
                }
                optionOk = true;
            } catch (ArrayIndexOutOfBoundsException e) {
                printer.printLine("Nie ma opcji o wskazanym numerze");
            } catch (InputMismatchException e) {
                printer.printLine("Nie podano liczby");
            }
        }
        return option;
    }
*/

    private void showRatings() {
        printer.printLine("Aktualna lista rankingowa :");
        printer.printRatingList(
                ratingManager.getRatingList());
        // komparator zwraca "+" jeśli to druga wartość jest większa, stąd sortowanie
        // będzie malejące
    }

    private void printOptions() {
        for (Option op : Option.values()) {
            System.out.println(op);
        }
    }

    private void exit() {
        fileManager.exportData(ratingList);
        printer.printLine("Poprawnie wyeksportowano dane");
        printer.printLine("Do zobaczenia przy planszy!");
        dataReader.close();
    }





    private enum Option {  // prywatna klasa wewnętrzna
        EXIT(0, "wyjście z programu"),
        RATINGS(1, "pokaż tabelę rankingową"),
        ADD_PLAYER(2, "dodaj nowego gracza"),
        EDIT_PLAYER(3, "edytuj gracza"),
        ADD_GAME(4, "wprowadź nową grę"),
        GAMES(5, "wyświetl historię gier"),
        REMOVE_PLAYER(20, "Usuń gracza"),
        REMOVE_GAME(40, "Usuń grę");

        private int value;
        private String describe;

        Option(int value, String describe) {
            this.value = value;
            this.describe = describe;
        }

        @Override
        public String toString() {
            return value + " - " + describe;
        }

        static Option createFromInt(int option) {
            return Option.values()[option];
        }
    }
}
