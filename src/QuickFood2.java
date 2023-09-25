import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.text.DecimalFormat;

public class QuickFood2 {
	// Declare some constants.
	final static String DRIVERS_FP = "src/drivers.txt";
	final static String INVOICE_FP = "src/invoice.txt";
	final static String DEFAULT_MSG = "Sorry! Our drivers are too far away from you to be able to deliver to your location.";
	final static int INITIAL_LOAD = -1;
	
	
	/* MAIN -------------------------------------------------------------------------------------*/
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
		System.out.println("\n\n/* ------------------------------------------------------------------------------------------*/");
	    System.out.println(inv);
		
		// Write the contents of `inv` to "src/data/invoice.txt".
		File f = new File(INVOICE_FP);
		f.createNewFile();
		
		try (FileWriter myWriter = new FileWriter(INVOICE_FP);) {
			myWriter.write(inv);
			System.out.println("\n\n/* ------------------------------------------------------------------------------------------*/"
					         + "\nPlease checkout '" + INVOICE_FP + "'.");
		}
		catch (IOException e) {
			System.out.println("An error occurred while trying to write to " + INVOICE_FP + "."
					         + "\n" + e.getMessage());
		}
	}
	/* END OF MAIN ------------------------------------------------------------------------------*/
	
	
	
	/* FORMAT TO A PRETTY ADDRESS ---------------------------------------------------------------*/
	// Take an input address in which the fields are separated by commas, and convert it
	//     into a string in which the fields are separated by newlines `\n`.
	public static String prettyAddress(String input) {
		String[] addArr = input.split(",");
		String newAddress;
		for (int i = 0; i < addArr.length; i++) {
			addArr[i] = addArr[i].trim();
		}
		newAddress = String.join("\n", addArr);
		return newAddress;
	}
	/* ------------------------------------------------------------------------------------------*/
	
	
	
	/* FORMAT TO A PRETTY NUMBER ----------------------------------------------------------------*/
	// Conver the input number to the following format: xxx xxx xxxx
	public static String prettyNumber(String num) {
		num = num.replaceAll(" ", "");
		String numNew = num.substring(0, 3) + " " + num.substring(3, 6) + " " + num.substring(6);
		return numNew;
	}
	/* ------------------------------------------------------------------------------------------*/
	
	
	
	/* CAPITALIZE THE FIRST LETTER OF A GIVEN STRING --------------------------------------------*/
	public static String capFirstLetter(String raw) {
		return raw.substring(0, 1).toUpperCase() + raw.substring(1);
	}
	/* ------------------------------------------------------------------------------------------*/
	
	
	
	/* GET THE CLOSEST DRIVER WITH THE SMALLEST LOAD --------------------------------------------*/
	// Based on the argument `cityToMatch`, get a driver in that city (if possible) with the smallest load.
	// If there are no drivers in `cityToMatch`, return "none".
	public static String getDriver(String cityToMatch) {
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
			System.out.println("\nAn error has occurred while reading the 'drivers.txt' file."
					         + "\n" + e.getMessage() + ".");
		}
		// Return the final value of `currentDriver` back to the caller.
		return currentDriver;
	}
	/* ------------------------------------------------------------------------------------------*/
	
	
	
	/* POPULATE THE INVOICE INFORMATION ---------------------------------------------------------*/
	// Capture all the details from both the customer (`cObj`) and the restaurant (`rObj`),
	//     and write the output to the text-file "src/data/invoice.txt".
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
	/* ------------------------------------------------------------------------------------------*/
}
