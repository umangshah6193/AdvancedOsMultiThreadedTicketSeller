package com.aos.multithread.util;

import com.aos.multithread.customer.Customer;
import com.aos.multithread.seat.Seat;
import com.aos.multithread.seller.MainSeller;

public class Utilities {
	
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
	public static void displaySeatingArrangement(Seat[][] seatingArrangement, int numberOfRows, int numberOfColumns)
	{
		System.out.println("############################################################################ SEATING ARRANGEMENT VIEW ############################################################################");
		for (int row = 0; row < numberOfRows; row++)
		{
			for (int col = 0; col < numberOfColumns; col++)
			{
				if (seatingArrangement[row][col].isSeatEmpty()) 
					System.out.printf("%7s ", "O");
				else 
					System.out.printf("%7s ", seatingArrangement[row][col].getCustomerSeat().getTicket());
			}
			System.out.println();
		}
	}

}
