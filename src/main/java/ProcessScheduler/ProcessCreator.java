package ProcessScheduler;


import java.util.Queue;

/**
 *Thread to obtain a job from the job queue and add it to the ready queue.
 */
public class ProcessCreator implements Runnable {
	private JobQueue jobQueue = null;
	private Queue<ProcessControlBlock> readyQueue = null;
	private EventLog log = null;

	public ProcessCreator(JobQueue jobQueue, Queue<ProcessControlBlock> readyQueue, EventLog log) {
		this.jobQueue = jobQueue;
		this.readyQueue = readyQueue;
		this.log = log;
	}
	/**
	 * Synchronizes access to the jobQueue so no other thread can modify it while processing.
	 * Removes a PCB from the job queue.
	 * Sets state to "ready".
	 * Adds PCB to the ready queue.
	 * adds PCB to log.
	 * Sets arrival time to current system time.
	 */
	public void run() {
        while (!jobQueue.getQueue().isEmpty()) {
            synchronized (jobQueue) {
                ProcessControlBlock pcb = jobQueue.getQueue().poll();
                if (pcb != null) {
                    pcb.setState("ready");
                    pcb.setArrivalTime(System.nanoTime());
                    readyQueue.add(pcb);
                    log.addPCB(pcb);
                    System.out.println(pcb.printProcessControlBlock());
                }
            }
        }
	}

	public JobQueue getJobQueue() {
		return jobQueue;
	}

	public Queue<ProcessControlBlock> getReadyQueue() {
		return readyQueue;
	}
	
	

}
