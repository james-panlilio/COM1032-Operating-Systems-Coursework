package task1;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class TaskOne {
    
    // store all the output in this ArrayList for testing purposes
    private static final List<String> bufferOutput = new ArrayList<>();
    
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            TaskOne taskA = new TaskOne();
            
            System.out.println("Enter commands: cat, sort, uniq, wc or | ");
            while (true) {
                System.out.print(">> ");
                String input = scanner.nextLine().trim();
                try {
                    taskA.executeCommands(input);
                    bufferOutput.forEach(System.out::println);
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                } finally {
                    bufferOutput.clear();
                }
            }
        }
    }
    // To be completed
    public void executeCommands(String inputString) {
        if (inputString.isBlank()) return;

        String[] commands = inputString.split("\\|");
        List<String> currentOutput = new ArrayList<>();

        for (String cmdCheck : commands) {
            if (cmdCheck.trim().isEmpty()) {
                throw new IllegalArgumentException("Invalid command in pipeline");
            }
        }
        
        for (int i = 0; i < commands.length; i++) {
            String command = commands[i].trim();
            String[] parts = command.split("\\s+");  // split by space
            String cmd = parts[0];

            switch (cmd) {

                case "cat":
                    if (parts.length < 2) throw new IllegalArgumentException("Invalid cat command");

                    File file = new File(parts[1]);
                    if (!file.exists()) {
                        throw new IllegalArgumentException("Invalid file " + parts[1]);
                    }
                    if (file.isDirectory()) {
                        throw new IllegalArgumentException(parts[1] + " is a directory");
                    }
                    if (!file.isFile()) {
                        throw new IllegalArgumentException("Invalid file " + parts[1]);
                    }

                    currentOutput.clear();
                    try (Scanner fileScanner = new Scanner(file)) {
                        while (fileScanner.hasNextLine()) {
                            currentOutput.add(fileScanner.nextLine());
                        }
                    } catch (IOException e) {
                        throw new IllegalArgumentException("Error reading file: " + parts[1]);
                    }
                    break;

                case "sort":
                    // if it's the first command and has file input
                    if (i == 0 && parts.length >= 2) {
                        File sortFile = new File(parts[1]);
                        if (!sortFile.exists() || sortFile.isDirectory() || !sortFile.isFile()) {
                            throw new IllegalArgumentException("Invalid file " + parts[1]);
                        }

                        currentOutput.clear();
                        try (Scanner fileScanner = new Scanner(sortFile)) {
                            while (fileScanner.hasNextLine()) {
                                currentOutput.add(fileScanner.nextLine());
                            }
                        } catch (IOException e) {
                            throw new IllegalArgumentException("Error reading file: " + parts[1]);
                        }
                    }

                    Collections.sort(currentOutput);
                    break;

                case "uniq":
                    if (i == 0 && parts.length >= 2) {
                        File uniqFile = new File(parts[1]);
                        if (!uniqFile.exists() || uniqFile.isDirectory() || !uniqFile.isFile()) {
                            throw new IllegalArgumentException("Invalid file " + parts[1]);
                        }

                        currentOutput.clear();
                        try (Scanner fileScanner = new Scanner(uniqFile)) {
                            while (fileScanner.hasNextLine()) {
                                currentOutput.add(fileScanner.nextLine());
                            }
                        } catch (IOException e) {
                            throw new IllegalArgumentException("Error reading file: " + parts[1]);
                        }
                    }

                    List<String> uniqList = new ArrayList<>();
                    String prev = null;
                    for (String line : currentOutput) {
                        if (!line.equals(prev)) {
                            uniqList.add(line);
                            prev = line;
                        }
                    }
                    currentOutput = uniqList;
                    break;

                case "wc":
                    boolean countLinesOnly = Arrays.asList(parts).contains("-l");

                    // Grab filename if wc is first in the pipe and has file input
                    String filename = null;
                    for (String part : parts) {
                        if (!part.equals("wc") && !part.equals("-l")) {
                            filename = part;
                            break;
                        }
                    }

                    if (i == 0 && filename != null) {
                        File wcFile = new File(filename);
                        if (!wcFile.exists() || wcFile.isDirectory() || !wcFile.isFile()) {
                            throw new IllegalArgumentException("Invalid file " + filename);
                        }

                        currentOutput.clear();
                        try (Scanner fileScanner = new Scanner(wcFile)) {
                            while (fileScanner.hasNextLine()) {
                                currentOutput.add(fileScanner.nextLine());
                            }
                        } catch (IOException e) {
                            throw new IllegalArgumentException("Error reading file: " + filename);
                        }
                    }

                    int lineCount = currentOutput.size();

                    if (countLinesOnly) {
                        currentOutput.clear();
                        currentOutput.add(String.valueOf(lineCount));
                    } else {
                        int wordCount = 0;
                        int charCount = 0;
                        for (String line : currentOutput) {
                            if (!line.trim().isEmpty()) {
                                wordCount += line.trim().split("\\s+").length;
                            }
                            charCount += line.length();
                        }
                        currentOutput.clear();
                        currentOutput.add(lineCount + " " + wordCount + " " + charCount);
                    }
                    break;

                default:
                    throw new IllegalArgumentException("Invalid command " + cmd);
            }
        }

        // Final result stored in bufferOutput for testing or printing
        bufferOutput.addAll(currentOutput);
    }


    // more methods can be added 

    
    public List<String> getCommandOutput() {
        return new ArrayList<>(bufferOutput);
    }
}
