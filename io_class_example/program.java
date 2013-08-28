import java.io.*;

public class program {

	public static void main(String[] args){

		try {
	 	DataOutputStream dos = new DataOutputStream(new FileOutputStream("mydata.data"));
	
 		for(int i=0; i<100; i++)
	 		dos.writeInt(i); // write 100 ints
		
		dos.writeUTF("100 hundred grades from my class");
		dos.writeUTF("another string to confuse Java, Ha!");
			
		 dos.close();
	 
		} catch (Exception e) {
			 e.printStackTrace();
		}
	
		try {
		 DataInputStream dis = new DataInputStream(new FileInputStream("mydata.data"));
	 
		 int[] array = new int[100];
	 
		 
		 
		for(int i=0; i<100; i++) 
			array[i] = dis.readInt(); //read 100 ints
		
		
		String description = dis.readUTF();
		String description2 = dis.readUTF();
		
		dis.close();
		 		 
		for(int j=0; j<100; j++)  
				System.out.println(String.valueOf(array[j]));
				
		System.out.println(description);
		System.out.println(description2);
		

		
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}

