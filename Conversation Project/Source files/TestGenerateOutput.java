
public class TestGenerateOutput {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		State state = new State();
		State beliefState = new GeneralBeliefState();
		State disbeliefState = new GeneralDisbeliefState();
		State helpState = new HelpState();
		State comfortState = new ComfortState();
		State crazyState = new CrazyState();
		State lyingState = new LyingState();
		
		String test;
		
		//Test General Belief State
		
		System.out.print("\n\n");
		System.out.println("Belief State: ");
		
		state = beliefState;
		
		test = state.generateOutput("Crazy puppy doggie");
		System.out.println(test);
		
		test = state.generateOutput("help twinkie");
		System.out.println(test);
		
		test = state.generateOutput("Elephant puppy don't moose");
		System.out.println(test);
		
		test = state.generateOutput("hurt");
		System.out.println(test);
		
		test = state.generateOutput("liar");
		System.out.println(test);
		
		test = state.generateOutput("Zergling");
		System.out.println(test);
		
		//Test General Disbelief state
		
		System.out.print("\n\n");
		System.out.println("Disbelief State");
		state = disbeliefState;
		
		test = state.generateOutput("crazy");
		System.out.println(test);
		
		test = state.generateOutput("liar");
		System.out.println(test);
		
		test = state.generateOutput("believe");
		System.out.println(test);
		
		test = state.generateOutput("turkey");
		System.out.println(test);
		
		
		//Test Help State
		System.out.print("\n\n");
		System.out.println("Help State: ");
		state = helpState;
		
		test = state.generateOutput("Crazy");
		System.out.println(test);
		
		test = state.generateOutput("Hurt");
		System.out.println(test);
		
		test = state.generateOutput("Believe");
		System.out.println(test);
		
		test = state.generateOutput("don't");
		System.out.println(test);
		
		test = state.generateOutput("Chicken");
		System.out.println(test);
		
		
		
		//Test Comfort State
		System.out.print("\n\n");
		System.out.println("Comfort State");
		state = comfortState;
		
		test = state.generateOutput("crazy");
		System.out.println(test);
		
		test = state.generateOutput("don't");
		System.out.println(test);
		
		test = state.generateOutput("believe");
		System.out.println(test);
		
		test = state.generateOutput("hurt");
		System.out.println(test);
		
		test = state.generateOutput("Worgen");
		System.out.println(test);
		
		
		//Test Crazy State		
		System.out.print("\n\n");
		System.out.println("Crazy State");
		state = crazyState;
		
		test = state.generateOutput("believe");
		System.out.println(test);
		
		test = state.generateOutput("don't");
		System.out.println(test);
		
		test = state.generateOutput("liar");
		System.out.println(test);
		
		test = state.generateOutput("Crush");
		System.out.println(test);
		
		//Test Lying state

		System.out.print("\n\n");
		System.out.println("Lying State");
		state = lyingState;
		
		test = state.generateOutput("crazy");
		System.out.println(test);
		
		test = state.generateOutput("don't");
		System.out.println(test);
		
		test = state.generateOutput("believe");
		System.out.println(test);
		
		test = state.generateOutput("course");
		System.out.println(test);
	}

}
