import java.util.Scanner;
public class Survivor {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int k = 0;
		Scanner scan = new Scanner(System.in);
		
		State state = new InitialState();
		State beliefState = new GeneralBeliefState();
		State disbeliefState = new GeneralDisbeliefState();
		State helpState = new HelpState();
		State comfortState = new ComfortState();
		State crazyState = new CrazyState();
		State lyingState = new LyingState();
		
		System.out.println("Edwin: " + state.getOpeningPhrase());
		
		String input;
		while(k < 15){
			boolean changeState= false;
			input = scan.nextLine();

			String output = state.generateOutput(input);

			if(output.charAt(0) == '*'){//I know I must change states
				changeState = true;
				
				//I must change my state to NEW_STATE in "*CHNAGE_STATE=<NEW_STATE>"
				//Here I find what NEW_STATE is
				String check = "";
				int count = 14;
				while(output.charAt(count) != ' ')
					count ++;
				for(int i = 14; i < count; i ++){
					check += output.charAt(i);
				}
			//	System.err.println("Check = " + check);
				
				
				
				//Find which state to change to based on NEW_STATE found
				if(check.equals("crazy")){
					state = crazyState;
				}
				else if(check.equals("help")){
					state = helpState;
				}
				else if(check.equals("disbelief")){
					state = disbeliefState;
				}
				else if(check.equals("belief")){
					state = beliefState;
				}
				else if(check.equals("liar")){
					state = lyingState;
				}
				else if(check.equals("comfort")){
					state = comfortState;
				}
				else
					System.err.println("\nSomething has gone wrong with the state transitions");
			}
			
			if(changeState)
				output = state.generateOutput("*" + output);//Pass the new output to the new state
			if(k < 14)
				System.out.println("Edwin: " + output);
			k ++;
		}
		
		System.out.println("Edwin: " + state.getTerminalPhrase());
	}
	
}
