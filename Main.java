import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException; // Import the IOException class to handle errors
import java.io.FileWriter; // Import the FileWriter class
import java.time.LocalDate; // Import the LocalDateTime class
import java.time.format.DateTimeFormatter; // Import the DateTimeFormatter class

/**
 * Main class
 * This class is the entry point to the application
 * 
 * @author Israel Bango
 */
public class Main {
	static int projNumber = 0; // this static variable will keep track of project number
	static Scanner userInput = new Scanner(System.in); // Scanner object to read user inputs throughout the program

	/**
	 * <h3>Main method</h3> Entry point of program <br>
	 * Allows the user to choose options from a menu and calls respective methods
	 * 
	 * @param args It stores Java command line arguments 
	 * @since version 1(cap_stone 1)
	 */
	/* main method */
	public static void main(String[] args) {
		ArrayList<Project> projectList = new ArrayList<Project>(); // create a List array of projects
		System.out.print("=====================================================================================\n");
		System.out.print("==================== Structural engineering firm \" Poised\" ========================\n");
		System.out.println("===================================================================================\n");

		while (true) {
			System.out.print("\n\n==============================Main menu======================================\n");
			System.out.print("\nPlease choose an option from the menu bellow\n");
			System.out.print("\n1 - Create new Project\n" + "2 - Update existing projects\n"
					+ "3 - Get projects from the database\n" + "4 - Finalize existing projects\n"
					+ "5 - View pending projects\n" + "6 - View overdue projects \n"
					+ "7 - Find project by number or name\n" + "8 - Exit program\n");

			String option = userInput.nextLine(); // read input entered by user
			switch (option) {
			case "1":
				projectList.add(new Project()); // Add project to list of projects and send it to createNew() function
				createNew(projectList);
				break;

			case "2":
				updateProject(projectList);
				break;

			case "3":
				System.out.print("=======================EXISTING PROJECTS==============================\n");
				getExistingProjects(projectList);
				break;

			case "4":
				finalizeMenu(projectList);
				break;

			case "5":
				pendingProject(projectList);
				break;

			case "6":
				overDueProject(projectList);
				break;
			case "7":
				// find project by number or name
				findProject(projectList);
				break;
			case "8":
				if (projectList.isEmpty()) {
					/* there's nothing to do */
				} else {
					// save to file
					callSaveToFile(projectList);
				}
				System.out.print("Exiting program... bye! \n");
				System.exit(0);

			default:
				System.out.print("Invalid selection. Try again\n");
				break;

			}

		}

	}

	/**
	 * Method converts user date input to LocalDate object if it cannot convert the
	 * user input it throws an exception that is caught in the calling function
	 * 
	 * @param strDate The date format in string type that will be converted to
	 *                LocalDate object
	 * @return date object
	 * @since version 1
	 * 
	 */
	static LocalDate getDateFormat(String strDate) {
		LocalDate dateObj = null;
		DateTimeFormatter myFormatObj = null;
		myFormatObj = DateTimeFormatter.ofPattern("dd MMMM yyyy"); // format how date object will be
		dateObj = LocalDate.parse(strDate, myFormatObj); // convert string entered by user to LocalDate object
		return dateObj; // return object to calling function
	}

	/**
	 * Method saves Person information related to the project ask user for person
	 * details and save it to the project
	 * 
	 * @param projObj The project whose person we want to save information
	 * @param whoIs   who the person is. whether a customer, contractor or architect
	 * @since version 1
	 */
	static void insertPersonInfo(Project projObj, String whoIs) {
		String name = "";
		String email = "";
		String phone = "";
		String add = "";

		// ask for user input
		System.out.print("Enter name: ");
		name = userInput.nextLine();
		while (true) {
			try {
				System.out.print("Enter email: ");
				email = userInput.nextLine();

				// validate email address
				// regular expression bellow for email validation
				String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." 
						+ "[a-zA-Z0-9_+&*-]+)*@" 
						+ "(?:[a-zA-Z0-9-]+\\.)+[a-z"
						+ "A-Z]{2,7}$";

				if (email.matches(emailRegex)) { // if email is valid
					break;
				} else { // email is not valid
					throw new Exception();
				}
			} catch (Exception e) {
				System.out.println("*Invalid email*");
				continue;
			}

		}

		// Makes sure the user enters valid phone number format
		while (true) {
			try {
				System.out.print("Enter Telephone: ");
				phone = userInput.nextLine();
				// validate phone number
				// 1) Begins with 0 or +27
				// 2) then contains 9 digits
				if (phone.matches("[0/+27]?[0-9]+") && (phone.length() >= 9 && phone.length() <= 12)) {
					// if phone matches regular expression
					// This is a valid number
					break;
				} else { // invalid number
					throw new Exception();
				}
			} catch (Exception e) {
				System.out.println("*Invalid phone number*");
				continue;
			}
		}

		System.out.print("Enter address:\n");
		add = userInput.nextLine();

		// save information based on role of person in project
		if (whoIs == "customer") {
			// add customer information to customer object
			projObj.setCustomer(name, phone, email, add, whoIs);

		} else if (whoIs == "architect") {
			// add architect information to architect object
			projObj.setArchitect(name, phone, email, add, whoIs);

		} else if (whoIs == "contractor") {
			// add contractor information to contractor object
			projObj.setContractor(name, phone, email, add, whoIs);
		}
	}

