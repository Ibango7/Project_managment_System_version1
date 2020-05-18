import java.time.*; 							// import the time class
import java.time.format.DateTimeFormatter;	   // import DateTimeFormatter
import java.io.File; 						  // Import the File class
import java.io.FileWriter;   				 // Import the FileWriter class
import java.io.IOException; // Import the IOException class to handle errors

/**
 * Project class 
 * Class allows the  instantiation of  project objects.
 * <br>
 * @author Israel Bango
 */
public class Project {
	/**
    *
    * default constructor
    * <br>
    * This is executed when a new object of class Project is instantiated
    * with no arguments passed.
    *
    * @since version 2 (cap_stone 2)
    */
	public Project() {
		project_number = 0;
		project_name = "";
		bulding_type = "";
		address = "";
		erf_number = "";
		project_fee = 0.0;
		amount_paid = 0.0;
		project_date_assigned =  LocalDate.now();
		project_deadline = null;
		isFinalized = false;
		customer = null;
		architect = null;
		contractor = null;
		System.out.println();
	}
	
	/**
	 * Constructor with parameters.<br>
	 * This is executed when a new object of class Project is instantiated with arguments passed
	 * 
	 * @param pNum project number
	 * @param pName project name
	 * @param bType project building type
	 * @param add project address
	 * @param erf project erf number
	 * @param pFee project fee
	 * @param aPaid amount paid by customer to date
	 * @param pDealine project deadline
	 * @since version 1 (cap_stone 1)
	 */
	public Project(int pNum, String pName, String bType, String add, String erf, double pFee, double aPaid ,LocalDate pDealine) {
		// call setter functions
		setNumber(pNum);
		setName(pName);
		setType(bType);
		setAddress(add);
		setErfNumber(erf);
		setFee(pFee);
		setAmountPaid(aPaid);
		setDeadline(pDealine);
		isFinalized = false; // project when created is not finalized		
		project_date_assigned =  LocalDate.now(); // current date is the date the project was created format(yyy-mm-dd)
	}
	
	/**
	 * This method Finalizes a project passed to it and saves the completed 
	 * project to complete project#.txt file.
	 * <br>
	 * Generates an invoice if amount paid is less than full amount
	 * @param projObj The project to be finalized
	 * @since version 1
	 */
	public void finalizeProject(Project projObj) {
		// generate  customer invoice only if amount paid is less than the full amount yet
		String invoice = "";
		
		if(projObj.getAmountPaid() < projObj.getFee()) {
			invoice = "Name: " + projObj.getCustomer().getName() + "\n";
			invoice += "Email: " + projObj.getCustomer().getEmail() + "\n";
			invoice += "Telephone: " + projObj.getCustomer().getPhone() + "\n";
			invoice += "address: " + projObj.getCustomer().getAddress() + "\n";
			invoice += "Due amount: " + (projObj.getFee() - projObj.getAmountPaid()) + "\n";
			
			System.out.println("_________________Customer\'s invoice_____________________\n");
			System.out.println(invoice);
			System.out.println("_________________________________________________________");
		}
		
		projObj.setIsFinalized(true); // mark project as finalized
		
		// create Date object of the day the project was finalized
		LocalDateTime finalDate = LocalDateTime.now();
		// Format will be: weekday, Month, day, yea,r hour, minute, and second
		DateTimeFormatter myDateFormat = DateTimeFormatter.ofPattern("E, MMM dd yyyy HH:mm:ss");
		String formattedDate = finalDate.format(myDateFormat); // store date finalized in a string variable
		
		String content = " "; // this variable will store all information pertaining to the project
		content = "Project number: " + projObj.getNumber() + "\n";
		content += "Is project finalized ?: " + projObj.isFinalized()+ "\n";
		content += "Project name: " + projObj.getName() + "\n";
		content += "Type of building: " + projObj.getType() + "\n";
		content += "The physical address for the project: " + projObj.getAddress() + "\n";
		content += "ERF number: " + projObj.getErfNumber() + "\n";
		content += "The total fee charged for the project: " + projObj.getFee() + "\n";
		content += "The total amount paid to date: " + projObj.getAmountPaid() + "\n";
		content += "Project date assigned: " + projObj.getDate() + "\n";
		content += "Project deadline: " + projObj.getDeadline() + "\n";
		content += "Project date finalized: " + formattedDate + "\n";
		content += "\n\n";
		content += "Customer\'s details:\n";
		content += "Name: " + projObj.getCustomer().getName() + "\n";
		content += "Email: " + projObj.getCustomer().getEmail() + "\n";
		content += "Telephone: " + projObj.getCustomer().getPhone() + "\n";
		content += "address: " + projObj.getCustomer().getAddress() + "\n";
		content += "\n\n";
		content += "Architect\'s details:\n";
		content += "Name: " + projObj.getArchitect().getName() + "\n";
		content += "Email: " + projObj.getArchitect().getEmail() + "\n";
		content += "Telephone: " + projObj.getArchitect().getPhone() + "\n";
		content += "address: " + projObj.getArchitect().getAddress() + "\n";
		content += "\n\n";
		content += "Contractor\'s details:\n";
		content += "Name: " + projObj.getContractor().getName() + "\n";
		content += "Email: " + projObj.getContractor().getEmail() + "\n";
		content += "Telephone: " + projObj.getContractor().getPhone() + "\n";
		content += "address: " + projObj.getContractor().getAddress();
	
		 // store all information about the project to specified text file
		// create and store information to file inside try block so that we are able to 
	   // recover and know if something went wrong during file creating and/or writing
		 try {
			 File oFile = new File("Completed project" + projObj.getNumber() + ".txt"); // specify file
			 
			 if (oFile.createNewFile()) { // if file was successfully created
				 // write to file created above
		         FileWriter myWriter = new FileWriter(oFile); // fileWriter object
		         myWriter.write(content); // write information about the project to file
		         myWriter.close(); // close file after finish writing
		        
		         System.out.println("***Project information saved to: " + oFile.getName() + "***");
		        
		      } else {
		    	  // File already exists, create new file named along with the project number
		    	 // e.g: Complete project2.txt
		    	  
		    	  String newFile =  "Completed project" + projObj.getNumber() + ".txt";
		    	
		    	  File oFile2 = new File(newFile); // specify file
		    	  
		    	  /* write to file created above*/
			      FileWriter myWriter = new FileWriter(oFile2);
			      myWriter.write(content); // write information about the project to file
			      myWriter.close(); // close file after finish writing
			      // let user know of output
			      System.out.println("***Project information saved to: " + oFile2.getName() + "***");
		      }
		    } catch (IOException e) {
		    	System.out.println("**An error occurred**");
		        e.printStackTrace();
		    }	
	}
	
