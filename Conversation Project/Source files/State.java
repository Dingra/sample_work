
public class State {
	String OPENING_PHRASE;
	String DEFAULT_PHRASE; 
	String TERMINAL_PHRASE;
	
	int counter;//The number of turns Survivor has been in this state
	
	char punctuation[];
	
	String[] toDisbelief;
	String[] toHelp;
	String[] toComfort;
	String[] toCrazy;
	String[] toLying;
	String[] toBelief;
	
	String[] negative;
	String[] inquiries;
	
	
	public State(){
		counter = 0;
		char[] p = {',', '?', '!', '.', '@', '#', '$', '%'
				, '^', '&', '*', '(', ')', '-', '_', '+', '='
				, '\\', ':', ';', '<', '>', '[', ']', '{', '}'};
		punctuation = p;
		
		//These key words negate certain other key words
		String[] n = {"Don't", "can't", "won't", "not", "never", "aren't", "no"};
		
		//These words indicate that the user is asking the program a question
		String[] in = {"What", "Where", "When", "How", "Why"};
		
		negative = n;
		inquiries = in;

	}
	String getOpeningPhrase(){

		return OPENING_PHRASE;
	}

	private String checkKeyWords(String s){
		for(int i = 0; i < toCrazy.length; i ++)
			if(s.compareToIgnoreCase(toCrazy[i]) == 0)
				return "crazy";
		
		for(int i = 0; i < toLying.length; i ++)
			if(s.compareToIgnoreCase(toLying[i]) == 0)
				return "liar";
				
		for(int i = 0; i < toDisbelief.length; i ++)
			if(s.compareToIgnoreCase(toDisbelief[i]) == 0)
				return "disbelief";
		
		for(int i = 0; i < toHelp.length; i++)
			if(s.compareToIgnoreCase(toHelp[i])==0)
				return "help";
		
		for(int i = 0; i < toComfort.length; i ++)
			if(s.compareToIgnoreCase(toComfort[i])==0)
				return "comfort";
		
		for(int i = 0; i < toBelief.length; i ++)
			if(s.compareToIgnoreCase(toBelief[i])== 0)
				return "belief";
		return "";
}
	
	
	
	public String generateOutput(String s){
		String ret = "";
		boolean changeState = false;
		
		String[] input = s.split(" ");
		String keyWords = "";
		String keyWordFound = "";//The first word found that is a key word
		
		//This gets a string of all key words (most will probably be null)
		if(s.charAt(0)!= '*'){
			for(int i = 0; i < input.length; i++)
				keyWords += checkKeyWords(input[i]);
			System.err.println("Key words: " + keyWords);
		
		//Put the key words into an array to iterate through them more easily
			String[] kw = keyWords.split(" ");
			for(int i = 0; i < kw.length; i++){
				if(false);
				else if(kw[i].equals("crazy")){
					keyWordFound = "crazy";
					changeState = true;
					break;
				}
				else if(kw[i].equals("liar")){
					keyWordFound = "liar";
					changeState = true;
					break;
				}
				else if(kw[i].equals("disbelief")){
					keyWordFound = "disbelief";
					changeState = true;
					break;
				}
				else if(kw[i].equals("belief")){
					keyWordFound = "belief";
					changeState = true;
					break;
				}
				else if(kw[i].equals("comfort")){
					keyWordFound = "comfort";
					changeState = true;
					break;
				}
				else if(kw[i].equals("help")){
					keyWordFound = "help";
					changeState = true;
					break;
				}
			}
		}
		
		if(changeState)
			ret += "*CHANGE_STATE=" + keyWordFound + " " + s;
		else return getOpeningPhrase();
		
		return ret;
	}
	
	/**
	 * This method is necessary because the program needs to know that "hurt" and "hurt?" are the same word
	 * @param s The input string
	 * @return The input string without the punctuation at the end
	 */
	protected String removePunctuation(String s){
		for(int i  = 0; i < s.length(); i ++)
			for(int j = 0; j < punctuation.length; j ++)
				if(s.charAt(i) == punctuation[j])
					return s.substring(0,i);
		return s;
	}
	
	/**
	 * After the user input has been checked for key words to change the current state,
	 * if no state change is necessary, the input is set here to be processed
	 * 
	 * @param s: The user's input
	 * @param q: Whether or not the user's input is a question
	 * @return An output, based on the current input
	 */
	private String generateStateOutput(String s, boolean q){
		String ret = "";
		
		return ret;
	}
	
	/**
	 * 
	 * @param s: The user input, which the program will have determined to be a question
	 * @return The output for this inquiry
	 */
	private String answerQuestion(String s){
		String ret = "";
		
		return ret;
	}
	
	/**
	 * 
	 * @param s The user input
	 * @return True if the parameter s contains a negation key word, false otherwise
	 */
	protected boolean negated(String s){
		String[] input = s.split(" ");
		
		for(int i = 0; i < input.length; i ++)//Check all words in the input
			for(int j = 0; j < inquiries.length; j ++)//Check all negative words
				if(input[i].compareToIgnoreCase(negative[j]) == 0)//Compare them
					return true;//If they are the same, the method has finished
		
		return false;
	}

	/**
	 * 
	 * @param s The user input
	 * @return True if the paramater s is a question, and false otherwise
	 */
	protected boolean question(String s){
		String[] input = s.split(" ");
		
		for(int i = 0; i < input.length; i ++){//Check all words in the input
			for(int j = 0; j < inquiries.length; j ++)//Check all words that suggest an inquiry
				if(input[i].compareToIgnoreCase(inquiries[j]) == 0)//Compare them
					return true;//If they are the same, the method has finished
		}
			
		return false;//This code is only reached if both for loops finish without finding an inquiry
	}
	
	//If the input is not a question, this method will be called to generate the output
	private String inputNotQuestion(String s){
		return null;
	}
	
	private String getInquiry(){
		return null;
	}
	
	private String getInquiryTopic(){
		return null;
	}
	
	public String getDefaultPhrase(){
		return DEFAULT_PHRASE;
	}
	
	public String getTerminalPhrase(){
		return TERMINAL_PHRASE;
	}
}