	/**
	 * Method creates a new project. <br>
	 * Asks user to enter project details and save them to project created in
	 * projectList
	 * 
	 * @param projectList The list of projects currently available
	 * @since version 1
	 */
	static void createNew(ArrayList<Project> projectList) {
		// get current index of project passed from list of projects to fill in the
		// details
		int currIndex;
		for (currIndex = 0; currIndex < projectList.size(); currIndex++) {
			// currIndex is the index
			// projectList.get(currIndex) is the project we want
		}

		currIndex = currIndex - 1; // fix out of bound index(when currSize is out of loop above it has 1 more than
									// currently available subscripts)

		System.out.print("\n=======Create New Project======= \n\n");

		// ===== ask for project information=========
		System.out.print("Enter project name: \n");
		String projName = userInput.nextLine(); // read user input
		projectList.get(currIndex).setName(projName); // save input to project

		System.out.println("The number of this project is " + (++projNumber));
		projectList.get(currIndex).setNumber(projNumber); // save project number to project and post increment

		System.out.print("Enter project address:\n");
		String projAdd = userInput.nextLine(); // read user input
		projectList.get(currIndex).setAddress(projAdd); // save input to project

		System.out.print("Enter project erf number:\n");
		String erf = userInput.nextLine(); // read user input
		projectList.get(currIndex).setErfNumber(erf); // save input to project

		System.out.print("Enter project type(eg:house, apartment etc...):\n");
		String buildType = userInput.nextLine();
		projectList.get(currIndex).setType(buildType);

		// try block bellow makes sure user enter number for amount being charged
		while (true) {
			try {
				System.out.print("What is the amount being charged for this  project ?:\n");
				String projPrice = userInput.nextLine();
				projectList.get(currIndex).setFee(Double.parseDouble(projPrice));
				break;
			} catch (Exception e) {
				System.out.println("*make sure you enter a monetary value*");
				continue;
			}
		}

		// call method bellow that makes sure the user enters the correct date format
		// Method receives the location of the project in the list we want to enter due
		// date
		enterProjectDueDate(projectList, currIndex);

		// ==== ask for customer's information===
		System.out.print("________________________________________________\n");
		System.out.print("Enter Customer\'s details\n\n");

		insertPersonInfo(projectList.get(currIndex), "customer"); // save customer information to project

		// This code bellow checks whether project name was captured or not
		// if not; save project name with building type name plus the surname of
		// customer
		projName = projName.replaceAll(" ", ""); // remove any white space from the if statement bellow to ignore white
												// spaces

		if (projName.isEmpty()) {
			String str = projectList.get(currIndex).getCustomer().getName(); // get name of customer
			String[] arrOfStr = str.split(" ", 0); // split user name in order to get surname(last name)

			// save project name with building type and surname of customer
			String newName = projectList.get(currIndex).getType() + " " + arrOfStr[arrOfStr.length - 1];
			projectList.get(currIndex).setName(newName);
		}

		// ==== ask for architect's information====
		System.out.print("________________________________________________\n");
		System.out.print("Enter Architect\'s details\n\n");
		insertPersonInfo(projectList.get(currIndex), "architect"); // save architect information to project

		// ==== ask for contractor's information====
		System.out.print("________________________________________________\n");
		System.out.print("Enter Contractor\'s details\n\n");
		insertPersonInfo(projectList.get(currIndex), "contractor"); // save contractor information to project
		System.out.print("****Project details have been successufully captured!****\n");
	}

