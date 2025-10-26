package ProcessScheduler;

/**
 * 
 * Defines a PCB Object in order for it to be scheduled and executed.
 * 
 */
public class ProcessControlBlock {
	/**
	 * Instance fields for each attribute in a PCB object.
	 */
	private String PID = null;
	private String state = null;
	private int priority = 0;
	private long arrivalTime = -1;
	private long CPUBurstTime = 0;
	private boolean requiresUserInput = true;
	private String processPath = null;
	private String PCBResult = null;
	private int contextSwitches = 0;
	private long executionTime = -1;
	private long CPUBurstTimeStatic = 0;

	/** Constructor assigning input parameters to instance fields. Input parameters read from processInput file.
	 * @param PID
	 * @param priority
	 * @param CPUBurstTime
	 * @param processPath
	 */
	public ProcessControlBlock(String PID, int priority, long CPUBurstTime, String processPath) {
		super();
		this.PID = PID;
		validatePriority(priority);
		this.state = "new";
		this.arrivalTime = -1; //"unscheduled";
		this.CPUBurstTime = CPUBurstTime;
		this.processPath = processPath;
		this.PCBResult = null;
		this.CPUBurstTimeStatic = CPUBurstTime;

	}

	public void setCPUBurstTime(long time) {
		if (time < 0) {
			this.CPUBurstTime = Long.valueOf("0");
		} else {
			this.CPUBurstTime = time;
		}

	}

	private void validatePriority(int priority) {
		if (priority < 0 || priority > 10) {
			throw new IllegalArgumentException("Invalid priority!");
		} else {
			this.priority = priority;
		}
	}
	/*
	 * print the details of the PCB
	 * do not change the format of the printout for consistency of marking
	 */
	public String printProcessControlBlock() {
		return ("(PID: " + getPID() + ", State: " + getState() + ", Priority: " + getPriority()
				+ ", SchedulerArrivalTime: " + getArrivalTime() + ", CPUBurstTime: " + getCPUBurstTime() + "ms)");

	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPID() {
		return this.PID;
	}

	public int getPriority() {
		return this.priority;
	}

	public long getArrivalTime() {
		return this.arrivalTime;
	}

	public void setArrivalTime(long time) {
		this.arrivalTime = time;
	}

	public long getCPUBurstTime() {
		return this.CPUBurstTime;
	}

	public boolean getRequiresUserInput() {
		return this.requiresUserInput;
	}

	public void setRequiresUserInput(boolean setIO) {
		this.requiresUserInput = setIO;
	}

	public String getProcessPath() {
		return this.processPath;
	}

	public void setPCBResult(String result) {
		this.PCBResult = result;
	}

	public String getPCBResult() {
		return this.PCBResult;
	}

	public int getContextSwitches() {
		return this.contextSwitches;
	}

	public void addContextSwitch() {
		this.contextSwitches += 1;
	}

	public void setContextSwitch(int amount) {
		this.contextSwitches = amount;
	}

	public long getExecutionTime() {
		return this.executionTime;
	}

	public long getCPUBurstTimeStatic() {
		return this.CPUBurstTimeStatic;
	}


	/**
	 * Sets the total execution time of a process in milliseconds.
	 * the arrival time of the PCB should have been recorded.
	 * Get the current time, 
	 * (you may have to convert nanoseconds to milliseconds)
	 * then you can calcuate the execution time of PCB.
	 * this method is called when PCB execution is finished.
	 */
	public void setExecutionTime() {
        long now = System.nanoTime();
        if (arrivalTime > 0) {
            this.executionTime = (now - arrivalTime) / 1_000_000; // convert ns to ms
            }
	}
}

