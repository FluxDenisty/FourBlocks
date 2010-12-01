import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;

public class FourBlocks extends JFrame{
	
	public static BlockLib lib;
	int score;
	JLabel header;
	JButton start;
	JPanel all;
	JPanel topPanel;
	JPanel gameBoard;
	JPanel blockBoard;
	JPanel info;
	JPanel scoreBox;
	JLabel scoreDisplay;
	JLabel game;
	Block active;
	Color bg;
	Timer gameClock;
	Boolean canMove;
	static Timer refresh;
	int delay;
	static int refreshRate;
	Block.blockType[][] grid;
	static FourBlocks instance;
	Boolean running;
	Boolean candrop;
	
	public static void main(String[] args) {
		lib = new BlockLib();
		final FourBlocks main = new FourBlocks();
//		refresh = new Timer(refreshRate, new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent arg0) {
//				instance = main;
//			}
//		});
	}
	
	FourBlocks() {
		running = false;
		canMove = true;
		setVisible(false);
		refreshRate = 100;
		score = 0;
		
		grid = new Block.blockType[10][20];
		for (int i = 0; i < 10; i++){
			for (int j = 0; j < 20; j++){
				grid[i][j] = null;
			}
		}
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(400, 600);
		KeyListener actions = new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				if (arg0.getKeyCode() == KeyEvent.VK_SPACE){
					candrop = true;
				}
			}
			
			@Override
			public void keyPressed(KeyEvent arg0) {
				if (canMove && running){
					canMove = false;
					if (arg0.getKeyCode() == KeyEvent.VK_DOWN){
						down();
					}else if (arg0.getKeyCode() == KeyEvent.VK_UP){
						rotate();
					}else if (arg0.getKeyCode() == KeyEvent.VK_LEFT){
						left();
					}else if (arg0.getKeyCode() == KeyEvent.VK_RIGHT){
						right();
					}else if (arg0.getKeyCode() == KeyEvent.VK_SPACE){
							fall();
					}
					paintBlock(blockBoard.getGraphics(), active);
					canMove = true;
				}else if (!running && arg0.getKeyCode() == KeyEvent.VK_SPACE){
//					header.setText("BEGIN");
					game.setVisible(false);
					clearBoard();
					display(blockBoard.getGraphics());
					active = new Block();
					paintBlock(blockBoard.getGraphics(), active);
					gameClock.start();
					running = true;
				}
			}
		};
		ActionListener go = new ActionListener() {
		
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals("Start") && !running){
					header.setText("BEGIN");
					active = new Block();
					paintBlock(blockBoard.getGraphics(), active);
					gameClock.start();
					candrop = true;
					blockBoard.remove(game);
					running = true;
				}
			}
		};
		//top area
		header = new JLabel("I HAS A JAVA", SwingConstants.CENTER);
		header.setLocation(0, 0);
		
		start = new JButton("Start");
		start.addActionListener(go);
		
		topPanel = new JPanel();
		topPanel.setAlignmentX(CENTER_ALIGNMENT);
		topPanel.add(header);
		topPanel.add(start);
		
		//game panel
		game = new JLabel();
		game.setPreferredSize(new Dimension(200, 100));
		game.setAlignmentX(CENTER_ALIGNMENT);
		game.setAlignmentY(CENTER_ALIGNMENT);
		game.setText("Press space to begin");
		
		blockBoard = new JPanel();
		blockBoard.setAlignmentY(CENTER_ALIGNMENT);
		blockBoard.setAlignmentX(CENTER_ALIGNMENT);
		bg = Color.GRAY;
		blockBoard.setBackground(bg);
		blockBoard.setPreferredSize(new Dimension(251, 501));
		blockBoard.add(game);
		
		scoreDisplay = new JLabel();
		scoreDisplay.setText("0");
		
		scoreBox = new JPanel();
		scoreBox.setPreferredSize(new Dimension(75,50));
		scoreBox.add(scoreDisplay);
		
		info = new JPanel();
		info.setPreferredSize(new Dimension(100, 500));
		info.setBackground(new Color(102, 204, 204));
		info.add(scoreBox);
		
		gameBoard = new JPanel();
		gameBoard.setPreferredSize(new Dimension(400, 550));
		gameBoard.setBackground(bg);
		gameBoard.add(blockBoard);
		gameBoard.add(info);

		all = new JPanel();
		all.setPreferredSize(new Dimension(400, 600));
		all.setAlignmentX(LEFT_ALIGNMENT);
		
