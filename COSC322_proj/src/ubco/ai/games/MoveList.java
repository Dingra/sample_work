package ubco.ai.games;

/**
 * 
 * @author Andrew 'AI' Ingram
 * Implemented: March 27, 2011
 * 
 * A list of all possible moves from a particular space
 */
public class MoveList {

	Move start;//The beginning of the list
	
	/**The constructor must initialise an empty list */
	public MoveList(){
		start = null;
	}
	
	/**
	 * Add a new move to the list
	 * @param m: the move to be added to the list
	 */
	public void add(Move m){
		if(start == null){
			start = m;
			m.next = null;
			m.previous = null;
		}
	
		else{
			Move current = start;
			
			//Move to the end of the list
			while(current.next != null){
				current = current.next;
			}
			current.next = m;
			m.previous = current;
		}
	}
	
	/**
	 * Remove the item m from the list
	 * @param m: The node being considered
	 */
	public void remove(Move m){
		
		if(start == m)
			start = null;
		
		//If it is the last item in the list
		else if(m.next == null){
			 m.previous.next = null;
		 }
		//If it is not the first or last one in the list
		 else{
			 m.next.previous = m.previous;
			 m.previous.next = m.next;
		 }
	}
	
	/**
	 * 
	 * @param one: A list
	 * @param two: Another list
	 * @return A new list of all items in 'one' and 'two'
	 */
	public MoveList merge(MoveList one, MoveList two){
		Move current = one.start;
		
		if(current != null){
		while(current.next != null)
			current = current.next;
		}
		else return two;
		
		if(two.start != null){
		current.next = two.start;
		two.start.previous = current;
		}
		
		return one;
	}
	
	/**
	 * 
	 * @param m: The move being considered
	 * Removes all nodes preceding the node m
	 */
	public void removePrevious(Move m){
		start = m;
		m.previous = null;
	}
	
}
