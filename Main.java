import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	
	public static FileLoader fl = new FileLoader();
	public static Scanner keyboardWord = new Scanner(System.in);

	public static void mains(String[] args) {
		FileLoader fl = new FileLoader();
		MasterAccount ms = new MasterAccount(1, "Daniel", "test", new ArrayList<Account>());
		ms.accounts.add(new Account(1000, "Savings"));
		ms.accounts.add(new Account(2000, "Chequing"));
		fl.addToArray(ms);
		ms = new MasterAccount(2, "Harrison", "test2", new ArrayList<Account>());
		ms.accounts.add(new Account(1500, "Savings"));
		ms.accounts.add(new Account(2500, "Chequing"));
		fl.addToArray(ms);
		fl.fileWriter(fl.accountArray);
	}

	public static void main(String[] args) {
		ConsolePrinting.title();
		System.out.println(
				"Welcome to DHP Bank\nWould you like to:\n1. Enter account\n2. Create account\n3. Delete account\n4. Shut down");
		int mainChoice = getInt(4);
		if (mainChoice == 1) {
			enterAccount();
		} else if (mainChoice == 2) {
			createAccount();
		} else if (mainChoice == 3) {
			deleteAccount();
		}
		else if (mainChoice == 4){
			System.out.println("Thank you for choosing DHP Bank.");
			System.exit(0);
		}
	}
	
	public static boolean passwordCheck(String password, MasterAccount ms) {
		boolean state = password.equals(ms.getPass());
		if (!state) {
			System.out.println("Password is incorrect, please re-enter.");
		}
		return state;
	}

	public static void createAccount() {
		System.out.println("What would you like to name the account?");
		String accName = keyboardWord.nextLine();
		System.out.println("What would you like the pasword to be?");
		String accPass = keyboardWord.nextLine();
		int newId = fl.addMasterAccount(new MasterAccount(0,accName,accPass,new ArrayList<Account>()));
		System.out.println("Account created with:\nName: " + accName + "\nPassword: " + accPass + "\nID: " + newId);
		main(null);
	}
	
	public static void deleteAccount(){
		System.out.println("What is the id of the account you want to delete?");
		int idChosen = getInt();
		while(fl.getMasterAccount(idChosen) == null){
			System.out.println("Please enter a valid id");
			idChosen = getInt();
		}
		MasterAccount ms = fl.getMasterAccount(idChosen);
		System.out.println("What is the password to the account?");
		String password = keyboardWord.nextLine();
		while (!passwordCheck(password, ms)) {
			password = keyboardWord.nextLine();
		}
		fl.deleteMasterAccount(fl.getMasterAccount(idChosen));
		System.out.println("Account deleted");
		main(null);
	}
	
	public static void enterAccount(){
		System.out.println("What is the account id?");
		int id = getInt();
		int attempts = 5;
		while (fl.getMasterAccount(id) == null){
			System.out.println("Please enter a valid id");
			id = getInt();
		}
		MasterAccount ms = fl.getMasterAccount(id);
		System.out.println("What is the password to the account?");
		String password = keyboardWord.nextLine();
		while (!passwordCheck(password, ms) && attempts > 0) {
			attempts--;
			password = keyboardWord.nextLine();
		}
		if(attempts == 0){
			System.out.println("Too many incorrect passwords, returning to main menu.");
			main(null);
		}else{
			accountMenu(ms);
		}
		
	}
	
	public static void accountMenu(MasterAccount ms){
		System.out.println("Would you like to:\n1. Edit account(s)\n2. Add sub-account\n3. Delete sub-account\n4. Exit");
		int numTyped = getInt(4);
		if (numTyped == 1) {
			editSub(ms);
		} else if (numTyped == 2) {
			addSub(ms);
		}
		else if (numTyped == 3){
			deleteSub(ms);
		}
		else if (numTyped == 4){
			main(null);
		}
	}
	
	public static int getInt(int max){
		int amount = 0;
		String in = "";
		Scanner kb = new Scanner(System.in);
		while(true){
			in = kb.nextLine();
			try{
				amount = Integer.parseInt(in);
			}catch(Exception e){
				System.out.println("Invalid input, try again");
				continue;
			}
			if(amount > 0 && amount <= max){
				break;
			}else{
				System.out.println("Invalid input, please try a positive value between 1-"+max);
			}
		}
		return amount;
	}
	
	public static int getInt(){
		int amount = 0;
		String in = "";
		Scanner kb = new Scanner(System.in);
		while(true){
			in = kb.nextLine();
			try{
				amount = Integer.parseInt(in);
			}catch(Exception e){
				System.out.println("Invalid input, try again");
				continue;
			}
			if(amount > 0){
				break;
			}else{
				System.out.println("Invalid input, please try a positive value");
			}
		}
		return amount;
	}
	
	public static double getDoubleInput(){
		double amount = 0;
		String in = "";
		Scanner kb = new Scanner(System.in);
		while(true){
			in = kb.nextLine();
			try{
				amount = Double.parseDouble(in);
			}catch(Exception e){
				System.out.println("Invalid input, try again");
				continue;
			}
			if(amount > 0){
				break;
			}else{
				System.out.println("Invalid input, please try a positive value");
			}
		}
		return amount;
	}
	
	public static void editSub(MasterAccount ms){
		System.out.println("Sub-accounts in account are:");
		ConsolePrinting.printAccounts(ms.accounts);
		System.out.println("Which account would you like to edit? (Enter exact name or type 'exit')");
		String accChosen = keyboardWord.nextLine();
		while (!accChosen.equals("Savings") && !accChosen.equals("Chequing") && !accChosen.equals("exit")) {
			System.out.println("Please enter a correct name.");
			accChosen = keyboardWord.nextLine();
		}
		accChosen = checkSubState(ms, accChosen);
		if (accChosen.equals("exit")){
			accountMenu(ms);
		}
		Account currAcc = ms.loadAccount(accChosen);
		System.out.println("Would you like to:\n1. Deposit\n2. Withdraw\n3. Check balance\n4. Exit");
		int numTyped2 = getInt(4);
		if (numTyped2 == 1) {
			currAcc.deposit();
			ms.saveAccount(currAcc);
			fl.saveMasterAccount(ms);
			fl.fileWriter(fl.accountArray);
			editSub(ms);
		} else if (numTyped2 == 2) {
			currAcc.withdraw();
			ms.saveAccount(currAcc);
			fl.saveMasterAccount(ms);
			fl.fileWriter(fl.accountArray);
			editSub(ms);
		} else if (numTyped2 == 3) {
			System.out.println("Account balance is: " + currAcc.getBalance());
			editSub(ms);
		} else if (numTyped2 == 4) {
			accountMenu(ms);
		}
	}
	
	public static void addSub(MasterAccount ms){
		System.out.println("Would you like to create a:\n1.Savings account\n2.Chequing account\n3.Exit");
		int userInput = getInt(3);
		if (userInput == 1) {
			if(ms.loadAccount("Savings")!=null){
				System.out.println("There is already a Savings sub account.");
			}else{
				System.out.println("How much money will be in the account?");
				double baseAmount = getDoubleInput();
				ms.addAccount(new Account(baseAmount, "Savings"));
				fl.saveMasterAccount(ms);
				fl.fileWriter(fl.accountArray);
			}
		} else if (userInput == 2 && ms.loadAccount("Chequing")==null) {
			if(ms.loadAccount("Chequing")!=null){
				System.out.println("There is already a Chequings sub account.");
			}else{
				System.out.println("How much money will be in the acount?");
				double baseAmount = getDoubleInput();
				ms.addAccount(new Account(baseAmount, "Chequing"));
				fl.saveMasterAccount(ms);
				fl.fileWriter(fl.accountArray);
			}
		}
		else if (userInput == 3){
			accountMenu(ms);
		}
		accountMenu(ms);
	}
	
	public static void deleteSub(MasterAccount ms){
		System.out.println("Sub-accounts in account are:");
		ConsolePrinting.printAccounts(ms.accounts);
		System.out.println("Which account would you like to delete? (Enter exact name or type 'exit')");
		String accChosen = keyboardWord.nextLine();
		while (!accChosen.equals("Savings") && !accChosen.equals("Chequing") && !accChosen.equals("exit")) {
			System.out.println("Please enter a correct name.");
			accChosen = keyboardWord.nextLine();
		}
		accChosen = checkSubState(ms, accChosen);
		if (accChosen.equals("exit")){
			accountMenu(ms);
		}
		ms.deleteAccount(accChosen);
		fl.saveMasterAccount(ms);
		fl.fileWriter(fl.accountArray);
		accountMenu(ms);
	}
	
	public static String checkSubState(MasterAccount ms, String accName){
		while (ms.loadAccount(accName) == null && !accName.equals("exit")){
			System.out.println("There is no sub-account with that name. Please re-enter or type 'exit'");
			accName = keyboardWord.nextLine();
		}
		return accName;
	}
}