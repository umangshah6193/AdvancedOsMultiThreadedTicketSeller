package com.aos.multithread.seat;

import com.aos.multithread.customer.Customer;

public class Seat {
	
	private int seatNumber;
	private Customer customerSeat;
	
	public Seat(int num)
	{
		seatNumber = num;
		customerSeat = null;
	}

	public void assignSeat(Customer c)
	{
		customerSeat = c;
	}

	public boolean isSeatEmpty() {
		return customerSeat == null;
	}
	
	public int getSeatNumber() {
		return seatNumber;
	}
	
	public Customer getCustomerSeat(){
		return customerSeat;
	}
}