//		all.add(topPanel);
		all.add(gameBoard);
		all.addKeyListener(actions);
		
		delay = 1000;
		gameClock = new Timer(delay, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (canMove)
					down();
			}
		});


		this.add(all);
		
		setVisible(true);

		all.requestFocusInWindow();
	}

	public void display(Graphics g){
		Image buffer = createImage(blockBoard.getWidth(), blockBoard.getHeight());
		Graphics gt = buffer.getGraphics();
		for (int i = 0; i < 10; i++){
			for (int j = 0; j < 20; j++){
				if (grid[i][j] == null){
					gt.setColor(Color.GRAY);
				}else{
					switch (grid[i][j]){
					case I:
						gt.setColor(Color.CYAN);
						break;
					case J:
						gt.setColor(Color.BLUE);
						break;
					case L:
						gt.setColor(Color.ORANGE);
						break;
					case O:
						gt.setColor(Color.YELLOW);
						break;
					case S:
						gt.setColor(Color.GREEN);
						break;
					case T:
						gt.setColor(Color.MAGENTA);
						break;
					case Z:
						gt.setColor(Color.RED);
						break;
					}
				}
				paintSquare(i, j, gt);
			}
		}
		g.drawImage(buffer, 0, 0, this);
	}
	public void paintBlock(Graphics g,Block b){
		g.setColor(b.colour);
		for (int i = 0; i < b.sizex; i++){
			for (int j = 0; j < b.sizey; j++){
				if (b.filled[j][i])
					paintSquare(b.x + i, b.y + j, g);
				else{
					clearSquare(b.x + i, b.y + j, g);
				}
			}
		}
	}
	public void paintSquare(int x, int y, Graphics g){
		g.fillRect(x * 25,y * 25,25,25);
		Color old = g.getColor();
		g.setColor(Color.BLACK);
		g.drawRect(x * 25,y * 25,25,25);
		g.setColor(old);
	}
	public void clearSquare(int x, int y, Graphics g){
		Color old = g.getColor();
		g.setColor(new Color(0, 0, 0, 0));
		g.fillRect(x * 25,y * 25,25,25);
		g.setColor(Color.BLACK);
		g.drawRect(x * 25,y * 25,25,25);
		g.setColor(old);
	}
	
//	public void clearBlock(Graphics g,Block b){
//		g.setColor(bg);
//		for (int i = 0; i < b.sizex; i++){
//			for (int j = 0; j < b.sizey; j++){
//				if (b.filled[j][i])
//					clearSquare(b.x + i, b.y + j, g);
//			}
//		}
//	}
//	public void clearSquare(int x, int y, Graphics g){
//		g.fillRect(x * 25,y * 25,25,25);
//	}
	
	public void fall(){
		while (checkColision(0,1)){
			active.y++;
			if (!running)
				gameOver();
		}
		display(blockBoard.getGraphics());
		paintBlock(blockBoard.getGraphics(), active);
	}
	public void down(){
		if (checkColision(0,1)){
			active.y++;
		}
		display(blockBoard.getGraphics());
		paintBlock(blockBoard.getGraphics(), active);
		if (!running)
			gameOver();
	}
	public void left(){
		if (checkColision(-1,0)){
			active.x--;
			display(blockBoard.getGraphics());
			paintBlock(blockBoard.getGraphics(), active);
		}
	}
	public void right(){
		if (checkColision(1,0)){
			active.x++;
			display(blockBoard.getGraphics());
			paintBlock(blockBoard.getGraphics(), active);	
		}
	}
	public void rotate(){
		int next = 0;
		int old  = active.dir;
		if (old ==3)
			next = 0;
		else
			next = old + 1;
		active.filled = active.model[next];
		if (checkColision(0, 0)){
			active.dir = next;
			display(blockBoard.getGraphics());
		}else{
			active.filled = active.model[old];
		}
		
		
		/** OLD METHOD **
		Boolean[][] temp = new Boolean[active.sizex][active.sizey];
		for (int i = 0; i < active.sizex; i++){
			for (int j = 0; j < active.sizey; j++){
				temp[i][j] = active.filled[j][i];
			}
		}
		for (int i = 0; i < active.sizey; i++){
			for (int j = 0; j < active.sizex; j++){
				if (temp[j][i]){
					try {
						if (grid[i + active.x][j + active.y] != null)
							return;
					}catch(Exception e){
							return;
					}
				}
			}
		}
		active.filled = temp;
		int x = active.sizex;
		active.sizex = active.sizey;
		active.sizey = x;
		display(blockBoard.getGraphics()); **/
	}
	public Boolean checkColision(int x,int y){
		for (int i = 0; i < active.sizex; i++){
			for (int j = 0; j < active.sizey; j++){
				if (active.filled[j][i]){
					try{
						if (grid[i + active.x + x][j + active.y + y] != null){
							if (y > 0)
								setBlock();
							return false;
						}
					}catch(Exception e){
						if (i + active.x + x > 9){
							return false;
						}else if (i + active.x + x < 0){
							return false;
						}else if (j + active.y + y > 19){
							setBlock();
							return false;
						}
					}
				}
			}
		}
		return true;
	}
	public void setBlock(){
		for (int i = 0; i < active.sizex; i++){
			for (int j = 0; j < active.sizey; j++){
				if (active.filled[j][i]){
					if (i + active.y == 0){
						running = false;
					}
					grid[i + active.x][j + active.y] = active.type;
				}
			}
		}
		checkRows();
		paintBlock(blockBoard.getGraphics(), active);
		scoreDisplay.setText(Integer.toString(score));
		active = new Block();
			
	}
	public void checkRows(){
		Boolean kill = true;
		for (int i = 0; i < 20; i++){
			kill = true;
			for (int j = 0; j < 10; j++){
				if (grid[j][i] == null){
					kill = false;
				}
			}
			if (kill){
				for (int k = i; k > 0; k--){
					for (int l = 0;l < 10; l++){
						grid[l][k] = grid[l][k-1];
					}
				}
				for (int l = 0;l < 10; l++){
					grid[l][0] = null;
				}
				score+= 10;
			}
		}
	}
	public void gameOver(){
		gameClock.stop();
		running = false;
		canMove = false;
		game.setText("Game Over!");
		game.setVisible(true);
	}
	public void clearBoard(){
		for (int i = 0; i < 20; i++){
			for (int j = 0; j < 10; j++){
					grid[j][i] = null;
			}
		}
	}
}
