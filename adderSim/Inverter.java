
public class Inverter implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	ValueChangeListener listener = new ValueChangeListener() {

		@Override
		public void valueChanged(ValueChangeEvent event) {
			// TODO Auto-generated method stub
			if (out!=null)
				out.setValue(!in.getValue());
		}
		
	};
	
	private Wire in;
	public void setIn(Wire v) {
		in = v;
		v.addValueChangeListener(listener);
	}
	public Wire getIn() {
		return in;
	}
	
	private Wire out;
	public void setOut(Wire w) {
		out = w;
		if(in!=null)
			out.setValue(!in.getValue());
	}
	public Wire getOut() {
		return out;
	}
}
