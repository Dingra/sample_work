
public class GeneralDisbeliefState extends State {
	private final String OPENING_PHRASE;
	private final String DEFAULT_PHRASE;
	private final String TERMINAL_PHRASE;
	
	String[] toCrazy;
	String[] toLying;
	String[] toBelief;
	
	String[] occur;
	String[] escape;
	String[] description;
	String[] beach;
	String[] monster;
	String[] dream;//Words that may suggest that Survivor only dreamed the incident
	String[] evidence;
	String[] drugs;
	String[] misc;
	
	int counter;
	
	public GeneralDisbeliefState(){
		OPENING_PHRASE = "I guess you don't believe me then.";
		DEFAULT_PHRASE = "Sounds like you don't believe me.  Wonderful.";
		TERMINAL_PHRASE = "Well it sure is nice to know that somebody believes me.  Thanks for nothing.";
		
		//Upon seeing these key words while in this state, the program will suspect the user thinks it is lying
		String[] ly = {"Liar", "fib", "fiction", "tale", "lying", "lie", "lies", "tales", "fibs", "prank"
				, "pranked", "prankster", "pranksters", "joke", "gag"};
		
		String[] cr = {"crazy", "nuts", "shrink", "psycho", "mad", "imagining", "imagined", "imagine"
				, "psychiatrist", "counsellor", "counselling", "counsel", "losing", "lost"};
		
		//Upon seeing these key words, the program will stop thinking the user is trying to help it
		String[] b = {"believe"};
		
		toCrazy = cr;
		toLying = ly;
		toBelief = b;
		
		String[] dr = {"dream", "dreamed", "dreamt", "nightmare", "dreaming"};
		String[] be = {"beach", "there"};//synonyms and pronouns for "beach"
		String[] occ = {"Happen", "Happened", "occur", "occurred", "attack", "attacked"};//Synonyms, pronouns and words with similar connotations for "happen"
		String[] e = {"escape", "escaped", "that"};//Synonyms and pronouns for "escape"
		String[] d = {"look", "sound", "act", "behave", "move"};//Any sort of description
		String[] mo = {"sahuagin", "monster", "creature", "sahuagins", "creatures, monsters", "things", "beasts"
				,"fishmen"};
		String[] ev = {"prove", "proof", "evidence", "footprint", "footprints", "foot", "print", "picture", "photo", "photograph", "anyone"
		, "witness", "witnesses", "witnessed"};
		String[] m = {"drink", "drinks", "drinking", "drunk", "intoxicated", "full"};
		String[] dru = {"drugs", "crack", "cocaine", "high", "stoned", "pot", "weed", "marijuana", "haroine"};
		
		beach = be;
		occur = occ;
		escape = e;
		description = d;
		monster = mo;
		evidence = ev;
		drugs = dru;
		misc = m;
		dream = dr;
	}
	
	public String getOpeningPhrase(){
		return OPENING_PHRASE;
	}
	
	public String generateOutput(String s){
		if(s.charAt(0) == '*')//The state has just changed to this state
			counter = 0;
		
		counter ++;
		
		String ret = "";
		boolean changeState = false;
		
		boolean negative = negated(s);
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
					if(!negative)
						keyWordFound = "crazy";
					else
						keyWordFound = "belief";//"You are not crazy" for example
					changeState = true;
					break;
				}
				else if(kw[i].equals("liar")){
					if(!negative)
						keyWordFound = "liar";
					else
						keyWordFound = "belief";
					changeState = true;
					break;
				}
				else if(kw[i].equals("belief")){
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
		if(q)//If the input is a question
			return answerQuestion(s);
		else return inputNotQuestion(s);
	}
	
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
		
		String description = "They looked like fish men.  They were ugly, green scaly and had fins everywhere." +
				" They were an making that incessant hissing noise";
		
		String whySahuagin = "Look, I don't know if that's what they were really called.  I looked the description up" +
				" online and this is what I found.";
		
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
			return "I ran like hell!  I think they tried to catch me, but...I don't know, maybe they just" +
					" aren't very fast on land and it was like, ten degrees below zero so that might have slowed" +
					" them down";
		return inputNotQuestion(s);//If this method cannot find an answer to the question, pass it to the other method to see if it can
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
					
			for(int i = 0; i < toBelief.length; i ++)
				if(removePunctuation(s).compareToIgnoreCase(toBelief[i]) == 0)
					return "belief";
			return "";
	}
	
	public String inputNotQuestion(String s){
		String[] input = s.split(" ");
		String kw = null;
		for(int i = 0; i < input.length; i ++){
			for(int j = 0; j < evidence.length; j ++){
				if(removePunctuation(input[i]).compareToIgnoreCase(evidence[j]) == 0){
					kw = evidence[j];
					break;
				}
			}
			for(int j = 0; j < misc.length; j ++){
				if(removePunctuation(input[i]).compareToIgnoreCase(misc[j]) == 0){
					kw = misc[j];
					break;
				}
			}
			for(int j = 0; j < dream.length; j ++){
				if(removePunctuation(input[i]).compareToIgnoreCase(dream[j]) == 0){
					kw = "dream";
					break;
				}
			}
			for(int j = 0; j < drugs.length; j ++){
				if(removePunctuation(input[i]).compareToIgnoreCase(drugs[j]) == 0){
					kw = "drugs";
					break;
				}
			}
		}

		if(kw == "prove" || kw == "proof" || kw == "evidence")
			return "How do you expect me to prove something like this?";
		else if(kw == "footprints" || kw == "footprint" || kw == "foot" || kw == "print" || kw == "prints")
			return "There might be foot prints back there but I'm sure not going to look!";
		else if(kw == "picture" || kw == "photo" || kw == "pictures" || kw == "photos" || kw == "photograph" || kw == "photographs")
			return "Those things were gonna kill me.  Do you really think I was going to take their picture?";
		else if(kw == "witness" || kw == "witnesses" || kw == "anyone" || kw == "witnessed")
			return "It's the end of November.  Of course there was nobody else at the beach!";
		else if(kw == "dream")
			return "My jeans were wet the next day from when I jumped into the lake.  There's no way it was a dream";
		else if(kw == "drink" || kw == "drunk" || kw== "drinking" || kw == "intoxicated")
			return "I don't even drink.";
		else if(kw == "drugs")
			return "Is that supposed to be funny?";
		else if(kw == "full")
			return "Geez, you could try being a bit more supportive.";
		
	return getDefaultPhrase();
	}
	
	public String getDefaultPhrase(){
		return DEFAULT_PHRASE;
	}
	
	public String getTerminalPhrase(){
		return TERMINAL_PHRASE;
	}
}
