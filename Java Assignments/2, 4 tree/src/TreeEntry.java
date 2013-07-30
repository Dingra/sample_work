
public class TreeEntry<K extends Comparable<K>,V> {
	
	protected K key;
	protected V value;
	
	public TreeEntry(K k, V v){
		key = k;
		value = v;
	}
	
	public K getKey(){
		return key;
	}


}
