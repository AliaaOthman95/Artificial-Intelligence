import java.util.ArrayList;

public class KNode {
	private ArrayList<int[]> states;
	private ArrayList<Integer>  value;

	public KNode( ArrayList<int[]>  state, ArrayList<Integer>  value) {
		this.value = value;
		this.states = state;

	}

	public ArrayList<int[]> getStates() {
		return states;
	}

	public void setStates(ArrayList<int[]> states) {
		this.states = states;
	}

	public ArrayList<Integer> getValue() {
		return value;
	}

	public void setValue(ArrayList<Integer> value) {
		this.value = value;
	}
}
