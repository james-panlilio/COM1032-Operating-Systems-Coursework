package ProcessScheduler;

import java.util.Comparator;
import java.util.Queue;

public class Scheduler {
	private String schedulerAlgorithm = null;
	private long timeQuantum = 0;
	private boolean quantumExpired = false;
	private Queue<ProcessControlBlock> readyQueue = null;
	private EventLog log = null;

	public Scheduler(Queue<ProcessControlBlock> readyQueue, String schedulerAlgorithm, long timeQuantum, EventLog log) {
		this.log = log;
		this.schedulerAlgorithm = schedulerAlgorithm;
		this.timeQuantum = timeQuantum;
		this.readyQueue = readyQueue;
	}

	public String getAlgorithm() {
		return this.schedulerAlgorithm;
	}

	public long getQuantum() {
		return this.timeQuantum;
	}

	public boolean getQuantumExpired() {
		return this.quantumExpired;
	}

	public Queue<ProcessControlBlock> getReadyQueue() {
		return readyQueue;
	}

	/**
	 * Method to add a pcb to ready queue. 
	 * 
	 * @param PCB
	 */
	public synchronized void addToReadyQueue(ProcessControlBlock PCB) {
		getReadyQueue().add(PCB);
	}

	/**
	 * Method to run algorithm corresponding to what the runtime arguments specify.
	 */
	public int runAlgorithm() {
		if (getAlgorithm().equalsIgnoreCase("FCFS")) {
			FCFS();
			return 1;
		} else if (getAlgorithm().equalsIgnoreCase("RR")) {
			RR();
			return 2;
		} else if (getAlgorithm().equalsIgnoreCase("Priority")) {
			priorityScheduling();
			return 3;
		}
		return -1;
	}
	
	public void priorityScheduling() {
        ProcessControlBlock highestPriority = null;
        synchronized (readyQueue) {
            for (ProcessControlBlock pcb : readyQueue) {
                if (highestPriority == null || pcb.getPriority() > highestPriority.getPriority()) {
                    highestPriority = pcb;
                }
            }
            if (highestPriority != null) {
                readyQueue.remove(highestPriority);
            }
        }
        if (highestPriority != null) runCPU(highestPriority);
	}

	/**
	 * First Come First Served algorithm.
	 */
	public void FCFS() {
        ProcessControlBlock pcb;
        synchronized (readyQueue) {
            if (readyQueue.isEmpty()) return;
            pcb = readyQueue.poll();
        }
        runCPU(pcb);
	}

	/**
	 * Round Robin algorithm.
	 */
	public void RR() {
        ProcessControlBlock pcb;
        synchronized (readyQueue) {
            if (readyQueue.isEmpty()) return;
            pcb = readyQueue.poll();
        }

        long time = Math.min(pcb.getCPUBurstTime(), timeQuantum);
        pcb.setCPUBurstTime(pcb.getCPUBurstTime() - time);

        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (pcb.getCPUBurstTime() > 0) {
            pcb.addContextSwitch();
            System.out.println(pcb.getPID() + ": Quantum exceeded. Context Switches: " + pcb.getContextSwitches());
            readyQueue.add(pcb);
        } else {
            runCPU(pcb);
        }

	}
	
	/**
	 * Launches a process on the CPU thread
	 */
	private void runCPU(ProcessControlBlock pcb) {
	    pcb.setState("running");

	    System.out.println(pcb.getPID() + ": Running");
	    System.out.println(pcb.printProcessControlBlock());  // <-- add this

	    CPU cpu = new CPU(pcb, log);
	    cpu.run();
	}

}
