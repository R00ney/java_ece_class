import java.util.LinkedList;
import java.util.List;


public class Wire implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private String name = "?";
	public Wire(String name) {
		setName(name);
	}
	public void setName(String n){
		name = n;
	}
	public String getName(){
		return name;
	}
	
	private boolean value;
	public void setValue(boolean avalue) {
		value = avalue;
		notifyListeners(new ValueChangeEvent(this));
	}
	public boolean getValue() {
		return value;
	}

	List<ValueChangeListener> listeners = 
			new LinkedList<ValueChangeListener>();
	public void addValueChangeListener(ValueChangeListener listener) {
		listeners.add(listener);
	}
	
	private void notifyListeners(ValueChangeEvent event) {
		for(ValueChangeListener l: listeners) {
			l.valueChanged(event);
		}
	}
	
	public String getAbbreviatedValue() {
		if (getValue())
			return "T";
		else
			return "F";
	}
}
