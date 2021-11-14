package nhom11;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.util.Date;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class MachineGraphics extends JPanel implements Runnable {
	private static final long serialVersionUID = 1L;
	private int row;
	private int col;
	private int bound = 2;
	private int size = 50;
	private int score = 0;
	private JButton[][] btn;
	private Point p1 = null;
	private Point p2 = null;
	private Point p = null;
	private Algorithm algorithm;
	private MyLine line;
	private MyFrame frame;
	private Color backGroundColor = Color.lightGray;
	//private int item;
	private boolean kt = false;

	public MachineGraphics(MyFrame frame, int row, int col) {
		this.frame = frame;
		this.row = row + 2;
		this.col = col + 2;
		//item = row * col / 2;

		setLayout(new GridLayout(row, col, bound, bound));
		setBackground(backGroundColor);
		setPreferredSize(new Dimension((size + bound) * col, (size + bound) * row));
		setBorder(new EmptyBorder(10, 10, 10, 10));
		setAlignmentY(JPanel.CENTER_ALIGNMENT);

		newGame();

	}

	public void newGame() {
		algorithm = new Algorithm(this.row, this.col);
		addArrayButton();

	}

	private void addArrayButton() {
		btn = new JButton[row][col];
		for (int i = 1; i < row - 1; i++) {
			for (int j = 1; j < col - 1; j++) {
				btn[i][j] = createButton(i + "," + j);
				Icon icon = getIcon(algorithm.getMatrix()[i][j]);
				btn[i][j].setIcon(icon);
				add(btn[i][j]);
			}
		}
	}

	private Icon getIcon(int index) {
		int width = 48, height = 48;
		Image image = new ImageIcon(getClass().getResource("/nhom11/icon/icon" + index + ".jpg")).getImage();
		Icon icon = new ImageIcon(image.getScaledInstance(width, height, image.SCALE_SMOOTH));
		return icon;

	}

	private JButton createButton(String action) {
		JButton btn = new JButton();
		btn.setActionCommand(action);
		btn.setBorder(null);
		// btn.addActionListener(this);
		return btn;
	}

	public void execute(Point p1, Point p2) {
		//System.out.println("delete");
		setDisable(btn[p1.x][p1.y]);
		setDisable(btn[p2.x][p2.y]);
	}

	private void setDisable(JButton btn) {
		btn.setIcon(null);
		btn.setBackground(backGroundColor);
		btn.setEnabled(false);
	}

	public static void pause(int seconds) {
		System.out.println("pause");
		Date start = new Date();
		Date end = new Date();
		while (end.getTime() - start.getTime() < seconds * 1000) {
			end = new Date();
		}
	}

	public void search() {

		for (int x = 1; x < row - 1; ++x) {
			for (int y = 1; y < col - 1; ++y) {
				p2 = new Point(x, y);
				// System.out.println("(" + p1.x + "," + p1.y + ")" + " --> " + "(" + p2.x + ","
				// + p2.y + ")");
				line = algorithm.checkTwoPoint(p1, p2);
				if (line != null) {
					// System.out.println("line != null");
					if (algorithm.getMatrix()[p1.x][p1.y] != 0 || algorithm.getMatrix()[p2.x][p2.y] != 0) {
						score += 10;
					}
					
					algorithm.getMatrix()[p1.x][p1.y] = 0;
					algorithm.getMatrix()[p2.x][p2.y] = 0;
					// algorithm.showMatrix();
					execute(p1, p2);
					line = null;
					
					
					// item--;
					frame.time++;
					frame.getMachineLbScore().setText(score + "");
				}
				btn[p1.x][p1.y].setBorder(null);
				p2 = null;
				// System.out.println("done");
			}
		}

	}

	@Override
	public void run() {
		while (kt == false) {
			for (int i = 1; i < row - 1; ++i) {
				for (int j = 1; j < col - 1; ++j) {
					kt = true;
					p1 = new Point(i, j);
					// System.out.println(i + "," + j);
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					search();
					for (int a = 1; a < row - 1; ++a) {
						for (int b = 1; b < col - 1; ++b) {
							p = new Point(a, b);
							if (algorithm.getMatrix()[p.x][p.y] != 0) {
								kt = false;
							}
						}
					}

				}
			}
			if (kt == true) {
				frame.showDialogNewGame("MÁY đã thắng!\nBạn có muốn chơi lại?", "Thua cuộc");
				break;
			}
			run();
		}
	}
}
