package pieces;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;

import mainGame.Board;


@SuppressWarnings("serial")
public class Knight extends Piece{
	public Knight(int XCoordinate, int YCoordinate, String color) {
		super(XCoordinate, YCoordinate, color);
	}
	
	public ArrayList<int[]> getPreviwIndex() {
		// the knight has 8 possible positions if x is incremented/decremented then y is incremented/decremented twice. and vice versa
		ArrayList<int[]> indexes = new ArrayList<int[]>();
	    // == X+ / Y-- ==
	    int x = this.getX() + 100;
	    int y = this.getY() - 200;
	    if(addPreviewIfPossible(x, y) != null) indexes.add(new int[] {x, y});
	    
	    // == X+ / Y++ ==
	    x = this.getX() + 100;
	    y = this.getY() + 200;
	    if(addPreviewIfPossible(x, y) != null) indexes.add(new int[] {x, y});
	    
	    // == X- / Y-- ==
	    x = this.getX() - 100;
	    y = this.getY() - 200;
	    if(addPreviewIfPossible(x, y) != null) indexes.add(new int[] {x, y});
	    
	    // == X- / Y++ ==
	    x = this.getX() - 100;
	    y = this.getY() + 200;
	    if(addPreviewIfPossible(x, y) != null) indexes.add(new int[] {x, y});
	    
	    // == X++ / Y+ ==
	    x = this.getX() + 200;
	    y = this.getY() + 100;
	    if(addPreviewIfPossible(x, y) != null) indexes.add(new int[] {x, y});
	    
	    // == X++ / Y- ==
	    x = this.getX() + 200;
	    y = this.getY() - 100;
	    if(addPreviewIfPossible(x, y) != null) indexes.add(new int[] {x, y});
	        
	    // == X-- / Y+ ==
	    x = this.getX() - 200;
	    y = this.getY() + 100;
	    if(addPreviewIfPossible(x, y) != null) indexes.add(new int[] {x, y});
	    
	    // == X-- / Y- ==
	    x = this.getX() - 200;
	    y = this.getY() - 100;
	    if(addPreviewIfPossible(x, y) != null) indexes.add(new int[] {x, y});
	    
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
	
	private JLabel addPreviewIfPossible(int x, int y) {
		JPanel parent = (JPanel) this.getParent();
		Board board = (Board) parent;
		if(x >= 0 && x < board.getWidth() && y >= 0 && y < board.getHeight()) {
			Piece occupyingPiece = board.getPieceAt(x, y);
			if(occupyingPiece == null || !occupyingPiece.color.equals(this.color)) {	
				return getPreview(x, y, WIDTH, HEIGHT); 
			}
			return null;		
		}
		return null;
	}
	
	
	
}
