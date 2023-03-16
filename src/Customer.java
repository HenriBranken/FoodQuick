import java.util.Scanner;

/**
 * The blueprint for creating a customer object with the attributes [1] <code>orderNumber</code>, 
 * [2] <code>name</code>, [3] <code>contactNumber</code>, [4] <code>address</code>, [5] <code>city</code>
 * and [6] <code>email address</code>.
 * @author Henri Branken
 *
 */
public class Customer {
	// Attributes of the Customer object.
	private int orderNumber;
	private String name;
	private String contactNumber;
	private String address;
	private String city;
	private String email;

	// Customer Constructor
	// Notice that we set the attributes of the Customer object via setter methods which prompts
	// the user for keyboard input.
	// The input is captured by a Scanner and assigned to the associated attribute.
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
	
	
	
	// ------------------------------------------------------------------------------------------- /
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
	
	int getOrderNumber() {
		return this.orderNumber;
	}
	// ------------------------------------------------------------------------------------------- /
	
	
	
	// ------------------------------------------------------------------------------------------- /
	// Prompt the user for the his/her name, and store the value in `this.name` in the constructor.
	private String setName() {
		System.out.println("\nPlease enter the customer's name:");
		return sc.nextLine();
	}
	
	String getName() {
		return this.name;
	}
	// ------------------------------------------------------------------------------------------- /
	
	
	
	// ------------------------------------------------------------------------------------------- /
	// Prompt the user for his/her Contact Number, and store the value in `this.contactNumber` in the constructor.
	private String setContactNumber() {
		System.out.println("\nWhat is the customer's contact number?");
		return sc.nextLine();
	}
	
	String getContactNumber() {
		return this.contactNumber;
	}
	// ------------------------------------------------------------------------------------------- /
	
	
	
	// ------------------------------------------------------------------------------------------- /
	// Prompt the user for his/her address, and store the value in `this.address` in the constructor.
	private String setAddress() {
		System.out.println("\nWhat is the customer's address?");
		return sc.nextLine();
	}
	
	String getAddress() {
		return this.address;
	}
	// ------------------------------------------------------------------------------------------- /
	
	
	
	// ------------------------------------------------------------------------------------------- /	
	// Prompt the user for his/her City, and store the value in `this.city` in the constructor.
	private String setCity() {
		System.out.println("\nIn what city is the customer located?");
		return sc.nextLine();
	}
	
	String getCity() {
		return this.city;
	}
	// ------------------------------------------------------------------------------------------- /
	
	
	
	// ------------------------------------------------------------------------------------------- /
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
	// ------------------------------------------------------------------------------------------- /
	
	
	
	/**
	 * Print out a text representation of the <code>Customer</code> object to the console.
	 */
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
