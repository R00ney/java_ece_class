public class WaitNotifyExample {
 public static class Producer extends Thread {
   public Producer(List<Integer> aList) {
     list = aList;
   }
   List<Integer> list;
   protected void addToList() {
     synchronized(list) {
        list.add((int)(Math.random()*10));
     }
   }
   public void run() {
     // add integers a list
     while(true) {
        addToList();
     }
   }
 }
 
}
