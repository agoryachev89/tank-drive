package tank;

public class Bullet implements Blocked {
	private int x;
	private int y;
	private Direction direction;
	private AbstractTank parentTank;
	private Delay speed;
	private Delay bangTime;
	public boolean isLive;
	public boolean die;
	
	public Bullet(int x, int y, Direction direction, AbstractTank parentTank) {
		this.x = x;
		this.y = y;
		this.direction = direction;
		this.parentTank = parentTank;
		this.speed = new Delay(1);
		this.bangTime = new Delay(100);
		parentTank.canShoot = false;
		this.isLive = true;
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
	
	public AbstractTank getParentTank() {
		return this.parentTank;
	}
	
	public void move(long delta) {
		if (speed.updateAndCheck(delta)){
			switch (this.direction) {
			case UP:
				this.setY(this.getY()-1);
				break;
			case DOWN:
				this.setY(this.getY()+1);
				break;
			case LEFT:
				this.setX(this.getX()-1);
				break;
			case RIGHT:
				this.setX(this.getX()+1);
				break;
			}
		}
		
	}
	
	public void bang(long delta) {
		this.isLive = false;
		if (bangTime.updateAndCheck(delta)){
			this.die = true;
		}
	}

	@Override
	public boolean destroy() {
		parentTank.canShoot = true;
		return true;
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
					if (((this.x + 7) > x) & (this.y < (y + (size*8-1)))) {
						return true;
					} 
					break;
			case 2:
					if ((this.x < (x + (size*8-1))) & (this.y < (y + (size*8-1)))) {
						return true;
					} 
					break;
			case 3:
					if ((this.x < (x + (size*8-1))) & ((this.y + 7) > y)) {
						return true;
					} 
					break;
			case 4:
					if (((this.x + 7) > x) & ((this.y + 7) > y)) {
						return true;
					} 
					break;
					
		}
		return false;
	}
	

}
