package com.mycompany.flappybird;
//@author Zihan Manbit

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.sound.sampled.*;
import javax.swing.*;

public class GamePanel extends JPanel implements ActionListener, KeyListener {

    int boardWidth = 360;
    int boardHeight = 640;

    //Images
    Image backgroundImg;
    Image birdImg;
    Image topPipeImg;
    Image bottomPipeImg;

    //Bird
    int birdX = boardWidth / 8;
    int birdY = boardHeight / 2;
    int birdWidth = 34;
    int birdHeight = 24;

    class Bird {

        int x = birdX;
        int y = birdY;
        int width = birdWidth;
        int height = birdHeight;
        Image img;

        Bird(Image img) {
            this.img = img;
        }
    }

    //Pipes
    int pipeX = boardWidth;
    int pipeY = 0;
    int pipeWidth = 64;
    int pipeHeight = 512;

    class Pipe {

        int x = pipeX;
        int y = pipeY;
        int width = pipeWidth;
        int height = pipeHeight;
        Image img;
        boolean passed = false;

        Pipe(Image img) {
            this.img = img;
        }
    }

    //game logic
    Bird bird;
    int velocityX = -4;
    int velocityY = 0;
    int gravity = 1;

    ArrayList<Pipe> pipes;
    Random random = new Random();

    Timer gameLoop;
    Timer placePipesTimer;

    boolean gameOver = false;
    boolean gameStarted = false;
    boolean easyMode=false;
    boolean hardMode=false;
    static long highscore = 0;
    double score = 0;

    Clip jumpClip;
    Clip pointClip;
    Clip hitClip;
    Clip dieClip;
    Clip swooshClip;
    Clip backgroundClip;

