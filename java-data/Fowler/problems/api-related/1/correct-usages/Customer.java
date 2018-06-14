package de.dhbw.fowler;

import java.lang.*;
import java.util.*;

public class Customer {

	private String name;
	private ArrayList<Rental> rentals = new ArrayList<>();

	public Customer(String newname) {
		name = newname;
	}

	public void addRental(Rental rental) {
		rentals.add(rental);
	}
	
	public void removeRental(Rental rental) {
		rentals.remove(rental);
	}
  
}
