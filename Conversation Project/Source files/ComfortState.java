/**
 * 
 * @author Drew Ingram
 * Implemented 11/17/2012
 * 
 * When the program is in this state, it believes the user is trying to comfort it
 */
public class ComfortState extends State{
	final String OPENING_PHRASE;
	final String DEFAULT_PHRASE;
	final String TERMINAL_PHRASE;
	
	String[] toCrazy;
	String[] toDisbelief;
	String[] toBelief;
	String[] toHelp;
	
	String[] occur;
	String[] escape;
	String[] description;
	String[] beach;
	String[] monster;
	String[] evidence;
	String[] misc;//Miscellaneous key words
	
	int counter;
	
	public ComfortState(){
		DEFAULT_PHRASE = "I'm not sure what you mean by that.";
		OPENING_PHRASE = "That's nice of you to say.";
		TERMINAL_PHRASE = "Look I have to get going...but thanks for talking to me.  I feel a bit better knowing that there's sombody who believes me." +
				"  Bye!";
		
		String[] cr = {"crazy", "nuts", "shrink", "psycho", "mad", "imagining", "imagined", "imagine"
				, "psychiatrist", "counsellor", "counselling", "counsel", "dream", "dreamed", "dreamt"
				, "nightmare", "losing", "lost"};
		
		//Upon seeing these key words, the program will stop thinking the user is trying to comfort it
		String[] b = {"believe"};
		
		//Upon seeing these key words while in this state, the program will suspect the user is trying to help it.
		String [] h = {"help", "stop", "save", "prevent", "protect", "protection", "find", "resolve"
				, "solve", "figure", "danger", "dangerous", "prove", "proof", "evidence", "footprint", "footprints"
				, "foot", "print", "picture", "photo", "photograph", "anyone", "witness", "witnesses", "witnessed"};
		
		String[] co = {"hurt", "injured", "safe", "scared", "afraid", "terrified", "terrifying", "worried", "traumatized"
				,"Nightmares", "scary"};
		String[] ly = {"Liar", "fib", "fiction", "tale", "lying", "lie"};
		
		//Upon seeing these words in this state, the program will suspect that the user does not believe it
		String[] db = {"full"};
		
		toCrazy 	= cr;
		toDisbelief = db;
		toBelief 	= b;
		toHelp 		= h;
		toComfort 	= co;
		toLying = ly;
		
		String[] be = {"beach"};//synonyms and pronouns for "beach"
		String[] occ = {"Happen", "Happened", "occur", "occurred", "attack", "attacked"};//Synonyms, pronouns and words with similar connotations for "happen"
		String[] e = {"escape", "escaped", "away"};//Synonyms and pronouns for "escape"
		String[] d = {"look", "sound", "act", "behave", "move", "big", "strong", "fast", "ugly", "Scaly"};//Any sort of description
		String[] mo = {"sahuagin", "monster", "creature", "sahuagins", "creatures, monsters", "things", "beasts"
				,"fishmen", "that", "they"};
		String[] m = {"mob", "police", "authorities", "cops", "fight", "back", "army", "military", "capture"};
		
		beach = be;
		occur = occ;
		escape = e;
		description = d;
		monster = mo;
		misc = m;
	}
	
	public String getOpeningPhrase(){
		return OPENING_PHRASE;
	}

	private String checkKeyWords(String s){
		for(int i = 0; i < toCrazy.length; i ++)
			if(removePunctuation(s).compareToIgnoreCase(toCrazy[i]) == 0)
				return "crazy";

				
		for(int i = 0; i < toDisbelief.length; i ++)
			if(removePunctuation(s).compareToIgnoreCase(toDisbelief[i]) == 0)
				return "disbelief";
		
		for(int i = 0; i < toHelp.length; i++)
			if(removePunctuation(s).compareToIgnoreCase(toHelp[i])== 0)
				return "help";

		
		for(int i = 0; i < toBelief.length; i ++)
			if(removePunctuation(s).compareToIgnoreCase(toBelief[i])== 0)
				return "belief";
		
		for(int i = 0; i < toLying.length; i ++)
			if(removePunctuation(s).compareToIgnoreCase(toLying[i]) == 0)
				return "liar";
		return "";
}
	
	
	
