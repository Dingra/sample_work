
public class testDisbelief {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		State s = new State();
		GeneralDisbeliefState test = new GeneralDisbeliefState();
		s = test;

		String check = s.generateOutput("Chicken Turkey Crazy Puppy");
		System.out.println(check);
		check = s.generateOutput("Chicken Turkey liar Puppy");
		System.out.println(check);
		check = s.generateOutput("Chicken Turkey Believe puppy");
		System.out.println(check);
		check = s.generateOutput("Chicken Turkey Puppy");
		System.out.println(check);
		check = s.generateOutput("crAzy");
		System.out.println(check);
	}

}
