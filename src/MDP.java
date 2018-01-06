public class MDP {

	public static final int SIZE = 3;
	public static final double GAMMA = 0.9;
	public static final int THRESHOLD = 100;
	// represent directions agent can take
	public enum Direction {
		UP, DOWN, LEFT, RIGHT
	}
	// represents Action the agent can take , its direction and the directions perpendicular to it.
	public static class Action {
		int dr;
		int dc;
		Action first;
		Action second;
		Direction dir;

		public Action(int x, int y, Direction direction) {
			this.dr = x;
			this.dc = y;
			this.dir = direction;
		}
	}
	// holds the value and the direction of a cell
	public static class Pair {
		double value;
		Direction dir;
	}

	public static Pair[][] getValueIteration(int[][] rewards, Action[] actions, int destX, int destY) {

		// initialize the values array by values of zeros
		Pair[][] values = new Pair[SIZE][SIZE];
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				values[i][j] = new Pair();
			}
		}

		// loop until we reach the threshold 
		int itr = 0;
		while (itr++ < THRESHOLD) {
			// curr holds the current values
			Pair[][] curr = new Pair[SIZE][SIZE];
			// for each state in thr grid do:
			for (int i = 0; i < SIZE; i++) {
				for (int j = 0; j < SIZE; j++) {
					// initialize the curr with empty pair
					curr[i][j] = new Pair();
					// if it isn't the exit cell
					if (!(i == destX && j == destY)) {
						// try all the actions
						for (Action action : actions) {
							// calculating the V[k] given an action
							double target = 0;
							// taking the intended action with prop of 0.8
							if (i + action.dr >= 0 && i + action.dr < SIZE && j + action.dc >= 0
									&& j + action.dc < SIZE) {
								target = 0.8 * (rewards[i + action.dr][j + action.dc]
										+ GAMMA * values[i + action.dr][j + action.dc].value);
								// taking one of the perpendicular actions with prob of 0.1
								if (i + action.first.dr >= 0 && i + action.first.dr < SIZE && j + action.first.dc >= 0
										&& j + action.first.dc < SIZE)
									target += 0.1 * (rewards[i + action.first.dr][j + action.first.dc]
											+ GAMMA * values[i + action.first.dr][j + action.first.dc].value);
								// taking one of the perpendicular actions with prob of 0.1
								if (i + action.second.dr >= 0 && i + action.second.dr < SIZE
										&& j + action.second.dc >= 0 && j + action.second.dc < SIZE)
									target += 0.1 * (rewards[i + action.second.dr][j + action.second.dc]
											+ GAMMA * values[i + action.second.dr][j + action.second.dc].value);
							}
							// take the action which results in max value
							if (target >= curr[i][j].value) {
								curr[i][j].value = target;
								curr[i][j].dir = action.dir;
							}
						}
					} else {
						// exit value
						curr[destX][destY].value = 10;
					}

				}

			}
			values = curr;
		}

		return values;
	}

	public static Action[][] getPolicyIteration(int[][] rewards, Action[] actions, Action[][] randomPolicy, int destX,
			int destY) {

		Pair[][] values;
		// initialize with random policy
		Action[][] policy = randomPolicy;
		int itr = 0;

		while (itr++ < THRESHOLD) {

			// evaluate the values by given policy "Policy Evaluation step"
			values = getPolicyEvalutation(rewards, actions, policy, destX, destY);
			// "Policy improvement" using one iteration of value iteration
			// curr holds the current values
			Pair[][] curr = new Pair[SIZE][SIZE];
			// for each state in thr grid do:
			for (int i = 0; i < SIZE; i++) {
				for (int j = 0; j < SIZE; j++) {
					// initialize the curr with empty pair
					curr[i][j] = new Pair();
					// if it isn't the exit cell
					if (!(i == destX && j == destY)) {
						// try all the actions
						for (Action action : actions) {
							double target = 0;
							// taking the intended action with prop of 0.8
							if (i + action.dr >= 0 && i + action.dr < SIZE && j + action.dc >= 0
									&& j + action.dc < SIZE) {
								target = 0.8 * (rewards[i + action.dr][j + action.dc]
										+ GAMMA * values[i + action.dr][j + action.dc].value);
								// taking one of the perpendicular actions with prob of 0.1
								if (i + action.first.dr >= 0 && i + action.first.dr < SIZE && j + action.first.dc >= 0
										&& j + action.first.dc < SIZE)
									target += 0.1 * (rewards[i + action.first.dr][j + action.first.dc]
											+ GAMMA * values[i + action.first.dr][j + action.first.dc].value);
								// taking one of the perpendicular actions with prob of 0.1
								if (i + action.second.dr >= 0 && i + action.second.dr < SIZE
										&& j + action.second.dc >= 0 && j + action.second.dc < SIZE)
									target += 0.1 * (rewards[i + action.second.dr][j + action.second.dc]
											+ GAMMA * values[i + action.second.dr][j + action.second.dc].value);
							}
							// take the action which results in max value
							if (target >= curr[i][j].value) {
								curr[i][j].value = target;
								curr[i][j].dir = action.dir;
								policy[i][j] = action;
							}
						}
					} else {
						// exit value
						curr[destX][destY].value = 10;
					}
				}
			}

		}

		return policy;

	}

	public static Pair[][] getPolicyEvalutation(int[][] rewards, Action[] actions, Action[][] randomPolicy, int destX,
			int destY) {

		Action action;
		Pair[][] values = new Pair[SIZE][SIZE];
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				values[i][j] = new Pair();
			}
		}

		int itr = 0;
		while (itr++ < THRESHOLD) {
			Pair[][] curr = new Pair[SIZE][SIZE];
			for (int i = 0; i < SIZE; i++) {
				for (int j = 0; j < SIZE; j++) {
					curr[i][j] = new Pair();
					if (!(i == destX && j == destY)) {
						// get the action from the given policy
						action = randomPolicy[i][j];
						double target = 0;
						// calculate V[s] depending on the taken action
						if (i + action.dr >= 0 && i + action.dr < SIZE && j + action.dc >= 0 && j + action.dc < SIZE) {
							target = 0.8 * (rewards[i + action.dr][j + action.dc]
									+ GAMMA * values[i + action.dr][j + action.dc].value);
							if (i + action.first.dr >= 0 && i + action.first.dr < SIZE && j + action.first.dc >= 0
									&& j + action.first.dc < SIZE)
								target += 0.1 * (rewards[i + action.first.dr][j + action.first.dc]
										+ GAMMA * values[i + action.first.dr][j + action.first.dc].value);
							if (i + action.second.dr >= 0 && i + action.second.dr < SIZE && j + action.second.dc >= 0
									&& j + action.second.dc < SIZE)
								target += 0.1 * (rewards[i + action.second.dr][j + action.second.dc]
										+ GAMMA * values[i + action.second.dr][j + action.second.dc].value);
						}
						curr[i][j].value = target;
						curr[i][j].dir = action.dir;
					} else {
						curr[destX][destY].value = 10;
					}
				}
			}
			values = curr;
		}
		return values;
	}

	public static void main(String[] args) {

		// initialize the actions
		Action up = new Action(-1, 0, Direction.UP);
		Action down = new Action(1, 0, Direction.DOWN);
		Action left = new Action(0, -1, Direction.LEFT);
		Action right = new Action(0, 1, Direction.RIGHT);
		up.first = left;
		up.second = right;
		down.first = left;
		down.second = right;
		left.first = up;
		left.second = down;
		right.first = up;
		right.second = down;
		Action[] actions = new Action[] { up, down, left, right };
		int[] rvalues = new int[] { 3, 100, 0, -3 };
		int[][] rewards = { { 3, -1, 10 }, { -1, -1, -1 }, { -1, -1, -1 } };
		Action[][] randomPolicy = { { down, left, up }, { right, down, up }, { right, up, up } };

		// for all r values
		for (int r : rvalues) {

			rewards[0][0] = r;

			Pair[][] pairs = getValueIteration(rewards, actions, 0, 2);
			Action[][] policyIteration = getPolicyIteration(rewards, actions, randomPolicy, 0, 2);
			
			System.out.println("For r = " + r + " --> ");
			System.out.println("Evaluation of Value Iteration");
			for (int i = 0; i < SIZE; i++) {
				for (int j = 0; j < SIZE; j++) {
					System.out.print("(" + pairs[i][j].value + " " + pairs[i][j].dir + ") ");
				}
				System.out.println();
			}
			System.out.println();
			////////////////////////////////////////////////////////////////////////////
			System.out.println("Evaluation of Policy Evaluation");
			for (int i = 0; i < SIZE; i++) {
				for (int j = 0; j < SIZE; j++) {
					if( i == 0 && j == 2 ) {
						System.out.print("(" + "null" + ") ");
					}else {
						System.out.print("(" + policyIteration[i][j].dir + ") ");
					}
				
				}
				System.out.println();
			}
			System.out.println();
		}
	}
}
