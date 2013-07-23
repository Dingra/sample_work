/**
 * 
 * @author Drew Ingram
 * implemented 17/11/2012
 * 
 * When the program is in this state, it suspects that the user is trying to help it
 */
public class HelpState extends State{
	private final String OPENING_PHRASE;
	private final String DEFAULT_PHRASE;
	private final String TERMINAL_PHRASE;
	
	int counter;
	
	String[] toCrazy;
	String[] toBelief;
	String[] toComfort;
	String[] toDisbelief;
	String[] toLying;
	
	String[] negative;
	String[] inquiries;
	
	String[] toHelp;//These do not change the state, but are used to determine the output in this state
	
	String[] occur;
	String[] escape;
	String[] description;
	String[] beach;
	String[] monster;
	String[] evidence;
	String[] misc;//Miscellaneous key words
	
	
	public HelpState(){
		OPENING_PHRASE = "You are helping me?";
		DEFAULT_PHRASE = "What?  I don't know what you mean.  Try to focus.  This is really important!";
		TERMINAL_PHRASE = "You know what?  I know what I have to do.  I'm going to call my redneck cousin in.  He's just crazy enough to help out!" +
				"  Thanks for hearing me out.  I gotta go now.  See ya!";
		
		//Upon seeing these key words in this state, the program will suspect that the user thinks it is crazy.
		String[] cr = {"crazy", "nuts", "shrink", "psycho", "mad", "imagining", "imagined", "imagine"
				, "psychiatrist", "counsellor", "counselling", "counsel", "dream", "dreamed", "dreamt"
				, "nightmare", "losing", "lost"};
		
		//Upon seeing these key words in this state, the program will suspect that user is trying to cheer it up.
		String[] co = {"hurt", "injured", "safe", "scared", "afraid", "terrified", "worried", "traumatized"
				, "nightmares", "scary", "scared"};
		
		//Upon seeing these key words, the program will stop thinking the user is trying to help it
		String[] b = {"believe"};
		
		//Upon seeing these key words, the program will suspect that the user no longer believes it
		String[] db = {"full", "dream", "dreamed", "dreamt", "nightmare", "drunk", "drink", "drinking", "drugs"};
		
		String[] ly = {"Liar", "fib", "fiction", "tale", "lying", "lie", "tales"};
		
		//These key words negate certain other key words
		String[] n = {"Don't", "can't", "won't", "not", "never", "aren't", "no"};
		
		//These words indicate that the user is asking the program a question
		String[] in = {"What", "Where", "When", "How", "Why", "Who"};
		
		String [] h = {"help", "stop", "save", "prevent", "protect", "protection", "find", "resolve"
				, "solve", "figure", "danger", "dangerous", "do", "prove", "proof", "evidence", "footprint", "picture", "photo", "photograph"};
		
		String[] ev = {"prove", "proof", "evidence", "footprint", "footprints", "foot", "print", "picture", "photo", "photograph", "anyone"
				, "witness", "witnesses", "witnessed"};
		
		toCrazy 	= cr;
		toBelief	= b;
		toComfort 	= co;
		toDisbelief = db;
		negative 	= n;
		inquiries 	= in;
		
		toHelp = h;
		
		String[] be = {"beach"};//synonyms and pronouns for "beach"
		String[] occ = {"Happen", "Happened", "occur", "occurred", "attack", "attacked"};//Synonyms, pronouns and words with similar connotations for "happen"
		String[] e = {"escape", "escaped", "away"};//Synonyms and pronouns for "escape"
		String[] d = {"look", "sound", "act", "behave", "move"};//Any sort of description
		String[] mo = {"sahuagin", "monster", "creature", "sahuagins", "creatures, monsters", "things", "beasts"
				,"fishmen", "that", "they"};
		String[] m = {"mob", "police", "authorities", "cops", "fight", "back", "army", "military", "capture"};
		
		beach = be;
		occur = occ;
		escape = e;
		description = d;
		monster = mo;
		evidence = ev;
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
		
		for(int i = 0; i < toComfort.length; i ++)
			if(removePunctuation(s).compareToIgnoreCase(toComfort[i])== 0)
				return "comfort";
		
		for(int i = 0; i < toBelief.length; i ++)
			if(removePunctuation(s).compareToIgnoreCase(toBelief[i])== 0)
				return "belief";
		return "";
}
	
	
	
	public String generateOutput(String s){
		String ret = "";
		boolean changeState = false;
		
		if(s.charAt(0) == '*')//The state has just changed to this state
			counter = 0;
		
		counter ++;
		
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
				else if(kw[i].equals("comfort")){
					keyWordFound = "comfort";
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
	
	//If the input is not a question, this method will be called to generate the output
	private String inputNotQuestion(String s){
		String[] input = s.split(" ");
		String kw = null;
		for(int i = 0; i < input.length; i ++){
			for(int j = 0; j < toHelp.length; j ++){
				if(removePunctuation(input[i]).compareToIgnoreCase(toHelp[j])== 0){
					kw = toHelp[j];
					break;
				}
			}
			
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
		}

		if(kw == "stop" || kw == "prevent" || kw == "protect" || "kw" == "protection" || kw == "do")
			return "Something definitely needs to be done.  Of course, the're's not a heck of a lot we can do when" +
					" when nobody believes us";
		else if(kw == "danger" || kw == "dangerous")
			return "You're right.  Somebody could get seriously hurt.";
		else if(kw == "picture" || kw == "pictures" || kw == "photo" || kw == "photograph" || kw == "photos" || kw == "photographs")
			return "I didn't have any sort of camera with me, so no pictures";
		else if(kw == "footprint" || kw == "foot" || kw == "print")
			return "I guess there could still be footprints there.  But we'll have to go back to check and it isn't safe.";
		else if(kw == "witness" || kw == "witnesses" || kw == "witnessed" || kw == "anyone" || kw == "others")
			return "I was the only one there.  It IS November after all.  Not a lot of people visiting the beach";
		else if(kw == "proof" || kw == "proof" || kw == "evidence")
			return "I haven't got any proof.";
		else if(kw == "mob")
			return "You want to form a mob?  That's a terrible idea.  There's no way we can convice them these things are real, and even" +
					" if we could, those things are dangerous!  Somebody might get hurt!";
		else if(kw == "police" || kw == "cops" || kw == "authorities")
			return "What do you expect the police to do?  They won't believe us, and there are probably dozens of those things in the lake";
		else if(kw == "back")
			return "You want to go back there?  No way, it's way too dangerous!";
		else if(kw == "fight")
			return "There were too many of them to fight.";
		else if(kw == "army" || kw == "military")
			return "Get real, there's no way we could get the military to help";
		else if(kw == "capture")
			return "There is no way I'm going back there!";
		
	return getDefaultPhrase();
	}
	
	public String getDefaultPhrase(){
		return DEFAULT_PHRASE;
	}
	public String getTerminalPhrase(){
		return TERMINAL_PHRASE;
	}
}
