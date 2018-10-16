package com.aos.multithread.util;

import com.aos.multithread.customer.Customer;
import com.aos.multithread.seat.Seat;
import com.aos.multithread.seller.MainSeller;
import com.aos.multithread.seller.H_Seller;
import com.aos.multithread.seller.L_Seller;
import com.aos.multithread.seller.M_Seller;

public class Simulation {
	public void wakeupSellers() {

	}
	public static void main(String[] args) {

		//number of customers per seller per hour
		int numberOfCustomersPerSeller = 10;
		if (args.length > 0) numberOfCustomersPerSeller = Integer.parseInt(args[0]);

		final Object threadLock = new Object();

		int numberOfRows = 10;
		int numberOfColumns = 10;
		Seat[][] seatingArrangement = generateSeatingArrangement(numberOfRows, numberOfColumns);

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
		sellers_array = increaseNewCustomer(sellers_array, numberOfCustomersPerSeller);

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
		


		//print the following with the current time
		//- customer added to the queue
		//- customer is attended (given a seat or told there are no seats)
		//- customer gets a ticket and goes to seat
		//- print seatingArrangement chart everytime a ticket is sold

		//wait for all sellers to finish



		synchronized(threadLock) {
			printSeating(seatingArrangement, numberOfRows, numberOfColumns);
		}



		//seller needs to take in parameters or have some ref to
		//2D array, time?, universal threadLock
	}

	/**
	 * Create a seatingArrangement chart and label with seat numbers
	 * @param numberOfRows: max number of rows for the chart
	 * @param numberOfColumns max number of columns for the chart
	 * @return seatingArrangement chart with the given size and fully labeled
	 */
	public static Seat[][] generateSeatingArrangement(int numberOfRows, int numberOfColumns)
	{
		//create 10x10 seatingArrangement and label with seat numbers
		Seat[][] seatingArrangement = new Seat[numberOfRows][numberOfColumns]; 
		int numSeat = 1;
		for (int row = 0; row < numberOfRows; row++)
		{
			for (int column = 0; column < numberOfColumns; column++)
			{
				seatingArrangement[row][column] = new Seat(numSeat);
				numSeat++;
			}
		}
		return seatingArrangement;
	}

	/**
	 * Add the given number of customers for each seller's queue
	 * @param sellers_array: array containing all the sellers
	 * @param numAdd: number of customers to add to each seller
	 * @return updated array with the new customers added to each queue
	 */
	public static MainSeller[] increaseNewCustomer(MainSeller[] sellers_array, int numAdd)
	{
		for (int i = 0; i < sellers_array.length; i++)
		{
			for (int count = 0; count < numAdd; count++)
			{
				Customer c = new Customer(i);
				sellers_array[i].addCustomer(c);
			}
			sellers_array[i].sortQueue();
		}
		return sellers_array;
	}

	/**
	 * Print the current seatingArrangement chart
	 * @param seatingArrangement: current seatingArrangement chart
	 * @param numberOfRows: max number of rows for the chart
	 * @param numberOfColumns: max number of columns for the chart
	 */
	/*
	public static void printSeating(Seat[][] seatingArrangement, int numberOfRows, int numberOfColumns)
	{
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		for (int row = 0; row < numberOfRows; row++)
		{
			for (int col = 0; col < numberOfColumns; col++)
			{
				if (seatingArrangement[row][col].isSeatEmpty()) System.out.printf("%5s ", "O");
				else System.out.printf("%5s ", "X");
			}
			System.out.println();
		}
	}
	*/
	public static void printSeating(Seat[][] seatingArrangement, int numberOfRows, int numberOfColumns)
	{
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		for (int row = 0; row < numberOfRows; row++)
		{
			for (int col = 0; col < numberOfColumns; col++)
			{
				if (seatingArrangement[row][col].isSeatEmpty()) 
					System.out.printf("%7s ", "O");
				else 
					System.out.printf("%7s ", seatingArrangement[row][col].getCustomer().getTicket());
			}
			System.out.println();
		}
	}

}