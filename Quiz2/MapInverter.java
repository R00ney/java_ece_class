/*
Given a Map<String,Integer> object, invert the map so that the values map to the keys.  For example, if you had these mappings initially:
Key : Value
Ben : 10
James : 15
You would now have these mappings:
10: Ben
15: James
If a value appears multiple times in the original map, that suggests it should map to multiple strings in the inverted map.  But, our Map<Integer,String> doesn't allow that.  So, you may pick an arbitrary key for it in the inverted map.  Implement your solution as a static method within a class with the following signature:
public static Map<Integer,String> invertMap(Map<String,Integer> map)
You must also include code  that tests your method (a main method that calls it, for example) in your submitted solution.
*/

import java.util.*;

public class MapInverter {

public static Map<Integer,String> invertMap(Map<String,Integer> map){
	
	Map<Integer,String> invert = new HashMap<Integer,String>();
	
	for(String s: map.keySet()){
		Integer newKey = map.get(s);
		
		while(invert.containsKey(newKey) == true){
			//invert already has the integer value,
			// arbritarily add 1 until an unused value is found
			newKey = newKey+1;
		}
		
		invert.put(newKey,s);	
	}
	
	return invert;
}



public static void main(String[] args){
	Map<String, Integer> map =new HashMap<String,Integer>();
	
	
	//Create map to be inverted here!
	map.put("Ben",10);
	map.put("James",15);
	
	
	
	System.out.println(map);
	
	Map<Integer,String> inverted = invertMap(map);
	
	System.out.println(inverted);
}



}
