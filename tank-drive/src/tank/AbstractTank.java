package tank;

public abstract class AbstractTank implements Blocked {
	private int x;
	private int y;
	private Direction direction;
	public boolean canShoot;
	
	public AbstractTank(int x, int y, Direction direction) {
		this.x = x;
		this.y = y;
		this.direction = direction;
		this.canShoot = true;
	}
	
	public int getX() {
		return this.x;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public Direction getDirection() {
		return this.direction;
	}
	
	public void setDirection(Direction direction) {
		this.direction = direction;
	}
	
	public boolean isChangeAxis(Direction direction) {
		boolean directionPresent;
		boolean directionFuture;
		
		directionPresent = ((this.direction == Direction.UP)|(this.direction == Direction.DOWN)) ? true : false;
		directionFuture = ((direction == Direction.UP)|(direction == Direction.DOWN)) ? true : false;
		
		return (directionPresent==directionFuture) ? true : false;
	}
	
	@Override
	public boolean isInTheArea(int x, int y, int size) {
		int quarter=1;

		if (x > this.x) {
			if (y > this.y) {
				quarter = 4;
			} else { 
				quarter = 1;
			}
		} else {
			if (y > this.y) {
				quarter = 3;
			} else { 
				quarter = 2;
			}
		}
		
		switch (quarter) {
			case 1:
					if (((this.x + 31) > x) & (this.y < (y + (size*8-1)))) {
						return true;
					} 
					break;
			case 2:
					if ((this.x < (x + (size*8-1))) & (this.y < (y + (size*8-1)))) {
						return true;
					} 
					break;
			case 3:
					if ((this.x < (x + (size*8-1))) & ((this.y + 31) > y)) {
						return true;
					} 
					break;
			case 4:
					if (((this.x + 31) > x) & ((this.y + 31) > y)) {
						return true;
					} 
					break;
					
		}
		return false;
	}
}
