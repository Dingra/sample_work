/**
 * 
 * @author Drew Ingram
 * implemented 17/11/2012
 * 
 * When the program is in this state, it suspects that the user thinks it is lying
 * or imagining things
 */
public class LyingState extends State{
	
	String[] toCrazy;
	String[] toDisbelief;
	String[] toBelief;
	String[] toLying;
	
	String[] occur;
	String[] escape;
	String[] description;
	String[] beach;
	String[] monster;
	
	String[] prank;
	
	private final String DEFAULT_PHRASE;
	private final String OPENING_PHRASE;
	private final String TERMINAL_PHRASE;
	public LyingState(){
		OPENING_PHRASE = "I'm not lying!";
		DEFAULT_PHRASE = "What are you talking about?  I don't quite know what you mean.";
		TERMINAL_PHRASE = "Okay, fine!  I wasn't actually at the lake that night.  I was at the strip club, okay?  Jesus Christ, why couldn't you " +
				"\nhave thought that I was crazy like everyone else.  Now go ahead and tell everyone what a dirty pervert I am.  I'm going to go" +
				"\nget a drink.";
		
		String[] cr = {"crazy", "nuts", "shrink", "psycho", "mad", "imagining", "imagined", "imagine"
				, "psychiatrist", "counsellor", "counselling", "counsel", "dream", "dreamed", "dreamt"
				, "nightmare", "losing", "lost"};
		
		//Upon seeing these words in this state, the program will no longer suspect that the user thinks it is crazy
		String[] db = {"drunk", "drink", "drinking", "prove", "proof", "evidence", "footprint", "footprints", "foot", "print", "picture", "photo"
				, "photograph", "anyone", "witness", "witnesses", "witnessed", "full"};
		
		//Key words that got the program into this state
		String[] ly = {"Liar", "fib", "fiction", "tale", "lying", "lie"};
		
		String[] p = {"prank", "pranked", "prankster", "pranksters", "joke", "gag"};
		String[] b = {"believe"};
		
		toCrazy 	= cr;
		toDisbelief = db;
		toBelief 	= b;
		toLying = ly;
		
		
		String[] be = {"beach", "there"};//synonyms and pronouns for "beach"
		String[] occ = {"Happen", "Happened", "occur", "occurred", "attack", "attacked"};//Synonyms, pronouns and words with similar connotations for "happen"
		String[] e = {"escape", "escaped", "that"};//Synonyms and pronouns for "escape"
		String[] d = {"look", "sound", "act", "behave", "move"};//Any sort of description
		String[] mo = {"sahuagin", "monster", "creature", "sahuagins", "creatures, monsters", "things", "beasts"
				,"fishmen", "that", "they", "fish"};
		
		beach = be;
		occur = occ;
		escape = e;
		description = d;
		monster = mo;
		
		prank = p;
		
	}
	
	public String getOpeningPhrase(){
		return OPENING_PHRASE;
	}
	
	public String getDefaultPhrase(){
		return DEFAULT_PHRASE;
	}

	private String checkKeyWords(String s){
		for(int i = 0; i < toCrazy.length; i ++)
			if(removePunctuation(s).compareToIgnoreCase(toCrazy[i]) == 0)
				return "crazy";
				
		for(int i = 0; i < toDisbelief.length; i ++)
			if(removePunctuation(s).compareToIgnoreCase(toDisbelief[i]) == 0)
				return "disbelief";

		for(int i = 0; i < toBelief.length; i ++)
			if(removePunctuation(s).compareToIgnoreCase(toBelief[i])== 0)
				return "belief";
		return "";
}
	
	
	public String generateOutput(String s){
		String ret = "";
		boolean changeState = false;
		
		boolean inquiry = question(s);
		boolean negative = negated(s);
		
		
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
				else if(kw[i].equals("belief") && !(negative) && (!inquiry)){
					keyWordFound = "belief";
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
		if(q)
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
		String story = "I was relaxing at the beach at the end of the dock when a bunch of these fish things showed up and started" +
				"\nto close in on me.  I ran and I barely got away.";
		
		String description = "They looked like humanoid fish things.  They had fins and scales and whatnot all over their" +
				"\n bodies, and they stank like fish.  They were all making that hissing noise";
		
		String whySahuagin = "Look, I don't know if that's really what they were called.  They were ugly fish men and " +
				"\nwhen I looked them up on the internet they were the first things I could find that matched the description";
		
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
			return "I really have no idea why the attacked me";
		else if(inquiry == "why" && topic == "believe")
			return "I don't know...I just wish somebody would realize I'm telling the truth";
		else if(inquiry == "why" && topic == "beach")
			return "I've had a busy week and I wanted to go outside somewhere quiet where nobody else would be";
		else if(inquiry == "where")
			return "At Kal Beach, at the end of the dock";
		else if(inquiry == "when"){
			return "Three days ago, at about nine o'clock at night.  I was there for just a few minutes" +
					"\nbefore they attacked me.";
		}
		else if(inquiry == "how" && topic == "escape")
			return "I ran like hell!  Maybe those things were just really slow out of the water, I don't know.";
		return inputNotQuestion(s);//If this method cannot generate an output, see if the other method can.
	}
	
	private String inputNotQuestion(String s){
		String[] input = s.split(" ");
		
		//If user's input contains the word "prank", must use handlePrank to determine what is
		//meant
		for(int i = 0; i < input.length; i ++){
			for(int j = 0; j < prank.length; j ++)
				if(removePunctuation(input[i]).compareToIgnoreCase(prank[j]) == 0)
					return handlePrank(s);
			
			for(int j = 0; j < toLying.length; j ++)
				if(removePunctuation(input[i]).compareToIgnoreCase(toLying[j]) == 0)
					return "I'm not making this up!  This all really happened.  It was pretty scary.";
		}
		
		return getDefaultPhrase();
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
	
	//If this method is called, it is because the key word 'prank' was used.  The method tries
	//to determine if the user is accusing the program of pulling a prank (or joking etc.) or
	//if the user thinks a prank was pulled on the program
	private String handlePrank(String s){
		String[] survivorPranked = {"somebody", "someone"};
		String[] input = s.split(" ");
		
		for(int i = 0; i < survivorPranked.length; i ++)
			for(int j = 0; j < input.length; j ++)
				if(removePunctuation(input[j]).compareToIgnoreCase(survivorPranked[i]) == 0)
					return "*CHANGE_STATE=disbelief " + s;//The program no longer thinks the user suspects it of lying
		return "I don't go around trying to make people look like idiots by convincing them that sahaugins are real.";
	}
	public String getTerminalPhrase(){
		return TERMINAL_PHRASE;
	}

}
