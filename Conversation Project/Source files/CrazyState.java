/**
 * 
 * @author Drew Ingram
 * Implemented 17/11/2012
 * 
 * When the program is in this state, it suspects that the user believes it is crazy
 */
public class CrazyState extends State{
	
	String[] toBelief;
	String[] toDisbelief;
	String[] toLying;
	
	String[] toCrazy;
	
	String[] evidence;//When the user asks about evidence in this state, Survivor will assume the user wants proof

	private final String OPENING_PHRASE;
	private final String DEFAULT_PHRASE;
	private final String TERMINAL_PHRASE;
	
	String[] occur;
	String[] escape;
	String[] description;
	String[] beach;
	String[] monster;
	
	int counter;
	public CrazyState(){
		OPENING_PHRASE = "You think I'm Crazy??";
		DEFAULT_PHRASE = "I don't know what you're talking about.  Are you trying to mess with me?";
		TERMINAL_PHRASE = "I'm telling you I'm not crazy!  I'm not crazy!  I'm not!  I'M NOT!  WHY WON'T YOU SHUT UP?  STOP ASKING ME THAT!" +
				"\n  GRRRGHHHHRRRGHGGGGHHHH  AHHHHHHHHHHHHHHH-- *thunk*";
		
		//Upon seeing these key words in this state, the program will decide that the user believes it after all
		String[] b = {"believe", "should", "could"};
		
		//Upon seeing these words in this state, the program will no longer suspect that the user thinks it is crazy
		String[] db = {"drunk", "drink", "drinking", "prove", "proof", "evidence", "footprint", "picture", "photo", "photograph", "drugs"};
		
		//Upon seeing these key words while in this state, the program will suspect the user thinks it is lying
		String[] ly = {"Liar", "fib", "fiction", "tale", "lying", "lie", "fibbing"};
		
		String[] cr = {"crazy", "nuts", "shrink", "psycho", "mad", "imagining", "imagined", "imagine"
				, "psychiatrist", "counsellor", "counselling", "counsel", "losing", "lost", "mental", "help"};
		
		String[] be = {"beach", "there"};//synonyms and pronouns for "beach"
		String[] occ = {"Happen", "Happened", "occur", "occurred", "attack", "attacked"};//Synonyms, pronouns and words with similar connotations for "happen"
		String[] d = {"look", "sound", "act", "behave", "move"};//Any sort of description
		String[] mo = {"sahuagin", "monster", "creature", "sahuagins", "creatures, monsters", "things", "beasts"
				,"fishmen", "that"};
		String[] es = {"escape", "escaped", "get"};//Synonyms and pronouns for "escape"
		
		beach = be;
		occur = occ;
		escape = es;
		description = d;
		monster = mo;
		
		toBelief 		= b;
		toDisbelief 	= db;
		toLying 		= ly;
		
		toCrazy = cr;
	}
	
	public String getOpeningPhrase(){
		return OPENING_PHRASE;
	}
	
		private String checkKeyWords(String s){

			for(int i = 0; i < toLying.length; i ++)
				if(removePunctuation(s).compareToIgnoreCase(toLying[i]) == 0)
					return "liar";
					
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
					else if(kw[i].equals("belief") && !(inquiry) && !(negative)){
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
			String story = "I was lying down at the dock at Kal beach and a bunch of those things jumped out of the water.  They were hissing at me" +
					"\nand it looked like they wanted to kill me so I ran.  When I looked back there were probably two dozen of them.";
			
			String description = "They were ugly, fish things.  They looked like fish and smelled like fish but they were walking on two legs" +
					"\nThey had fins and scales all over their bodies.";
			
			String whySahuagin = "After I got away, I looked them up on the internet.  The closest things I could find to what I saw were called sahuagins.";
			
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
				return "I have no idea why they would want to attack me!";
			else if(inquiry == "why" && topic == "beach")
				return "I've been really busy this week so I thought I'd relax outside where nobody else was around";
			else if(inquiry == "why" && topic == "believe")
				return "Because it really happened!  I was there, I saw them and I barely escaped!";
			else if(inquiry == "where")
				return "At Kal Beach, at the end of the dock";
			else if(inquiry == "when"){
				return "Three days ago at night time.";
			}
			else if(inquiry == "how" && topic == "escape")
				return "I ran like hell and they didn't catch me.  I dont' know why.";
			return getDefaultPhrase();
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
		 * @return True if the parameter s is a question, and false otherwise
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
			String[] input = s.split(" ");
			String kw = null;
			
			for(int i = 0; i < input.length; i ++){
				for(int j = 0; j < toCrazy.length; j ++)
					if(removePunctuation(input[i]).compareToIgnoreCase(toCrazy[j]) == 0){
						kw = toCrazy[j];
					}			
			}
			
			
			if(kw == "crazy" || kw == "nuts" || kw == "mad" || kw == "lost" || kw == "losing")
				return "Well that's just great.  You hear what I have to say and just assume I've gone bonkers.";
			else if(kw == "shrink" || kw == "psychiatrist" || kw == "counsellor" || kw == "counselling" || kw == "counsel" || kw == "mental"
				|| kw == "help")
				return "I don't need professional help.  What I saw was very clear.";
			else if(kw == "imagine" || kw == "imagined")
				return "I saw them close up!  I can still smell that stench, and I can hear that awful noise they were making";
			else if(kw == "prove")
				return "I haven't got any proof!  Why don't you just believe me?";
			
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
				for(int j = 0; j < toBelief.length; j ++){
					if(removePunctuation(input[i]).compareToIgnoreCase(toBelief[j]) == 0){
						topic = "believe";
						terminate = true;
						break;
					}
				}
			}
			
			return topic;
		}
				
	public String getDefaultPhrase(){
		return DEFAULT_PHRASE;
	}
	
	public String getTerminalPhrase(){
		return TERMINAL_PHRASE;
	}
}
