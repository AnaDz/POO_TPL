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
        
        static public Direction getDir(int l, int c){
            switch(l){
                case 0 :
                    switch(c){
                        case 1:
                            return EST;
                        case -1:
                            return OUEST;
                        default :
                            return null;
                    }
                case 1 :
                    switch(c){
                        case 0:
                            return NORD;
                        default :
                            return null;
                    }
                case -1 :
                    switch(c){
                        case 0 :
                            return SUD;
                        default:
                            return null;
                    }
                default :
                    return null;
            }
        }
}
