
public class Node {
		public int[] getState() {
		return state;
	}

	public void setState(int[] state) {
		this.state = state;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

		private int[] state;
		private int value;

		public Node(int[] state, int value) {
			this.value = value;
			this.state = state;

		}
	
}
