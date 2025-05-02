//parent class for all pieces
package pieces;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import mainGame.Board;


@SuppressWarnings("serial")
public class Piece extends JLabel implements MouseListener{
	
    public int XCoordinate;
    public int YCoordinate;
    public final int WIDTH = 100;    
    public final int HEIGHT = 100;
    public String color;
    public JLabel preview = new JLabel();
    public boolean previewPhase = true;
    

    public Piece(int XCoordinate, int YCoordinate, String color) {
        this.setBounds(XCoordinate, YCoordinate, WIDTH, HEIGHT);
        this.XCoordinate = XCoordinate;
        this.YCoordinate = YCoordinate;
        this.color = color;
        this.setOpaque(false);
        this.addMouseListener(this);
        
        
        // Draw the piece's image
        if (this.color.equalsIgnoreCase("white")) {
        	this.setIcon(new ImageIcon("C:\\Users\\louay\\Desktop\\Eclipse workplace\\Chess\\PiecesImg\\" + getClass().getSimpleName() + ".png"));
        } 
        else {
        	this.setIcon(new ImageIcon("C:\\Users\\louay\\Desktop\\Eclipse workplace\\Chess\\PiecesImg\\" + getClass().getSimpleName() + "1.png"));
        }
        
       
    }

    
    public void showPreview() {
        JPanel parent = (JPanel) this.getParent();

        Board board = (Board) parent;
        int targetX = XCoordinate;
        int targetY = YCoordinate - HEIGHT;

        //visual preview layer
        preview = new JLabel();
        preview.setBounds(targetX, targetY, WIDTH, HEIGHT);
        preview.setBackground(new Color(255, 0, 0, 128)); // transparent red
        preview.setOpaque(true);

        // Transparent click-catcher
        JLabel previewClickCatcher = new JLabel();
        previewClickCatcher.setBounds(targetX, targetY, WIDTH, HEIGHT);
        previewClickCatcher.setOpaque(false); // invisible

        Piece currentPiece = this;
        Piece occupyingPiece = board.getPieceAt(targetX, targetY);
        
        boolean collision = occupyingPiece != null && occupyingPiece.color.equals(currentPiece.color);
        boolean eating = occupyingPiece != null && !occupyingPiece.color.equals(currentPiece.color);

        previewClickCatcher.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                e.consume(); // Stop the click from hitting pieces

                if (collision) {
                    System.out.println("Can't move here: same color piece in the way.");
                    currentPiece.previewPhase = true;
                    currentPiece.removePreview();
                    return;
                }
                else if(eating) {
                	eatPiece(board, occupyingPiece);
                }
                currentPiece.previewPhase = true;
                currentPiece.move();
                currentPiece.removePreview();
            }
        });

        // Add red preview first
        parent.add(preview);
        if (occupyingPiece != null) {
            parent.setComponentZOrder(preview, parent.getComponentZOrder(occupyingPiece) + 1);
        } else {
            parent.setComponentZOrder(preview, parent.getComponentCount() - 1);
        }

        // Add transparent click-catcher ON TOP of everything
        parent.add(previewClickCatcher);
        parent.setComponentZOrder(previewClickCatcher, 0); // 0 = top-most

        updateBoard();
    }




    public void eatPiece(Board board, Piece piece) {
    	board.piecesOnBoard.remove(piece);
    	board.remove(piece);
    	updateBoard();
    }

    
    public void move() {
    	
    	this.XCoordinate = preview.getX();
    	this.YCoordinate = preview.getY();
    	this.setBounds(XCoordinate, YCoordinate, WIDTH, HEIGHT);
    	updateBoard();
    }
    
	@Override
	public void mouseClicked(MouseEvent e) {
		System.out.println("Hey, " + this.getClass().getSimpleName() + " clicked at " + XCoordinate + ", " + YCoordinate + " " + previewPhase);
		System.out.println("X coordinate: "+e.getX()+" Y coordinate: "+e.getY());
		if(previewPhase) {	
    		previewPhase = false;
    		showPreview();
    	}
		else {	//clicked on the piece again to remove preview
			previewPhase = true;
			removePreview();
		}
	}


	public void removePreview() {
	    JPanel parent = (JPanel) this.getParent();
	    parent.remove(preview);

	    // Also remove the transparent click-catcher (if it exists)
	    for (java.awt.Component comp : parent.getComponents()) {
	        if (comp instanceof JLabel && comp != this && comp != preview) {
	            JLabel label = (JLabel) comp;
	            if (!label.isOpaque()) {
	                parent.remove(label);
	                break;
	            }
	        }
	    }

	    updateBoard();
	}

	
	public void addPreview() {
		JPanel parent = (JPanel) this.getParent();
		parent.add(preview);
    	updateBoard();
	}
	
	public void updateBoard() {
		JPanel parent = (JPanel) this.getParent();
		parent.revalidate();
    	parent.repaint();
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



