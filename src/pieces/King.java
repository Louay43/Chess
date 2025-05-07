package pieces;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;

import mainGame.Board;


@SuppressWarnings("serial")
public class King extends Piece {

	public boolean hasMoved = false;

	public King(int XCoordinate, int YCoordinate, String color) {
		super(XCoordinate, YCoordinate, color);
	}
	
	public ArrayList<int[]> getPreviwIndex() {
		ArrayList<int[]> indexes = new ArrayList<int[]>();
		// the king has 8 possible positions

		// == X- / Y- ==
		int x = this.getX() - 100;
		int y = this.getY() - 100;
		JLabel preview = addPreviewIfPossible(x, y);
		if (preview != null) {
		    indexes.add(new int[] {x, y});
		}

		// == X / Y- ==
		x = this.getX();
		y = this.getY() - 100;
		preview = addPreviewIfPossible(x, y);
		if (preview != null) {
			indexes.add(new int[] {x, y});
		}


		// == X+ / Y- ==
		x = this.getX() + 100;
		y = this.getY() - 100;
		preview = addPreviewIfPossible(x, y);
		if (preview != null) {
			indexes.add(new int[] {x, y});
		}

		// == X+ / Y ==
		x = this.getX() + 100;
		y = this.getY();
		preview = addPreviewIfPossible(x, y);
		if (preview != null) {
			indexes.add(new int[] {x, y});
		}

		// == X+ / Y+ ==
		x = this.getX() + 100;
		y = this.getY() + 100;
		preview = addPreviewIfPossible(x, y);
		if (preview != null) {
			indexes.add(new int[] {x, y});
		}

		// == X / Y+ ==
		x = this.getX();
		y = this.getY() + 100;
		preview = addPreviewIfPossible(x, y);
		if (preview != null) {
			indexes.add(new int[] {x, y});
		}

		// == X- / Y+ ==
		x = this.getX() - 100;
		y = this.getY() + 100;
		preview = addPreviewIfPossible(x, y);
		if (preview != null) {
			indexes.add(new int[] {x, y});
		}

		// == X- / Y ==
		x = this.getX() - 100;
		y = this.getY();
		preview = addPreviewIfPossible(x, y);
		if (preview != null) {
			indexes.add(new int[] {x, y});
		}
		
		return indexes;
	}
	
