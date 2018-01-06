import java.util.ArrayList;

public class Main {
	// testing 
		public static void main(String[] args) {
			HillClimbing localSearch = new HillClimbing();
			Node resultNode ;
			//---------------------------------------------------------------------//
			// default hillclimbing
			//resultNode = hill.hillClimbing(hill.getRandomStart(),0);
			//hill.print(resultNode);
			//---------------------------------------------------------------------//
			// random restarts
			
//			for (int k = 0; k < 100; k++) {
//				int max = 50 ; // the value you choose as the max number of loops
//				ArrayList<Node> results = new ArrayList<Node>();
//				// rum hill climbing number max time 
//				// add the results in results array
//				for(int i = 0 ; i < max ; i ++) {
//					int[] board = hill.getRandomStart();
//					results.add(hill.hillClimbing(board,0));
//				}
//				// get the best from the results
//				Node best = results.get(0);
//				for (int i = 1; i < results.size(); i++) {
//					if(results.get(i).value<best.value) {
//						best = results.get(i); 
//					}
//				}
//				// print the results
//				hill.print(best);
//				
//			}
//			System.out.println(counter);
			//---------------------------------------------------------------------//
			// sideways 
//			for (int i = 0; i < 100; i++) {
//				resultNode = localSearch.hillClimbing(localSearch.getRandomStart(),50);
//				localSearch.print(resultNode);
//			}
//			
//			System.out.println(localSearch.counter);
//			
			//----------------------------------------------------------------------//
			// k Local Beam
			
			// specify k and the max number of iterations
			int k = 50 ;
			int max = 100 ;
			int count = 0 ;
			for (int w = 0; w < 100; w++) {
				ArrayList<int[]> states = new ArrayList<int[]>();
				ArrayList<Integer> values = new ArrayList<Integer>();

				for(int i = 0 ; i < k ; i ++) {
					int[] board = localSearch.getRandomStart(); //start with k nodes
					int value = localSearch.getNumAttacks(board);
					states.add(board);
					values.add(value);
				}
				KNode resNode = localSearch.KLocalhillClimbing(states,values,k,max);
				ArrayList<Integer> attacks = resNode.getValue();
				ArrayList <int[]> result = resNode.getStates();
				for (int i = 0; i < k ; i++) {
					int [] state = result.get(i);
					for(int j = 0 ; j < 8 ; j++) {
						System.out.print(state[j]);
					}
					System.out.print(",");
					System.out.println(attacks.get(i));
					if(attacks.get(i)<4) count ++ ;
				
				}
				System.out.println();
			}
			System.out.println(count+"***********");
			
			
			
			
		}

}
