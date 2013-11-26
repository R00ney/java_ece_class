import java.util.*;

public class WaitNotifyExample {
	
 public static class Producer extends Thread {
   public Producer(List<Integer> aList) {
     list = aList;
   }
   
   List<Integer> list;
   
   protected void addToList() {
     synchronized(list) {
        list.add((int)(Math.random()*10));
        //list.notify(); //added notify here, for consumer to use value.
     }     
   }
   
   public void run() {
     // add integers a list
     while(true) {
        addToList();
     }
   }
   
 }

 public static class Consumer extends Thread {
   public Consumer(List<Integer> aList) {
     list = aList;
   }
   
   List<Integer> list;
   
   protected void getFromList() {
      synchronized(list) {
         while(list.size()==0){
         	//try{ list.wait(); //added wait here
         	//} catch (InterruptedException ie){} //do nothing but continue
         }
         System.out.println(list.get(0));
         list.remove(0);
     }
     
  }
  
  public void run() {
     // add integers a list
     while(true) getFromList();
  }
 }
 
 public static void main(String[] args) {
   List<Integer> list = new LinkedList<Integer>();
   new Producer(list).start();
   new Consumer(list).start();
 }
}
