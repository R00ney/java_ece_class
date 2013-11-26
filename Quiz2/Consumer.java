import java.util.*;

public static class Consumer extends Thread {
   public Consumer(List<Integer> aList) {
     list = aList;
   }
   List<Integer> list;
   
   protected void getFromList() {
      synchronized(list) {
         while(list.size()==0);
         System.out.println(list.get(0));
         list.remove(0);
     }
  }
  
  public void run() {
     // add integers a list
     while(true) getFromList();
  }
 
 
 public static void main(String[] args) {
   List<Integer> list = new LinkedList<Integer>();
   new Producer(list).start();
   new Consumer(list).start();
 }
}
