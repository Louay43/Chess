//parent class for all pieces
package pieces;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import mainGame.Board;



@SuppressWarnings("serial")
public abstract class Piece extends JLabel implements MouseListener{
	
	public static Pawn enPassantTarget = null;
	public static String currentTurn = "white";
	public static Piece selectedPiece = null;
	public static boolean inCheck = false;
    public int XCoordinate;
    public int YCoordinate;
    public final int WIDTH = 100;    
    public final int HEIGHT = 100;
    public String color;
    public ArrayList<JLabel> previewLabels = new ArrayList<>();
    public ArrayList<JLabel> clickCatchers = new ArrayList<>();


    public Piece(int XCoordinate, int YCoordinate, String color) {
        this.setBounds(XCoordinate, YCoordinate, WIDTH, HEIGHT);
        this.XCoordinate = XCoordinate;
        this.YCoordinate = YCoordinate;
        this.color = color;
        this.setOpaque(false);
        this.addMouseListener(this);
        
        
        // Draw the piece's image
        if (this.color.equalsIgnoreCase("white")) {
        	this.setIcon(new ImageIcon("./PiecesImg/" + getClass().getSimpleName() + ".png"));
        } 
        else {
        	this.setIcon(new ImageIcon("./PiecesImg/" + getClass().getSimpleName() + "1.png"));
        }
        
       
    }

    
    public abstract void showPreview();
     
    public void removePreview() {
    	
    	JPanel parent = (JPanel) this.getParent();
    	if (parent == null) return;
    	
    	// Remove all preview labels
    	for (JLabel label : previewLabels) {
    		parent.remove(label);
    	}
    	
    	// Remove all click-catcher labels
    	for (JLabel label : clickCatchers) {
    		parent.remove(label);
    	}
    	
    	previewLabels.clear();
    	clickCatchers.clear();
    	
    	parent.revalidate();
    	parent.repaint();
    	
    	selectedPiece = null;
    	
    }

    public abstract ArrayList<int[]> getPreviwIndex();
    
    public void eatPiece(Board board, Piece piece) {
    	if (piece != null) {
    		board.piecesOnBoard.remove(piece);
    		board.remove(piece);
    		board.revalidate();
    		board.repaint();
    	}
    }

    public void returnPiece(Board board, Piece piece) {
    	if(piece != null) {
    		board.piecesOnBoard.add(piece);
    		board.add(piece);
    		updateBoard();  		
    	}
    }

    public void move(int x, int y, int w, int h) {
    	
    	this.XCoordinate = x;
    	this.YCoordinate = y;
    	this.setBounds(XCoordinate, YCoordinate, w, h);
    	updateBoard();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    	if (!this.color.equals(currentTurn)) {
    		//Not your turn — ignore this click
    		System.out.println("It's not " + color + "'s turn.");
    		return;
    	}
    	
    	if (selectedPiece == null) {
    		selectedPiece = this;
    		showPreview();
    	} 
    	else if (selectedPiece == this) {
    		removePreview();
    		selectedPiece = null;
    	} 
    	else {
    		selectedPiece.removePreview();
    		selectedPiece = this;
    		showPreview();
    	}
    }
    
    public void updateBoard() {
    	JPanel parent = (JPanel) this.getParent();
    	parent.revalidate();
    	parent.repaint();
    	
    }
    
    public JLabel getPreview(int x, int y, int w, int h) {
    	JLabel newPreview = new JLabel();
    	newPreview.setBounds(x, y, w, h);
    	newPreview.setBackground(new Color(255, 0, 0, 128)); // transparent red
    	newPreview.setOpaque(true);
    	return newPreview;
    }

    public JLabel getCatchPreview(int x, int y, int w, int h) {
    	JLabel newCatchPreview = new JLabel();
    	newCatchPreview.setBounds(x, y, w, h);
    	newCatchPreview.setOpaque(false);
    	return newCatchPreview;
    }
    
    
    public ArrayList<int[]> getAttackSquares() {
    	ArrayList<int[]> attacks = getPreviwIndex();
    	return attacks;
    }

    public boolean check(String color, Board board) {
    	//find the enemies king
    	int x = 0; int y = 0;
    	String opposingColor = color.equalsIgnoreCase("white") ? "black" : "white";
    	for(Piece p : board.piecesOnBoard) {
    		
    		if(!p.color.equalsIgnoreCase(color) && p instanceof King) {
    			x = p.getX(); y = p.getY();
    			if(((King)p).isSquareAttacked(x, y, opposingColor, board)) {
    				return true;
    			}
    		}
    	}
    	return false;
    	
    }

    @SuppressWarnings("unchecked")
	public boolean isCheckmate(String color, Board board) {
    	String opposingColor = color.equals("white") ? "black" : "white";  
    	ArrayList<Piece> piecesOnBoard = (ArrayList<Piece>) board.piecesOnBoard.clone();
    	for (Piece p : piecesOnBoard) {
    		if (p.color.equals(color)) {
//    			System.out.println(p.getClass().getSimpleName()+ " "+p.color+" "+": "+p.XCoordinate+" "+p.YCoordinate);
    			ArrayList<int[]> moves = p.getPreviwIndex();
    			

    			for (int[] move : moves) {
    				int oldX = p.XCoordinate, oldY = p.YCoordinate;
    				Piece captured = board.getPieceAt(move[0], move[1]);
    				
    				// Try the move
    				if (captured != null) {eatPiece(board, captured);}
    				p.move(move[0], move[1], p.WIDTH, p.HEIGHT);
    				
    				// Check if still in check
    				boolean stillInCheck = check(opposingColor, board);
    				
    				// Undo the move
    				p.move(oldX, oldY, p.WIDTH, p.HEIGHT);
    				if (captured != null) {
    					returnPiece(board, captured);
    				}
//    				System.out.println(move[0]+": "+move[1]+" "+stillInCheck);
    				if (!stillInCheck) return false; // Found a valid escape
    			}
    		}
    	}
    	return true; // No valid moves to escape check
    }
	
	public void simulateMovement(JLabel invisPreview, Piece currentPiece, Board board ) {
		int oldX = currentPiece.getX(); int oldY = currentPiece.getY(); 
        Piece oldPiece = board.getPieceAt(invisPreview.getX(), invisPreview.getY());
        
        
        if( oldPiece != null) {
        	eatPiece(board, oldPiece);
        }
        
        currentPiece.move(invisPreview.getX(), invisPreview.getY(), invisPreview.getWidth(), invisPreview.getHeight());
        currentPiece.removePreview();
        
        String opposingColor = currentPiece.color.equalsIgnoreCase("white") ? "black" : "white";
        
        //if this causes the piece to be in check or if it doesnt cancel out an already existing check do not allow
        if(check(opposingColor, board)) {
        	currentPiece.move(oldX, oldY, currentPiece.getWidth(), currentPiece.getHeight());
        	returnPiece(board, oldPiece);
        	inCheck = true;
        	board.showCheckMessage();
        }

        else {                   	                 
        	//Swap turn
        	inCheck = false;
        	Piece.currentTurn = Piece.currentTurn.equals("white") ? "black" : "white";
        	
        	if(isCheckmate(Piece.currentTurn, board)) {
        		board.gameOver();         		
        	}
        	
        	((Board) currentPiece.getParent()).flipBoard();
        	updateBoard();
        	
        	
        }
	}
	

	
	@Override
	public void mousePressed(MouseEvent e) {}


	@Override
	public void mouseReleased(MouseEvent e) {}


	@Override
	public void mouseEntered(MouseEvent e) {}


	@Override
	public void mouseExited(MouseEvent e) {}
	

}