	/**
	 * Method asks user to enter project due date. The due date is entered in String
	 * format and the getDateFormat method is called that them converts the user
	 * input to LocalDate Object. <br>
	 * If input date cannot be converted something went wrong with the format; let
	 * the user now
	 * 
	 * @param projectList The list of projects currently available
	 * @param currIndex   The index of the project wanted to change due date
	 * @since version 1
	 */
	public static void enterProjectDueDate(ArrayList<Project> projectList, int currIndex) {
		while (true) {
			try {
				// Call getDateFormat function to convert user input to LocalDate and save
				// deadline to project
				System.out.print("Enter the project due date(format: day month year; eg: 09 November 2020):\n");
				String dateStr = userInput.nextLine(); // read user input
				projectList.get(currIndex).setDeadline(getDateFormat(dateStr));
				break; // if it gets here all is well. Come out of the loop
			} catch (Exception e) {
				// if something went wrong ask user for date again
				System.out.print(
						"*Make sure date format is correct \nFormat: day month year \nMake use of two digits for the day eg: 01\n"
								+ "and first letter of month must be captalized eg: January*\n\n");
				continue;
			}
		}
	}

	/**
	 * Method updates some features of existing projects based on user's choice
	 * namely:
	 * <br>
	 * <ul>
	 * <li>Project due date</li>
	 * <li>Amount paid to date</li>
	 * <li>Contractor's details</li>
	 * </ul>
	 * <br>
	 * if there no projects available the user will not be able to continue if the
	 * project number is not valid, it will let the user know
	 * 
	 * @param projectList The list of projects currently available
	 * @since version 1
	 */
	static void updateProject(ArrayList<Project> projectList) {
		System.out.print("=========================Update existing Project=================================\n\n");

		while (true) {
			System.out.print("Note that there are currently " + projNumber + " project(s) registered.\n"
					+ "Project numbers start from 1\n");

			if (projNumber == 0) { // there are 0 projects do not allow user to continue
				return;
			}

			// ask user for project number that he/she wants to edit
			System.out.print("\nEnter the project number you want to update: ");
			int index = 0;
			try {
				String i = userInput.nextLine();
				index = Integer.parseInt(i); // convert user input to integer
			} catch (Exception e) {
				System.out.print("**wrong input format!**");
				return;
			}

			// code bellow checks if project number is valid(index not out of bounds)
			index = index - 1; // subscript start at 0
			if (index >= 0 && index < projNumber
					&& projectList.isEmpty() == false && projectList.get(index) != null) {

				// Show user options available to update
				System.out.print("\nChoose an option from the menu bellow\n");
				System.out
						.print("\n1 - Change project due date\n" + "2 - Change total amount of the fee paid to date \n"
								+ "3 - Update Contractor\'s contact details\n" + "4 - Go back to main menu\n");

				String opt = userInput.nextLine();
				switch (opt) {
				case "1":
					System.out.print("\n=========== Change project due date===========\n\n");
					enterProjectDueDate(projectList, index);
					System.out.print("***Due date updated successfully!***\n\n");
					return; // go back to main menu

				case "2":
					System.out.print("\n==========Change total amount of the fee paid to date ===========\n\n");

					// try block bellow makes sure user enters monetary value
					while (true) {
						try {
							System.out.print("Enter total amount of the fee paid to date for project " + (index + 1) + " :");
							String amountStr = userInput.nextLine();
							double amountPaid = Double.parseDouble(amountStr);
							projectList.get(index).setAmountPaid(amountPaid); // save amount paid to date to project
							System.out.print("***total amount of the fee paid to date updated successfully!***\n\n");
							break;
						} catch (Exception e) {
							System.out.println("*make sure you enter a monetary value*");
							continue;
						}
					}
					return; // go back to main menu;

				case "3":
					System.out.print("\n=============Update Contractor\'s contact details======================\n\n");
					System.out.print("Contractor\'s details for project " + (index + 1) + ":\n");
					insertPersonInfo(projectList.get(index), "contractor");
					System.out.print("***Contractor\'s details updated successfully!***\n\n");
					return;

				case "4":
					return; // go back to main menu

				default:

					System.out.print("Invalid menu Selection\n\n");
					break;
				}

			} else {

				System.out.print("You have entered and an invalid project number!\n");
				return;
			}
		}

	}

