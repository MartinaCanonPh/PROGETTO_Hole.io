package graphic;

import javax.swing.*;
import logic.CollisionsManager;
import logic.KeyEvents;
import logic.Map;
import logic.HoleLogic;
import settings.*;
import java.awt.*;
import java.util.ArrayList;

public class Scene extends JPanel implements Runnable{
	private static final long serialVersionUID = 1L;
    public static ArrayList<Rectangle> bricks;
    public static ArrayList<Rectangle> items;
    public static ArrayList <HoleLogic> hole;
    public KeyEvents keyEvents;
    private CollisionsManager c;
    public Map map;
    public static boolean dead;
    public static boolean levelTwo;
    private Thread panel;
    public static boolean stopGame,runGame;
    public static boolean AiModeOn;
    
    private void setValues()
    {
    	dead=false; 
    	levelTwo=false;
    	stopGame=false; runGame=false;
    	AiModeOn=false;
    }
    
    public Scene()
    {
    	setValues();
    	bricks = new ArrayList<Rectangle>();
    	items = new ArrayList<Rectangle>();
        hole = new ArrayList<HoleLogic>();
    	map = new Map();

    }
   
    public static void returnToMenu() {
    	 Graphic.setScene(new Scene());
    	 stopGame=true;
    	 runGame=false;
    	 Sound.gameStop();
    	 Sound.game.setFramePosition(0);
    	 //Sound.intro.setFramePosition(Sound.intro.getFrameLength());
    	 Graphic.setMenu(new Menu());
    	 Graphic.setOptions(new Options());
    	 Graphic.setCredits(new Credits());
		 Graphic.setPanel(Graphic.getMenu());
    }

     void game(String s) {
    	this.setBackground(Color.BLACK);
    	if(levelTwo && s.equals("level1.txt"))
    		s="level2.txt";
    	map.readmap(s);
    	runGame=true;
    	
    	c = new CollisionsManager(hole);
    	keyEvents = new KeyEvents(hole);
    	
        Sound.newGame();

        panel = new Thread(this);
        panel.start();
    }
   
    @Override
    protected void paintComponent(Graphics g) {
    	super.paintComponent(g);
    	map.Drawmap(g); 
    	if(hole.size()==2) {
	    	if(dead==false)
	    		hole.get(0).drawHole(g);
    	}
    	else if(hole.size()==1) {
    		if(dead==false)
    	    	hole.get(0).drawHole(g);
    	}
    }
  
     
    @Override
	public void run() {
		while(!stopGame) {
	
			Sound.Game();
			if(stopGame==false)
			{
				if(hole.size()==2) 
				{
					if(dead==false) hole.get(0).move();
				}
				else if(hole.size()==1)
					hole.get(0).move();
				
				c.eat(map.levelmap);

				repaint();				
				try {
					if(AiModeOn)
						Thread.sleep(5);
					else
						Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
    }
}