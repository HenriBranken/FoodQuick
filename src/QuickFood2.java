import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.text.DecimalFormat;

/**
 * QuickFood2 is a food delivery system for a company called "Quick Food".
 * QuickFood is a company that receives orders from a client, and distributes
 * them to a driver based on their current load and their location.  This class
 * ensures that orders are distributed accordingly.
 * @author Henri Branken
 *
 */
public class QuickFood2 {
	// Declare some constants.
	final static String DRIVERS_FP = "src/data/driver-info.txt";
	final static String INVOICE_FP = "src/data/invoice.txt";
	final static String DEFAULT_MSG = "Sorry! Our drivers are too far away from you to be able to deliver to your location.";
	final static int INITIAL_LOAD = -1;
	final static String SEP = "\n\n// ----------------------------------------------------------------------------------- /";
	
	
	// MAIN ------------------------------------------------------------------------------------- /
	/**
	 * The ultimate goal of the <code>main(...)</code> function is to generate an invoice based on
	 * particulars captured from the user.
	 * @param args No String array is passed to this main method.
	 * @throws IOException A Problem has occured while trying to output the invoice data to 
	 * <code>INVOICE_FP</code>.
	 */
	public static void main(String[] args) throws IOException {
		// Create a new Customer object, and print out its details.
		Customer cust = new Customer();
		System.out.println(cust);
		System.out.println();
		
		// Create a new Restaurant object, and print out its details.
		Restaurant rest = new Restaurant();
		System.out.println(rest);
		System.out.println();
		
		// Declare an empty invoice variable `inv` and populate it with text.
		// If the customer city matches the restaurant city, then proceed to find the closest driver with the smallest load,
		// ELSE, assign `DEFAULT_MSG` to `inv`.
		String inv = "";
		if (cust.getCity().trim().equalsIgnoreCase(rest.getCity().trim())) {
			String chosenDriver = getDriver(rest.getCity());
			if (chosenDriver.equalsIgnoreCase("none")) {
				inv = DEFAULT_MSG;
			}
			else {
				inv = printInvoice(cust, rest, chosenDriver);
			}
		}
		else {
			inv = DEFAULT_MSG;
		}
		System.out.println(SEP);
	    System.out.println(inv);
		
		// Write the contents of `inv` to "src/data/invoice.txt".
		File f = new File(INVOICE_FP);
		f.createNewFile();
		
		try (FileWriter myWriter = new FileWriter(INVOICE_FP);) {
			myWriter.write(inv);
			System.out.println(SEP + "\nPlease checkout '" + INVOICE_FP + "'.");
		}
		catch (IOException e) {
			System.out.println("An error occurred while trying to write to " + INVOICE_FP + "."
					         + "\n" + e.getMessage());
		}
	}
	// END OF MAIN ------------------------------------------------------------------------------ /
	
	
	
	// FORMAT TO A PRETTY ADDRESS --------------------------------------------------------------- /
	/**
	 * Takes an input address in which the fields are separated by commas, and converts it
	 * into a string in which the fields are separated by newlines <code>`\n`</code>.
	 * @param input Customer Address information in which the fields are comma delimited.
	 * @return A formatted string in which the fields are delimited by newlines.  This makes
	 * the invoice output more user-friendly to read.
	 */
	public static String prettyAddress(String input) {
		String[] addArr = input.split(",");
		String newAddress;
		for (int i = 0; i < addArr.length; i++) {
			addArr[i] = addArr[i].trim();
		}
		newAddress = String.join("\n", addArr);
		return newAddress;
	}
	// ------------------------------------------------------------------------------------------ /
	
	
	
	// FORMAT TO A PRETTY NUMBER ---------------------------------------------------------------- /
	/**
	 * Converts an input number (of type String) to the following format: xxx xxx xxxx.
	 * This makes the contact numbers more user-friendly to read in the invoice. 
	 * @param num The contact number of either the client or the restaurant.
	 * @return The contact number in the specified xxx xxx xxxx String format.
	 */
	public static String prettyNumber(String num) {
		num = num.replaceAll(" ", "");
		String numNew = num.substring(0, 3) + " " + num.substring(3, 6) + " " + num.substring(6);
		return numNew;
	}
	// ------------------------------------------------------------------------------------------ /
	
	
	
	// CAPITALIZE THE FIRST LETTER OF A GIVEN STRING -------------------------------------------- /
	/**
	 * Capitalizes the first letter of a raw String.
	 * @param raw A string whose first letter is not necessarily capitalized.
	 * @return A string whose first letter is definitely capitalized (for characters of the 
	 * Latin alphabet at least).
	 */
	public static String capFirstLetter(String raw) {
		return raw.substring(0, 1).toUpperCase() + raw.substring(1);
	}
	// ------------------------------------------------------------------------------------------ /
	
	
	
