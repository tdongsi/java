package my.java.programming.fourth.chapter2;

public class Vehicle {
	public double currentSpeed;
	public double currentDirection;
	private int ID;
	
	private String ownerName;
	
	public static int nextID;
	
	Vehicle() {
		ID = nextID++;		
	}
	
	Vehicle( String ownerName ) {
		this();
		this.ownerName = ownerName;
	}
	
	public static void main( String[] args) {
		Vehicle car = new Vehicle( "Ti Heo" );
		car.currentSpeed = 0.0;
		car.currentDirection = 0.0;
		
		Vehicle jeep = new Vehicle();
		jeep.currentSpeed = 0.0;
		jeep.currentDirection = 90.0;
		
		System.out.println( "My car " + car );
		System.out.println( "My jeep " + jeep );
		
		System.out.println( "Highest Vehicle ID: " + Vehicle.currentHighestID() );
	}

	public String toString() {
		return "Vehicle [currentSpeed=" + currentSpeed + ", currentDirection="
				+ currentDirection + ", ID=" + ID + ", ownerName=" + ownerName
				+ "]";
	}
	
	public static int currentHighestID() {
		return (nextID-1);
	}

	public int getID() {
		return ID;
	}

	public String getOwnerName() {
		return ownerName;
	}
	
}

class LinkedList {
	Object data;
	
	LinkedList nextItem;
}
