package de.dhbw.fowler;

import java.lang.*;
import java.util.*;

public class Customer {

	private String name;
	private ArrayList<Rental> rentals = new ArrayList<>();

	public void pattern(Rental rental) {
		rentals.add(rental);
		rentals.remove(rental);
	}
}
