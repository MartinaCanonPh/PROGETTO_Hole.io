package ai;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("itemPosition")
public class Item {
	
	@Param(0)
	private int x;
	
	@Param(1)
	private int y;
	
	public Item() {}
	
	public Item(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}

	public int getX() {return x;}
	public void setX(int x) {this.x = x;}

	public int getY() {return y;}
	public void setY(int y) {this.y = y;}

	@Override
	public String toString() {
		return "Item [x=" + x + ", y=" + y + "]";
	}
	
}
