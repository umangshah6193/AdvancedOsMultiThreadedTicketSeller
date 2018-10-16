package com.aos.multithread.seat;

import com.aos.multithread.customer.Customer;

public class Seat {
	
	private int seatNumber;
	private Customer seatTaken;
	
	public Seat(int num)
	{
		seatNumber = num;
		seatTaken = null;
	}

	public void assignSeat(Customer c)
	{
		seatTaken = c;
	}

	public boolean isSeatEmpty() {
		return seatTaken == null;
	}
	
	public int getSeatNumber() {
		return seatNumber;
	}
	
	public Customer getCustomer(){
		return seatTaken;
	}
}
