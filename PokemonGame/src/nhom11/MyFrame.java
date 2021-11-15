package nhom11;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

public class MyFrame extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	//private String author = "Nhom 11";
	private int maxTime = 300;
	public int time = maxTime;
	private int row = 10;
	private int col = 10;
	private int width = 1300;
	private int height = 600;
	private JLabel lbMyScore;
	private JLabel lbMachineScore;
	//private JProgressBar progressTime;
	private JButton btnNewGame;
	private MyGraphics myGraphicsPanel;
	private MachineGraphics machineGraphicsPanel;
	private JPanel mainPanel;

	public MyFrame() {
		add(mainPanel = createMainPanel());
		setTitle("Pikachu Game");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(width, height);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private JPanel createMainPanel() {
		JPanel panel = new JPanel(new FlowLayout());
		panel.add(createLeftPanel());
		panel.add(createRightPanel());
		return panel;
	}

	private JPanel createRightPanel() {
		machineGraphicsPanel = new MachineGraphics(this, row, col);
		Thread t1 = new Thread(machineGraphicsPanel);
		t1.start();
		JPanel panel = new JPanel(new GridBagLayout());
		// panel.setBorder(new EmptyBorder(20, 20, 20, 20));
		panel.setBackground(Color.gray);
		panel.add(machineGraphicsPanel);
		return panel;
	}

	private JPanel createLeftPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(createGraphicsPanel(), BorderLayout.CENTER);
		panel.add(createControlPanel(), BorderLayout.EAST);
		//panel.add(createStatusPanel(), BorderLayout.PAGE_END);
		return panel;
	}

	private JPanel createGraphicsPanel() {
		myGraphicsPanel = new MyGraphics(this, row, col);
		JPanel panel = new JPanel(new GridBagLayout());
		// panel.setBorder(new EmptyBorder(20, 20, 20, 20));
		panel.setBackground(Color.gray);
		panel.add(myGraphicsPanel);
		return panel;
	}

	private JPanel createControlPanel() {
		lbMyScore = new JLabel("0");
		lbMachineScore = new JLabel("0");
		// lbTime = new JLabel("0");
//		progressTime = new JProgressBar(0, 100);
//		progressTime.setValue(100);

		// create panel container score and time

		JPanel panelLeft = new JPanel(new GridLayout(0, 1, 5, 5));
		panelLeft.add(new JLabel("My Score:"));
		panelLeft.add(new JLabel("Machine Score:"));

		JPanel panelCenter = new JPanel(new GridLayout(0, 1, 5, 5));
		panelCenter.add(lbMyScore);
		panelCenter.add(lbMachineScore);
//		panelCenter.add(progressTime);

		JPanel panelScoreAndTime = new JPanel(new BorderLayout(5, 0));
		panelScoreAndTime.add(panelLeft, BorderLayout.WEST);
		panelScoreAndTime.add(panelCenter, BorderLayout.CENTER);

		// create panel container panelScoreAndTime and button new game
		JPanel panelControl = new JPanel(new BorderLayout(10, 10));
		panelControl.setBorder(new EmptyBorder(10, 3, 5, 3));
		panelControl.add(panelScoreAndTime, BorderLayout.CENTER);
		panelControl.add(btnNewGame = createButton("New Game"), BorderLayout.PAGE_END);

//		Icon icon = new ImageIcon(getClass().getResource(
//				"/nhom11/icon/pokemon.png"));

		// use panel set Layout BorderLayout to panel control in top
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(new TitledBorder("Status"));
		panel.add(panelControl, BorderLayout.PAGE_START);
		// panel.add(new JLabel(icon), BorderLayout.CENTER);
		return panel;
	}

	// create status panel container author
//	private JPanel createStatusPanel() {
//		JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
//		panel.setBackground(Color.lightGray);
//		JLabel lbAuthor = new JLabel(author);
//		lbAuthor.setForeground(Color.blue);
//		panel.add(lbAuthor);
//		return panel;
//	}

	// create a button
	private JButton createButton(String buttonName) {
		JButton btn = new JButton(buttonName);
		btn.addActionListener(this);
		return btn;
	}

	public void newGame() {
		mainPanel.removeAll();
		mainPanel.add(createLeftPanel());
		mainPanel.add(createRightPanel());
		mainPanel.validate();
		mainPanel.setVisible(true);
		myGraphicsPanel.setScore(0);
		machineGraphicsPanel.setScore(0);
		lbMyScore.setText("0");
		lbMachineScore.setText("0");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnNewGame) {
			newGame();
			//showDialogNewGame("Bạn có muốn chơi game mới", "Thông báo");
		}
	}

	// public JLabel getLbTime() {
	// return lbTime;
	// }
	//
	// public void setLbTime(JLabel lbTime) {
	// this.lbTime = lbTime;
	// }

//	@Override
//	public void run() {
//		while (true) {
//			try {
//				Thread.sleep(1000);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//			progressTime.setValue((int) ((double) time / maxTime * 100));
//		}
//	}

	public JLabel getLbScore() {
		return lbMyScore;
	}

	public void setLbScore(JLabel lbScore) {
		this.lbMyScore = lbScore;
	}
	
	public JLabel getMachineLbScore() {
		return lbMachineScore;
	}

	public void getMachineLbScore(JLabel lbScore) {
		this.lbMachineScore = lbScore;
	}

//	public JProgressBar getProgressTime() {
//		return progressTime;
//	}
//
//	public void setProgressTime(JProgressBar progressTime) {
//		this.progressTime = progressTime;
//	}

	public void setTime(int time) {
		this.time = time;
	}

	public int getTime() {
		return time;
	}

	public void showDialogNewGame(String message, String title) {
		int select = JOptionPane.showOptionDialog(null, message, title, JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE, null, null, null);
		if (select == 0) {
			newGame();
		} else {
			System.exit(0);
		}
	}
}