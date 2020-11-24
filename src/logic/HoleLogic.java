package logic;

import javax.swing.*;
import javax.swing.JOptionPane;

import ai.EmbASPManager;
import ai.Hole;
import ai.Item;
import ai.Brick;
import directions.Down;
import directions.Left;
import directions.Right;
import directions.Up;
import graphic.Scene;
import settings.Settings;
import java.io.File;
import java.awt.*;

public class HoleLogic{
	private String right_icon;
	private String left_icon;
	private String up_icon;
	private String down_icon;
	    
	public enum Direction{up,down,left,right};
	private Direction current;
	Direction button;
	int speed;
    private int x;
    private int y;
    private String image;
    private Rectangle rectangleHole;
    private int scaleX = 35, scaleY = 35 ;
    public static int grow = 0;
    private void loadImages()
    {
    	right_icon= "assets"+File.separator+"images"+File.separator+"hole_right.png";
    	left_icon= "assets"+File.separator+"images"+File.separator+"hole_left.png";
    	up_icon= "assets"+File.separator+"images"+File.separator+"hole_up.png";
    	down_icon= "assets"+File.separator+"images"+File.separator+"hole_down.png";
    	
    }
    
    public static EmbASPManager aiManager;
    public void initAiManager()
    {
		aiManager = new EmbASPManager(Hole.class, Up.class, Down.class, Left.class, Right.class, Item.class, Brick.class);
		aiManager.reset();
    }

    public HoleLogic(int x, int y, String image,int xx, int yy)
    {	
    	loadImages();
    	speed=4;
    	current=Direction.right;
    	button=null;
        setX(x);
        setY(y);
        setImage(image);
        setscaleX(xx);
        setscaleY(yy);
    }
    
    boolean canMove(int x,int y) {
    	rectangleHole=new Rectangle(x, y, getScaleX(),getScaleY());
    	for(Rectangle r: Scene.bricks) {
    			// down -> alessia
    		if( (getY()+getScaleY()) >= Settings.WINDOW_HEIGHT-Settings.CELL_SIZE )
    				setY( getY() - ( (getY()+getScaleY()) - (Settings.WINDOW_HEIGHT-Settings.CELL_SIZE) ) );
    			// up -> davide
    		else if( (getY()) <= Settings.CELL_SIZE )
				setY( getY()+(Settings.CELL_SIZE-getY()) );
    			//right -> Martina
    		else if( (getX()+getScaleX()) >= Settings.WINDOW_WIDTH-Settings.CELL_SIZE )
				setX( getX() - ( (getX()+getScaleX()) - (Settings.WINDOW_WIDTH-Settings.CELL_SIZE) ) );
    			// left -> Martina++;)
    		else if( (getX()) <= Settings.CELL_SIZE )
				setX( getX()+(Settings.CELL_SIZE-getX()) );
    		
    		if(rectangleHole.intersects(r))
    			return false;
    		
			
    	}    	
    	return true;
    }
    
    void setscaleX(int scaleX) {
    	this.scaleX = scaleX;
    }
    void setscaleY(int scaleY) {
    	this.scaleY = scaleY;
    }
    public int getScaleX() {return this.scaleX;}
    public int getScaleY() {return this.scaleY;}
    void setX(int x)
    {
    	if(x>450)
    		this.x=0;
    	else if(x<0)
    		this.x=450;
    	else
    		this.x = x;    
    }

    void setY(int y)
    {
    	if(y>450)
    		this.y=0;
    	else if(y<0)
    		this.y=450;
    	else
    		this.y = y;        }

    void setImage(String image)
    {
        if(image == null)
        {
            JOptionPane.showMessageDialog(null, "Image is null");
            System.exit(1);
        }
        else
            this.image = image;
    }

   
    int getY() {return y;}
    int getX() {return x;}
    String getImage() {return image;}
    
    public void drawHole(Graphics graphics)
    {	
    	switch(current) {
			case up:
				 setImage(up_icon);
				break;
			case down:
				setImage(down_icon);    			
				break;
			case left:
				setImage(left_icon);
				break;
			case right:
				setImage(right_icon);
		}
	
        ImageIcon img = new ImageIcon(image);
        Settings.scaleIcon(img, getScaleX(), getScaleY(),grow);
        graphics.drawImage(img.getImage(),x,y,null);
     
    }

    public void updatePosition() {
    	if(button==Direction.left  && canMove(getX()-speed, getY()))
          {	
              if(current!=HoleLogic.Direction.left)
              	  current=HoleLogic.Direction.left;
              	  setX(getX() - speed);
          }
    	
          else if(button==Direction.right && canMove(getX()+speed, getY()))
          {
        	  if(current!=HoleLogic.Direction.right)
              	  current=HoleLogic.Direction.right;
              	  setX(getX() + speed);
          }
    
          else if(button==Direction.up && canMove(getX(), getY()-speed))
          {
          	if(current!=HoleLogic.Direction.up)
          		current=HoleLogic.Direction.up;
          		setY(getY() - speed);
          }
    	
          else if(button==Direction.down && canMove(getX(), getY()+speed) )
          {
          	if(current!=HoleLogic.Direction.down)
          		current=HoleLogic.Direction.down;
          		setY(getY() + speed);
          }
    }
    
    public void DLVupdatePosition() {
   		initAiManager();
    	aiManager.setFacts(new Hole(this.x, this.y));
    	for(Rectangle r: Scene.bricks)
    	{
    		aiManager.setFacts(new Brick(r.x,r.y));
    	}
    	button=aiManager.getAnswerSets();
    	System.out.println("BUTTON BY DLV: "+button);
    	updatePosition();
    }

	public void move() {
		// TODO Auto-generated method stub
		if(Scene.AiModeOn)
		{
			DLVupdatePosition();
		}
		else
		{
			updatePosition();
			//initAiManager();
		}
	}
}