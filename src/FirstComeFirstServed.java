import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

/*
 *  Author: Robert Hasbrouck
 *  Version: 2.0
 * 
 */
public class FirstComeFirstServed {

	public static void main(String[] args) {
		int numOfProc;
		int[][] processes; //Matrix of processes.  
		
		try {
		    BufferedReader in = new BufferedReader(new FileReader("info.data"));
		    PrintWriter writer = new PrintWriter("output.data", "UTF-8");
		    String str;
		    
		    //get number of processes
		    if((str = in.readLine()) != null){
		    	numOfProc = Integer.parseInt(str);
		    	processes = new int[numOfProc][3];
		    }else{
		    	processes = null;
		    	numOfProc = 0;
		    }
		    
		    //Since doing FCFS ignore next line
		    str = in.readLine();
		    
		    //fill matrix
		    int i = 0;
		    while ((str = in.readLine()) != null){
		    	processes[i][0] = Character.getNumericValue(str.charAt(0));
		    	processes[i][1] = Character.getNumericValue(str.charAt(2));
		    	processes[i][2] = Character.getNumericValue(str.charAt(4));
		    	i++;
		    }	        
		    in.close();
		    
		    //Dispatcher
		    int time = 0;
		    int start;
		    int select;
		    while(isProc(processes, numOfProc)){
		    	select = selectProc(processes, numOfProc, time);
		    	
		    	System.out.println("Entereing Process:" + select + " at time:" + time);
		    	System.out.println("Process:" + select + " arr:" + processes[select][0] + " burst: " + processes[select][1] + " pri: " + processes[select][2]);
		    	start = time;
		    	
		    	//run selected process until it is complete
		    	while(processes[select][1] > 0){
		    		processes[select][1]--;
		    		time++;
		    	}
		    	
		    	writer.println(start + " "   + time + " " + select);
		    	System.out.println("Leaving Process:" + select + " at time:" + time);
		    	System.out.println("Output.data created.");
		    }	    
		    
	    	writer.close();
		} catch (IOException e) {
			System.err.println("Error:" + e.getMessage());
		}
		
		System.exit(0);
	}
	
	//test to see if any processes have burst time left
	public static boolean isProc(int[][] p, int num){
		for(int i = 0; i < num; i++){
			if(p[i][1] != 0)
				return true;
		}
		return false;
	}
	
	//Select process by finding first to arrive
	public static int selectProc(int[][] p, int num, int t){
		int min = t + 1;
		int proc = 0;
		
		for(int i = 0; i < num; i++){
			if(p[i][1] != 0 && p[i][0] <= t){
				if(p[i][0] < min){
					min = p[i][0];
					proc = i;
				}
			}
		}
		
		return proc;
	}
}
