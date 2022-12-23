//summer project 1
//progress stamp: working on checkCollisions()


//we removed some import statments becuse they are children of theses
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;



public class GamePanel extends JPanel implements ActionListener {
    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HIGHT = 600;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = (SCREEN_HIGHT*SCREEN_WIDTH)/UNIT_SIZE;
    static final int DELAY = 100;
    static final int[] x = new int [GAME_UNITS];//codinates of the body parts of the snake including the head
    static final int[] y = new int [GAME_UNITS];
    int bodyParts = 6;//inital body parts
    int applesEaten;
    int appleX;
    int appleY;
    char direction = 'D';//initally snake will go up
    boolean running = false;
    Timer timer;//part of swing
    Random random;
    GamePanel() {
        random = new Random();//you can use to create random ints> we used it for apple cordinates 
        //remember there is a super call her to Jframe and all other hierical classes
        //this< refrences java.swing object panel and its methods
        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);//a specifc fild can recive the input
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }

    public void startGame() {
        newApple();
        running = true;
        timer = new Timer(DELAY,this);//(int,ActionListener object)//in between event delay
        timer.start();//causes timer to start,sending action events to listeners//actionPreformed
    }
//GRAPHICS
    public void paintComponent(Graphics g) {//has a super impmentation.so it is not called in this class
        super.paintComponent(g);
        draw(g);
    }
//GRAPHICS
    public void draw(Graphics g) {//will run on start//continoue drawing?
        if(running){
            //Graphics object g has methods that let you draw
            for(int i=0; i<SCREEN_HIGHT/UNIT_SIZE; i++){//lines drawn vertically and horizintally for grid
                g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HIGHT);//(0,0)(0,600) vertical line spaced out by unitsize
                g.drawLine(0,i*UNIT_SIZE, SCREEN_WIDTH,i*UNIT_SIZE);//you multiply unit_size becaue you want unit spaceing
            }
            g.setColor(Color.red);//make apple and plot random
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);//fill upper corners

            for(int i=0; i<bodyParts; i++){//snake
                if(i==0){//for head
                    g.setColor(Color.green);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
                else{//for body
                    g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));//multi color
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
                
            }
            //score board
            g.setColor(Color.red);
            g.setFont(new Font("Ink Free",Font.BOLD,40));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: " + applesEaten))/2, g.getFont().getSize());
        }
        else{
            gameOver(g);
        }
    }
    public void newApple(){//create random apple cordinate
        //cordiates for apple; in a unit square
        //remember 25,25 is square(0,0)>thats why we multiply by unit size
        appleX = random.nextInt((int)SCREEN_WIDTH/UNIT_SIZE)*UNIT_SIZE;//random cordnate (0-(screenwidth/unitsize-1))*25
        appleY = random.nextInt((int)SCREEN_HIGHT/UNIT_SIZE)*UNIT_SIZE;

    }
    public void move() {//?
        for(int i=bodyParts; i>0; i--){//loop body parts size
            //body part//follow head
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        switch(direction){//manupulating head//index 0 is head
            case 'U':
            y[0] = y[0]-UNIT_SIZE;//remember the panell has cordinates like an 2d array, (0,0) (c,r) is the top right
            break;
            case 'D':
            y[0] = y[0]+UNIT_SIZE;
            break;
            case 'L':
            x[0] = x[0]-UNIT_SIZE;
            break;
            case 'R':
            x[0] = x[0]+UNIT_SIZE;
        }
    }

    public void checkApple() {
        if((x[0]==appleX)&&(y[0]==appleY)){
            bodyParts++;
            applesEaten++;
            newApple();
        }

    }

    public void checkCollisions() {
        //check if head touches its body
        for(int i= bodyParts; i>0; i--){
            if((x[0]==x[i])&&(y[0]==y[i])){
                running = false;
            }
        }
        //check if head collides with border
        if(x[0]<0) running = false;//leftBorder
        if(x[0]>SCREEN_WIDTH) running = false;//rightBorder
        if(y[0]<0) running = false;//topBorder
        if(y[0]>SCREEN_HIGHT) running = false;//bottomBorder

        if(running==false) timer.stop();
    }

    public void gameOver(Graphics g) {
        //gameOver text
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free",Font.BOLD,75));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Game Over", (SCREEN_WIDTH - metrics1.stringWidth("Game Over"))/2, SCREEN_HIGHT/2);
        
        g.setColor(Color.red);
            g.setFont(new Font("Ink Free",Font.BOLD,40));
            FontMetrics metrics2 = getFontMetrics(g.getFont());
            g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics2.stringWidth("Score: " + applesEaten))/2, g.getFont().getSize());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(running){
            move();//im guessing repaint is called after this esqutes implistly which basically calls draw()
            checkApple();
            checkCollisions();
        }
        repaint();//call to paint(draw?)
    }

    // inner class
    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch(e.getKeyCode()){
                case KeyEvent.VK_LEFT:
                    if(direction != 'R') direction = 'L';
                    break;
                case KeyEvent.VK_RIGHT:
                    if(direction != 'L') direction = 'R';
                    break;
                case KeyEvent.VK_UP:
                    if(direction != 'D') direction = 'U';
                    break;
                case KeyEvent.VK_DOWN:
                    if(direction != 'U') direction = 'D';
                    break;
            }

        }
    }

}