	/**
	 * Method shows pending projects to the user.<br>
	 * A pending project is a project that has not been completed yet.
	 * if the project is not finalized it is pending.
	 * isFinalize() returns true or false
	 * 
	 * @param projectList The list of projects currently available that will be
	 *                    traversed to check for pending projects
	 * 
	 * @since version 3
	 */
	static void pendingProject(ArrayList<Project> projectList) {
		System.out.print("====================Pending Projects=========================== \n\n");

		if (projectList.isEmpty()) {
			System.out.print("No projects available\n");
			System.out.print("OR make sure you have retrieved projects from the database (Option 3)");
		} else {
			// Display projects that have not been finalized
			for (Project obj : projectList) {
				if (!obj.isFinalized()) {
					displayExistingProjects(obj);
				}
			}
		}
		goBacktoMenu();
	}

	/**
	 * Display overdue projects currently present in the list of projects.<br>
	 * An overdue project is a project that is not finalized and its deadline is passed
	 * 
	 * @param projectList The list of projects currently available that will be
	 *                    traversed <br>
	 *                    to check overdue projects
	 * @since version 3
	 */
	static void overDueProject(ArrayList<Project> projectList) {
		System.out.print("====================Overdue projects ========================= \n\n");

		if (projectList.isEmpty()) {
			System.out.print("No projects available\n");
			System.out.print("Or make sure you get projects from the database (Option 3)");
		} else {
			// Display projects that are overdue
			// a project is overdue if it is not finalized and the deadline is passed
			// code compare dates if current date is bigger than deadline and finalized is
			// false, it is an overdue project
			LocalDate dateNow = LocalDate.now(); // current date is the date the project was created format(yyy-mm-dd)
			for (Project obj : projectList) {
				if (!obj.isFinalized() && dateNow.isAfter(obj.getDeadline())) {
					displayExistingProjects(obj);
				}
			}
		}

	}

