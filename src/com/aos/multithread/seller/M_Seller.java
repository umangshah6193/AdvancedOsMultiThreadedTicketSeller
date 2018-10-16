package com.aos.multithread.seller;

import com.aos.multithread.customer.Customer;
import com.aos.multithread.seat.Seat;

public class M_Seller extends MainSeller{
	private Object threadLock;

	public M_Seller(Seat[][] s, String sellerID, Object lk) {
		// Seller H takes 1 or 2 minutes to complete a ticket sale
		super(s, r.nextInt(4) + 2, sellerID, lk);
		threadLock = lk;
	}

	public void sell() {
		while (!customers.isEmpty()) {						
			//Object threadLock = new Object();

			if (customers.isEmpty()) return;
			// Get customer in queue that is ready
			Customer customer = customers.peek();

			// Find seat for the customer
			// Case for Seller M
			boolean flag = true;
			int counter = 1;

			Seat seat = null;
			
			synchronized(threadLock) {		
				find_seat:
					for(int i = 5; i >= 0 && i < seatingArrangement.length;) {
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
						if(flag == true){
							i += counter;
							flag = false;
						}
						else{
							i -= counter;
							flag = true;
						}
						counter++;
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
