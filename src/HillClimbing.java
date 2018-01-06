import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class HillClimbing {
    int counter = 0 ;
	 
	//takes any state calculate the number of attacks could
	//happen on rows,columns or diagonal .
	public int getNumAttacks(int[] board) {
		int attacks = 0;
		for (int i = 0; i < board.length; i++) {
			for (int j = i + 1; j < board.length; j++) {
				if (board[i] == board[j])
					attacks += 1;

				int offset = j - i;
				if (board[i] == board[j] - offset || board[i] == board[j] + offset)
					attacks += 1;

			}
		}
		return attacks;
	}
	/* we get the neighbour each time compare its value to the current value 
	 * and if it is better then we update the current and if not we return 
	 * current and stop the algorithm.
	 * sideways to conquer the sideway problem by allowing the algorithm to 
	 * try to find the max value even if we stuck in equal value for a while
	 */
	public Node hillClimbing(int[] board, int sideways) {
		Node current  = new Node (board , getNumAttacks(board));
		Node neighbour;
		while(current.getValue() != 0) {
			int []state = getNewState(current.getState());
			neighbour = new Node(state, getNumAttacks(state));
			if (neighbour.getValue() > current.getValue()) {
				return current ;
			}else if (neighbour.getValue() == current.getValue()) {
				if(sideways == 0) {
					return current;
				}else {
					sideways -- ;
				}
			}
			
			current = neighbour ;
		}
		return current;
	}
	
	/* starting from k initial state , we get the neighbours of each one state from k then choose the best successor
	 * compare each successor to the corresponding current state and update the current states
	 * depending on the condition, if we reach a state that we finished the number of iterations
	 *  or we found that no current state is updated then we stop
	 */
	public KNode KLocalhillClimbing(ArrayList<int[]> states, ArrayList<Integer> values , int k , int max) {
		
		KNode current  = new KNode (states ,values);
		KNode neighbour;
		ArrayList<int[]> neighbours = new ArrayList<int[]>();
		ArrayList<Integer> attacks = new ArrayList<Integer>();
		int count = 0 ;
		while(max >= 0 ) {
			for (int i = 0; i < k ; i++) {
				int []state = getNewState(states.get(i)); // get new state
				int value = getNumAttacks(state); // calculate its value
				neighbours.add(state);// add this state to the list
				attacks.add(value);// add the value to attacks list
			}
			neighbour = new KNode(neighbours, attacks); //initialize new neighbour with the lists
			for (int i = 0; i < k; i++) {
				if (neighbour.getValue().get(i) >= current.getValue().get(i)) count++  ; //count the state which won't change
				current = neighbour ;
			}
			if(count == k)  return current ; // all the states wasn't changed
			count = 0;
			max -- ;
		}
		return current;
	}
	
	/*get the neighbours of each state then get the best successor of them 
	 * tries to change the row of each queen then get the best row for it 
	 * and then do it again with each queen separately until it find the best 
	 * successor from this state nd return it.
	 */
	public int[] getNewState(int[] board) {
		int[] state ;
		int min  ;
		int attacks ;
		int [] best = board ;
		for (int i = 0; i < 8; i++) {
			state = Arrays.copyOf(best, best.length);
			min = Integer.MAX_VALUE;
			for (int j = 0; j < 8; j++) {
				state[i] = j;
				attacks = getNumAttacks(state);
				if(attacks < min) {
					min = attacks;
					best =  Arrays.copyOf(state, state.length); 
				}

			}
		}
		return best;
	}
	
	// generating a random number to represent the row of each queen in the board
	public int [] getRandomStart () {
		int [] queens = new int[8];
		Random r = new Random();
		for (int i = 0; i < 8; i++) {
			queens[i] = r.nextInt(8);
		}
		return queens;
	}
	 // print state of the board
	public void print (Node resultNode) {
		int attacks = resultNode.getValue();
		int [] result = resultNode.getState();
		for(int i = 0 ; i < 8 ; i++) {
			System.out.print(result[i]);
		}
		System.out.println();
		System.out.println(attacks);
		if (attacks < 2) counter++ ;
	}

	
}
