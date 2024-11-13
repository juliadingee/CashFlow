package application;

import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List; 

public class ReadCSV2 {
	
	String[][] list;
	private int rows;
	private int colums;
	
	FinanceNode head = new FinanceNode();
	FinanceNode tail = new FinanceNode();
	
	class FinanceNode {
		
		int day = 0;
		int month = 0;
		int year = 0;
		double amount = 0;
		String description = "";
		String category = "";
		String date = "";
		double balance = 0;
		
		FinanceNode next = null;
		FinanceNode prev = null;
		
		public FinanceNode() {}
		
		public FinanceNode(int day, int month, int year, double amount, 
				String description, String category, String date, double balance) {	
			this.day = day;
			this.month = month;
			this.year = year;
			this.amount = amount;
			this.description = description;
			this.category = category;
			this.date = date;
			this.balance = balance;
		}
		
	} // end Node
	
	//couple of getters for 2D array
	public int getRows() {
		return rows;
	}
	
	public int getColums() {
		return colums;
	}
	
	//couple of getters for linked list
	public FinanceNode getHead() {
		return head;
	}
	
	public FinanceNode getTail() {
		return tail;
	}
	
	/**
	 * Constructor will create a ReadCSV2 2D array that will contain contents of csv file.
	 * @param path directory path to csv file including the name of file at end of path
	 */
	public ReadCSV2(String path){
        
        try {
            BufferedReader bfReader = new BufferedReader(new FileReader(path)); 

        	String line = bfReader.readLine();
        	rows = 1;
            colums = line.split(",").length;
            
        	
        	//get rows to create static array
        	while ((line = bfReader.readLine()) != null) {
        		rows++;
        	}
        	
        	//create static array and reset bfReader
        	list = new String[rows][colums];
        	bfReader.close();
        	
        	bfReader = new BufferedReader(new FileReader(path));
            
//        	////////////////////////////////////
//        	this will read in data into 2D array
//        	////////////////////////////////////
        	
            int rIndex = 0;
            while ((line = bfReader.readLine()) != null){
            	
            	//split returns an array. Each array will then be added to a row in list
            	String[] tempArray = line.split(",");
            	
            	for(int i=0; i < tempArray.length && tempArray.length == colums; i++) {
            		list[rIndex][i] = tempArray[i];
            	}	
            	
            	//goes to next row
            	rIndex++;
            }
            
            bfReader.close();
            
            createLinkedList();
        
        }//end try
        catch (IOException e){
            System.out.println("No File found.");
            e.printStackTrace();
        }//end catch
             
    }//end method
	
	/**
	 * Created 2D array will also be transferred to a doubly linked list.
	 * Allowing the user to either utilize the array or the linked list.
	 */
	public void createLinkedList() {
		
		int listDay = 0;
		int listMonth = 0;
		int listYear = 0;
		double listAmount = 0;
		String listDescription = "";
		String listCategory = "";
		String listDate = "";
		double listBalance = 0;
		
		FinanceNode cursor = head;
		
		for(int x = 1; x < getRows(); x++) {
			
			// if month is single digit
			if(list[x][1].substring(1,2).compareTo("/") == 0) {
				
				//System.out.printf("x = %d Category: %s\n", x + 1, list[x][7]);
				
				listMonth = Integer.parseInt(list[x][1].substring(0,1));
				
				// if day is single digit
				if(list[x][1].substring(3,4).compareTo("/") == 0) {
					listDay = Integer.parseInt(list[x][1].substring(2,3));
				}
				else {
					listDay = Integer.parseInt(list[x][1].substring(2,4));
				}
				
			} // end if
			
			// if month is double digit
			else if(list[x][1].substring(2,3).compareTo("/") == 0) {
				
				listMonth = Integer.parseInt(list[x][1].substring(0,2));
				
				// if day is single digit
				if(list[x][1].substring(4,5).compareTo("/") == 0) {
					listDay = Integer.parseInt(list[x][1].substring(3,4));
				}
				else {
					listDay = Integer.parseInt(list[x][1].substring(3,5));
				}

			} // end else if
			
			listYear = Integer.parseInt(list[x][1].substring(list[x][1].length() - 4));
			listAmount = Double.parseDouble(list[x][4]);
			listDescription = list[x][7];
			listCategory = list[x][8];
			listDate = list[x][1];
			listBalance = Double.parseDouble(list[x][10]);
			
			FinanceNode newNode = new FinanceNode(listDay, listMonth, listYear, listAmount,
					listDescription, listCategory, listDate, listBalance);
			
			cursor.next = newNode;
			cursor.next.prev = cursor;
			
			cursor = cursor.next;
			
			//tying tail to end of linked list
			if(x == getRows() - 1) {
				cursor.next = tail;
				tail.prev = cursor;
			}
			
		} // end for
	}
	
	/**
	 * toString method to print out CSV file contents
	 */
	public String toString() {
		
		String string = "";
		
		for(int i=0; i < rows; i++) {
			
			for(int j=0; j<colums; j++) {
				
				if(list[i][j] == "" ){

					switch(j) {
					case 0: string = string + "                ";
					break;
					case 2: string = string + "                              ";
					break;
					case 3: string = string + "  ";
					break;
					case 5: string = string + "   ";
					break;
					
					}
						
					
				}else if(j == 9){
					string = string + "      " + list[i][j] + "            ";
				}
				
					 
				else string = string + " " + list[i][j] + " ";
					
				
			
			}
			
			
			string = string + "\n";
		}
		
		return string;		
	}
	
}//end class ReadCSV