	/**
	 * Method finds projects by name or number based on user input. <br>
	 * If number or name input by the user is is not found, it lets the user know.
	 * 
	 * @param projectList The list of projects currently available that will be
	 *                    traversed to find project
	 * @since version 3
	 */
	static void findProject(ArrayList<Project> projectList) {
		System.out.print("====================Find projects========================= \n\n");

		// System.out.print("Search by:\n" + "1 - Project number\n" + "2 - Project
		// name\n");

		// String option = userInput.nextLine();
		System.out.print("Enter project number or name to find it:\n");
		String input = userInput.nextLine();

		// code checks if user input is a number or not
		boolean isNumber = false;
		for (int i = 0; i < input.length(); i++) {
			if (!Character.isDigit(input.charAt(i))) {
				// not a number
				isNumber = false;
				break;

			} else {
				// a number
				isNumber = true;
			}
		}

		// if user input is a number or name search for project accordingly
		boolean foundSomething = false; // This flag will notify user when something went wrong
		try {
			for (Project obj : projectList) {
				if ((isNumber && (obj.getNumber() == Integer.parseInt(input)))
						|| (!isNumber && obj.getName().equals(input))) {

					displayExistingProjects(obj);
					foundSomething = true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();

		}
		// notify user in case something went wrong
		if (foundSomething) {
			// pass
		} else {
			System.out.print("Search result is empty!\n");
		}

		goBacktoMenu(); // go back to main menu
	}

	/**
	 * Method calls finalizeProject() to finalize a given project.<br> 
	 * It asks for the project number the user wants to finalize and finalizes it.
	 * Once the project has been finalized it saves the project to a specified txt file(complete
	 * project#.txt)
	 * 
	 * @param projectList The list of projects currently available that will be
	 *                    traversed to find project that is to be finalized
	 * @since version 1
	 */
	static void finalizeMenu(ArrayList<Project> projectList) {
		System.out.print("========================Finalize project==========================\n\n");
		System.out.print("Note that there are currently " + projNumber + " project(s) registered.\n"
				+ "Project numbers start from 1\n");

		if (projNumber == 0) { // there are 0 projects do not allow user to continue
			return;
		}

		// ask user for project number that he/she wants to finalize
		System.out.print("\nEnter the project number you want to finalize: ");
		int index = 0;
		try {
			String i = userInput.nextLine();
			index = Integer.parseInt(i); // convert user input to integer
		} catch (Exception e) {
			System.out.print("**wrong input format!**");
			return;
		}

		// code bellow checks if project number is valid(index not out of bounds)
		index = index - 1; // subscript start at 0
		if (index >= 0 && index < projNumber 
				&& projectList.isEmpty() == false && projectList.get(index) != null) {

			System.out.print("\n\n"); // newlines

			// this calls method finalizeProject from Project class
			projectList.get(index).finalizeProject(projectList.get(index));

		} else {
			System.out.print("Make sure you enter a valid project number!\n");
		}

	}

	/**
	 * This method gets projects from txt file and instantiates them to ProjetList.<br>
	 * Projects are read from a file and then instantiated to Project Objects
	 * 
	 * @param projectList Projects read from the file and then instantiated to
	 *                    Project Objects will be added to the <b>projectList</b>
	 * @since version 3
	 */
	static void getExistingProjects(ArrayList<Project> projectList) {
		ArrayList<String> list = new ArrayList<String>(); // list will store projects from txt file
		File inputFile = null;
		try {
			inputFile = new File("existing_projects.txt"); // specify file
			Scanner fileReader = new Scanner(inputFile); // Scanner object
			int count = 1;
			while (fileReader.hasNext()) {
				String line = fileReader.nextLine();
				String[] str = line.split(", ", 0); // line read from file split at comma(", ") and stored to str array

				// add line to list ArrayList
				for (String i : str)
					list.add(i);

				// each project detail in existing_project.txt file occupies 4 lines
				// after every 4 lines enter the if condition bellow as all details of a project
				// have been captured to ArrayList
				if (count % 4 == 0) {
					Project proj = new Project(); // create project object that will be stored in projectList
					proj.setName(list.get(0));
					boolean finalized = Boolean.parseBoolean(list.get(1));
					proj.setIsFinalized(finalized);
					proj.setType(list.get(2));
					proj.setAddress(list.get(3));
					proj.setErfNumber(list.get(4));
					proj.setFee(Double.parseDouble(list.get(5)));
					proj.setAmountPaid(Double.parseDouble(list.get(6)));

					// convert date string to data object
					LocalDate dateObj = null;
					DateTimeFormatter myFormatObj = null;
					myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // format how date object will be
					dateObj = LocalDate.parse(list.get(7), myFormatObj);
					proj.setDate(dateObj); // project date assigned
					dateObj = LocalDate.parse(list.get(8), myFormatObj);
					proj.setDeadline(dateObj); // project deadline

					proj.setCustomer(list.get(9), list.get(10), list.get(11), list.get(12), "customer");
					proj.setArchitect(list.get(13), list.get(14), list.get(15), list.get(16), "architect");
					proj.setContractor(list.get(17), list.get(18), list.get(19), list.get(20), "contractor");

					projectList.add(proj); // add project to the list of projects
					proj.setNumber(++projNumber); // increment number of projects
					list.clear(); // list is cleared for next new project
				}
				count++;
			}
			// traverse through projecList
			for(Project obj: projectList) {
				displayExistingProjects(obj); // display projects to the user
			}
			fileReader.close();
		} catch (FileNotFoundException e) {

			System.out.print("File not found");
		} catch (Exception e) {
			System.out.println("Error in " + inputFile.getName());
			System.out.println(
					"make sure the values in the file are comma separated plus a white space such as -> (\", \")");
		}

		goBacktoMenu();
	}

	/**
	 * This method allows user to return to main menu by pressing the <b>enter</b> key
	 * 
	 * @since version 3
	 */
	static void goBacktoMenu() {
		System.out.print("\n*** Press enter to go back to main menu ***\n");
		try {
			System.in.read();
		} catch (Exception e) {
		}
	}

	/**
	 * This method displays project details to the user.
	 * Project details are shown in a comprehensive manner
	 * 
	 * @param proj project to be displayed to user is an object of Project class
	 * @since version 3
	 */
	public static void displayExistingProjects(Project proj) {
		// print projects
		String content = " "; // this variable will store all information pertaining to the project
		content = "**************Project number: " + proj.getNumber() + " ************************\n\n";
		content += "Is project finalized ?: " + proj.isFinalized() + "\n";
		content += "Project name: " + proj.getName() + "\n";
		content += "Type of building: " + proj.getType() + "\n";
		content += "The physical address for the project: " + proj.getAddress() + "\n";
		content += "ERF number: " + proj.getErfNumber() + "\n";
		content += "The total fee charged for the project: " + proj.getFee() + "\n";
		content += "The total amount paid to date: " + proj.getAmountPaid() + "\n";

		/* format how date assigned and date deadline will be shown to user */
		DateTimeFormatter myFormatObj; 
		myFormatObj = DateTimeFormatter.ofPattern("dd MMMM yyyy");
		String formattedDate = proj.getDate().format(myFormatObj);
		content += "Project date assigned: " + formattedDate + "\n";
		formattedDate = proj.getDeadline().format(myFormatObj);
		content += "Project deadline: " + formattedDate + "\n";
		content += "\n";
		content += "Customer\'s details:\n";
		content += "Name: " + proj.getCustomer().getName() + "\n";
		content += "Email: " + proj.getCustomer().getEmail() + "\n";
		content += "Telephone: " + proj.getCustomer().getPhone() + "\n";
		content += "address: " + proj.getCustomer().getAddress() + "\n";
		content += "\n";
		content += "Architect\'s details:\n";
		content += "Name: " + proj.getArchitect().getName() + "\n";
		content += "Email: " + proj.getArchitect().getEmail() + "\n";
		content += "Telephone: " + proj.getArchitect().getPhone() + "\n";
		content += "address: " + proj.getArchitect().getAddress() + "\n";
		content += "\n";
		content += "Contractor\'s details:\n";
		content += "Name: " + proj.getContractor().getName() + "\n";
		content += "Email: " + proj.getContractor().getEmail() + "\n";
		content += "Telephone: " + proj.getContractor().getPhone() + "\n";
		content += "address: " + proj.getContractor().getAddress() + "\n\n";
		System.out.print(content); // display projects
	}

	/**
	 * This method creates the file that will store project details and calls
	 * saveTofile method to save details to the file created. 
	 * The file name is existing_projects.txt
	 * 
	 * @param projectList The list of projects currently available that will be
	 *                    saved to txt file
	 * @since version 3
	 */
	public static void callSaveToFile(ArrayList<Project> projectList) {
		// save all alterations made to projects to existing_projects.txt
		File inputFile = null;
		try {
			inputFile = new File("existing_projects.txt"); // specify file
			if (inputFile.createNewFile()) {
				// file is created for the first time
				// update details about the projects to this text file
				saveToFile(projectList);

			} else {
				// file already Exists update accordingly
				saveToFile(projectList);
			}
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}

		System.out.print("Changes to projects saved to " + inputFile.getName());
		System.exit(0); // terminate program
	}

	/**
	 * This method save changes made to projects to specified
	 * file(existing_projects.txt)
	 * 
	 * @param projectList The list of projects that will be saved to file
	 * @throws IOException // File exception object
	 * @since version 3
	 */
	static void saveToFile(ArrayList<Project> projectList) throws IOException {
		// save changes to file
		FileWriter fileWriter = new FileWriter("existing_projects.txt");
		String content = "";

		for (Project obj : projectList) {
			content = obj.getName() + ", ";
			content += obj.isFinalized() + ", ";
			content += obj.getType() + ", ";
			content += obj.getAddress() + ", ";
			content += obj.getErfNumber() + ", ";
			content += obj.getFee() + ", ";
			content += obj.getAmountPaid() + ", ";
			content += obj.getDate() + ", ";
			content += obj.getDeadline() + "\n";
			content += obj.getCustomer().getName() + ", ";
			content += obj.getCustomer().getEmail() + ", ";
			content += obj.getCustomer().getPhone() + ", ";
			content += obj.getCustomer().getAddress() + "\n";
			content += obj.getArchitect().getName() + ", ";
			content += obj.getArchitect().getEmail() + ", ";
			content += obj.getArchitect().getPhone() + ", ";
			content += obj.getArchitect().getAddress() + "\n";
			content += obj.getContractor().getName() + ", ";
			content += obj.getContractor().getEmail() + ", ";
			content += obj.getContractor().getPhone() + ", ";
			content += obj.getContractor().getAddress() + "\n";
			fileWriter.write(content);
		}

		fileWriter.close();
	}

}