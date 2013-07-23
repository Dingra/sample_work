
public class GeneralBeliefState extends State {
	
	String[] toDisbelief;
	String[] toHelp;
	String[] toComfort;
	String[] toCrazy;
	String[] toLying;
	String[] negative;
	String[] inquiries;
	
	String[] occur;
	String[] escape;
	String[] description;
	String[] beach;
	String[] monster;
	
	int counter;
	boolean negated;
	
	private final String OPENING_PHRASE;
	private final String DEFAULT_PHRASE;
	private final String TERMINAL_PHRASE;
	
	public GeneralBeliefState(){
		DEFAULT_PHRASE = "Sorry, I'm a bit distracted.  Do you think you could rephrase that?";
		OPENING_PHRASE = "So you believe me then.  That's a relief.";
		TERMINAL_PHRASE = "Thanks for hearing me out.  I'd better get going.";
		
		//Upon seeing these key words in this state, the program will suspect that the user thinks it is crazy.
		String[] cr = {"crazy", "nuts", "shrink", "psycho", "mad", "imagining", "imagined", "imagine"
				, "psychiatrist", "counsellor", "counselling", "counsel", "dream", "dreamed", "dreamt"
				, "nightmare", "losing", "lost"};
		
		//Upon seeing these key words while in this state, the program will suspect the user is trying to help it.
		String [] h = {"help", "stop", "save", "prevent", "protect", "protection", "find", "resolve"
				, "solve", "figure", "danger", "dangerous", "mob", "capture", "prove", "proof", "evidence", "footprint", "foot", "print"
				, "picture", "photo", "photograph", "anyone", "witness", "witnesses", "witnessed"};
		
		//Upon seeing these key words in this state, the program will suspect that user is trying to cheer it up.
		String[] co = {"hurt", "injured", "safe", "scared", "afraid", "terrified", "worried", "traumatized"
				,"Nightmares", "terrifying", "scary", "scare"};
		
		//Upon seeing these key words while in this state, the program will suspect the user thinks it is lying
		String[] ly = {"Liar", "fib", "fiction", "tale", "lying", "lie"};
		
		//These key words negate certain other key words
		String[] n = {"Don't", "can't", "won't", "not", "never", "aren't", "no"};
		
		//These words indicate that the user is asking the program a question
		String[] in = {"What", "Where", "When", "How", "Why", "What's", "Whats", "Where'd", "whered"
				, "what'd", "whatd", "when'd", "when's", "how'd", "howd", "why'd", "whyd"};
		
		//Upon seeing these words in this state, the program will suspect that the user does not believe it
		String[] db = {"full", "dream", "dreamed", "dreamt", "nightmare"};
		
		toDisbelief = db;
		toHelp = h;
		toComfort = co;
		toCrazy = cr;
		toLying = ly;
		negative = n;
		inquiries = in;
		
		String[] be = {"beach", "there"};//synonyms and pronouns for "beach"
		String[] occ = {"Happen", "Happened", "occur", "occurred", "attack", "attacked"};//Synonyms, pronouns and words with similar connotations for "happen"
		String[] e = {"escape", "escaped", "away"};//Synonyms and pronouns for "escape"
		String[] d = {"look", "sound", "act", "behave", "move"};//Any sort of description
		String[] mo = {"sahuagin", "monster", "creature", "sahuagins", "creatures, monsters", "things", "beasts"
				,"fishmen", "that"};
		
		beach = be;
		occur = occ;
		escape = e;
		description = d;
		monster = mo;
	}
	
