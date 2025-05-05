package pieces;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;

import mainGame.Board;



@SuppressWarnings("serial")
public class Pawn extends Piece{
	
	boolean firstMove = true;	//if pawn did not move yet
	
	public Pawn(int XCoordinate, int YCoordinate, String color) {
		super(XCoordinate, YCoordinate, color);
	}
	
	public void showPreview() {
		int direction = color.equalsIgnoreCase("white") ? -1 : 1;
        JPanel parent = (JPanel) this.getParent();
        Board board = (Board) parent;

        
        //4 pieces
        Piece NormalOccupyingPiece = board.getPieceAt(XCoordinate, YCoordinate + direction * HEIGHT);
        Piece TwoStepOccupyingPiece = board.getPieceAt(XCoordinate, YCoordinate + 2 * direction * HEIGHT);
        Piece RightOccupyingPiece = board.getPieceAt(XCoordinate + WIDTH, YCoordinate + direction * HEIGHT);
        Piece LeftOccupyingPiece = board.getPieceAt(XCoordinate - WIDTH, YCoordinate + direction * HEIGHT);

        
        //4 previews
        ArrayList<JLabel> previews = new ArrayList<JLabel>();
        
        if(NormalOccupyingPiece == null) {
        	JLabel Normalpreview = getPreview(XCoordinate, YCoordinate + direction * HEIGHT, WIDTH, HEIGHT);
        	previews.add(Normalpreview);	
        }
        
        if(firstMove && TwoStepOccupyingPiece == null) {
        	JLabel TwoStepPreview = getPreview(XCoordinate, YCoordinate + 2 * direction * HEIGHT, WIDTH, HEIGHT);
        	previews.add(TwoStepPreview);
        }
        
        if(RightOccupyingPiece != null && RightOccupyingPiece.color != this.color) {	//exists a piece  with different color
        	JLabel rightPreview = getPreview(XCoordinate + WIDTH, YCoordinate + direction * HEIGHT, WIDTH, HEIGHT);
        	previews.add(rightPreview);
        }
        
        if(LeftOccupyingPiece != null && LeftOccupyingPiece.color != this.color) {
        	JLabel leftPreview = getPreview(XCoordinate - WIDTH, YCoordinate + direction * HEIGHT, WIDTH, HEIGHT);
        	previews.add(leftPreview);        	
        }
        
        //remove out of boudns pieces
        previews.removeIf(p -> 
        p.getX() < 0 || 
        p.getX() + p.getWidth() > board.getWidth() ||
        p.getY() < 0 || 
        p.getY() + p.getHeight() > board.getHeight()
        );

        
        // Transparent click-catcher
        ArrayList<JLabel> previewsClickCatcher = new ArrayList<JLabel>();
        for(JLabel preview : previews) {
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
                    firstMove = false;
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
            updateBoard();
        }

        for (JLabel clickCatcher : previewsClickCatcher) {
            parent.add(clickCatcher);
            parent.setComponentZOrder(clickCatcher, 0);
            clickCatchers.add(clickCatcher);
            updateBoard();
        }

    }
	
}
