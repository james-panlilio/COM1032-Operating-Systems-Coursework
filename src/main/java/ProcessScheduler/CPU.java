package ProcessScheduler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
/**
 * CPU Thread. Runs a python script from a given PCB.
 *
 */
public class CPU extends Thread {

	/**
	 * Instance fields used in constructor for parsing in other objects
	 */
	private boolean running = true; // if CPU is actively executing a process
	private ProcessControlBlock PCB = null;
	private EventLog log = null;

	public CPU(ProcessControlBlock PCB, EventLog log) {
		this.PCB = PCB;
		this.log = log;
	}

	/**
	 * Run method - Runs a python process when called.
	 * use ProcessBuilder to run a Python process
	 * You should put the current CPU thread to sleep for the time the python script
	 * should run for.
	 * and wait until python script is completed (use waitFor())
	 * store the output of Python script in PCB
	 * record the total execution time
	 * set the state to be 'terminated'
	 * record the number of context switches
	 * Save the PCB output in the EventLog (e.g., P01: Complete, Context Switches:
	 * 0, Output: Sum is 9)
	 * 
	 */
	public void run() {
		long startTime = System.nanoTime();
        PCB.setState("running");

        try {
            ProcessBuilder pb = new ProcessBuilder("/usr/bin/python3", PCB.getProcessPath());
            Process process = pb.start();

            // Wait until the script finishes execution
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line);
            }
            process.waitFor();

            // Simulate execution delay based on estimated burst time
            Thread.sleep(PCB.getCPUBurstTimeStatic());

            long endTime = System.nanoTime();
            PCB.setExecutionTime();
            PCB.setPCBResult(output.toString());
            PCB.setState("terminated");

            String result = String.format("%s: Complete, Context Switches: %d, Output: %s, Execution Time: %dms",
                    PCB.getPID(), PCB.getContextSwitches(), PCB.getPCBResult(), PCB.getExecutionTime());
            log.add(result);

            System.out.println(PCB.printProcessControlBlock());  // <- This shows correct "terminated" state

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }

	public ProcessControlBlock getPCB() {
		return this.PCB;
	}

	public boolean getRunning() {
		return this.running;
	}

}
