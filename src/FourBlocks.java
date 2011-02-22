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
	JPanel all;
	JPanel topPanel;
	JPanel gameBoard;
	JPanel blockBoard;
	JPanel info;
	JPanel scoreBox;
	JPanel lineBox;
	JLabel scoreDisplay;
	JPanel nextDisplay;
	JPanel holdDisplay;
	JLabel game;
	JLabel lineText;
	JLabel nextText;
	JLabel holdText;
	JLabel linenum;
	JLabel scoreText;
	Block active;
	Block next;
	Block hold;
	Timer gameClock;
	static Timer refresh;
	Block.blockType[][] grid;
	Color bg = Color.GRAY;
	int score = 0;
	int lines = 0;
	int delay = 1000;
	int level = 1;
	int linesTill;
	Boolean canMove = true;
	Boolean running = false;
	Boolean canDrop = true;
	Boolean canHold = true;
	
	public static void main(String[] args) {
		lib = new BlockLib();
		final FourBlocks main = new FourBlocks();
	}
	
	FourBlocks() {
		
		setVisible(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(400, 600);
		
		grid = new Block.blockType[10][20]; // init game grid to null (empty square) 
		for (int i = 0; i < 10; i++){
			for (int j = 0; j < 20; j++){
				grid[i][j] = null;
			}
		}
		
		//Listeners
		KeyListener actions = new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				if (arg0.getKeyCode() == KeyEvent.VK_SPACE){
					canDrop = true;
				}
			}
			
			@Override
			public void keyPressed(KeyEvent arg0) {  
				if (canMove && running && active != null){  //Take ACTION
					canMove = false;
					if (arg0.getKeyCode() == KeyEvent.VK_DOWN || arg0.getKeyCode() == KeyEvent.VK_S){
						down();
					}else if (arg0.getKeyCode() == KeyEvent.VK_UP || arg0.getKeyCode() == KeyEvent.VK_W){
						rotate();
					}else if (arg0.getKeyCode() == KeyEvent.VK_LEFT || arg0.getKeyCode() == KeyEvent.VK_A){
						left();
					}else if (arg0.getKeyCode() == KeyEvent.VK_RIGHT || arg0.getKeyCode() == KeyEvent.VK_D){
						right();
					}else if (arg0.getKeyCode() == KeyEvent.VK_SPACE){
						fall();
					}else if (arg0.getKeyCode() == KeyEvent.VK_H){
						hold();
					}
					display(blockBoard.getGraphics());
					canMove = true;
				}else if (!running && arg0.getKeyCode() == KeyEvent.VK_ENTER){ //Begin new game
					game.setVisible(false);
					blockBoard.remove(game);
					clearBoard();
					active = new Block();
					gameClock.start();
					running = true;
					canMove = true;
					canDrop = true;
					score = 0;
					lines = 0;
					level = 1;
					linesTill = 15;
					scoreDisplay.setText("0");
					linenum.setText("0");
					gameClock.setDelay(delay);
					display(blockBoard.getGraphics());
				}
			}
		};
		
		gameClock = new Timer(delay, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (active == null){
					loadNext();
				}
				else if (canMove)
					down();
				display(blockBoard.getGraphics());
			}
		});
		
		//GUI init
		game = new JLabel();
		game.setPreferredSize(new Dimension(200, 100));
		game.setAlignmentX(CENTER_ALIGNMENT);
		game.setAlignmentY(CENTER_ALIGNMENT);
		game.setText("Press enter to begin");
		
		blockBoard = new JPanel();
		blockBoard.setAlignmentY(CENTER_ALIGNMENT);
		blockBoard.setAlignmentX(CENTER_ALIGNMENT);
		blockBoard.setBackground(bg);
		blockBoard.setPreferredSize(new Dimension(251, 501));
		blockBoard.add(game);
		
		scoreText = new JLabel();
		scoreText.setPreferredSize(new Dimension(70,10));
		scoreText.setText("   SCORE");
		
		scoreDisplay = new JLabel();
		scoreDisplay.setText("0");
		
		lineText = new JLabel();
		lineText.setText("Lines Done");
		
		linenum = new JLabel();
		linenum.setAlignmentX(CENTER_ALIGNMENT);
		linenum.setText("0");
		
		nextText = new JLabel();
		nextText.setText("Next Block");
		
		nextDisplay = new JPanel();
		nextDisplay.setBackground(Color.BLACK);
		nextDisplay.setPreferredSize(new Dimension(110,85));
		
		holdText = new JLabel();
		holdText.setText("HOLD (press H)");
		
		holdDisplay = new JPanel();
		holdDisplay.setBackground(Color.WHITE);
		holdDisplay.setPreferredSize(new Dimension(110,85));
		
		lineBox = new JPanel();
		lineBox.setPreferredSize(new Dimension(80,50));
		lineBox.setBackground(Color.ORANGE);
		lineBox.setAlignmentX(CENTER_ALIGNMENT);
		lineBox.add(lineText);
		lineBox.add(linenum);
		
		scoreBox = new JPanel();
		scoreBox.setPreferredSize(new Dimension(80,50));
		scoreBox.setAlignmentX(CENTER_ALIGNMENT);
		scoreBox.add(scoreText);
		scoreBox.add(scoreDisplay);
		
		info = new JPanel();
		info.setPreferredSize(new Dimension(120, this.getHeight()));
		info.setBackground(new Color(148, 112, 255));
		info.add(scoreBox);
		info.add(lineBox);
		info.add(nextText);
		info.add(nextDisplay);
		info.add(holdText);
		info.add(holdDisplay);
		
		gameBoard = new JPanel();
		gameBoard.setPreferredSize(new Dimension(400, this.getHeight()));
		gameBoard.setBackground(bg);
		gameBoard.add(blockBoard);
		gameBoard.add(info);

		all = new JPanel();
		all.setPreferredSize(new Dimension(this.getWidth(), this.getHeight()));
		all.setAlignmentX(LEFT_ALIGNMENT);
		all.add(gameBoard);
		all.addKeyListener(actions);

		this.add(all);
		
		setVisible(true);

		all.requestFocusInWindow();
	}

	public void display(Graphics g){
		Image buffer = createImage(blockBoard.getWidth(),blockBoard.getHeight());
		Display.go(g,buffer,grid,active,this);
	}
	
	public void loadNext() {
		canHold = true;
		if (next != null)
			active = new Block(next.type);
		else
			active = new Block();
		next = new Block();
		next.x = 0;
		next.y = 0;
		nextDisplay.getGraphics().setColor(Color.BLACK);
		nextDisplay.getGraphics().fillRect(0, 0, 110, 85);
		Display.paintBlock(nextDisplay.getGraphics(), next);
		
		canMove = true;
		display(blockBoard.getGraphics());
	}

	public void hold(){
		if (!canHold)
			return;
		canHold = false;
		if (hold == null){
			hold = new Block(active.type);
			hold.x = hold.y = 0;
			loadNext(); 
		}else{
			Block.blockType temp = active.type;
			active = new Block(hold.type);
			hold = new Block(temp);
		}
		hold.x = hold.y = 0;
		holdDisplay.getGraphics().setColor(Color.WHITE);
		holdDisplay.getGraphics().fillRect(0, 0, 110, 85);
		Display.paintBlock(holdDisplay.getGraphics(), hold);
		display(blockBoard.getGraphics());
	}
	public void fall(){
		while (checkColision(0,1)){
			active.y++;
			if (!running)
				gameOver();
		}
	}
	public void down(){
		if (checkColision(0,1)){
			active.y++;
			if (!running)
				gameOver();
		}
	}
	public void left(){
		if (checkColision(-1,0)){
			active.x--;
		}
	}
	public void right(){
		if (checkColision(1,0)){
			active.x++;	
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
		}else{
			active.filled = active.model[old];
		}
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
						gameOver();
					}
					grid[i + active.x][j + active.y] = active.type;
				}
			}
		}
		checkRows();
		scoreDisplay.setText(Integer.toString(score));
		display(blockBoard.getGraphics());
		active = null;
		canMove = false;
		if (linesTill  <= 0){
			level++;
			linesTill = level * 5 + 10;
			gameClock.setDelay(delay - level * 100);
		}	
			
	}
	public void checkRows(){
		Boolean kill = true;
		int temp = lines;
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
				lines++;
				linesTill--;
				linenum.setText(Integer.toString(lines));
				score+= 10;
			}
		}
		temp = lines - temp;
		if (temp > 0)
			score += (temp-1)*2*10;
	}
	public void gameOver(){
		gameClock.stop();
		running = false;
		canMove = false;
		display(blockBoard.getGraphics());
		game.setText("Game Over!");
		blockBoard.add(game,0);
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
