package pieces;


import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;

import mainGame.Board;

@SuppressWarnings("serial")
public class Queen extends Piece{
	public Queen(int XCoordinate, int YCoordinate, String color) {
		super(XCoordinate, YCoordinate, color);
	}

	@Override
	public void showPreview() {
		JPanel parent = (JPanel) this.getParent();
	    Board board = (Board) parent;

	    ArrayList<JLabel> previews = new ArrayList<>();
	    
	    // === VERTICAL UP ===
	    int y = this.getY() - 100;
	    while (y >= 0) {
	        Piece occupying = board.getPieceAt(XCoordinate, y);
	        if (occupying == null) {
	            previews.add(getPreview(XCoordinate, y, WIDTH, HEIGHT));
	        } else {
	            if (!occupying.color.equals(this.color)) {
	                previews.add(getPreview(XCoordinate, y, WIDTH, HEIGHT)); // enemy piece: can capture
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
	            previews.add(getPreview(XCoordinate, y, WIDTH, HEIGHT));
	        } 
	        else {
	            if (!occupying.color.equals(this.color)) {
	                previews.add(getPreview(XCoordinate, y, WIDTH, HEIGHT));
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
	            previews.add(getPreview(x, YCoordinate, WIDTH, HEIGHT));
	        } else {
	            if (!occupying.color.equals(this.color)) {
	                previews.add(getPreview(x, YCoordinate, WIDTH, HEIGHT));
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
	            previews.add(getPreview(x, YCoordinate, WIDTH, HEIGHT));
	        } else {
	            if (!occupying.color.equals(this.color)) {
	                previews.add(getPreview(x, YCoordinate, WIDTH, HEIGHT));
	            }
	            break;
	        }
	        x -= 100;
	    }
	    
	    // === TOP RIGHT ===
	    y = this.getY() - 100;
	    x = this.getX() + 100;
	    while (y >= 0 && x < board.getWidth()) {
	        Piece occupying = board.getPieceAt(x, y);
	        if (occupying == null) {
	            previews.add(getPreview(x, y, WIDTH, HEIGHT));
	        } else {
	            if (!occupying.color.equals(this.color)) {
	                previews.add(getPreview(x, y, WIDTH, HEIGHT)); // enemy piece: can capture
	            }
	            break; // stop either way after first encounter
	        }
	        y -= 100;
	        x +=100;
	    }

	    // === TOP LEFT ===
	    y = this.getY() - 100;
	    x = this.getX() - 100;
	    while (y >= 0 && x >= 0) {
	        Piece occupying = board.getPieceAt(x, y);
	        if (occupying == null) {
	            previews.add(getPreview(x, y, WIDTH, HEIGHT));
	        } else {
	            if (!occupying.color.equals(this.color)) {
	                previews.add(getPreview(x, y, WIDTH, HEIGHT)); // enemy piece: can capture
	            }
	            break; // stop either way after first encounter
	        }
	        y -= 100;
	        x -=100;
	    }

	    // === BOTTOM RIGHT ===
	    y = this.getY() + 100;
	    x = this.getX() + 100;
	    while (y < board.getHeight() && x < board.getWidth()) {
	        Piece occupying = board.getPieceAt(x, y);
	        if (occupying == null) {
	            previews.add(getPreview(x, y, WIDTH, HEIGHT));
	        } else {
	            if (!occupying.color.equals(this.color)) {
	                previews.add(getPreview(x, y, WIDTH, HEIGHT)); // enemy piece: can capture
	            }
	            break; // stop either way after first encounter
	        }
	        y += 100;
	        x +=100;
	    }

	    // === BOTTOM LEFT ===
	    y = this.getY() + 100;
	    x = this.getX() - 100;
	    while (y < board.getHeight() && x >= 0) {
	        Piece occupying = board.getPieceAt(x, y);
	        if (occupying == null) {
	            previews.add(getPreview(x, y, WIDTH, HEIGHT));
	        } else {
	            if (!occupying.color.equals(this.color)) {
	                previews.add(getPreview(x, y, WIDTH, HEIGHT)); // enemy piece: can capture
	            }
	            break; // stop either way after first encounter
	        }
	        y += 100;
	        x -=100;
	    }

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

                    if(board.getPieceAt(invisPreview.getX(), invisPreview.getY()) != null) {
                    	eatPiece(board, board.getPieceAt(invisPreview.getX(), invisPreview.getY()));
                    }
                    currentPiece.move(invisPreview.getX(), invisPreview.getY(), invisPreview.getWidth(), invisPreview.getHeight());
                    currentPiece.removePreview();
                    updateBoard();
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