	/**
	 * Method instantiates a new customer object for the project based on the arguments passed.
	 * 
	 * @param name Name of the customer
	 * @param phone Phone of the customer
	 * @param email Email of the customer
	 * @param add address of the customer
	 * @param r	 This refers to the role which is customer in this case
	 * @since version 1
	 */
	public void setCustomer(String name, String phone, String email, String add, String r) {
		customer = new Person(name, phone, email, add, r); // create customer object and assign data
	}
	
	/**
	 * Method instantiates a new architect object for the project based on the arguments passed.
	 * 
	 * @param name Name of the architect
	 * @param phone Phone of the architect
	 * @param email Email of the architect
	 * @param add address of the architect
	 * @param r	 This refers to the role which is architect in this case
	 * @since version 1
	 */
	public void setArchitect(String name, String phone, String email, String add, String r) {
		architect = new Person(name, phone, email, add, r); // create architect object and assign data
	}
	
	/**
	 * Method instantiates a new contractor object for the project based on the arguments passed.
	 * 
	 * @param name Name of the contractor
	 * @param phone Phone of the contractor
	 * @param email Email of the contractor
	 * @param add address of the contractor
	 * @param r	 This refers to the role which is contractor in this case
	 * @since version 1
	 */
	public void setContractor(String name, String phone, String email, String add, String r) {	
		contractor = new Person(name, phone, email, add, r); // create contractor object and assign data
	}
	
	/**
	 * Simple method.<br>
	 * This method receives a project name as an argument and assigns it to the project object
	 * 
	 * @param n Name that will be assigned to the project
	 * @since version 1
	 */
	public void setName(String n) {
		project_name = n;
	}
	
	/**
	 * Simple method.<br>
	 * This method receives a project number as an argument and assigns it to the project object
	 * 
	 * @param n Number that will be assigned to the project
	 * @since version 1
	 */
	public void setNumber(int n) {
		project_number = n;
	}
	
	/**
	 * Simple method.<br>
	 * This method receives a building type as an argument and assigns it to the project object
	 * 
	 * @param t Building type of the project
	 * @since version 1
	 */
	public void setType(String t) {
		bulding_type = t;
	}
	
	/**
	 * Simple method.<br>
	 * This method receives the address of the project as an argument and assigns it to the project object
	 * 
	 * @param a Address of the project
	 * @since version 1
	 */
	public void setAddress(String a) {
		address = a;
	}
	
