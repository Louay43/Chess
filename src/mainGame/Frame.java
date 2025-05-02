package mainGame;
import javax.swing.ImageIcon;
import javax.swing.JFrame;



public class Frame {

	public static void main(String[] args) {

		JFrame frame = new JFrame("Chess");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.setIconImage(new ImageIcon("./PiecesImg/pawn1.png").getImage());
		frame.setResizable(false);
		
		frame.setSize(800, 800);		//800x800 with each piece 100x100
		frame.setLocationRelativeTo(null);
		
		Board board = new Board();
		frame.add(board);
		frame.pack();
		frame.setVisible(true);
	}

	
}
