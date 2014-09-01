package tank;

public class Delay {
	private long delayNanos;
	private long passedNanos;
	
	public Delay(long delayMillis){
		this.delayNanos = delayMillis * 1000000;
	}
	
	public boolean updateAndCheck (long deltaMillis) {
		passedNanos += deltaMillis * 1000000;
		
		if (passedNanos > delayNanos) {
			passedNanos = 0;
			return true;
		} else {
			return false;
		}
	}
}
