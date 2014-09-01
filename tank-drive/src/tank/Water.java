package tank;

public class Water extends Landscape {

	public Water(int x, int y) {
		super(x, y);
	}

	@Override
	public boolean destroy() {
		return false;
	}
	
}
