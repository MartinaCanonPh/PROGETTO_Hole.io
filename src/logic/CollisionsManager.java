package logic;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JOptionPane;
import settings.*;
import graphic.Scene;

public class CollisionsManager {
	private Rectangle player;

    private ArrayList<HoleLogic> h;
    private enum cmDirection{up,down,left,right,notLeft,notRight,notUp,notDown,up1,down1,left1,right1};
    private cmDirection current=cmDirection.right;
    private int score=0;
    private int cont = 1;
    public CollisionsManager(ArrayList<HoleLogic> h){
		this.h = h;
	}
        
    public void growUp() {
    	HoleLogic.grow+=10;
    	h.get(0).setscaleX(h.get(0).getScaleX()+HoleLogic.grow);
    	h.get(0).setscaleY(h.get(0).getScaleY()+HoleLogic.grow);
    	
    	
    }
        
   
    public void eat(int [][]levelmap) {
    	 
    	player = new Rectangle(h.get(0).getX(),h.get(0).getY(),h.get(0).getScaleX(),h.get(0).getScaleY());
    	for(int i=0; i<Scene.items.size(); i++) {
    		if(player.intersects(Scene.items.get(i))) {
    			Sound.nomNom();
    			double xx=  Scene.items.get(i).getX(); int xxx=(int) xx;
    			double yy=  Scene.items.get(i).getY(); int yyy=(int) yy;
				levelmap[yyy/Settings.SIZE][xxx/Settings.SIZE]=1;
				score+=100;
				Scene.items.remove(Scene.items.get(i));
				 
				if((score/100) % 5*cont == 0) 
				{
					growUp();
					if(Scene.levelTwo)
						cont+=2;
				}
    		}
    	} 
    	if(Scene.items.isEmpty())
		{    		
			JOptionPane.showMessageDialog(null, "YOUR SCORE!"+"\n"+"Score: "+score);
			score=0;
			cont=1;
			Scene.returnToMenu();
		}
    }
     
}