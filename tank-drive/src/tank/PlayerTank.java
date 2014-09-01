package tank;

public class PlayerTank extends AbstractTank {

	public PlayerTank(int x, int y) {
		super(x, y, Direction.UP);

	}

	@Override
	public boolean destroy() {
		return true;
	}
}
