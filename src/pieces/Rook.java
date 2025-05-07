package pieces;


import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;

import mainGame.Board;


@SuppressWarnings("serial")
public class Rook extends Piece{
	
	public boolean hasMoved = false;
	public Rook(int XCoordinate, int YCoordinate, String color) {
		super(XCoordinate, YCoordinate, color);
	}
	
	public ArrayList<int[]> getPreviwIndex() {
		JPanel parent = (JPanel) this.getParent();
	    Board board = (Board) parent;
	    
	    ArrayList<int[]> indexes = new ArrayList<int[]>();
	    
		// === VERTICAL UP ===
	    int y = this.getY() - 100;
	    while (y >= 0) {
	        Piece occupying = board.getPieceAt(XCoordinate, y);
	        if (occupying == null) {
	        	indexes.add(new int[] {XCoordinate, y});
	        } else {
	            if (!occupying.color.equals(this.color)) {
	            	indexes.add(new int[] {XCoordinate, y}); // enemy piece: can capture
	            }
	            break; // stop either way after first encounter
	        }
	        y -= 100;
	    }

	    // === VERTICAL DOWN ===
	    y = this.getY() + 100;
	    while (y < board.getHeight()) {
	        Piece occupying = board.getPieceAt(XCoordinate, y);
	        if (occupying == null) {
	        	indexes.add(new int[] {XCoordinate, y});
	        } 
	        else {
	            if (!occupying.color.equals(this.color)) {
	            	indexes.add(new int[] {XCoordinate, y});
	            }
	            break;
	        }
	        y += 100;
	    }

	    // === HORIZONTAL RIGHT ===
	    int x = this.getX() + 100;
	    while (x < board.getWidth()) {
	        Piece occupying = board.getPieceAt(x, YCoordinate);
	        if (occupying == null) {
	        	indexes.add(new int[] {x, YCoordinate});
	        } else {
	            if (!occupying.color.equals(this.color)) {
	            	indexes.add(new int[] {x, YCoordinate});
	            }
	            break;
	        }
	        x += 100;
	    }

	    // === HORIZONTAL LEFT ===
	    x = this.getX() - 100;
	    while (x >= 0) {
	        Piece occupying = board.getPieceAt(x, YCoordinate);
	        if (occupying == null) {
	        	indexes.add(new int[] {x, YCoordinate});
	        } else {
	            if (!occupying.color.equals(this.color)) {
	            	indexes.add(new int[] {x, YCoordinate});
	            }
	            break;
	        }
	        x -= 100;
	    }
	    
	    return indexes;
	}

	
	@Override
	public void showPreview() {
	    JPanel parent = (JPanel) this.getParent();
	    Board board = (Board) parent;

	    ArrayList<JLabel> previews = new ArrayList<>();

	    ArrayList<int[]> indexes = getPreviwIndex();
	    for(int[] index : indexes) {
	    	previews.add(getPreview(index[0], index[1], WIDTH, HEIGHT));
	    }
	    
	    // remove out of bounds pieces
 		previews.removeIf(p -> p.getX() < 0 || p.getX() + p.getWidth() > board.getWidth() || p.getY() < 0 
 				|| p.getY() + p.getHeight() > board.getHeight());
 		
	    // Transparent click-catcher
	    ArrayList<JLabel> previewsClickCatcher = new ArrayList<>();
	    for (JLabel preview : previews) {
	        JLabel ClickCatcher = getCatchPreview(preview.getX(), preview.getY(), preview.getWidth(), preview.getHeight());
	        previewsClickCatcher.add(ClickCatcher);
	    }
	    
	    Piece currentPiece = this;
        
        for(JLabel invisPreview : previewsClickCatcher) {
        	invisPreview.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                	e.consume(); // Stop the click from hitting pieces 
                	
                	currentPiece.simulateMovement(invisPreview, currentPiece, board);
                	
                }
            });
        }
	    
	    for (JLabel preview : previews) {
	        parent.add(preview);
	        parent.setComponentZOrder(preview, parent.getComponentCount() - 1); // bring to front
	        previewLabels.add(preview);
	    }

	    for (JLabel clickCatcher : previewsClickCatcher) {
            parent.add(clickCatcher);
            parent.setComponentZOrder(clickCatcher, 0);
            clickCatchers.add(clickCatcher);
            updateBoard();
        }

	    updateBoard();
	}
	

}