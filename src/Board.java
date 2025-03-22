import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import pieces.*;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Board extends JPanel{
	
	//white pieces
	Pawn Wpawn1 = new Pawn(12, 610, "white");
	Pawn Wpawn2 = new Pawn(112, 610, "white");
	Pawn Wpawn3 = new Pawn(212, 610, "white");
	Pawn Wpawn4 = new Pawn(312, 610, "white");
	Pawn Wpawn5 = new Pawn(412, 610, "white");
	Pawn Wpawn6 = new Pawn(512, 610, "white");
	Pawn Wpawn7 = new Pawn(612, 610, "white");
	Pawn Wpawn8 = new Pawn(712, 610, "white");
	
	Rook Wrook1 = new Rook(12, 710, "white");
	Knight Wknight1 = new Knight(112, 710, "white");
	Bishop Wbishop1 = new Bishop(212, 710, "white");
	King Wking = new King(312, 710, "white");
	Queen Wqueen = new Queen(412, 710, "white");
	Bishop Wbishop2 = new Bishop(512, 710, "white");
	Knight Wknight2 = new Knight(612, 710, "white");
	Rook Wrook2 = new Rook(712, 710, "white");
	
	//black pieces
	Pawn Bpawn1 = new Pawn(12, 110, "black");
	Pawn Bpawn2 = new Pawn(112, 110, "black");
	Pawn Bpawn3 = new Pawn(212, 110, "black");
	Pawn Bpawn4 = new Pawn(312, 110, "black");
	Pawn Bpawn5 = new Pawn(412, 110, "black");
	Pawn Bpawn6 = new Pawn(512, 110, "black");
	Pawn Bpawn7 = new Pawn(612, 110, "black");
	Pawn Bpawn8 = new Pawn(712, 110, "black");
	
	Rook Brook1 = new Rook(12, 10, "black");
	Knight Bknight1 = new Knight(112, 10, "black");
	Bishop Bbishop1 = new Bishop(212, 10, "black");
	King Bking = new King(312, 10, "black");
	Queen Bqueen = new Queen(412, 10, "black");
	Bishop Bbishop2 = new Bishop(512, 10, "black");
	Knight Bknight2 = new Knight(612, 10, "black");
	Rook Brook2 = new Rook(712, 10, "black");
	
	
    public Board() {
        this.setPreferredSize(new Dimension(800, 800));
        this.setBackground(new Color(0, 0, 0));
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        int state = 0;
        for(int i = 0; i < 800; i+=100, state++) {   //rows
            for(int j = 0; j < 800; j+=100, state++) {   //columns
                if(state % 2 == 0) {
                    g.setColor(new Color(118, 150, 86));
                }
                else {
                    g.setColor(new Color(238, 238, 210));
                }
                g.fillRect(i, j, 100, 100);                
            }
            if(state % 2 == 0) {
                g.setColor(new Color(238, 238, 210));
            }
            else {
                g.setColor(new Color(118, 150, 86));
            }
        }

        
        Wpawn1.draw(g, this);
        Wpawn2.draw(g, this);
        Wpawn3.draw(g, this);
        Wpawn4.draw(g, this);
        Wpawn5.draw(g, this);
        Wpawn6.draw(g, this);    
        Wpawn7.draw(g, this);
        Wpawn8.draw(g, this);
        Wrook1.draw(g, this);
        Wknight1.draw(g, this);
        Wbishop1.draw(g, this);
        Wqueen.draw(g, this);
        Wking.draw(g, this);
        Wbishop2.draw(g, this);
        Wknight2.draw(g, this);
        Wrook2.draw(g, this);
        
        Bpawn1.draw(g, this);
        Bpawn2.draw(g, this);
        Bpawn3.draw(g, this);
        Bpawn4.draw(g, this);
        Bpawn5.draw(g, this);
        Bpawn6.draw(g, this);    
        Bpawn7.draw(g, this);
        Bpawn8.draw(g, this);
        Brook1.draw(g, this);
        Bknight1.draw(g, this);
        Bbishop1.draw(g, this);
        Bqueen.draw(g, this);
        Bking.draw(g, this);
        Bbishop2.draw(g, this);
        Bknight2.draw(g, this);
        Brook2.draw(g, this);

    }


}
