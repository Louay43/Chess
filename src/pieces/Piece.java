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
	
	public static Piece selectedPiece = null;
    public int XCoordinate;
    public int YCoordinate;
    public final int WIDTH = 100;    
    public final int HEIGHT = 100;
    public String color;
    protected ArrayList<JLabel> previewLabels = new ArrayList<>();
    protected ArrayList<JLabel> clickCatchers = new ArrayList<>();


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




    public void eatPiece(Board board, Piece piece) {
    	board.piecesOnBoard.remove(piece);
    	board.remove(piece);
    	updateBoard();
    }

    
    public void move(int x, int y, int w, int h) {
    	
    	this.XCoordinate = x;
    	this.YCoordinate = y;
    	this.setBounds(XCoordinate, YCoordinate, w, h);
    	updateBoard();
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        if (selectedPiece == null) {
            //No piece selected yet, allow activation
            selectedPiece = this;
            showPreview();
        } 
        else if (selectedPiece == this) {
            //Reclick same piece: cancel preview
            removePreview();
            selectedPiece = null;
        } 
        else {
        	
        	//Another piece is selected — switch selection
            selectedPiece.removePreview();  // remove preview from the previously selected piece
            selectedPiece = this;  // set new selected piece
            showPreview();         
        }
    }



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


	
	public void addPreview(JLabel preview) {
		JPanel parent = (JPanel) this.getParent();
		parent.add(preview);
    	updateBoard();
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
	


	@Override
	public void mousePressed(MouseEvent e) {}


	@Override
	public void mouseReleased(MouseEvent e) {}


	@Override
	public void mouseEntered(MouseEvent e) {}


	@Override
	public void mouseExited(MouseEvent e) {}

}



