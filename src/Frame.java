import javax.swing.*;


public class Frame {

	public static void main(String[] args) {

		JFrame frame = new JFrame("Chess");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.setIconImage(new ImageIcon("./PiecesImg/pawn1.png").getImage());
		frame.setResizable(false);
		
		frame.setSize(800, 800);		//80x80 with each piece 10x10
		frame.setLocationRelativeTo(null);

		frame.add(new Board());
		frame.pack();
		frame.setVisible(true);
	}

	
}
