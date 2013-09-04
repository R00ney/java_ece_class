import java.io.*;

class ExampleBufferedReaderWithErrorHandeling {
	
	public static void main(String[] args) {
		
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String line;
		
		try{ line = in.readLine(); }
		catch(IOException e) {
			
			System.out.println(e.toString());
			return;
		}
		
		System.out.println(line);
	}
}

		
