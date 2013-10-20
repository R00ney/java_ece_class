public class HelloThread extends Thread {

    public void run() {
        System.out.println("class HelloThread!");
    }

    public static void main(String args[]) {
        (new HelloThread()).start();
    }

}