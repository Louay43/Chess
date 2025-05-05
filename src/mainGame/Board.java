package mainGame;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import pieces.*;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@SuppressWarnings("serial")
public class Board extends JPanel{
	
	//white pieces
	Pawn Wpawn1 = new Pawn(0, 600, "white");	Rook Wrook1 = new Rook(0, 700, "white");
	Pawn Wpawn2 = new Pawn(100, 600, "white");	Knight Wknight1 = new Knight(100, 700, "white");
	Pawn Wpawn3 = new Pawn(200, 600, "white");	Bishop Wbishop1 = new Bishop(200, 700, "white");
	Pawn Wpawn4 = new Pawn(300, 600, "white");	King Wking = new King(300, 700, "white");
	Pawn Wpawn5 = new Pawn(400, 600, "white");	Queen Wqueen = new Queen(400, 700, "white");
	Pawn Wpawn6 = new Pawn(500, 600, "white");	Bishop Wbishop2 = new Bishop(500, 700, "white");
	Pawn Wpawn7 = new Pawn(600, 600, "white");	Knight Wknight2 = new Knight(600, 700, "white");
	Pawn Wpawn8 = new Pawn(700, 600, "white");	Rook Wrook2 = new Rook(700, 700, "white");
	
		
	//black pieces
	Pawn Bpawn1 = new Pawn(0, 100, "black");	Rook Brook1 = new Rook(0, 0, "black");
	Pawn Bpawn2 = new Pawn(100, 100, "black");	Knight Bknight1 = new Knight(100, 0, "black");
	Pawn Bpawn3 = new Pawn(200, 100, "black");	Bishop Bbishop1 = new Bishop(200, 0, "black");
	Pawn Bpawn4 = new Pawn(300, 100, "black");	King Bking = new King(300, 0, "black");
	Pawn Bpawn5 = new Pawn(400, 100, "black");	Queen Bqueen = new Queen(400, 0, "black");
	Pawn Bpawn6 = new Pawn(500, 100, "black");	Bishop Bbishop2 = new Bishop(500, 0, "black");
	Pawn Bpawn7 = new Pawn(600, 100, "black");	Knight Bknight2 = new Knight(600, 0, "black");
	Pawn Bpawn8 = new Pawn(700, 100, "black");	Rook Brook2 = new Rook(700, 0, "black");
	
	public ArrayList<Piece> piecesOnBoard = new ArrayList<>();
	
	
	
    public Board() {
        this.setPreferredSize(new Dimension(800, 800));
        this.setLayout(null);
        
        piecesOnBoard.add(Wpawn1);	piecesOnBoard.add(Bpawn1); 		 piecesOnBoard.add(Wrook1);		piecesOnBoard.add(Brook1);     
        piecesOnBoard.add(Wpawn2);	piecesOnBoard.add(Bpawn2);		 piecesOnBoard.add(Wknight1);	piecesOnBoard.add(Bknight1);   
        piecesOnBoard.add(Wpawn3);	piecesOnBoard.add(Bpawn3);       piecesOnBoard.add(Wbishop1);    piecesOnBoard.add(Bbishop1);   
        piecesOnBoard.add(Wpawn4);	piecesOnBoard.add(Bpawn4);       piecesOnBoard.add(Wking);		piecesOnBoard.add(Bking);      
        piecesOnBoard.add(Wpawn5);	piecesOnBoard.add(Bpawn5);       piecesOnBoard.add(Wqueen);		piecesOnBoard.add(Bqueen);     
        piecesOnBoard.add(Wpawn6);	piecesOnBoard.add(Bpawn6);       piecesOnBoard.add(Wrook2);		piecesOnBoard.add(Brook2);     
        piecesOnBoard.add(Wpawn7);	piecesOnBoard.add(Bpawn7);       piecesOnBoard.add(Wknight2);	piecesOnBoard.add(Bknight2);   
        piecesOnBoard.add(Wpawn8);	piecesOnBoard.add(Bpawn8);       piecesOnBoard.add(Wbishop2); 	piecesOnBoard.add(Bbishop2);   
        
        
        for(Piece piece : piecesOnBoard) {
        	this.add(piece);
        }
        
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Component clicked = getComponentAt(e.getPoint());

                //If the user clicked on something that is NOT a JLabel, it must be the empty board
                if (!(clicked instanceof JLabel)) {
                    if (Piece.selectedPiece != null) {
                        Piece.selectedPiece.removePreview();
                        Piece.selectedPiece = null;
                        repaint();
                    }
                }
            }
        });


    }
    
    public Piece getPieceAt(int x, int y) {	//if there is a pieece return it if not return nulll
        for (Piece piece : piecesOnBoard) {
            if (piece.XCoordinate == x && piece.YCoordinate == y) {
                return piece;
            }
        }
        return null;
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


    }




}