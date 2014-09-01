package tank;

public class Concrete extends Landscape {

	public Concrete(int x, int y) {
		super(x, y);
	}

	@Override
	public boolean destroy() {
		return false;
	}

}
