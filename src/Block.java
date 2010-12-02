import java.awt.Color;


public class Block {
	public blockType type;
	public Color colour;
	public int x;
	public int y;
	public int dir;
	public Boolean[][][] model;
	public Boolean[][] filled;
	public int sizex;
	public int sizey;
	
	public enum blockType{
		I,J,L,O,S,T,Z
	}
	public Block(){
		int n  = (int) Math.floor((Math.random()*7));
		blockType values[] = blockType.values();
		type = values[n];
		initBlock();
	}
	public Block(int type){
		blockType values[] = blockType.values();
		this.type = values[type];
		initBlock();
	}
	public Block(blockType type){
		this.type = type;
		initBlock();
	}
	public void initBlock(){
		x = 5;
		dir = 0;
		if (type == blockType.I){
			colour = Color.CYAN;
			sizex = 4;
			sizey = 4;
			model =  FourBlocks.lib.IBlock;
			filled = model[0];
		}else if (type == blockType.J){
			colour = Color.BLUE;
			sizex = 3;
			sizey = 3;
			model =  FourBlocks.lib.JBlock;
			filled = model[0];
		}else if (type == blockType.L){
			colour = Color.ORANGE;
			sizex = 3;
			sizey = 3;
			model =  FourBlocks.lib.LBlock;
			filled = model[0];
		}else if (type == blockType.O){
			colour = Color.YELLOW;
			sizex = 2;
			sizey = 2;
			model =  FourBlocks.lib.OBlock;
			filled = model[0];
		}else if (type == blockType.S){
			colour = Color.GREEN;
			sizex = 3;
			sizey = 3;
			model =  FourBlocks.lib.SBlock;
			filled = model[0];
		}else if (type == blockType.T){
			colour = Color.MAGENTA;
			sizex = 3;
			sizey = 3;
			model =  FourBlocks.lib.TBlock;
			filled = model[0];
		}else if (type == blockType.Z){
			colour = Color.RED;
			sizex = 3;
			sizey = 3;
			model =  FourBlocks.lib.ZBlock;
			filled = model[0];
		}
		y = - sizey/2;
	}
	
}
