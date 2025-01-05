import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;

public class FlappyBird extends JPanel implements ActionListener, KeyListener {
    int boardWidth = 360;
    int boardHeight = 640;

    //images
    Image backgroundImg;
    Image birdImg;
    Image topPipeImg;
    Image bottomPipeImg;

    //bird Class
    int birdX = boardWidth/8;
    int birdY = boardHeight/2;
    int birdWidth = 4=34;
    int birdHeight = 24;
    
    class Bird {
        int x = birdX;
        int y = birdY;
        int width = birdWidth;
        int height = birdHeight;
        Image img;

        Bird(Image img){
            this.img = img;
        }
    }

    //pipe class
    int pipeX = boardWidth;
    int pipeY = 0;
    int pipeWidh = 64; //scale to 1/6
    int pipeHeight = 512;

    class Pipe {
        int x = pipeX;
        int y = pipeY;
        int width = pipeWidh;
        int height = pipeHeight;
        Image img;
        boolean passed = false;

        Pipe(Image img){
            this.img = img;
        }   
    }

    //game play
    Bird bird;
    int velocityX  = -4; //move to the left by 4 pixels
    int velocityY = 0;
    int gravity = 1; //the bird will go down 1 pixel every frame automatically

    ArrayList<Pipe> pipes;
    Random random = new Random();

    Timer gameLoop;
    Timer placePipeTimer;
    boolean gameOver = false;
    double score = 0;

    FlappyBird(){
        setPreferredSize(new Dimension(boardWidth, boardHeight));

        setFocusable(true);
        addKeyListener(this);

        //load images
        backgroundImg = new ImageIcon (getClass().getResource("assets/flappybirdbkgd.png")).getImage();
        birdImg = new ImageIcon (getClass().getResource("assets/flappyBird.gif")).getImage();
        topPipeImg = new ImageIcon (getClass().getResource("assets/topPipe.png")).getImage();
        bottomPipeImg = new ImageIcon (getClass().getResource("assets/bottomPipe.png")).getImage();


        //bird
        bird = new Bird(birdImg);
        pipes = new ArrayList<Pipe>();

        //place pipes
        placePipeTimer = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //code to be executed
                placePipes();
            }
        });
        placePipeTimer.start();
        //game timer
        gameLoop = new Timer(1000/60, this);//how long to start timer after, miliseconds gone between frames
        gameLoop.start();
    }

    void placePipes() {

    }
}