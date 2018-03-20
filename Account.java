package defaultpackage;
import java.util.Scanner;

/**
 * @author Punit Thanki
 * Start Date: 2018/03/14
 * End Date: 20180/03/19 
 * UserID: 20218002	
 * Version 1
 */

public class Account {
	public String id;
	public double balance;
	Scanner input = new Scanner(System.in);
	public Account(){
		this.id = "";
		this.balance = 0;
	}
	public Account(double balance, String id){
		this.id = id;
		this.balance = balance;
	}
	public void deposit(){
		System.out.println("How much would you like to deposit?");
		double amountD = 0;
		try{
			amountD = input.nextDouble();
		}
		catch(Exception e){
			System.out.println("Invalid input!! Please please enter a number greater than 0");
			amountD = -1;
			input.next();
		}
		while(amountD<0 || !input.hasNextDouble()){
			System.out.println("Invalid input!! Please please enter a valid number.");
			try{
				amountD = input.nextDouble();
			}
			catch(Exception e){
				amountD = -1;
				input.next();
			}
		}
		this.balance+=amountD;	
		System.out.println("Your new balance is " + this.balance);
		
	}
	public void withdraw(){
		System.out.println("How much would you like to withdraw?");
		double amountW = input.nextDouble();
		while(balance<amountW){
			System.out.println("Insufficient Funds! You only have" + balance + "dollars");
			amountW = input.nextDouble();
		}
		while(amountW<0 || !input.hasNextDouble()){
			System.out.println("Invalid input!! Please please enter a number greater than 0");		
		try{
			amountW = input.nextDouble();
		}
		catch(Exception e){
			amountW = -1;
			input.next();
		}
	}
		if(balance>amountW){
			this.balance-=amountW;
		}
		System.out.println("Your new balance is " + this.balance);
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	
}