	@Override
	public void showPreview() {
		JPanel parent = (JPanel) this.getParent();
		Board board = (Board) parent;

		ArrayList<JLabel> previews = new ArrayList<>();
		

		ArrayList<int[]> indexes = getPreviwIndex();
		for (int[] index : indexes) {
		    if (!isSquareAttacked(index[0], index[1], this.color, board)) {
		    	previews.add(getPreview(index[0], index[1], WIDTH, HEIGHT));
		    }
		}
		

		// Castling logic
		if (!this.hasMoved) {
			if(color.equals("white")) {
				for(JLabel preview1 : Whitecastle()) {
					if (!isSquareAttacked(preview1.getX(), preview1.getY(), this.color, board)) {
						previews.add(preview1);			        
				    }
				}				
			}
			else {
				for(JLabel preview1 : blackCastle()) {
					if (!isSquareAttacked(preview1.getX(), preview1.getY(), this.color, board)) {
						previews.add(preview1);			        
				    }
				}
			}
		}
		
		// remove out of bounds pieces
		previews.removeIf(p -> p.getX() < 0 || p.getX() + p.getWidth() > board.getWidth() || p.getY() < 0 
				|| p.getY() + p.getHeight() > board.getHeight());
				
		// Transparent click-catcher
		ArrayList<JLabel> previewsClickCatcher = new ArrayList<>();
		for (JLabel preview1 : previews) {
			JLabel ClickCatcher = getCatchPreview(preview1.getX(), preview1.getY(), preview1.getWidth(), preview1.getHeight());
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

		for (JLabel preview1 : previews) {
			parent.add(preview1);
			parent.setComponentZOrder(preview1, parent.getComponentCount() - 1); // bring to front
			previewLabels.add(preview1);
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
		if (x >= 0 && x < board.getWidth() && y >= 0 && y < board.getHeight()) {
			Piece occupyingPiece = board.getPieceAt(x, y);
			if (occupyingPiece == null || !occupyingPiece.color.equals(this.color)) {
				return getPreview(x, y, WIDTH, HEIGHT); // enemy piece: can capture
			}
			return null;
		}
		return null;
	}
	
	private ArrayList<JLabel> Whitecastle() {
		JPanel parent = (JPanel) this.getParent();
		Board board = (Board) parent;
		ArrayList<JLabel> previews = new ArrayList<>();
		// Kingside castling
		Piece rightRook = board.getPieceAt(XCoordinate + 400, YCoordinate);
		if (rightRook instanceof Rook && !((Rook) rightRook).hasMoved) {
		    boolean clear = true;
		    for (int xc = XCoordinate + 100; xc < XCoordinate + 400; xc += 100) {
		        if (board.getPieceAt(xc, YCoordinate) != null) {
		            clear = false;
		            break;
		        }
		    }
		    if (clear) {
		        JLabel castlePreview = getPreview(XCoordinate + 200, YCoordinate, WIDTH, HEIGHT);
		        previews.add(castlePreview);
		    }
		}

		// Queenside castling
		Piece leftRook = board.getPieceAt(XCoordinate - 300, YCoordinate);
		if (leftRook instanceof Rook && !((Rook) leftRook).hasMoved) {
		    boolean clear = true;
		    for (int xc = XCoordinate - 100; xc > XCoordinate - 300; xc -= 100) {
		        if (board.getPieceAt(xc, YCoordinate) != null) {
		            clear = false;
		            break;
		        }
		    }
		    if (clear) {
		        JLabel castlePreview = getPreview(XCoordinate - 200, YCoordinate, WIDTH, HEIGHT);
		        previews.add(castlePreview);
		    }
		}
		return previews;
	}
	
	private ArrayList<JLabel> blackCastle() {
		JPanel parent = (JPanel) this.getParent();
		Board board = (Board) parent;
		ArrayList<JLabel> previews = new ArrayList<>();
		// Kingside castling
		Piece rightRook = board.getPieceAt(XCoordinate + 300, YCoordinate);
			
		if (rightRook instanceof Rook && !((Rook) rightRook).hasMoved) {
		    boolean clear = true;
		    for (int xc = XCoordinate + 100; xc < XCoordinate + 300; xc += 100) {
		        if (board.getPieceAt(xc, YCoordinate) != null) {
		            clear = false;
		            break;
		        }
		    }
		    if (clear) {
		        JLabel castlePreview = getPreview(XCoordinate + 200, YCoordinate, WIDTH, HEIGHT);
		        previews.add(castlePreview);
		    }
		}

		// Queenside castling
		Piece leftRook = board.getPieceAt(XCoordinate - 400, YCoordinate);
		if (leftRook instanceof Rook && !((Rook) leftRook).hasMoved) {
			
		    boolean clear = true;
		    for (int xc = XCoordinate - 100; xc > XCoordinate - 400; xc -= 100) {
		        if (board.getPieceAt(xc, YCoordinate) != null) {
		            clear = false;
		            break;
		        }
		    }
		    if (clear) {
		        JLabel castlePreview = getPreview(XCoordinate - 200, YCoordinate, WIDTH, HEIGHT);
		        previews.add(castlePreview);
		    }
		}
		return previews;
	}
	
	private void whiteRookCastel(JLabel invisPreview, int oldX, Piece currentPiece, Board board) {
		// Castling rook move
		if (Math.abs(invisPreview.getX() - oldX) == 200) {
			// Kingside
			if (invisPreview.getX() > oldX) {
				Piece rook = board.getPieceAt(oldX + 400, currentPiece.YCoordinate);
				if (rook instanceof Rook) {
					rook.move(oldX + 100, currentPiece.YCoordinate, WIDTH, HEIGHT);
					((Rook) rook).hasMoved = true;
				}
			}
			// Queenside
			else {
				Piece rook = board.getPieceAt(oldX - 300, currentPiece.YCoordinate);
				if (rook instanceof Rook) {
					rook.move(oldX - 100, currentPiece.YCoordinate, WIDTH, HEIGHT);
					((Rook) rook).hasMoved = true;
				}
			}
		}
	}
	
	private void blackRookCastel(JLabel invisPreview, int oldX, Piece currentPiece, Board board) {
		// Castling rook move
		if (Math.abs(invisPreview.getX() - oldX) == 200) {
			// Kingside
			if (invisPreview.getX() > oldX) {
				Piece rook = board.getPieceAt(oldX + 300, currentPiece.YCoordinate);
				if (rook instanceof Rook) {
					rook.move(oldX + 100, currentPiece.YCoordinate, WIDTH, HEIGHT);
					((Rook) rook).hasMoved = true;
				}
			}
			// Queenside
			else {
				Piece rook = board.getPieceAt(oldX - 400, currentPiece.YCoordinate);
				if (rook instanceof Rook) {
					rook.move(oldX - 100, currentPiece.YCoordinate, WIDTH, HEIGHT);
					((Rook) rook).hasMoved = true;
				}
			}
		}
	}
	
	public boolean isSquareAttacked(int x, int y, String byColor, Board board) {
	    for (Piece p : board.piecesOnBoard) {
	        if (p.color.equals(byColor)) continue;

	        ArrayList<int[]> attackSquares = p.getAttackSquares();
	        for (int[] coords : attackSquares) {
	            if (coords[0] == x && coords[1] == y) return true;
	        }
	    }
	    return false;
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
        	inCheck = false;
        	if(color.equals("white")) {
        		whiteRookCastel(invisPreview, oldX, currentPiece, board);
        	}
        	else {
        		blackRookCastel(invisPreview, oldX, currentPiece, board);
        	}
   
        	hasMoved = true;
        	Piece.currentTurn = Piece.currentTurn.equals("white") ? "black" : "white";
        	
        	//after the move. check if it caused the other player to be checkmated
        	if(isCheckmate(Piece.currentTurn, board)) {
        		board.gameOver();                  		
        	}
        	
        	// Swap turn
        	((Board) currentPiece.getParent()).flipBoard();
        	updateBoard(); 
        	
        	
        }
	}
	
	
	
}