	/**
	 * Simple method.<br>
	 * Receives an erf number as an argument and assigns it to the project object
	 * 
	 * @param n Erf number of the project
	 * @since version 1
	 */
	public void setErfNumber(String n) {
		erf_number = n;
	}
	
	/**
	 * Simple method.<br>
	 * Method receives a fee amount and assigns it to the fee of the project
	 * 
	 * @param f Project fee
	 * @since version 1
	 */
	public void setFee(double f) {
		project_fee = f;
	}
	
	/**
	 * Simple method.<br>
	 * Method receives an amount as an argument and assigns it  to the amount the customer has
	 * paid to date for the project
	 * 
	 * @param a Amount paid by customer
	 * @since version 1
	 */
	public void setAmountPaid(double a) {
		amount_paid = a;
	}
	
	/**
	 * Simple method.<br>
	 * Method receives a date as an argument and assigns it to deadline of the project object
	 * 
	 * @param d Deadline date of project
	 * @since version 1
	 */
	public void setDeadline(LocalDate d) {
		project_deadline = d;
	}
	
	/**
	 * Simple method.<br>
	 * This method receives a date as an argument and gives the project object the date it was assigned.
	 * 
	 * @param d Date the project was assigned
	 * @since version 1
	 */
	public void setDate(LocalDate d) {
		project_date_assigned = d;
	}
	
	/**
	 * 
	 * @param s boolean value for project status:
	 * 			true for complete, false for incomplete
	 */
	public void setIsFinalized(boolean s) {
		isFinalized = s;
	}
	
	/**
	 * Simple method.<br>
	 * Method returns the name of the project
	 * 
	 * @return Project name
	 * @since version 1
	 */
	public String getName() {
		return project_name;
	}
	
	/**
	 * Simple method.<br>
	 * Method returns the number of the project  
	 * 
	 * @return Project number
	 * @since version 1
	 */
	public int getNumber() {
		return project_number;
	}
	
	/**
	 * Simple method.<br>
	 * Method returns the building type
	 * 
	 * @return project building type 
	 * @since version 1
	 */
	public String getType() {
		return bulding_type;
	}
	
	/**
	 * Simple method.<br>
	 * Method returns the project address
	 * 
	 * @return Address of the project
	 * @since version 1
	 */
	public String getAddress() {
		return address;
	}
	
	/**
	 * Simple method.<br>
	 * Method returns project erf number
	 * 
	 * @return erf number of the project
	 * @since version 1
	 */
	public String getErfNumber() {
		return erf_number;
	}
	
	/**
	 * Simple method.<br>
	 * Method returns the project cost
	 * 
	 * @return Project fee
	 * @since version 1
	 */
	public double getFee() {
		return project_fee;
	}
	
	/**
	 * Simple method.<br>
	 * Method returns the amount paid to date by the customer
	 * 
	 * @return amount the customer has paid to date
	 * @since version 1
	 */
	public double getAmountPaid() {
		return amount_paid;
	}
	
	/**
	 * Simple method.<br>
	 * Method returns the project deadline
	 * 
	 * @return deadline date of the project
	 * @since version 1
	 */
	public LocalDate getDeadline() {
		return project_deadline;
	}
	
	/**
	 * Simple method.<br>
	 * Method returns the date when the project was assigned
	 * 
	 * @return date the project was assigned
	 * @since version 1
	 */
	public LocalDate getDate(){
		return project_date_assigned;
	}
	
	/**
	 * Simple method.<br>
	 * Method returns completion status of the project object:
	 * true for complete and false for incomplete
	 * 
	 * @return boolean value of project completion status
	 * @since version 1
	 */
	public boolean isFinalized() {
		return isFinalized;
	}
	
	/**
	 * Simple method.<br>
	 * Method returns customer object of the project object
	 * 
	 * @return customer object
	 * @since version 1
	 */
	public Person getCustomer() {
		return customer;
	}
	
	/**
	 * Simple method.<br>
	 * Method returns architect object of the project object
	 * 
	 * @return architect object
	 * @since version 1
	 */
	public Person getArchitect() {
		return architect;
	}
	
	/**
	 * Simple method.<br>
	 * Method returns contractor object of the project object
	 * 
	 * @return contractor object
	 * @since version 1
	 */
	public Person getContractor() {
		return contractor;
	}
	
	/**
	 * Project class attributes
	 */
	private int project_number;
	private String project_name;
	private String bulding_type;
	private	String address;
	private	String erf_number;
	private	double project_fee;
	private	double amount_paid;
	private LocalDate project_date_assigned;
	private	LocalDate project_deadline;
	//private	LocalDate dateFinalized;
	private boolean isFinalized;
	private Person customer;
	private Person architect;
	private Person contractor;
	
}