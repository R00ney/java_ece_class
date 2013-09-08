// Bicyle class example from Oracle Java Tutorials
// Good demonstration of specifiers and class and method details

public class Bicycle {
    
    // all fields are private, therefor can only be
    // seen within class
    private int cadence;
    private int gear;
    private int speed;
        
    private int id;
    
    //this is a class field, seen by all instantiations of Bicycles,
    // 
    private static int numberOfBicycles = 0;

    
    // This is a constructor
    public Bicycle(int startCadence,
                   int startSpeed,
                   int startGear){
        gear = startGear;
        cadence = startCadence;
        speed = startSpeed;

        id = ++numberOfBicycles;
    }


    // This is a class method, called with Bicylce.getNumberOfBicycles()
    public static int getNumberOfBicycles() {
        return numberOfBicycles;
    }
    
    //The following are all method that can
    //be referenced from instantiated Bicycle objects
    public int getID() {
        return id;
    }
    
    public int getCadence(){
        return cadence;
    }
        
    public void setCadence(int newValue){
        cadence = newValue;
    }
        
    public int getGear(){
    return gear;
    }
        
    public void setGear(int newValue){
        gear = newValue;
    }
        
    public int getSpeed(){
        return speed;
    }
        
    public void applyBrake(int decrement){
        speed -= decrement;
    }
        
    public void speedUp(int increment){
        speed += increment;
    }
}