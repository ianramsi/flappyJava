import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;

public class Flappy extends JPanel implements ActionListener, KeyListener {
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
    int birdWidth = 40;    // Made bird wider
    int birdHeight = 40;   // Made bird taller
    
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
    int pipeWidh = 80;     // Made pipes wider
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

    Flappy(){
        setPreferredSize(new Dimension(boardWidth, boardHeight));

        setFocusable(true);
        addKeyListener(this);

        //load images
        backgroundImg = new ImageIcon("src/assets/flappybirdbkgd.png").getImage();
        birdImg = new ImageIcon("src/assets/flappyBird.gif").getImage();
        topPipeImg = new ImageIcon("src/assets/topPipe.png").getImage();
        bottomPipeImg = new ImageIcon("src/assets/bottomPipe.png").getImage();


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

        // Ensure pipes are placed with minimum gap from bird's starting position
        int minPipeY = birdY - pipeHeight/2;
        int maxPipeY = birdY + pipeHeight/2;
        int randomPipeY = (int)(pipeY - pipeHeight/4 - Math.random()*(pipeHeight/2));
        randomPipeY = Math.max(minPipeY, Math.min(maxPipeY, randomPipeY));
        int openingSpace = boardHeight/4;
        Pipe topPipe = new Pipe(topPipeImg);
        topPipe.y = randomPipeY;
        pipes.add(topPipe);

        Pipe bottomPipe = new Pipe(bottomPipeImg);
        bottomPipe.y = topPipe.y + pipeHeight + openingSpace;
        pipes.add(bottomPipe);

    }
@Override
public void paint(Graphics g) {
    super.paint(g);
    draw(g);
}
    public void draw(Graphics g) {
        //draw background
        g.drawImage(backgroundImg, 0, 0, this.boardWidth, this.boardHeight, null);
    
        //draw bird
        g.drawImage(bird.img, bird.x, bird.y, bird.width, bird.height, null);

        //draw pipes
        for (int i = 0; i < pipes.size(); i++) {
            Pipe pipe = pipes.get(i);
            g.drawImage(pipe.img, pipe.x, pipe.y, pipe.width, pipe.height, null);
        }

        //draw score
        g.setColor(Color.white);

        g.setFont(new Font("Arial", Font.PLAIN, 32));
        if (gameOver) {
            g.drawString("Game Over: " + String.valueOf((int) score), 10, 35);
        }
        else {
            g.drawString(String.valueOf((int) score), 10, 35);
        }
    }
    
    public void move () {
        velocityY += gravity;
        bird.y += velocityY;

        bird.y = Math.max(bird.y, 0); //apply gravity to the bird

        //pipes
        for (int i = 0; i < pipes.size(); i++) {
            Pipe pipe = pipes.get(i);
            pipe.x += velocityX;

            if(!pipe.passed && bird.x > pipe.x + pipe.width) {
                score = score + 0.5;
                pipe.passed = true; //bird passed the pipe

            }
            
            if (collision(bird, pipe)){
                System.out.println("Collision detected with pipe at y=" + pipe.y);
                gameOver = true;
            }            
        }
        if(bird.y > boardHeight) {
            System.out.println("Bird fell below screen at y=" + bird.y);
            gameOver = true;
        }
    }

    boolean collision(Bird bird, Pipe pipe) {
        return bird.x < pipe.x + pipe.width &&
               bird.x + bird.width > pipe.x &&
               bird.y < pipe.y + pipe.height &&
               bird.y + bird.height > pipe.y;
    }

    @Override
    public void actionPerformed(ActionEvent e) { //called every x milliseconds by gameLoop timer
        move();
        repaint();
        if (gameOver) {
            placePipeTimer.stop();
            gameLoop.stop();
        }
    }

    @Override
    public void keyPressed(KeyEvent key) {
        if (key.getKeyCode() == KeyEvent.VK_SPACE) {
            System.out.println("JUMP!!!!");
            velocityY = -9;

            if (gameOver) {
                //restart the game
                bird.y = birdY;
                velocityY = 0;
                pipes.clear();
                score = 0;
                gameLoop.start();
                placePipeTimer.start();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent key) {}

    @Override
    public void keyTyped(KeyEvent key) {}
}
