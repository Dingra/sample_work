
public class InitialState extends State {
	final String OPENING_PHRASE;
	
	String[] toDisbelief;
	String[] toHelp;
	String[] toComfort;
	String[] toCrazy;
	String[] toLying;
	String[] toBelief;
	String[] inquiries;
	String[] negative;
	
	public InitialState(){
		OPENING_PHRASE = "Hey.  I'm guessing you already heard...a few days ago I was attacked by a bunch of..." +
				"well, the best way I can describe them is \"sahuagins\".";
		
		//Upon the seeing these key words, the program will suspect that the user thinks it is crazy
		String[] cr = {"crazy", "nuts", "shrink", "psycho", "mad", "imagining", "imagined", "imagine"
				, "psychiatrist", "counsellor", "counselling", "counsel", "losing", "lost", "mental", "help", "hallucinating"};
		
		//Upon seeing these key words, the program will suspect the user thinks it is lying
		String[] ly = {"Liar", "fib", "fiction", "tale", "lying", "lie", "tales"};
		
		//Upon seeing these words, the program will suspect that the user is trying to help it
		String [] h = {"stop", "save", "prevent", "protect", "protection", "find", "resolve"
				, "solve", "figure", "danger", "dangerous"};
		
		//Upon seeing these key words in this state, the program will suspect that user is trying to cheer it up.
		String[] co = {"hurt", "injured", "safe", "scared", "afraid", "terrified", "worried", "traumatized", "scary"
				, "nightmares", "terrifying", "scare"};
		
		//Upon seeing these key words, the program will think the user believes it
		String[] b = {"believe"};
		
		String[] db = {"full", "dream", "dreamed", "dreamt", "nightmare", "drunk", "drink", "drinking", "drugs"};
		
		//These key words negate certain other key words
		String[] n = {"Don't", "can't", "won't", "not", "never", "aren't", "no"};
		
		//These words indicate that the user is asking the program a question
		String[] in = {"What", "Where", "When", "How", "Why"};
		
		toBelief = b;
		toDisbelief = db;
		toHelp = h;
		toComfort = co;
		toCrazy = cr;
		toLying = ly;
		negative = n;
		inquiries = in;
		
		
	}
	
	public String getOpeningPhrase(){
		return OPENING_PHRASE;
		
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
		else 
			ret += "*CHANGE_STATE=belief " + s;
		
		return ret;
	}
		
		private String checkKeyWords(String s){
			for(int i = 0; i < toCrazy.length; i ++)
				if(removePunctuation(s).compareToIgnoreCase(toCrazy[i]) == 0)
					return "crazy";
			
			for(int i = 0; i < toLying.length; i ++){
				if(removePunctuation(s).compareToIgnoreCase(toLying[i]) == 0){
					return "liar";
				}
			}
					
			for(int i = 0; i < toDisbelief.length; i ++)
				if(removePunctuation(s).compareToIgnoreCase(toDisbelief[i]) == 0)
					return "disbelief";
			
			for(int i = 0; i < toHelp.length; i++)
				if(removePunctuation(s).compareToIgnoreCase(toHelp[i])==0)
					return "help";
			
			for(int i = 0; i < toComfort.length; i ++)
				if(removePunctuation(s).compareToIgnoreCase(toComfort[i])==0)
					return "comfort";
			for(int i = 0; i < toBelief.length; i ++)
				if(removePunctuation(s).compareToIgnoreCase(toBelief[i]) == 0)
					return "belief";

			return "";
	}

}