	// GET THE CLOSEST DRIVER WITH THE SMALLEST LOAD -------------------------------------------- /
	/**
	 * Get a driver with the smallest load in the given city if possible (it might be the case
	 * that the customer resides in an area unreachable by Quick Food services).
	 * @param cityToMatch The city we need to filter against in our driver database, 
	 * <code>drivers-info.txt</code>.
	 * @return The name of the driver in <code>cityToMatch</code> with the smallest load.
	 * If there are no drivers in <code>cityToMatch</code>, then return the String "none" to the caller.
	 * @throws IOException Occurs if the <code>drivers-info.txt</code> file is corrupted or cannot be opened.
	 * @throws ArrayIndexOutOfBoundsException Typically occurs when there is a <code>NULL</code> value in a 
	 * row in <code>drivers-info.txt</code> such that the data for a driver cannot be parsed properly.
	 */
	public static String getDriver(String cityToMatch) throws IOException, ArrayIndexOutOfBoundsException {
		// We start out with `currentDriver = "none"`, and `currentLoad = -1`.
		String line = "";
		String delimiter = ",";
		String currentDriver = "none";
		int currentLoad = INITIAL_LOAD;
		
		String driverName, driverCity;
		int driverLoad;
		
		// For logging purposes, we also declare a `lineNumber` that gets incremented with each while iteration.
		int lineNumber = 1;
		
		try (BufferedReader br = new BufferedReader(new FileReader(DRIVERS_FP));) {
			// For each line extract the data from the 3 different fields [driver name, driver city, load],
			// and perform logic checks for each line to see if we can find a driver in `cityToMatch` with the smallest load.
			while ((line = br.readLine()) != null) {
				String[] driverArr = line.split(delimiter);
				// Not each line of data might be correctly formatted, hence the try/catch clause.
				try {
					driverName = driverArr[0].trim();
					driverCity = driverArr[1].trim();
					driverLoad = Integer.parseInt(driverArr[2].trim());
				}
				catch (ArrayIndexOutOfBoundsException e) {
					System.out.println("\nCould not parse line " + lineNumber + " in the 'drivers.txt' file:"
							         + "\n" + e.getMessage() + ".");
					lineNumber++;
					continue;
				}
				
				// Iteratively update the `currentDriver` and `currentRoad` if the new driver's load is less than the current load.
				if (driverCity.trim().equalsIgnoreCase(cityToMatch.trim())) {
					// IF currentLoad is negative, then assign `driverName` and `driverLoad` to `currentDriver` and `currentLoad` as a Start.
					if (currentLoad < 0) {
						currentDriver = driverName;
						currentLoad = driverLoad;
						lineNumber++;
						continue;
					}
					
					if (driverLoad < currentLoad) {
						currentDriver = driverName;
						currentLoad = driverLoad;
					}
				}
				lineNumber++;
			} // END of while loop.
			
		}
		catch (IOException e) {
			System.out.println("\nAn error has occurred while reading the 'drivers-info.txt' file."
					         + "\n" + e.getMessage() + ".");
		}
		// Return the final value of `currentDriver` back to the caller.
		return currentDriver;
	}
	// ------------------------------------------------------------------------------------------ /
	
	
	
	// POPULATE THE INVOICE INFORMATION --------------------------------------------------------- /
	/**
	 * Capture all the details from both the customer <code>(`cObj`)</code> and restaurant 
	 * <code>(`rObj`)</code> objects, and write the contents to the text file <code>"src/data/invoice.txt"</code>.
	 * @param cObj The Customer object.
	 * @param rObj The Restaurant object.
	 * @param chosenDriver The name of the driver assigned to the particular order.  Note that the
	 * city of the driver is the same as that of the customer.
	 * @return A large String value containing all the invoice information to be written to an
	 * external text file, <code>"src/data/invoice.txt"</code>.  The actual writing to <code>invoice.txt</code>
	 * takes place in the <code>main</code> clause.
	 */
	public static String printInvoice(Customer cObj, Restaurant rObj, String chosenDriver) {
		System.out.println("\n\n");
		
		String invoiceTxt = "";
		invoiceTxt  = "Order number: " + cObj.getOrderNumber();
	    invoiceTxt += "\nCustomer: " + capFirstLetter(cObj.getName());
	    invoiceTxt += "\nEmail: " + cObj.getEmail();
	    
	    // Make the `cObj.contactNumber` more reader-friendly.
	    String number = prettyNumber(cObj.getContactNumber());
	    invoiceTxt += "\nPhone Number: " + number;
	    invoiceTxt += "\nLocation: " + capFirstLetter(cObj.getCity()) + "\n";
	    invoiceTxt += "\nYou have ordered the following from " + rObj.getName() + " in " + capFirstLetter(rObj.getCity()) + ":\n";
	    
	    // For each MEAL ordered, extract the corresponding QUANTITY and PRICE, so that all the values align properly.
	    // `qty` is extracted from the `mealAndQty` hashMap, whereas `price` is extracted from the `mealAndPrice` hashMap.
	    Set<String> meals = rObj.getMealAndQuantity().keySet();
	    DecimalFormat df = new DecimalFormat("0.00");
	    df.setMaximumFractionDigits(2);
	    for (String meal: meals) {
	    	float price = rObj.getMealAndPrice().get(meal);
	    	int qty = rObj.getMealAndQuantity().get(meal);
	    	invoiceTxt += "\n" + qty + " x " + capFirstLetter(meal) + " (R" + df.format(price) + ")";
	    }
	    
	    invoiceTxt += "\n\nSpecial instructions: " + rObj.getSpecialInstr() + "\n";
	    invoiceTxt += "\nTotal: R" + df.format(rObj.getTotal()) + "\n";
	    invoiceTxt += "\n" + chosenDriver + " is nearest to the restaurant and so he will be delivering your order to you at:\n";
	    
	    invoiceTxt += "\n" + prettyAddress(cObj.getAddress()) + "\n";
	    
	    number = prettyNumber(rObj.getContactNumber());
	    invoiceTxt += "\nIf you need to contact the restaurant, their number is " + number + ".";
	    
	    // Print out the invoice to the console for the sake of convenience.
		return invoiceTxt;
	}
	// ------------------------------------------------------------------------------------------ /
}