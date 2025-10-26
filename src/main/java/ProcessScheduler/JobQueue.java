package ProcessScheduler;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/**
 * This class reads a text file, and create PCB objects to be added to the JobQueue
 */
public class JobQueue {

	private Queue<ProcessControlBlock> queue = null;

	public JobQueue() throws FileNotFoundException {
		this.queue = new LinkedList<ProcessControlBlock>();
	}

	/** Reads the InputScript file, Ignores line if it begins with a hash.
	 * @param filePath
	 * @throws FileNotFoundException
	 */
	public void readFile(String filePath) throws FileNotFoundException {
		File file = new File(filePath);

		if (!file.exists()) {
			throw new FileNotFoundException("The file at path " + filePath + " does not exist.");
		}

		Scanner scanner = new Scanner(file);
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine().trim();
			if (line.isEmpty() || line.startsWith("#")) continue;

			String[] parts = line.split(",");
			if (parts.length == 3) {
				String pid = parts[0].trim();
				int priority = Integer.parseInt(parts[1].trim());
				String path = parts[2].trim();
				addToQueue(pid, priority, path);
			}
		}
		scanner.close();
	}

	/** Creates a PCB object and adds it to the jobQueue. */
	public void addToQueue(String PID, int priority, String processPathFile) {
		long burst = generateBurst(processPathFile);
		ProcessControlBlock pcb = new ProcessControlBlock(PID, priority, burst, processPathFile);
		queue.add(pcb);
	}

	/** Estimates CPU burst time from the file size. */
	private long generateBurst(String PythonPathFile) {
		double generatedBurst = 0;
		long scriptSize = new File(PythonPathFile).length();
		generatedBurst = Math.round((Main.getBurstSpeed() * scriptSize) / 10) * 10;
		return (long) generatedBurst;
	}

	public Queue<ProcessControlBlock> getQueue() {
		return this.queue;
	}
}
