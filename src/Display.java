import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Component;



public class Display {
	public static void go (Graphics g, Image buffer, Block.blockType[][] grid, Block active, FourBlocks dis){
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
		if (active != null)
			paintBlock(gt, active);
		g.drawImage(buffer, 0, 0, dis);
	}
	public static void paintBlock(Graphics g,Block b){
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
	public static void paintSquare(int x, int y, Graphics g){
		g.fillRect(x * 25,y * 25,25,25);
		Color old = g.getColor();
		g.setColor(Color.BLACK);
		g.drawRect(x * 25,y * 25,25,25);
		g.setColor(old);
	}
	public static void clearSquare(int x, int y, Graphics g){
		Color old = g.getColor();
		g.setColor(new Color(0, 0, 0, 0));
		g.fillRect(x * 25,y * 25,25,25);
		g.setColor(Color.BLACK);
		g.drawRect(x * 25,y * 25,25,25);
		g.setColor(old);
	}
}
