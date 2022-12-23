import javax.swing.JFrame;

public class GameFrame extends JFrame {
    public GameFrame(){
        this.add(new GamePanel());//create and add GamePanel instance
        //now finish constucting the frame
        this.setTitle("Snake");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();//will fit any added componets added to the frame
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        
    }

}
