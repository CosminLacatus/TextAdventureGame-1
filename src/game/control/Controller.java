package game.control;

import game.helper.SqliteWrapper;
import game.model.*;
import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.List;
import java.util.Map;

public class Controller {

    public TextField txtFieldCommand;
    public TextArea txtAreaGameOutput;

    private Game game;
    private Player player;

    public void initialize() {
        game = new Game();
        Key key = new Key();
        Door door = new Door(key);
        player = new Player();

        game.placeOnBoard(key, 2, 3);
        game.placeOnBoard(door, 1, 5);
        game.placeOnBoard(player, 5, 5);


        System.out.println(game.displayBoard());
        txtAreaGameOutput.appendText(game.displayBoard() + "\n");

//        game.removeFromBoard(2, 3);
//        System.out.println(game.displayBoard());
//        txtAreaGameOutput.appendText(game.displayBoard());
//
//        game.placeOnBoard(key, 2, 3);
//        System.out.println(player.getArtifacts());
//        game.movePlayer(player, 2,3);
//        System.out.println(game.displayBoard());
//        txtAreaGameOutput.appendText(game.displayBoard());
//        System.out.println(player.getArtifacts());
//        txtAreaGameOutput.appendText(player.getArtifacts());
//
//        game.movePlayer(player, 1,5);
//        System.out.println(game.displayBoard());
//        txtAreaGameOutput.appendText(game.displayBoard());
//        System.out.println(player.getArtifacts());
//        txtAreaGameOutput.appendText(player.getArtifacts());

    }

    public void processCommand(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.ENTER)) {
            GameStrategy.processCommand(txtFieldCommand.getText(), game, player);
            txtFieldCommand.clear();
            txtAreaGameOutput.appendText(game.displayBoard() + "\n");
        }
    }

    public void saveGameProgress(ActionEvent event) {
        SqliteWrapper sqliteWrapper = new SqliteWrapper();
        for(int i=0; i<game.getGameBoards().size(); i++) {
            GameBoard gameBoard = game.getGameBoards().get(i);
            sqliteWrapper.insertGameBoard(gameBoard);
            for (Map.Entry<Artifact, List<Integer>> entry : gameBoard.getArtifactsPositions().entrySet())
            {
                sqliteWrapper.insertArtifact(entry.getKey());
                sqliteWrapper.insertArtifactsPosition(entry.getKey(), gameBoard);
            }

        }


    }
}