    GamePanel() {
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        setFocusable(true);
        addKeyListener(this);

        //loading image files
        backgroundImg = new ImageIcon(getClass().getResource("./flappybirdbg.png")).getImage();
        birdImg = new ImageIcon(getClass().getResource("./flappy_bird.gif")).getImage();
        topPipeImg = new ImageIcon(getClass().getResource("./toppipe.png")).getImage();
        bottomPipeImg = new ImageIcon(getClass().getResource("./bottompipe.png")).getImage();

        //loading audio files
        try {
            jumpClip = AudioSystem.getClip();
            jumpClip.open(AudioSystem.getAudioInputStream(getClass().getResource("./jump.wav")));

            pointClip = AudioSystem.getClip();
            pointClip.open(AudioSystem.getAudioInputStream(getClass().getResource("./point.wav")));

            hitClip = AudioSystem.getClip();
            hitClip.open(AudioSystem.getAudioInputStream(getClass().getResource("./hit.wav")));

            dieClip = AudioSystem.getClip();
            dieClip.open(AudioSystem.getAudioInputStream(getClass().getResource("./die.wav")));

            swooshClip = AudioSystem.getClip();
            swooshClip.open(AudioSystem.getAudioInputStream(getClass().getResource("./swooshing.wav")));

            backgroundClip = AudioSystem.getClip();
            backgroundClip.open(AudioSystem.getAudioInputStream(getClass().getResource("./Tintin.wav")));
        } catch (Exception e) {
            e.printStackTrace();
        }

        //bird
        bird = new Bird(birdImg);
        //pipes
        pipes = new ArrayList<Pipe>();

        //place pipes timer
        placePipesTimer = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                placePipes();
            }
        });

        //game timer
        gameLoop = new Timer(1000 / 60, this);

        playBackgroundSound();
    }

    //play jump sound
    public void playJumpSound() {
        if (jumpClip != null) {
            jumpClip.setFramePosition(0);
            jumpClip.start();
        }
    }
    //play point sound
    public void playPointSound() {
        if (pointClip != null) {
            pointClip.setFramePosition(0);
            pointClip.start();
        }
    }
    //play hit sound
    public void playHitSound() {
        if (hitClip != null) {
            hitClip.setFramePosition(0);
            hitClip.start();
        }
    }
    //play die sound
    public void playDieSound() {
        if (dieClip != null) {
            dieClip.setFramePosition(0);
            dieClip.start();
        }
    }
    //play swooshing sound
    public void playSwooshSound() {
        if (swooshClip != null) {
            swooshClip.setFramePosition(0);
            swooshClip.start();
        }
    }
    //play background sound
    public void playBackgroundSound() {
        if (backgroundClip != null) {
            backgroundClip.setFramePosition(0);
            backgroundClip.start();
            FloatControl gainControl = (FloatControl) backgroundClip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-3.0f); //reducing the volume
        }
    }

    public void placePipes() {
        int randomPipeY = (int) (pipeY - pipeHeight / 4 - Math.random() * (pipeHeight / 2));
        int openingSpace = boardHeight / 4;

        Pipe topPipe = new Pipe(topPipeImg);
        topPipe.y = randomPipeY;
        pipes.add(topPipe);

        Pipe bottomPipe = new Pipe(bottomPipeImg);
        bottomPipe.y = topPipe.y + pipeHeight + openingSpace;
        pipes.add(bottomPipe);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        //background
        g.drawImage(backgroundImg, 0, 0, boardWidth, boardHeight, null);

        //bird
        g.drawImage(bird.img, bird.x, bird.y, bird.width, bird.height, null);

        //pipes
        for (int i = 0; i < pipes.size(); i++) {
            Pipe pipe = pipes.get(i);
            g.drawImage(pipe.img, pipe.x, pipe.y, pipe.width, pipe.height, null);
        }

        //welcome screen
        if (!gameStarted) {
            g.setColor(Color.yellow);
            g.setFont(new Font("Serif", Font.BOLD, 24));
            g.drawString("WELCOME TO FLAPPY BIRD", 10, 125);
            g.setColor(Color.cyan);
            g.setFont(new Font("Monospaced", Font.BOLD, 25));
            g.drawString("PLay by clicking SPACE", 12, 160);
            g.setColor(Color.white);
            g.setFont(new Font("Serif", Font.BOLD, 25));
            g.drawString("Press 'D' to play in Day Mode", 18, 195);
            g.setColor(Color.gray);
            g.setFont(new Font("Serif", Font.BOLD, 25));
            g.drawString("Press 'N' to play in Night Mode", 12, 230);
            g.drawImage(bird.img, bird.x, bird.y, bird.width, bird.height, null);
        }

        //score
        g.setColor(Color.yellow);
        g.setFont(new Font("Arial", Font.BOLD, 32));
        if (gameOver) {
            g.setFont(new Font("Serif", Font.BOLD, 32));
            g.drawString("GAME OVER", 10, 35);
            g.drawString("Score: " + String.valueOf((int) score), 10, 70);
            g.setColor(Color.white);
            g.setFont(new Font("Monospaced", Font.BOLD, 23));
            g.drawString("Press ENTER to play again", 5, 100);
            g.setFont(new Font("Monospaced", Font.BOLD, 19));
            if(easyMode)
                g.drawString("Press 'H' to shift to HARD Mode", 5, 128);
            else if(hardMode)
                g.drawString("Press 'E' to shift to EASY Mode", 5, 128);
        } 
        else if (!gameOver && gameStarted) {
            g.setColor(Color.white);
            g.setFont(new Font("Arial", Font.BOLD, 15));
            g.drawString("High Score: " + String.valueOf(highscore), 10, 20);
            g.setColor(Color.yellow);
            g.setFont(new Font("Serif", Font.BOLD, 32));
            g.drawString("Score: " + String.valueOf((int) score), 10, 55);
        }
    }

    public void move() {
        //bird
        velocityY += gravity;
        bird.y += velocityY;
        bird.y = Math.max(bird.y, 0);

        //pipes
        for (int i = 0; i < pipes.size(); i++) {
            Pipe pipe = pipes.get(i);
            pipe.x += velocityX;

            if (!pipe.passed && bird.x > pipe.x + pipe.width) {
                pipe.passed = true;
                score += 0.5;
                playPointSound();
            }

            if (collision(bird, pipe)) {
                gameOver = true;
                playHitSound();
            }
        }

        if (bird.y > boardHeight) {
            gameOver = true;
            playDieSound();
        }
    }

    public boolean collision(Bird a, Pipe b) {
        return a.x < b.x + b.width
                && a.x + a.width > b.x
                && a.y < b.y + b.height
                && a.y + a.height > b.y;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
        if (gameOver) {
            placePipesTimer.stop();
            gameLoop.stop();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE && gameStarted) {
            velocityY = -9;
            playJumpSound();
        }
        //play again
        if (e.getKeyCode() == KeyEvent.VK_ENTER && gameOver) {
            playSwooshSound();
            //restrart the game
            if (score > highscore) {
                highscore = (long) score;
            }
            bird.y = birdY;
            velocityY = 0;
            pipes.clear();
            score = 0;
            gameOver = false;
            gameLoop.start();
            placePipesTimer.start();
        }
        //easy mode
        if (e.getKeyCode() == KeyEvent.VK_E && gameOver && hardMode) {
            playSwooshSound();
            //restrart the game
            if (score > highscore) {
                highscore = (long) score;
            }
            bird.y = birdY;
            velocityY = 0;
            pipes.clear();
            score = 0;
            highscore = 0;
            gameOver = false;
            gameLoop.start();
            placePipesTimer = new Timer(1500, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    placePipes();
                }
            });
            placePipesTimer.start();
            velocityX = -4;
            easyMode=true;
            hardMode=false;
        }
        //hard mode
        if (e.getKeyCode() == KeyEvent.VK_H && gameOver && easyMode) {
            playSwooshSound();
            //restrart the game
            if (score > highscore) {
                highscore = (long) score;
            }
            bird.y = birdY;
            velocityY = 0;
            pipes.clear();
            score = 0;
            highscore = 0;
            gameOver = false;
            gameLoop.start();
            placePipesTimer = new Timer(750, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    placePipes();
                }
            });
            placePipesTimer.start();
            velocityX = -8;
            hardMode = true;
            easyMode=false;
        }        
        //day mode
        if (e.getKeyCode() == KeyEvent.VK_D && !gameStarted) {
            backgroundImg = new ImageIcon(getClass().getResource("./flappybirdbg.png")).getImage();
            gameStarted = true;
            playSwooshSound();
            gameLoop.start();
            placePipesTimer.start();
            easyMode=true;
        }
        //night mode
        if (e.getKeyCode() == KeyEvent.VK_N && !gameStarted) {
            backgroundImg = new ImageIcon(getClass().getResource("./fbBG_night.png")).getImage();
            gameStarted = true;
            playSwooshSound();
            gameLoop.start();
            placePipesTimer.start();
            easyMode=true;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
