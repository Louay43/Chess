package pieces;


import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;

import mainGame.Board;

@SuppressWarnings("serial")
public class King extends Piece{
	
	public King(int XCoordinate, int YCoordinate, String color) {
		super(XCoordinate, YCoordinate, color);
	}

	@Override
	public void showPreview() {
		JPanel parent = (JPanel) this.getParent();
	    Board board = (Board) parent;

	    ArrayList<JLabel> previews = new ArrayList<>();
	    // the king has 8 possible positions
	    
	    // == X- / Y- ==
	    int x = this.getX() - 100;
	    int y = this.getY() - 100;
	    if(addPreviewIfPossible(x, y) != null) previews.add(addPreviewIfPossible(x, y));
	    
	    // == X / Y- ==
	    x = this.getX();
	    y = this.getY() - 100;
	    if(addPreviewIfPossible(x, y) != null) previews.add(addPreviewIfPossible(x, y));
	    
	    // == X+ / Y- ==
	    x = this.getX() + 100;
	    y = this.getY() - 100;
	    if(addPreviewIfPossible(x, y) != null) previews.add(addPreviewIfPossible(x, y));
	    
	    // == X+ / Y ==
	    x = this.getX() + 100;
	    y = this.getY();
	    if(addPreviewIfPossible(x, y) != null) previews.add(addPreviewIfPossible(x, y));
	    
	    // == X+ / Y+ ==
	    x = this.getX() + 100;
	    y = this.getY() + 100;
	    if(addPreviewIfPossible(x, y) != null) previews.add(addPreviewIfPossible(x, y));
	    
	    // == X / Y+ ==
	    x = this.getX();
	    y = this.getY() + 100;
	    if(addPreviewIfPossible(x, y) != null) previews.add(addPreviewIfPossible(x, y));
	        
	    // == X- / Y+ ==
	    x = this.getX() - 100;
	    y = this.getY() + 100;
	    if(addPreviewIfPossible(x, y) != null) previews.add(addPreviewIfPossible(x, y));
	    
	    // == X- / Y ==
	    x = this.getX() - 100;
	    y = this.getY();
	    if(addPreviewIfPossible(x, y) != null) previews.add(addPreviewIfPossible(x, y));
	    
	    
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
	
	private JLabel addPreviewIfPossible(int x, int y) {
		JPanel parent = (JPanel) this.getParent();
		Board board = (Board) parent;
		if(x >= 0 && x < board.getWidth() && y >= 0 && y < board.getHeight()) {
			Piece occupyingPiece = board.getPieceAt(x, y);
			if(occupyingPiece == null || !occupyingPiece.color.equals(this.color)) {	
				return getPreview(x, y, WIDTH, HEIGHT); // enemy piece: can capture
			}
			return null;		
		}
		return null;
	}
	
}