	public String getOpeningPhrase(){
		return OPENING_PHRASE;
	}

	
	public String generateOutput(String s){
		String ret = "";
		boolean changeState = false;
		
		if(s.charAt(0) == '*')//The state has just changed to this state
			counter = 0;
		
		counter ++;
		
		negated = negated(s);
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
				if(false);//This makes copying and pasting of the various "else-if" cases easier

				//If there is a negation word along with "crazy", the user might be saying "You are not crazy"
				//or something similar
				else if(kw[i].equals("crazy") && !negated){
					keyWordFound = "crazy";
					changeState = true;
					break;
				}
				else if(kw[i].equals("liar") && !negated){
					keyWordFound = "liar";
					changeState = true;
					break;
				}
				else if(kw[i].equals("disbelief")){
					keyWordFound = "disbelief";
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
		if(q)//If the input is a question
			return answerQuestion(s);
		else
			return inputNotQuestion(s);
	}
	
	/**
	 * 
	 * @param s: The user input, which the program will have determined to be a question
	 * @return The output for this inquiry
	 */
	private String answerQuestion(String s){
		String inquiry;
		String topic;
		
		
		//Edwin's tale about how he allegedly escaped from the sahuagins
		String story = "I was laying down at Kalamalka beach at the end of the dock three" +
				" days ago.  Suddenly I heard an incessant hissing noise.  I sat up and saw" +
				" some weird fish-man thing climbing up the ladder to the dock.  Then there" +
				" was a splash and a thud, and another one landed a couple feet away from  me" +
				" and stated making the ame noise.  I heard a splash and jolted forward, and anther" +
				" one landed right where I was standing.  They looked like they were gonna kill me, so" +
				" I ran.  There was another one at the side of the dock but I ran past that one pretty easily." +
				" I was almost at the end of the dock when another two jumped out of the water and in my way." +
				" I jumped into the water and ran to shore, then accross the beach, to the parking lot, then" +
				" I got into my car.  I looked back as I was driving away.  There had to be two dozen of them, all" +
				" making that shusing noise";
		
		String description = "Well they looked like fish men.  They were about four and a half" +
				" feet tall and crouched down.  They were also covered in green scales and had" +
				" sharp teeth and claws.  Their feet and hands were webbed, and they hald fins down" +
				" their backs and on the sides of their heads.  The kept making that annoying shushing" +
				" noise";
		
		String whySahuagin = "Well I'm not sure if that's really what they were.  After" +
				" I woke up the next day I did some research online and I found something" +
				" called a sahuagin that sort of looked like the things that chased me.";
		
		String[] input = s.split(" ");
		
		inquiry = getInquiry(input);
		topic = getInquiryTopic(input);
		
		if(inquiry == "what" && topic == "occur")
			return story;
		else if(inquiry == "what" && topic == "description")
			return description;
		else if(inquiry == "what" && topic == "monster")
			return whySahuagin;
		else if(inquiry == "why" && topic == "occur")
			return "I guess they were hungry, or I was on their territory or something";
		else if(inquiry == "why" && topic == "beach")
			return "I was just relaxing.  It's been a busy week.";
		else if(inquiry == "where")
			return "At Kal Beach, at the end of the dock";
		else if(inquiry == "when"){
			return "Three days ago, at about nine o'clock at night.  I was there for just a few minutes" +
					" before they attacked me.";
		}
		else if(inquiry == "how" && topic == "escape")
			return "Basically I just ran.  They were pretty clumsy out of water.";
		return DEFAULT_PHRASE;
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
	 * @param The word being checked
	 * This method checks the word to all key words and returns the
	 * category of the one it is in
	 * @return the category of of key words the input s is in, either
	 * 'crazy' 'lying' 'belief' or null
	 */
	private String checkKeyWords(String s){
			for(int i = 0; i < toCrazy.length; i ++)
				if(removePunctuation(s).compareToIgnoreCase(toCrazy[i]) == 0)
					return "crazy";
			
			for(int i = 0; i < toLying.length; i ++)
				if(removePunctuation(s).compareToIgnoreCase(toLying[i]) == 0)
					return "liar";
					
			for(int i = 0; i < toDisbelief.length; i ++)
				if(removePunctuation(s).compareToIgnoreCase(toDisbelief[i]) == 0)
					return "disbelief";
			
			for(int i = 0; i < toHelp.length; i++)
				if(removePunctuation(s).compareToIgnoreCase(toHelp[i])==0)
					return "help";
			
			for(int i = 0; i < toComfort.length; i ++)
				if(removePunctuation(s).compareToIgnoreCase(toComfort[i])==0)
					return "comfort";
			return "";
	}
	
	//This method will be called if the input is not a question
	//Will finish implementing it later
	private String inputNotQuestion(String s){
		if(!negated)
			return getDefaultPhrase();
		else
			return "*CHANGE_STATE=disbelief " + s;
	}
	
	public String getDefaultPhrase(){
		return DEFAULT_PHRASE;
	}
	
	public String getTerminalPhrase(){
		return TERMINAL_PHRASE;
	}

}
