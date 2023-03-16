import java.util.Scanner;

public class Customer {
	// Attributes of the Customer object.
	private int orderNumber;
	private String name;
	private String contactNumber;
	private String address;
	private String city;
	private String email;

	// The constructor for the Customer class.
	// Note that we set the attributes of the Customer object by prompting the user for input, 
	// and then scanning that in and returning it to the associated this.{...} variable.
	Customer() {
		this.orderNumber = setOrderNumber();
		this.name = setName();
		this.contactNumber = setContactNumber();
		this.address = setAddress();
		this.city = setCity();
		this.email = setEmail();
	}
	
	// Create a Scanner object, `sc`, for reading input from the user.
	Scanner sc = new Scanner(System.in);
	
	
	
	/*-------------------------------------------------------------------------------------------*/
	// Prompt the user for the order number, and store the value in `this.orderNumber` in the constructor.
	private int setOrderNumber() {
		System.out.println("What is the order number?");
		try {
			return Integer.parseInt(sc.nextLine());
		}
		catch (Exception e) {
			System.out.println("Error while parsing your input for 'Order Number':"
					        + "\n" + e.getMessage() + ".");
			throw new NumberFormatException("'Order Number' must be an Integer, e.g. 1234.");
		}
		
	}
	
	// Notice that the default modifier grants access to [1] Class, [2] Package, [3] Subclass in the same package.
	// It is more restrictive than the `protected` modifier.
	int getOrderNumber() {
		return this.orderNumber;
	}
	/*-------------------------------------------------------------------------------------------*/
	
	
	
	/*-------------------------------------------------------------------------------------------*/
	// Prompt the user for the his/her name, and store the value in `this.name` in the constructor.
	private String setName() {
		System.out.println("\nPlease enter the customer's name:");
		return sc.nextLine();
	}
	
	String getName() {
		return this.name;
	}
	/*-------------------------------------------------------------------------------------------*/
	
	
	
	/*-------------------------------------------------------------------------------------------*/
	// Prompt the user for his/her Contact Number, and store the value in `this.contactNumber` in the constructor.
	private String setContactNumber() {
		System.out.println("\nWhat is the customer's contact number?");
		return sc.nextLine();
	}
	
	String getContactNumber() {
		return this.contactNumber;
	}
	/*-------------------------------------------------------------------------------------------*/
	
	
	
	/*-------------------------------------------------------------------------------------------*/
	// Prompt the user for his/her address, and store the value in `this.address` in the constructor.
	private String setAddress() {
		System.out.println("\nWhat is the customer's address?");
		return sc.nextLine();
	}
	
	String getAddress() {
		return this.address;
	}
	/*-------------------------------------------------------------------------------------------*/
	
	
	
	/*-------------------------------------------------------------------------------------------*/	
	// Prompt the user for his/her City, and store the value in `this.city` in the constructor.
	private String setCity() {
		System.out.println("\nIn what city is the customer located?");
		return sc.nextLine();
	}
	
	String getCity() {
		return this.city;
	}
	/*-------------------------------------------------------------------------------------------*/
	
	
	
	/*-------------------------------------------------------------------------------------------*/
	// Prompt the user for his/her email address, and store the value in `this.email` in the constructor.
	private String setEmail() {
		System.out.println("\nWhat is the email address of the customer?");
		String email =  sc.nextLine();
		if (email.indexOf("@") == -1) {
			System.out.println("An invalid email address has been supplied."
					         + "\nIt should be of the format `something@somewhere.com`"
					         + "\nProgram Terminated...");
			System.exit(1);
		}
		return email;
	}

	String getEmail() {
		return this.email;
	}
	/*-------------------------------------------------------------------------------------------*/
	
	
	
	// Print out a text representation of the 'Customer' Object.
	public String toString() {
		String objectTxt =  "[Order Number]     " + orderNumber;
		       objectTxt += "\n[Customer Name]    " + name;
		       objectTxt += "\n[Contact Number]   " + contactNumber;
		       objectTxt += "\n[Address]          " + address;
		       objectTxt += "\n[City]             " + city;
		       objectTxt += "\n[Email]            " + email;
		return objectTxt;
	}
}
