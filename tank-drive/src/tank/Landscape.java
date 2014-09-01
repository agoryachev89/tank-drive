package tank;

public abstract class Landscape implements Blocked {
	private int x;
	private int y;
	
	public Landscape(int x, int y) {
		this.x = x;
		this.y = y;
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
					if (((this.x + 15) > x) & (this.y < (y + (size*8-1)))) {
						return true;
					} 
					break;
			case 2:
					if ((this.x < (x + (size*8-1))) & (this.y < (y + (size*8-1)))) {
						return true;
					} 
					break;
			case 3:
					if ((this.x < (x + (size*8-1))) & ((this.y + 15) > y)) {
						return true;
					} 
					break;
			case 4:
					if (((this.x + 15) > x) & ((this.y + 15) > y)) {
						return true;
					} 
					break;
					
		}
		return false;
	}
}
