import java.util.Scanner;
import java.util.*;

/**
 * The blueprint for creating a restaurant object with the attributes [1] <code>name</code>, [2] <code>city</code>, 
 * [3] <code>contactNumber</code>, [4] <code>mealAndQty</code>, [5] <code>mealAndPrice</code>, 
 * [6] <code>specialInstr</code> and [7] <code>total</code>.
 * @author Henri Branken
 *
 */
public class Restaurant {
	// Attributes of the Restaurant.
	private String name;
	private String city;
	private String contactNumber;
	private Map<String, Integer> mealAndQty;
	private Map<String, Float> mealAndPrice;
	private String specialInstr;
	private float total;
	
	// Restaurant Constructor.
	// Note that we set the attributes of the Restuarant object by prompting the user for input, 
	// and then scanning that in and returning it to the associated this.{...} variable.
	Restaurant(){
		this.name = setName();
		this.city = setCity();
		this.contactNumber = setContactNumber();
		this.mealAndQty = setMealAndQuantity();
		this.mealAndPrice = setMealAndPrice(mealAndQty);
		this.specialInstr = setSpecialInstr();
		this.total = setTotal(mealAndQty, mealAndPrice);
	}
	
	// Create a Scanner object, `sc`, for reading input from the user.
	Scanner sc = new Scanner(System.in);
	
	// ------------------------------------------------------------------------------------------- /
	// Prompt the user for the restaurant name, and store the value in `this.name` in the constructor.
	private String setName() {
		System.out.println("\nWhat is the name of the restaurant");
		return sc.nextLine();		
	}
	
	// Notice that the default modifier grants access to [1] Class, [2] Package, [3] Subclass in the same package.
	// It is more restrictive than the `protected` modifier.
	String getName() {
		return this.name;
	}
	// ------------------------------------------------------------------------------------------- /
	
	
	
	// ------------------------------------------------------------------------------------------- /
	// Prompt the user for the city in which the restaurant is based, and store the value in `this.city` in the constructor.
	private String setCity() {
		System.out.println("\nIn what city is the restaurant located?");
		return sc.nextLine();
	}
	
	String getCity() {
		return this.city;
	}
	// ------------------------------------------------------------------------------------------- /
	
	
	
	// ------------------------------------------------------------------------------------------- /
	// Prompt the user for the restaurant' contact number, and store the value in `this.contactNumber` in the constructor.
	private String setContactNumber() {
		System.out.println("\nWhat is the restaurant's contact number?");
		return sc.nextLine();
	}
	
	String getContactNumber() {
		return this.contactNumber;
	}
	// ------------------------------------------------------------------------------------------- /
	
	
	
	// ------------------------------------------------------------------------------------------- /
	// In `hashMap1`, each (String) key value (a meal) maps to an (integer) quantity value.
	// Each key value is unique.
	private Map<String, Integer> setMealAndQuantity() {
		Map<String, Integer> hashMap1 = new HashMap<String, Integer>();
		String response = "y";
		// The while loop terminates when the user inputs anything other than "y" for the `response` variable.
		while (response.trim().equalsIgnoreCase("y")) {
			// Prompt the user to enter the meal he'd like to order.  Store this as `meal`.
			System.out.println("\nWhat meal would you like to order?");
			String meal = sc.nextLine();
			
			// Prompt the user to enter the quantity of the selected meal he'd like to order.  Store this as `qty`.
			try {
				System.out.println("How many of the selected item would you like to order?");
				int qty = Integer.parseInt(sc.nextLine());
				
				// Append the new {key, value} pair, i.e. {`meal`, `qty`}, to `hashMap1` via the .put(...) method.
				hashMap1.put(meal, qty);
			}
			catch (Exception e) {
				System.out.println("Error while parsing your input for 'Quantity':"
						        + "\n" + e.getMessage() + ".");
				throw new NumberFormatException("'Quantity' must be an Integer, e.g. 3.");
			}
			
			// To add another meal to the list of meals, the user needs to enter "y".
			System.out.println("Would you like to select another meal? [y/n]");
			response = sc.nextLine().toLowerCase().trim();
		}
		return hashMap1;
	}
	
	Map<String, Integer> getMealAndQuantity() {
		return this.mealAndQty;
	}
	// ------------------------------------------------------------------------------------------- /
	
	
	
