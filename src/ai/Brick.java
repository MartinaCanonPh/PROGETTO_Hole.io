package ai;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("brick")
public class Brick {
	
	@Param(0)
	private int x;
	
	@Param(1)
	private int y;
	
	public Brick() {}
	
	
	public Brick(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}


	public int getX() {
		return x;
	}


	public void setX(int x) {
		this.x = x;
	}


	public int getY() {
		return y;
	}


	public void setY(int y) {
		this.y = y;
	}


	@Override
	public String toString() {
		return "Brick [x=" + x + ", y=" + y + "]";
	}
	
	

}
