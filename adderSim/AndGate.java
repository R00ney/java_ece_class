
public class AndGate implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	ValueChangeListener listener = new ValueChangeListener() {

		@Override
		public void valueChanged(ValueChangeEvent event) {
			// TODO Auto-generated method stub
			if (out!=null)
				out.setValue(in1.getValue() && in2.getValue());
		}
		
	};
	
	private Wire in1;
	public void setIn1(Wire v) {
		in1 = v;
		v.addValueChangeListener(listener);
	}
	public Wire getIn1() {
		return in1;
	}
	
	private Wire in2;
	public void setIn2(Wire v) {
		in2 = v;
		v.addValueChangeListener(listener);
	}
	public Wire getIn2() {
		return in2;
	}
	
	
	private Wire out;
	public void setOut(Wire w) {
		out = w;
		if(in1!=null && in2!=null)
			out.setValue(in1.getValue() && in2.getValue());
	}
	public Wire getOut() {
		return out;
	}
	
}
