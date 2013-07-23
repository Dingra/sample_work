
public class Test_OP_Phrase {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		State st = new State();
		
		System.out.println(st.getOpeningPhrase());
		st = new InitialState();
		System.out.println(st.getOpeningPhrase());
		st = new GeneralBeliefState();
		System.out.println(st.getOpeningPhrase());

	}

}