	public String generateOutput(String s){
		if(s.charAt(0) == '*')//The state has just changed to this state
			counter = 0;
		
		counter ++;
		
		String ret = "";
		boolean changeState = false;
		boolean inquiry = question(s);
			
		String[] input = s.split(" ");
		String keyWords = "";
		String keyWordFound = "";//The first word found that is a key word
		
		//This gets a string of all key words (most will probably be null)
		if(s.charAt(0)!= '*'){
			for(int i = 0; i < input.length; i++)
				keyWords += checkKeyWords(input[i]) + " ";
		
		//Put the key words into an array to iterate through them more easily
			String[] kw = keyWords.split(" ");
			for(int i = 0; i < kw.length; i++){
				if(false);
				else if(kw[i].equals("crazy")){
					keyWordFound = "crazy";
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
				else if(kw[i].equals("help")){
					keyWordFound = "help";
					changeState = true;
					break;
				}
				else if(kw[i].equals("liar")){
					keyWordFound = "liar";
					changeState = true;
					break;
				}
			}
		}
		
		if(changeState)
			ret += "*CHANGE_STATE=" + keyWordFound + " " + s;
		else return generateStateOutput(s, inquiry);
		
		return ret;
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
		if(q){//If it's a question
			return answerQuestion(s);
		}
		else{
			return inputNotQuestion(s);
		}
	}
	
	/**
	 * 
	 * @param s: The user input, which the program will have determined to be a question
	 * @return The output for this inquiry
	 */
	private String answerQuestion(String s){
		String inquiry;
		String topic;
		
		String[] input = s.split(" ");
		
		inquiry = getInquiry(input);
		topic = getInquiryTopic(input);
		
		//The basic questions ("how did you escape?", "what happened?" etc. are handled in the 'general belief' state)
		if(inquiry == "what" && topic == "occur")
			return "*CHANGE_STATE=belief " + s;
		else if(inquiry == "what" && topic == "description")
			return "*CHANGE_STATE=belief " + s;
		else if(inquiry == "what" && topic == "monster")
			return "*CHANGE_STATE=belief " + s;
		else if(inquiry == "why" && topic == "occur")
			return "*CHANGE_STATE=belief " + s;
		else if(inquiry == "why" && topic == "beach")
			return "*CHANGE_STATE=belief " + s;
		else if(inquiry == "where")
			return "*CHANGE_STATE=belief " + s;
		else if(inquiry == "when")
			return "*CHANGE_STATE=belief " + s;
		else if(inquiry == "how" && topic == "escape")
			return "*CHANGE_STATE=belief " + s;
		return inputNotQuestion(s);
	}
	/**
	 * This method is called if the user input is not a question, or if it is a question, but
	 * the answerQuestion method cannot come up with an output
	 * @param s the user input
	 * @return The program's output
	 */
	private String inputNotQuestion(String s){
		String kw = null;
		String[] input = s.split(" ");
		
		for(int i = 0; i < input.length; i ++)
			for(int j = 0; j < toComfort.length; j ++){
				if(removePunctuation(input[i]).compareToIgnoreCase(toComfort[j]) == 0)
					kw = toComfort[j];		
			}
		if(kw == "scare" || kw == "scary" || kw == "scared" || kw == "afraid" || kw == "terrified" || kw == "traumatized"
			|| kw == "terrifying")
			return "It was a pretty scary experience.  I thought they were going to tear me apart.";
		else if(kw == "hurt" || kw == "injured" || kw == "safe")
			return "It was a pretty close call but I'm fine now.  They didn't touch me.";
		else return getDefaultPhrase();
	}
	
	public String getDefaultPhrase(){
		return DEFAULT_PHRASE;
	}
	
	public String getTerminalPhrase(){
		return TERMINAL_PHRASE;
	}
	
	/**
	 * 
	 * @param input an array containing the characters of a user's input
	 * @return The key word in the input
	 */
	private String getInquiryTopic(String[] input){
		String topic = null;
		boolean terminate = false;
		
		for(int i = 0; i < input.length; i ++){
			for(int j = 0; j < occur.length; j ++){
				if(removePunctuation(input[i]).compareToIgnoreCase(occur[j])==0){
					topic = "occur";
					terminate = true;
					break;
				}
			}
			if(terminate)
				break;
			for(int j = 0; j < beach.length; j++){
				if(removePunctuation(input[i]).compareToIgnoreCase(beach[j])==0){
					topic = "beach";
					terminate = true;
					break;
				}
			}
			if(terminate)
				break;
			for(int j = 0; j < description.length; j ++){
				if(removePunctuation(input[i]).compareToIgnoreCase(description[j]) == 0){
					topic = "description";
					terminate = true;
					break;
				}
			}
			if(terminate)
				break;
			for(int j = 0; j < escape.length; j ++){
				if(removePunctuation(input[i]).compareToIgnoreCase(escape[j]) == 0){
					topic = "escape";
					terminate = true;
					break;
				}
			}
			if(terminate)
				break;
			for(int j = 0; j < monster.length; j ++){
				if(removePunctuation(input[i]).compareToIgnoreCase(monster[j]) == 0){
					topic = "monster";
					terminate = true;
					break;
				}
			}
			if(terminate)
				break;
		}
		
		return topic;
	}
	/**
	 * 
	 * @param input The user's input, with each word placed into an array
	 * @return The inquiry in this user input ("who", "what" etc.)
	 * This will always return an inquiry because this function is not called
	 * unless at least one is known to exist
	 */
	private String getInquiry(String[] input){		
		String inquiryFound = null;
		
		for(int i = 0; i < input.length; i ++){
			
			if(input[i].compareToIgnoreCase("what") == 0
					|| input[i].compareToIgnoreCase("what'd") == 0
					|| input[i].compareToIgnoreCase("whatd") == 0){
				inquiryFound = "what";
				break;
			}
			else if(input[i].compareToIgnoreCase("how") == 0
					|| input[i].compareToIgnoreCase("how'd")== 0
					|| input[i].compareToIgnoreCase("howd") == 0){
				inquiryFound = "how";
				break;
			}
			else if(input[i].compareToIgnoreCase("when") == 0
					|| input[i].compareToIgnoreCase("when'd") == 0
					|| input[i].compareToIgnoreCase("whend") == 0){
				inquiryFound = "when";
				break;
			}
			else if(input[i].compareToIgnoreCase("where") == 0
					|| input[i].compareToIgnoreCase("where'd") == 0
					|| input[i].compareToIgnoreCase("whered") == 0){
				inquiryFound = "where";
				break;
			}
			else if(input[i].compareToIgnoreCase("why") == 0
					|| input[i].compareToIgnoreCase("why'd") == 0
					|| input[i].compareToIgnoreCase("whyd") == 0){
				inquiryFound = "why";
				break;
			}
		}
		
		return inquiryFound;
	}
}
