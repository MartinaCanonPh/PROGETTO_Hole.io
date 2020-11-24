package logic;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import graphic.Scene;
import java.awt.event.KeyListener;
//import settings.Sound;

public class KeyEvents implements KeyListener {
    private ArrayList<HoleLogic> h;
    
    
    public KeyEvents(ArrayList<HoleLogic> h)
    {
        this.h = h;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

	@Override
    public void keyPressed(KeyEvent e) {
    	
    	///HOLE MOVEMENTS
		if(e.getKeyCode() == KeyEvent.VK_LEFT && h.get(0).canMove(h.get(0).getX()-h.get(0).speed, h.get(0).getY()))
        {	
        	h.get(0).button=HoleLogic.Direction.left;
        }
        
        else if(e.getKeyCode() == KeyEvent.VK_RIGHT && h.get(0).canMove(h.get(0).getX()+h.get(0).speed, h.get(0).getY()))
        {
        	h.get(0).button=HoleLogic.Direction.right;
        }
        
        else if(e.getKeyCode() == KeyEvent.VK_UP && h.get(0).canMove(h.get(0).getX(), h.get(0).getY()-h.get(0).speed))
        {
        	h.get(0).button=HoleLogic.Direction.up;
        }

        else if(e.getKeyCode() == KeyEvent.VK_DOWN && h.get(0).canMove(h.get(0).getX(), h.get(0).getY()+h.get(0).speed))
        {
        	h.get(0).button=HoleLogic.Direction.down;
        }
    	
         /////ESC
         if(e.getKeyCode()==KeyEvent.VK_ESCAPE)
        {
        	Scene.returnToMenu();
         }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}