//parent class for all pieces
package pieces;

import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Piece {
	public int XCoordinate;
	public int YCoordinate;
	public final int WIDTH = 75;	//every piece has the same fixed width and height
	public final int HEIGHT = 75;
	public String color;
	
	public Piece(int XCoordinate, int YCoordinate, String color) {
		this.XCoordinate = XCoordinate;
		this.YCoordinate = YCoordinate;
		this.color = color;
	}
	
	
	public void draw(Graphics g, JPanel panel) {
		if(this.color.equalsIgnoreCase("white")) {
			g.drawImage(new ImageIcon("./PiecesImg/"+this.getClass().toString().substring(13)+".png").getImage(), this.XCoordinate, this.YCoordinate, WIDTH, HEIGHT, panel);			
		}
		else {
			g.drawImage(new ImageIcon("./PiecesImg/"+this.getClass().toString().substring(13)+"1.png").getImage(), this.XCoordinate, this.YCoordinate, WIDTH, HEIGHT, panel);
		}
	}
}
