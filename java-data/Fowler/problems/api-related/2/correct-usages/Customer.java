package de.dhbw.fowler;

import java.lang.*;
import java.util.*;

public class Customer {
	
	private StringBuilder builder;
	
	public String statement() {
		double totalAmount = 0;	
		int frequentRenterPoints = 0;
		
		builder.setLength(0); // resets the builder
		
	  	builder.append("Rental Record for " + this.getName() + "\n");
		builder.append("\tTitle\t\tDays\tAmount\n");

	  	builder.append("Amount owed is " + String.valueOf(totalAmount) + "\n");
	  	builder.append("You earned " + String.valueOf(frequentRenterPoints) + " frequent renter points");
		return builder.toString();
  	}
}
