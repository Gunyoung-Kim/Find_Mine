import java.util.Random;
import java.util.stream.IntStream;

public class GameData {
	private final int NUM_OF_MINE = 99;
	private final int ROW = 16;
	private final int COLUMN = 30;
	private Box[][] gameBoard = new Box[ROW][COLUMN];
	
	public GameData() {
		
		for(int i=0;i<ROW;i++) {
			for(int j=0;j<COLUMN;j++) {
				gameBoard[i][j] = new Box();
			}
		}
		
	}
	
	public void setGameData(int start_x,int start_y) {
		/* 지뢰 심기 */
		int firstTouch = 30*start_x + start_y;
		int[] arr = new Random(System.currentTimeMillis()).ints(0,480).limit(99).toArray();
		
		for(int i=0;i<NUM_OF_MINE;i++) {
			int row = arr[i]/30;
			int col = arr[i]%30;
			
			gameBoard[row][col].setMine();
		}
		
		/*주변 지뢰 수 계산하기 */
		for(int i=0;i<ROW;i++) {
			for(int j=0;j<COLUMN;j++) {
				if(!gameBoard[i][j].IsMine()) {
					int[] drow = new int[] {-1,0,1,1,1,0,-1,-1};
					int[] dcol= new int[] {1,1,1,0,-1,-1,-1,0};
					int neighborhood = 0;
					
					for(int k=0;k<8;k++) {
						int nrow = i+drow[k];
						int ncol = j+dcol[k];
						
						if(nrow >=0&& nrow<ROW && ncol>=0 && ncol <COLUMN && gameBoard[nrow][ncol].IsMine())
							neighborhood++;
					}
					
					gameBoard[i][j].setNeighborhood(neighborhood);
				}
			}
		}
	}
	
	/* mine -> return -1, no mine-> return neighborhood */
	public int Click(int row,int column) {
		gameBoard[row][column].setIsClicked(true);
		if(gameBoard[row][column].IsMine())
			return -1;
		else 
			return gameBoard[row][column].getNeighborhood();
		
	}
	
	public boolean isClicked(int row,int col) {
		return gameBoard[row][col].getIsClicked();
	}
	
	public boolean isMine(int row,int col) {
		return gameBoard[row][col].IsMine();
	}
	
	class Box {
		private boolean mine;
		private boolean isClicked;
		private int neighborhood;
		
		public Box() {
			mine = false;
			isClicked = false;
			neighborhood = 0;
		}
		
		public void setIsClicked(boolean bln) {
			isClicked = bln;
		}
		
		public boolean getIsClicked() {
			return isClicked;
		}
		
		public void setNeighborhood(int neighborhood) {
			this.neighborhood = neighborhood;
		}
		
		public int getNeighborhood() {
			return this.neighborhood;
		}
		
		public void setMine() {
			mine = true;
		}
		
		public boolean IsMine() {
			return mine;
		}
	}
	
}