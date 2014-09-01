package tank;

public class Forest extends Landscape {

	public Forest(int x, int y) {
		super(x, y);
	}

	@Override
	public boolean destroy() {
		return false;
	}
	
	@Override
	public boolean isInTheArea(int x, int y, int size) {
		return false;
	}
	
}
