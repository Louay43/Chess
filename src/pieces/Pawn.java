package pieces;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;

import mainGame.Board;


@SuppressWarnings("serial")
public class Pawn extends Piece {

	boolean firstMove = true; // if pawn did not move yet
	boolean ateEnPssant = false;
	
	public Pawn(int XCoordinate, int YCoordinate, String color) {
		super(XCoordinate, YCoordinate, color);
	}
	
	public ArrayList<int[]> getPreviwIndex() {
		JPanel parent = (JPanel) this.getParent();
		Board board = (Board) parent;

		Piece RightOccupyingPiece = board.getPieceAt(XCoordinate + WIDTH, YCoordinate - 1 * HEIGHT);
		Piece LeftOccupyingPiece = board.getPieceAt(XCoordinate - WIDTH, YCoordinate - 1 * HEIGHT);

		// 4 previews
		ArrayList<int[]> indexes = new ArrayList<int[]>();

		

		if (RightOccupyingPiece == null) { 
			indexes.add(new int[] {XCoordinate + WIDTH, YCoordinate - 1 * HEIGHT});
		}

		if (LeftOccupyingPiece == null ) {
			indexes.add(new int[] {XCoordinate - WIDTH, YCoordinate - 1 * HEIGHT});
		}
		
		return indexes;
	}
	
	
	public void showPreview() {
		JPanel parent = (JPanel) this.getParent();
		Board board = (Board) parent;

		// 4 pieces
		Piece NormalOccupyingPiece = board.getPieceAt(XCoordinate, YCoordinate - 1 * HEIGHT);
		Piece TwoStepOccupyingPiece = board.getPieceAt(XCoordinate, YCoordinate - 2 * 1 * HEIGHT);
		Piece RightOccupyingPiece = board.getPieceAt(XCoordinate + WIDTH, YCoordinate - 1 * HEIGHT);
		Piece LeftOccupyingPiece = board.getPieceAt(XCoordinate - WIDTH, YCoordinate - 1 * HEIGHT);

		// 4 previews
		ArrayList<JLabel> previews = new ArrayList<JLabel>();

		if (NormalOccupyingPiece == null) {
			JLabel Normalpreview = getPreview(XCoordinate, YCoordinate - 1 * HEIGHT, WIDTH, HEIGHT);
			previews.add(Normalpreview);
		}

		if (firstMove && TwoStepOccupyingPiece == null && NormalOccupyingPiece == null) {
			JLabel TwoStepPreview = getPreview(XCoordinate, YCoordinate - 2 * 1 * HEIGHT, WIDTH, HEIGHT);
			previews.add(TwoStepPreview);
		}

		if (RightOccupyingPiece != null && RightOccupyingPiece.color != this.color) { // exists a piece with different color
			JLabel rightPreview = getPreview(XCoordinate + WIDTH, YCoordinate - 1 * HEIGHT, WIDTH, HEIGHT);
			previews.add(rightPreview);
		}

		if (LeftOccupyingPiece != null && LeftOccupyingPiece.color != this.color) {
			JLabel leftPreview = getPreview(XCoordinate - WIDTH, YCoordinate - 1 * HEIGHT, WIDTH, HEIGHT);
			previews.add(leftPreview);
		}

		// En passant capture
		int captureY = YCoordinate - HEIGHT; // move one step forward to capture

		if (Piece.enPassantTarget != null && Piece.enPassantTarget.color != this.color) {
			int targetY = Piece.enPassantTarget.YCoordinate;
			int targetX = Piece.enPassantTarget.XCoordinate;

			// must be on same rank and adjacent
			if (targetY == YCoordinate && Math.abs(targetX - XCoordinate) == WIDTH) {
				JLabel enPassantPreview = getPreview(targetX, captureY, WIDTH, HEIGHT);
				previews.add(enPassantPreview);
			}
		}

		// remove out of bounds pieces
		previews.removeIf(p -> p.getX() < 0 || p.getX() + p.getWidth() > board.getWidth() || p.getY() < 0 
				|| p.getY() + p.getHeight() > board.getHeight());

		// Transparent click-catcher
		ArrayList<JLabel> previewsClickCatcher = new ArrayList<JLabel>();
		for (JLabel preview : previews) {
			JLabel ClickCatcher = getCatchPreview(preview.getX(), preview.getY(), preview.getWidth(),
					preview.getHeight());
			previewsClickCatcher.add(ClickCatcher);
		}

		Piece currentPiece = this;

		for (JLabel invisPreview : previewsClickCatcher) {
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
			updateBoard();
		}

		for (JLabel clickCatcher : previewsClickCatcher) {
			parent.add(clickCatcher);
			parent.setComponentZOrder(clickCatcher, 0);
			clickCatchers.add(clickCatcher);
			updateBoard();
		}

	}

	@Override
	public ArrayList<int[]> getAttackSquares() {
	    ArrayList<int[]> attacks = new ArrayList<>();
	    int attackY = YCoordinate + HEIGHT;
	    int leftX = XCoordinate - WIDTH;
	    int rightX = XCoordinate + WIDTH;

	    if (leftX >= 0) attacks.add(new int[] { leftX, attackY });
	    if (rightX < 800) attacks.add(new int[] { rightX, attackY });

	    return attacks;
	}

	@Override
	public void simulateMovement(JLabel invisPreview, Piece currentPiece, Board board ) {
		// store old position before moving
		int oldY = currentPiece.YCoordinate; int oldX = currentPiece.XCoordinate;
		Piece oldPiece = board.getPieceAt(invisPreview.getX(), invisPreview.getY());
		Piece oldEnPassantPiece = Piece.enPassantTarget;
        String opposingColor = currentPiece.color.equalsIgnoreCase("white") ? "black" : "white";

		if( oldPiece != null) {
        	eatPiece(board, oldPiece);
        }
		
		// Handle en passant removal
		if (Piece.enPassantTarget != null && Piece.enPassantTarget != currentPiece) {
			if (invisPreview.getX() == Piece.enPassantTarget.XCoordinate && 
					invisPreview.getY() == Piece.enPassantTarget.YCoordinate - HEIGHT) {	
				eatPiece(board, Piece.enPassantTarget);
				ateEnPssant = true;
			}
			Piece.enPassantTarget = null;
		}

		currentPiece.move(invisPreview.getX(), invisPreview.getY(), invisPreview.getWidth(), invisPreview.getHeight());
		currentPiece.removePreview();
		
		if(check(opposingColor, board)) {
        	currentPiece.move(oldX, oldY, currentPiece.getWidth(), currentPiece.getHeight());
        	
        	if(ateEnPssant) {
        		returnPiece(board, oldEnPassantPiece);
        		Piece.enPassantTarget = (Pawn) oldEnPassantPiece;
        	}
        	returnPiece(board, oldPiece);
        	inCheck = true;
        	board.showCheckMessage();

        }

		else {
			inCheck = false;
			firstMove = false;
			// if it moved two spaces it could be eaten by en passant
			if (Math.abs(invisPreview.getY() - oldY) == 2 * HEIGHT) {
				Piece.enPassantTarget = (Pawn) currentPiece;
			} else {
				Piece.enPassantTarget = null;
			}
			
			// Swap turn
			Piece.currentTurn = Piece.currentTurn.equals("white") ? "black" : "white";
			
			if(isCheckmate(Piece.currentTurn, board)) {
				board.gameOver();                 		
			}
			
			((Board) currentPiece.getParent()).flipBoard();
			updateBoard();		
			
			
		}
		
	}

}