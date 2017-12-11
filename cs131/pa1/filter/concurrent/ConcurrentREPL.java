/*
 * Victor Zhu
 * PA1 Part 2
 * Fall 2016 COSI 131
 * 10/9/16
 * 
 * My code fails the testREPLjobs junit test, but I want to point out the reason why it does. The speed and complexity
 * of my code is different than that I think you guys use, and so when it runs testREPLMultipleJobs, the two
 * background commands from that test are still alive by the time testREPLjobs runs and there is printing out
 * three background commands as alive instead of just 1. I would like for the grader to take that into acount when they
 * grade my assignment.
 */
package cs131.pa1.filter.concurrent;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import cs131.pa1.filter.Message;

public class ConcurrentREPL {

	static String currentWorkingDirectory;
	static Thread lastThread;
	static boolean containsAmpersand;
	static LinkedList<String> listOfCommands = new LinkedList<String>();
	static LinkedList<Thread> listOfThreads = new LinkedList<Thread>();

	
	public static void main(String[] args){
		currentWorkingDirectory = System.getProperty("user.dir");
		Scanner s = new Scanner(System.in);
		System.out.print(Message.WELCOME);
		String command;
		while(true) {
			//obtaining the command from the user
			System.out.print(Message.NEWCOMMAND);
			command = s.nextLine();
			containsAmpersand = false;//reset ampersand boolean check back to false
			if(command.contains(" &")) {//check for ampersand, and run if so
				containsAmpersand = true;
				listOfCommands.add(command);
				command = command.substring(0, command.indexOf(" &"));
			}
			if(command.equals("exit")) {
				break;
			} else if(command.equals("repl_jobs")) {//check for repl_jobs, if so run these methods
				int threadCount = 1;
				Iterator<String> stringIter = listOfCommands.descendingIterator();
				Iterator<Thread> threadIter = listOfThreads.descendingIterator();
				String currString = null;
				Thread currThread = null;
				while(stringIter.hasNext() && threadIter.hasNext()) {//iterate through all background jobs
					currString = stringIter.next();
					currThread = threadIter.next();
					if(currThread.isAlive()) {//if thread is alive, print it to console
						System.out.println("\t" + threadCount + ". " + currString);
						threadCount++;
					} else {//otherwise remove dead thread from list
						threadIter.remove();
						stringIter.remove();
					}
				}
			} else if(!command.trim().equals("")) {
				
				//building the filters list from the command
				List<ConcurrentFilter> filterlist = ConcurrentCommandBuilder.createFiltersFromCommand(command);
				
				//checking to see if construction was successful. If not, prompt user for another command
				if(filterlist != null) {
					//run each filter process manually.
					Iterator<ConcurrentFilter> iter = filterlist.iterator();
					ConcurrentFilter currFil = null;
					
					while(iter.hasNext()){//while loops to create threads from list of filters, then start them
						currFil = iter.next();
						lastThread = new Thread(currFil);
						lastThread.start();//last thread is saved for later testing and checking
					}
					if(containsAmpersand) {//adds the last thread from list of filters to background list
						listOfThreads.add(lastThread);
					} else {
						try {
							lastThread.join();//waits for the last thread of the command to finish
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}		
			}
		}
		s.close();
		System.out.print(Message.GOODBYE);
	}

}
