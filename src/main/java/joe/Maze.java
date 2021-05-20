package joe;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;


/**
 * JavaFX App that is a maze game
 *
 * Author:
 * Joe Vieira
 * May 2021
 */
public class Maze extends Application {

    Canvas c = new Canvas();
    GraphicsContext gc = c.getGraphicsContext2D();

    //this variable will initially be used to store the location of the top right corner of each box to be drawn
    //for the maze to be drawn. After the maze has been drawn it will become the location of the blue square
    int [] location = new int[2];

    //this is the location of the winning location of the maze
    int [] endingLocation = new int[2];



    @Override
    public void start(Stage stage){
        FlowPane root = new FlowPane();

        c.setWidth(525);
        c.setHeight(525);
        root.getChildren().add(c);

        Scene scene = new Scene(root, 525, 525);
        stage.setTitle("Maze Game");
        stage.setScene(scene);
        stage.show();

        root.requestFocus();
        
        int[][] map = this.readFile();
        drawMaze(map);

        findStartingLocation(map);
        findEndingLocation(map);
        root.setOnKeyPressed(new KeyListenerDown());

    }

    /**Draws the maze on the canvas**/
    public void drawMaze(int[][] map) {
        location[0] = 0;
        location[1] = 0;
        int[] here = new int[]{0, 0};

        for(int i = 0; i < 21; i++) {
            for(int j = 0; j < 21; j++) {

                if(map[i][j] == 1) {
                    gc.setFill(Color.BLACK);
                    gc.fillRect(here[0], here[1], 25, 25);
//                    System.out.println("Black square at: " + here[0] + " " + here[1]);
                } else{
                    gc.setFill(Color.WHITE);
                    gc.fillRect(here[0], here[1], 25, 25);
//                    System.out.println("White square at: " + here[0] + " " + here[1]);
                }
                here = locationTracker();
            }
        }
    }

    /**Keeps track of the position of the top left corner of each box**/
    private int[] locationTracker() {

        location[0] += 25;

        if(location[0] >= 525) {
            location[0] = 0;
            location[1] += 25;
        }

        return location;
    }

    /**Populates a 2D array with the values from the maze file**/
    private int[][] readFile() {
        Scanner scan = null;
        File f = new File("/Users/jtv/Desktop/Maze.txt");
        try {
            System.out.println("attempting to read from: " + f.getCanonicalPath());
            scan = scan = new Scanner(f);
        } catch (FileNotFoundException fnfe) {
            System.out.println("File not found");
            fnfe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        
        int[][] mazeMap = new int [21][21];

        for (int i = 0; i < 21; i++) {
            for(int j = 0; j < 21; j++) {
                mazeMap[i][j] = scan.nextInt();
            }
        }
        return mazeMap;
    }

    /**Finds the starting place of the first square and draws the square at that spot**/
    public void findStartingLocation(int[][] map) {
        for(int i = 0; i < 1; i++) {
            for(int j = 0; j < 21; j++) {
                if( map[i][j] == 0) {
                    location[0] = j;
                    location[1] = i;
                }
            }
        }
        gc.setFill(Color.LIGHTBLUE);
        gc.fillRect(location[0] * 25, location[1] * 25, 25, 25);
        System.out.println("Location of start is: " + location[0] * 25 + " " + location[1] * 25);
    }

    public void findEndingLocation(int[][] map) {
        for(int i = 0; i < 21; i++) {
            for(int j = 20; j < 21; j++) {
                if (map[i][j] == 0) {
                    this.endingLocation[0] = i;
                    this.endingLocation[1] = j;
                }
            }
        }
    }


    public static void main(String[] args) {
        launch();
    }


    class KeyListenerDown implements EventHandler<KeyEvent> {
        int[][] map = readFile();
        @Override
        public void handle(KeyEvent keyEvent) {
            gc.setFill(Color.LIGHTBLUE);
//            System.out.println("down: " + keyEvent.getCode());

            //if press the ____ arrow, check to see if that spot is empty or is not the edge of the panel
            //if that spot is empty, then remove the current blue spot, and paint one below it

            if(keyEvent.getCode().equals(KeyCode.DOWN)) {
                System.out.println("Current spot is: " + location[0] * 25 + " " + location[1] * 25);
                try {
                    if (map[location[0]][location[1] + 1] == 0) {
                        System.out.println("Looking at this spot for empty space: " + location[0] * 25 + " " + (location[1] + 1) * 25);
                        gc.clearRect(location[0] * 25, location[1] * 25, 25, 25);
                        location[1] += 1;
                        gc.fillRect(location[0] * 25, location[1] * 25, 25, 25);
                        System.out.println("New spot is: " + location[0] * 25 + " " + location[1] * 25);
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("Cant go off the screen.");
                }
            }

            if(keyEvent.getCode().equals(KeyCode.UP)) {
//                System.out.println("Current spot is: " + location[0] * 25 + " " + location[1] * 25);
                try {
                    if (map[location[0]][location[1] - 1] == 0) {
//                    System.out.println("Looking at this spot for empty space: " + location[0] * 25 + " " + location[1] * 25);
                        gc.clearRect(location[0] * 25, location[1] * 25, 25, 25);
                        location[1] -= 1;
                        gc.fillRect(location[0] * 25, location[1] * 25, 25, 25);
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("Cant go off the screen.");
                }
            }

            if(keyEvent.getCode().equals(KeyCode.LEFT)) {
//                System.out.println("Current spot is: " + location[0] * 25 + " " + location[1] * 25);
                try {
                    if (map[location[0] - 1][location[1]] == 0) {
//                    System.out.println("Looking at this spot for empty space: " + location[0] * 25 + " " + location[1] * 25);
                        gc.clearRect(location[0] * 25, location[1] * 25, 25, 25);
                        location[0] -= 1;
                        gc.fillRect(location[0] * 25, location[1] * 25, 25, 25);
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("Cant go off the screen.");
                }
            }

            if(keyEvent.getCode().equals(KeyCode.RIGHT)) {
//                System.out.println("Current spot is: " + location[0] * 25 + " " + location[1] * 25);
                try {
                    if (map[location[0] + 1][location[1]] == 0) {
//                    System.out.println("Looking at this spot for empty space: " + location[0] * 25 + " " + location[1] * 25);
                        gc.clearRect(location[0] * 25, location[1] * 25, 25, 25);
                        location[0] += 1;
                        gc.fillRect(location[0] * 25, location[1] * 25, 25, 25);
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("Cant go off the screen.");
                }
            }


        }



    }
}