
public class BlockLib {
	Boolean[][][] IBlock;
	Boolean[][][] JBlock;
	Boolean[][][] LBlock;
	Boolean[][][] OBlock;
	Boolean[][][] SBlock;
	Boolean[][][] TBlock;
	Boolean[][][] ZBlock;
	
	public BlockLib(){
		IBlock = new Boolean[][][]{
				{
					{false,false,false,false},
					{false,false,false,false},
					{true ,true , true, true},
					{false,false,false,false}
				},
				{
					{false,true,false,false},
					{false,true,false,false},
					{false,true,false,false},
					{false,true,false,false}
				},
				{
					{false,false,false,false},
					{false,false,false,false},
					{true ,true , true, true},
					{false,false,false,false}
				},
				{
					{false,true,false,false},
					{false,true,false,false},
					{false,true,false,false},
					{false,true,false,false}
				}
		};
		JBlock = new Boolean[][][]{
				{
					{false,false,false},
					{true ,true ,true },
					{false,false,true }
				},
				{
					{false,true ,false},
					{false,true ,false},
					{true ,true ,false}
				},
				{
					{false,false,false},
					{true ,false,false},
					{true ,true ,true },
				},
				{
					{false,true ,true },
					{false,true ,false},
					{false,true ,false}
				},
				
		};
		LBlock = new Boolean[][][]{
				{
					{false,false,false},
					{true ,true ,true },
					{true ,false,false}
				},
				{
					{true ,true ,false},
					{false,true ,false},
					{false,true ,false}
				},
				{
					{false,false,false},
					{false,false,true },
					{true ,true ,true },
				},
				{
					{false,true ,false},
					{false,true ,false},
					{false,true ,true }
				},
				
		};
		OBlock = new Boolean[][][]{
				{
					{true ,true},
					{true ,true},
				},
				{
					{true ,true},
					{true ,true},
				},
				{
					{true ,true},
					{true ,true},
				},
				{
					{true ,true},
					{true ,true},
				},
		};
		SBlock = new Boolean[][][]{
				{
					{false,false,false},
					{false,true ,true },
					{true ,true ,false}
				},
				{
					{false,true ,false},
					{false,true ,true },
					{false,false,true }
				},
				{
					{false,false,false},
					{false,true ,true },
					{true ,true ,false},
				},
				{
					{false,true ,false},
					{false,true ,true },
					{false,false,true }
				},
		};
		TBlock = new Boolean[][][]{
				{
					{false,false,false},
					{true ,true ,true },
					{false,true ,false}
				},
				{
					{false,true ,false},
					{true ,true ,false},
					{false,true ,false}
				},
				{
					{false,false,false},
					{false,true ,false},
					{true ,true ,true },
				},
				{
					{false,true ,false},
					{false,true ,true },
					{false,true ,false}
				},
		};
		ZBlock = new Boolean[][][]{
				{
					{false,false,false},
					{true ,true ,false},
					{false,true ,true }
				},
				{
					{false,true ,false},
					{true ,true ,false},
					{true ,false,false}
				},
				{
					{false,false,false},
					{true ,true ,false},
					{false,true ,true }
				},
				{
					{false,true ,false},
					{true ,true ,false},
					{true ,false,false}
				},
		};
	}
}
