package logic;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;
import javax.swing.ImageIcon;
import graphic.Scene;
import settings.Settings;

public class Map {
	public int levelmap[][];
	private BufferedReader file ;
	
	public Map() {
		levelmap=new int[Settings.CELLS][Settings.CELLS];
	}
	
    public void readmap(String s){
    	try {
    		Scene.bricks.clear();
    		Scene.hole.clear();
			Scene.items.clear(); 
			int i=0;
			file = new BufferedReader(new FileReader("assets"+File.separator+s));
			while(file.ready()) {
				StringTokenizer st = new StringTokenizer(file.readLine()," ");
				int j=0;
				while(st.hasMoreTokens())
				{
					String cell=st.nextToken();
					switch(cell) {
						case "0":
							levelmap[i][j]=0;
							Scene.bricks.add(new Rectangle(j*Settings.SIZE,i*Settings.SIZE, Settings.SIZE, Settings.SIZE));
							break;
						case "1":
							levelmap[i][j]=1;
							break;
						case "2":
							levelmap[i][j]=2;
							Scene.items.add(new Rectangle(j*Settings.SIZE,i*Settings.SIZE, Settings.SIZE, Settings.SIZE));
							break;
						case "3":
							levelmap[i][j]=3;
							Scene.items.add(new Rectangle(j*Settings.SIZE,i*Settings.SIZE, Settings.SIZE, Settings.SIZE));
							break;
						case "4":
							levelmap[i][j]=4;
							Scene.hole.add(0,new HoleLogic(j*Settings.SIZE,i*Settings.SIZE,Settings.holeImg,35,35));
							break;
						case "5":
							levelmap[i][j]=5;
							Scene.items.add(new Rectangle(j*Settings.SIZE,i*Settings.SIZE, Settings.SIZE, Settings.SIZE));
						default:
							break;
						}
					j++;
				}
				i++;
			}
			file.close();
    	}
    	
		catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public void Drawmap(Graphics g) {
    	ImageIcon blocco = new ImageIcon(Settings.brickImg);
    	ImageIcon frutta = new ImageIcon(Settings.fruitImg);
    	ImageIcon flower = new ImageIcon(Settings.flowerImg);
    	ImageIcon edifice = new ImageIcon(Settings.edificeImg);
    	for(int i=0; i<Settings.CELLS; i++) {
			for(int j=0; j<Settings.CELLS; j++) {
				switch (levelmap[i][j]) {
					case 0:
						g.drawImage(blocco.getImage(),j*Settings.SIZE,i*Settings.SIZE,null);
						break;
					case 1:
						g.setColor(Color.BLACK);
						g.fillRect(j*Settings.SIZE, i*Settings.SIZE, Settings.SIZE, Settings.SIZE);
						break;
					case 2:
						g.drawImage(frutta.getImage(),j*Settings.SIZE,i*Settings.SIZE,null);
						break;
					case 3:
						g.drawImage(flower.getImage(),j*Settings.SIZE,i*Settings.SIZE,null);
						break;
					case 5:
						g.drawImage(edifice.getImage(),j*Settings.SIZE,i*Settings.SIZE,null);
					default:
						break;
				}
			}
    	}
    	
    	////// NB lasciare per avere maggiore fluidità
		for(Rectangle r:Scene.bricks) {
			g.setColor(Color.YELLOW);
			g.drawRect(r.x, r.y, r.width, r.height);
		}
    }
}



