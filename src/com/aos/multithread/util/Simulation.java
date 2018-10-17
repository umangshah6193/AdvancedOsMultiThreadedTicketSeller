package com.aos.multithread.util;

import com.aos.multithread.seat.Seat;
import com.aos.multithread.seller.MainSeller;
import com.aos.multithread.seller.H_Seller;
import com.aos.multithread.seller.L_Seller;
import com.aos.multithread.seller.M_Seller;

public class Simulation {
	
	public static void main(String[] args) {

		//number of customers per seller per hour
		int numberOfCustomersPerSeller = 10;
		if (args.length > 0) numberOfCustomersPerSeller = Integer.parseInt(args[0]);

		final Object threadLock = new Object();

		int numberOfRows = 10;
		int numberOfColumns = 10;
		Seat[][] seatingArrangement = Utilities.generateSeatingArrangement(numberOfRows, numberOfColumns);

		//create 10 seller_threads representing 10 sellers
		MainSeller[] sellers_array = new MainSeller[10];
		for (int i = 0; i < 10; i++)
		{
			if (i == 0) 
				sellers_array[i] = new H_Seller(seatingArrangement, "H" + (i + 1), threadLock);
			else if (i >= 1 && i < 4) 
				sellers_array[i] = new M_Seller(seatingArrangement, "M" + (i), threadLock);
			else if (i >= 4 && i < 10) 
				sellers_array[i] = new L_Seller(seatingArrangement, "L" + (i - 3), threadLock);
		}

		//add N customers for each seller for each hour
		//initially add N customers for each seller's queue
		sellers_array = Utilities.increaseNewCustomer(sellers_array, numberOfCustomersPerSeller);

		//wake up all seller seller_threads, so they can run in "parallel"
		//threadLock.notifyAll(); //use notifyAll for broadcast
		Thread seller_threads[] = new Thread[sellers_array.length];
		//for(int is = 0; is < sellers_array.length; is++)
		//{
		//	Thread currentThread = new Thread(sellers_array[is]);
		//	currentThread.start();
		//}
		for(int is = 0; is < sellers_array.length; is++)
		{
			seller_threads[is] = new Thread(sellers_array[is]);
			seller_threads[is].start();
		}
		for(int is = 0; is < sellers_array.length; is++)
		{
			try {
				seller_threads[is].join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch bthreadLock
				e.printStackTrace();
			}		
		}

		synchronized(threadLock) {
			Utilities.displaySeatingArrangement(seatingArrangement, numberOfRows, numberOfColumns);
		}
	}
}