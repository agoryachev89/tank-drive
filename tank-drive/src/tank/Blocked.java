package tank;

public interface Blocked {
	public boolean destroy();
	public boolean isInTheArea (int x, int y, int size);
}
