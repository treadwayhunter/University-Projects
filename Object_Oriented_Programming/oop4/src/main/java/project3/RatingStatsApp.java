package project3;

import java.util.Scanner;
import java.util.Set;
import java.io.IOException;

/**
 *  Provides functionality for interacting with the user, main class 
    @author tarek
    @author tesic
 */
public class RatingStatsApp{
	
	// Used to read from System's standard input
	static final Scanner CONSOLE_INPUT = new Scanner(System.in);
	
    public static void main(final String [] args) {
		
		String selection = "";
		
		try{
			
			DatasetHandler dh = new DatasetHandler();
			String setup = "Loading the datasets from:" + DataAnalysis.LINE_SEP
            	+ "     data folder: " + dh.getFolderPath() + DataAnalysis.LINE_SEP
				+ "     datasets available: " + dh.getDataSets() + DataAnalysis.LINE_SEP;
			System.out.println(setup);

			dh.printDB();
			
			String welcomeMessage = "Choose one of the following functions:" + DataAnalysis.LINE_SEP
                + "\t 1. Display computed statistics for specific dataID." + DataAnalysis.LINE_SEP
                + "\t 2. Add new collection and compute statistics." + DataAnalysis.LINE_SEP
				+ "\t 0. Exit program." + DataAnalysis.LINE_SEP;
			System.out.println(welcomeMessage);
			String newDataID="";
			selection = CONSOLE_INPUT.nextLine().strip();
			while (!selection.contains("0")) {
				boolean found = false; 
				Set<Dataset> datasets = dh.getDataSets();
				if (selection.contains("1")){
					if (datasets.size()<1){
						System.out.println("There is no data to select from, select another option\n"); 
					}else{
						dh.printDB();
						System.out.println("Please enter dataID from the list: \n");
						newDataID = CONSOLE_INPUT.nextLine().strip();
						if (!(dh.checkID(newDataID))){
							System.out.print("dataID not in the current database, select another option \n");
					    }else{
							found = true;
						}
					}
					//end 1 
				}else if(selection.contains("2")){

					System.out.println("Please enter new unique dataID: \n");
					newDataID = CONSOLE_INPUT.nextLine().strip();
					if (!(dh.checkID(newDataID))){
						System.out.println("For new " + newDataID + " collection, what is the source file name?\n");
						final String input = CONSOLE_INPUT.nextLine().strip();
						boolean check = dh.addCollection(newDataID,input);
						if(check) {
							System.out.println("Collection " + newDataID + " added\n");
							found = true;
						}
						else {
							System.out.println("File not found.");
							System.out.println("Try again.");
						}
					}else{
						System.out.println(newDataID + " is in the current database, displaying existing statistics.\n");
					}
					//end 2
				}else if(selection.contains("h")){
                    System.out.println(welcomeMessage);
           			selection = CONSOLE_INPUT.nextLine().strip();
           			continue;
				}//end selection 
				
				if (found){
					final String processStats = newDataID + ": statistics are already computed and saved \n"
		        		+ "Choose one of the following functions:\n\n"
						+ "\t 3. Use existing stat data.\n" 
						+ "\t 4. Process statistics again, I have new data.\n";
				
					Dataset d = dh.populateCollection(newDataID);
					String rc = "3";
					int stats = d.statsExist();
					if(stats > 0){
						System.out.println(processStats);
						rc = CONSOLE_INPUT.nextLine().strip();
					}
					if (rc.contains("4") || (stats == 0)) {
						System.out.println("Computing...");
						d.computeStats();
						//if stats were computed again, save them.
						dh.saveStats(newDataID);
						dh.saveDB();
					}

					//this blocks processes computed statistics in dh
					int k = 20;
					//prints report to file and console 
					dh.printReport(newDataID,k);
				}//end if found

				System.out.println("Please enter 0 to exit or 'h' to start again.\n");
				selection = CONSOLE_INPUT.nextLine().strip();
			}//end while 
			
	}catch(IOException e){
		System.out.println("Dataset path not found: " + e.getMessage());
		System.out.println("Please check the file and try again, exiting.");
	}
		System.out.println("Goodbye!");
	}//end mail
}//end class 
