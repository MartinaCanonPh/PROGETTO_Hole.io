package directions;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("right")
public class Right implements Direction{
	
	@Param(0)
	private int x;
	
	@Param(1)
	private int y;
	
	public Right() {}
	

	public Right(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}


	@Override
	public int getX() {
		// TODO Auto-generated method stub
		return x;
	}

	@Override
	public int getY() {
		// TODO Auto-generated method stub
		return y;
	}

	@Override
	public void setX(int x) {
		// TODO Auto-generated method stub
		this.x=x;
	}

	@Override
	public void setY(int y) {
		// TODO Auto-generated method stub
		this.y=y;
	}

	@Override
	public String toString() {
		return "Right [x=" + x + ", y=" + y + "]";
	}

}
