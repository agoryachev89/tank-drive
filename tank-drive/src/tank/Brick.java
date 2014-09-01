package tank;

public class Brick extends Landscape {

	public Brick(int x, int y) {
		super(x, y);
	}

	@Override
	public boolean destroy() {
		return true;
	}

}
