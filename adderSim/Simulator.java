
public class Simulator {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Wire w1 = new Wire();
		Wire w2 = new Wire();
		Wire w3 = new Wire();
		Wire w4 = new Wire();
		
		Inverter inv = new Inverter();
		Inverter inv2 = new Inverter();
		AndGate and1 = new AndGate();
		
		inv.setOut(w2);
		inv.setIn(w1);

		inv2.setIn(w2);
		inv2.setOut(w3);
		
		and1.setIn1(w3);
		and1.setIn2(w1);
		and1.setOut(w4);

		
		w1.setValue(false);
		
		//System.out.println(w4.getValue());
		
		w1.setValue(true);
		
		//System.out.println(w4.getValue());
		
		boolean[] array = {false, true};
		
		for(boolean b: array) {
			for(boolean b2: array) {
				w1.setValue(b);
				w3.setValue(b2);
				System.out.println(b+"&&"+b2+"="+w4.getValue());
			}
		}
		
	}

}
