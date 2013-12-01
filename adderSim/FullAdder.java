//Lab 9
//Neal O'Hara
//11/29/13
import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList; 

public class FullAdder implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	
	
	//internal logic for adder
	private XorGate xor1 = new XorGate();
	private XorGate xor2 = new XorGate();
	private AndGate and1 = new AndGate();
	private AndGate and2 = new AndGate();
	private OrGate or1 = new OrGate();

	private Wire w_xor1 = new Wire("w_xor1");
	private Wire w_and1 = new Wire("w_and1");
	private Wire w_and2 = new Wire("w_and2");
	private Wire w_gnd = new Wire("gnd");
	private Wire a;
	private Wire b;
	private Wire cin;
	private Wire sum;
	private Wire cout;
		
	public FullAdder(){
		//gnd until set
		a = w_gnd;
		b = w_gnd;
		cin = w_gnd;
		sum = w_gnd;
		cout =  w_gnd;
		
		//create full adder logic
		xor1.setIn1(a);
		xor1.setIn2(b);
		xor1.setOut(w_xor1);
		and1.setIn1(a);
		and1.setIn2(b);
		and1.setOut(w_and1);
		and2.setIn1(w_xor1);
		and2.setIn1(cin);
		and2.setOut(w_and2);
		xor2.setIn1(w_xor1);
		xor2.setIn2(cin);
		xor2.setOut(sum);
		or1.setIn1(w_and2);
		or1.setIn2(w_and1);
		or1.setOut(cout);
		
	}

	/*
	ValueChangeListener listener = new ValueChangeListener() {

		@Override
		public void valueChanged(ValueChangeEvent event) {
			// TODO Auto-generated method stub
			if (sum!=null){
				//sum.setValue(a.getValue() ^ b.getValue());
			}
		}
		
	};
	*/
	

	public void setA(Wire v) {
		a = v;
		//v.addValueChangeListener(listener);
	}
	public Wire getA() {
		return a;
	}
	

	public void setB(Wire v) {
		b = v;
		//v.addValueChangeListener(listener);
	}
	public Wire getB() {
		return b;
	}
	
	
	public void setCin(Wire v) {
		cin = v;
		//v.addValueChangeListener(listener);
	}
	public Wire getCin() {
		return cin;
	}
	

	public void setSum(Wire w) {
		sum = w;
		//sum auto updated by Xor2
		//if(a!=null && b!=null && cin!=null){
			//sum.setValue(a.getValue() ^ b.getValue());
		//}
	}
	public Wire getSum() {
		return sum;
	}
	

	public void setCout(Wire w) {
		cout = w;
		//cout auto update by and2
		//if(a!=null && b!=null && cin!=null){
			//cout.setValue(a.getValue() ^ b.getValue());
		//}
	}
	public Wire getCout() {
		return cout;
	}
}