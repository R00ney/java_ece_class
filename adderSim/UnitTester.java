//Lab 9
//Neal O'Hara
//11/29/13
import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList; 

public class UnitTester{
	public static void main(String[] args){
	
		Wire in1 = new Wire("a");
		Wire in2 = new Wire("b");
		Wire out1 = new Wire("x");
		Wire out2 = new Wire("y");
		Wire out3 = new Wire("z");
		
		AndGate and = new AndGate();
		OrGate or = new OrGate();
		XorGate xor = new XorGate();
		
		and.setIn1(in1);
		and.setIn2(in2);;
		and.setOut(out1);
		
		or.setIn1(in1);
		or.setIn2(in2);;
		or.setOut(out2);

		xor.setIn1(in1);
		xor.setIn2(in2);;
		xor.setOut(out3);

		List<Wire> ins = new ArrayList<Wire>();
		List<Wire> outs = new ArrayList<Wire>();
		ins.add(in1); ins.add(in2);
		outs.add(out1);
		outs.add(out2);
		outs.add(out3);
		
		System.out.println("Test gates:");
		unitTest(ins,outs);
		
		System.out.println("Test FullAdder");
		Wire wa = new Wire("A");
		Wire wb = new Wire("B");
		Wire wcin = new Wire("Cin");
		Wire wsum = new Wire("Sum");
		Wire wcout = new Wire("Cout");
		FullAdder fa = new FullAdder();
		fa.setA(wa);
		fa.setB(wb);
		fa.setCin(wcin);
		fa.setSum(wsum);
		fa.setCout(wcout);
		List<Wire> ins2 = new ArrayList<Wire>();
		List<Wire> outs2 = new ArrayList<Wire>();		
		ins2.add(wa);
		ins2.add(wb);
		ins2.add(wcin);
		outs2.add(wsum);
		outs2.add(wcout);
		unitTest(ins2,outs2);
		
	}

	public static void unitTest(List<Wire> input, List<Wire> output){

		unitTestHelper(input,0,output);
	}
	
	public static void unitTestHelper(List<Wire> in, int index,  List<Wire> out) {
		boolean[] values = {false, true};
		
		if (index==in.size()) {
			print(in,out);
			return;
		}
		
		// set in to each boolean value, one at a time
		for(boolean b: values) {
			in.get(index).setValue(b);
			// for this value, change the next wire to both
			// true and false to evaluate all combinations
			unitTestHelper(in,index+1,out);
		}
	
	}

	public static void print(List<Wire> in, List<Wire> out) {
		String s="";
		for(Wire i: in)
			s += i.getAbbreviatedValue() + " ";
		
		s+="| ";
		
		for(Wire i: out)
			s += i.getAbbreviatedValue() + " ";
			
		System.out.println(s);
	}

}