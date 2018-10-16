package com.aos.multithread.seller;

import com.aos.multithread.customer.Customer;
import com.aos.multithread.seat.Seat;

public class H_Seller extends MainSeller {

	private Object threadLock;
	public H_Seller(Seat[][] s, String sellerID, Object lk) {
		// Seller H takes 1 or 2 minutes to complete a ticket sale
		super(s, r.nextInt(2) + 1, sellerID, lk);
		threadLock = lk;
	}

	public void sell() {
		while (!customers.isEmpty()) {						
			//Object threadLock = new Object();

			if (customers.isEmpty()) return;
			// Get customer in queue that is ready
			Customer customer = customers.peek();

			// Find seat for the customer
			// Case for Seller H
			Seat seat = null;

			synchronized(threadLock) {	
				find_seat:
					for (int i = 0; i < seatingArrangement.length; i++) {
						for (int j = 0; j < seatingArrangement[0].length; j++) {
							if (seatingArrangement[i][j].isSeatEmpty()) {
								// Assign seat to customer
								// Seat number = (Row x 10) + (Col + 1)
								int seatNum = (i*10)+j+1;
								seat = new Seat(seatNum);
								super.assignSeat(customer, seat, i, j);
								break find_seat;
							}
						}
					}
			//threadLock.notifyAll();
			}
			if(seat != null){
				try {
					Thread.sleep(serviceTime * 100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch bthreadLock
					e.printStackTrace();
				}
			}
			printMsg(customer, seat);
			
			customers.remove();
		}
	}

}
