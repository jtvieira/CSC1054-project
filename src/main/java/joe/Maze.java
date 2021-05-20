package joe;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
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
    //for the maze to be drawn
    int [] location = new int[2];
    //variable to keep track of which row the program is drawing
    int count = 0;

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
    }

    public void drawMaze(int[][] map) {
        location[0] = 0;
        location[1] = 0;
        int[] here = {0, 0};

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
//        String hello = mapString(mazeMap);
//        System.out.println(hello);
        return mazeMap;
    }

    public static void main(String[] args) {
        launch();
    }


}