	// ------------------------------------------------------------------------------------------- /
	// In `hashMap2`, each (String) key value (a meal) maps to a (float) price value.
	// Each key value is unique.
	// In this procedure, we extract all the meals from `hashMap1` and store it into the `Set<String> meals`.
	// We then iterate over `meals`, and ask the user to input the price for each specific meal.
	// Once we have captured the `price`, we append the {`meal`, `price`} key/value pair to `hashMap2` via the .put(...) method.
	private Map<String, Float> setMealAndPrice(Map<String, Integer> mealAndQty) {
		// Instantiate a new HashMap of type `Map<String, Float>`.
		Map<String, Float> hashMap2 = new HashMap<String, Float>();
		
		// Create a unique list of `meals` by using the .keySet() operation on `mealAndQty`.
		Set<String> meals = mealAndQty.keySet();
		
		// For each meal, ask the user to input the price for the meal.  This information gets stored in `hashMap2` via the .put(...) method.
		for (String meal: meals) {
			try {
				System.out.println("\nWhat is the price (in Rands) for: " + meal + "?");
				float price = Float.parseFloat(sc.nextLine());
				hashMap2.put(meal, price);
			}
			catch (Exception e) {
				System.out.println("Error while parsing your input for 'Price':"
						        + "\n" + e.getMessage() + ".");
				throw new NumberFormatException("'Price' must be a float, e.g. 12.63.");
			}
		}
		return hashMap2;
	}
	
	Map<String, Float> getMealAndPrice() {
		return this.mealAndPrice;
	}
	// ------------------------------------------------------------------------------------------- /
	
	
	
	// ------------------------------------------------------------------------------------------- /
	// Prompt the user for special instructions (if any), and store the value in `this.specialInstr` in the constructor.
	private String setSpecialInstr() {
		// Prompt the user to determine if they have any special instructions for the restaurant.
		System.out.println("\nDo you have any special instructions for the restaurant? [y/n]");
		
		String answer = sc.nextLine().toLowerCase().trim();
		String specialInstructions = "";
		// If the user has any special instructions, store it in `specialInstructions`.
		if (answer.equals("y")) {
			System.out.println("What special instructions do you have?");
			specialInstructions = sc.nextLine();
			return specialInstructions;
		}
		
		// If there are no special instructions, then set `specialInstructions` to a default of "---".
		else {
			specialInstructions = "---";
			return specialInstructions;
		}
	}
	
	String getSpecialInstr() {
		return this.specialInstr;
	}
	// ------------------------------------------------------------------------------------------- /
	
	
	
	// ------------------------------------------------------------------------------------------- /
	// From the 2 maps we constructed earlier (`mealAndQty` & `mealAndPrice`), calculate the total cost for the user's order.
	// Finally, store the `total` in `this.total` in the constructor.
	private float setTotal(Map<String, Integer> mapQty, Map<String, Float> mapPrice) {
		// Initialise the `total` value to 0.  This value will accumulate in the for-loop.
		float total = 0;
		
		// Extract all the meals that the user has ordered, and store them in `Set<String> meals`.
		// Proceed by iterating over each `meal`.
		// For each meal, extract the `price` and `qty`.  Then multiply `qty` with `price` to get the total for that specific meal.
		// Add the result onto the running `total`.
		Set<String> meals = mapQty.keySet();
		for (String meal: meals) {
			float subTotal = 0;
			int qty = mapQty.get(meal);
			float price = mapPrice.get(meal);
			subTotal = (float) qty * price;
			total += subTotal;
		}
		
		// Format the `total` neatly to only contain two decimal places.
		return total;
	}
	
	float getTotal() {
		return this.total;
	}
	// ------------------------------------------------------------------------------------------- /
	
	
	
	/**
	 * Print out a text representation of the <code>Restaurant</code> object to the console.
	 */
	public String toString() {
		String objectTxt =  "[Restaurant Name]           " + name;
		       objectTxt += "\n[City of Restaurant]        " + city;
		       objectTxt += "\n[Restaurant Contact Number] " + contactNumber;
		       objectTxt += "\n[Meals and Quantities]      " + mealAndQty;
		       objectTxt += "\n[List of Meal Prices]       " + mealAndPrice;
		       objectTxt += "\n[Special Instructions]      " + specialInstr;
		       objectTxt += "\n[Total Cost]                R" + total;
		
		return objectTxt;
	}
}
