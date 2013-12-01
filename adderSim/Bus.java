//Lab 9
//Neal O'Hara
//11/29/13
import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList; 



public class Bus  implements java.io.Serializable{

	//default size 8
	private int size = 8;
	//
	Wire[] bus = new Wire[size];
	
	
	private static final long serialVersionUID = 1L;

	ValueChangeListener listener = new ValueChangeListener() {

		@Override
		public void valueChanged(ValueChangeEvent event) {
			// TODO Auto-generated method stub
			if (out!=null)
				out.setValue(in1.getValue() ^ in2.getValue());
		}
		
	};
	
	public void setBus(Wire[] v) {
		l = v.length;
		if( l != size){
			this.setSize(l);
		}
		bus = v;
		v.addValueChangeListener(listener);
	}
	public Wire getBus() {
		return bus;
	}
	
	//set size var and resize bus
	public void setSize(int v) {
		l = v.length;
		if( l != size){
			this.setSize(l);
		}
		bus = new Wire[v];
	}
	public Wire getBus() {
		return bus;
	}
	
}