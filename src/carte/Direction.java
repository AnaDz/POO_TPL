package carte;

public enum Direction {
	NORD(new int[] {0,1}), SUD(new int[] {0,-1}), EST(new int[] {1,0}), OUEST(new int[] {-1,0});
	
	private int dir[] = new int[2];
	
	Direction(int[] dir){
		this.dir[0] = dir[0];
		this.dir[1] = dir[1];
	}
	
	int getX(){
		return dir[0];
	}
	
	int getY(){
		return dir[1];
	}
}
