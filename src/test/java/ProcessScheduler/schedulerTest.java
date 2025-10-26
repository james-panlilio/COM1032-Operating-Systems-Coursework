package ProcessScheduler;

import static org.junit.Assert.*;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.Queue;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class schedulerTest {

	@Before
    public void setup() throws Exception {
        resetPrivateStaticField(Main.class, "processCreator", null);
        resetPrivateStaticField(Main.class, "dispatcher", null);
        resetPrivateStaticField(Main.class, "jobQueue", new JobQueue());
        resetPrivateStaticField(Main.class, "readyQueue", new LinkedList<ProcessControlBlock>());
        resetPrivateStaticField(Main.class, "fileSource", null);
        resetPrivateStaticField(Main.class, "algorithm", null);
        resetPrivateStaticField(Main.class, "quantum", 50L);
        resetPrivateStaticField(Main.class, "log", new EventLog());
    }

    private void resetPrivateStaticField(Class<?> clazz, String fieldName, Object newValue) throws Exception {
        Field field = clazz.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(null, newValue); // Set static field to new value
    }
	
	@Test
	public void testFCFS() {
		String[] args = new String[] { "InputScripts1.txt", "FCFS", "0" }; // runs main method with arguments.

		try {
			Main.initialise(args);
			Main.finaliseThreads();
		} catch (IOException e) {
		}

		Assert.assertEquals(4, Main.getLog().getPCBLog().size());
		// Get the first element
		ProcessControlBlock firstElement = Main.getLog().getPCBLog().get(0); // Retrieves the head of the queue without
																				// removing it.

		// Get the last element by iterating
		ProcessControlBlock lastElement = null;
		for (ProcessControlBlock pcb : Main.getLog().getPCBLog()) {
			lastElement = pcb; // The last assigned value will be the last element.
		}
		Assert.assertEquals("P01", firstElement.getPID());
		Assert.assertEquals("P04", lastElement.getPID());
	}

	@Test
	public void testPriority() {
		String[] args = new String[] { "InputScripts1.txt", "Priority", "0" }; // runs main method with arguments.

		try {
			Main.initialise(args);
			Main.finaliseThreads();
		} catch (IOException e) {
		}

		Assert.assertEquals(4, Main.getLog().getPCBLog().size());
		// Get the first element
		ProcessControlBlock firstElement = Main.getLog().getPCBLog().get(0); // Retrieves the head of the queue without
																				// removing it.

		// Get the last element by iterating
		ProcessControlBlock lastElement = null;
		for (ProcessControlBlock pcb : Main.getLog().getPCBLog()) {
			lastElement = pcb; // The last assigned value will be the last element.
		}
		Assert.assertEquals("P01", firstElement.getPID());
		Assert.assertEquals("P04", lastElement.getPID());
	}

	@Test
	public void testEmptyJobQueue() {
		JobQueue jobQueue = null;
		EventLog log = new EventLog();
		Queue<ProcessControlBlock> readyQueue = null;
		Scheduler scheduler = null;
		try {
			jobQueue = new JobQueue();
			readyQueue = new LinkedList<ProcessControlBlock>();
			scheduler = new Scheduler(readyQueue, "FCFS", 0, log);
			scheduler.FCFS();

		} catch (Exception e) {
			Thread.currentThread().interrupt();
		}
		assertEquals(0, scheduler.getReadyQueue().size());
		assertEquals(0, jobQueue.getQueue().size());
		assertEquals(0, readyQueue.size());
	}

	@Test
	public void testJobQueue() {
		JobQueue jobQueue = null;
		try {
			jobQueue = new JobQueue();
			jobQueue.readFile("InputScripts1.txt");

		} catch (Exception e) {
			Thread.currentThread().interrupt();
		}
		assertEquals(4, jobQueue.getQueue().size());
	}

	@Test
	public void testProcessCreation() {
		JobQueue jobQueue = null;
		EventLog log = new EventLog();
		Queue<ProcessControlBlock> readyQueue = null;
		try {
			jobQueue = new JobQueue();
			readyQueue = new LinkedList<ProcessControlBlock>();
			jobQueue.addToQueue("P01", 2, "process1.py");
			jobQueue.addToQueue("P02", 2, "process2.py");
			jobQueue.addToQueue("P03", 2, "process3.py");

			ProcessCreator creator = new ProcessCreator(jobQueue, readyQueue, log);
			creator.run();

		} catch (Exception e) {
			Thread.currentThread().interrupt();
		}

		assertEquals(0, jobQueue.getQueue().size());
		assertEquals(3, readyQueue.size());
	}
}
