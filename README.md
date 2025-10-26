# ⚙️ COM1032 — Multithreaded Scheduler Coursework

This repository contains my **Operating Systems Coursework (COM1032)** for the **University of Surrey (2024–25)**.  
It implements a simplified **multithreaded process scheduler** in **Java**, simulating how an operating system handles concurrent processes, priorities, and CPU scheduling.

---

## 🧠 Project Overview

The system models the behaviour of an **Operating System Scheduler**, which:
- Reads job definitions from input files (process ID, priority, and Python script name).  
- Manages jobs using a **priority-based queue**.  
- Uses **multiple CPU threads** to execute jobs concurrently.  
- Logs events such as process creation, dispatching, and completion.  

Each process corresponds to a **Python script**, executed by the scheduler to simulate real system-level task handling.

---

## ✨ Features

🧩 **Job Scheduling System**
- Reads job definitions from `.txt` files (e.g. `InputScripts1.txt`).  
- Each process has an ID, priority, and executable Python file.  
- Simulates **priority-based scheduling**.

🧵 **Multithreaded Execution**
- Processes are distributed across multiple simulated CPU threads.  
- Thread-safe job queue ensures consistent execution order.  

🧾 **Event Logging**
- All scheduling events are recorded using `EventLog.java`.  
- Includes process start, finish, and dispatch timestamps.

🐍 **Python Process Simulation**
- Java’s `Runtime` is used to execute external Python scripts.  
- Python files (`process0.py`–`process5.py`) perform tasks like arithmetic, list processing, and number theory calculations.  

📊 **Testing & Validation**
- Includes `schedulerTest.java` and `taskOneTest.java` for automated unit testing using **JUnit**.

---

## 📁 Project Structure

```
COM1032-Coursework-ap02856/
│
├── pom.xml                        # Maven build configuration
│
├── src/
│   ├── CPU.java                   # Simulates a CPU executing processes
│   ├── Dispatcher.java            # Dispatches jobs from the queue
│   ├── EventLog.java              # Records scheduler events
│   ├── JobQueue.java              # Thread-safe job queue
│   ├── Main.java                  # Program entry point
│   ├── ProcessControlBlock.java   # Tracks process state
│   ├── ProcessCreator.java        # Parses input and creates processes
│   ├── Scheduler.java             # Main scheduling logic
│
├── InputScripts1.txt – InputScripts4.txt   # Process definitions
├── process0.py – process5.py               # Python job scripts
│
├── TaskOne.java, taskOneTest.java          # Task A: File operations
├── schedulerTest.java                      # Task B: Scheduler testing
│
└── README.md                               # Project overview and usage
```

---

## 🧩 Input File Format

Each job is defined by:
```
PID, Priority, ScriptName
```

Example (`InputScripts1.txt`):
```
P01,1,process1.py
P02,7,process2.py
P03,6,process3.py
P04,3,process0.py
```

- **Priority 1** = lowest priority  
- **Priority 10** = highest priority  
- Lines beginning with `#` are ignored  

---

## 🧪 Example Python Processes

| Script | Description |
|---------|--------------|
| `process0.py` | Prints the sum of two integers |
| `process1.py` | Performs arithmetic operations |
| `process2.py` | Filters lists and removes elements |
| `process3.py` | Calculates HCF and LCM |
| `process4.py` | Prime factorisation and number analysis |
| `process5.py` | Fibonacci, random walk, and statistics |

---

## 🧰 Tech Stack

| Category | Technology |
|-----------|-------------|
| **Language** | Java 17 |
| **Build Tool** | Apache Maven |
| **Testing** | JUnit 4 / JUnit 5 |
| **Concepts Used** | Threads, Synchronisation, Priority Scheduling |
| **External Execution** | Python 3 |
| **Logging** | Custom Java EventLog system |

---

## 🚀 How to Run

1️⃣ **Clone the Repository**
```bash
git clone https://github.com/james-panlilio/COM1032-Coursework-ap02856.git
cd COM1032-Coursework-ap02856
```

2️⃣ **Compile the Project**
```bash
mvn clean compile
```

3️⃣ **Run the Program**
```bash
mvn exec:java -Dexec.mainClass="Main"
```

4️⃣ **Provide Input File**
When prompted, enter one of the sample job files (e.g. `InputScripts1.txt`).

✅ Eclipse automatically compiles and runs the project using Maven’s classpath, so no command-line setup is required.

---

## 🧑‍💻 Author

**James Panlilio**  
BSc Computer Science — University of Surrey (2nd Year Student)  
📍 Interests: Java Development, Operating Systems, and Full-Stack Engineering  

---

## 🎯 Learning Outcomes

- Implemented a working **multithreaded scheduler** in Java  
- Demonstrated understanding of **process management** and **thread synchronisation**  
- Practiced **file parsing**, **runtime execution**, and **event logging**  
- Applied **testing methodologies** using JUnit  

---

## 📄 License

This project is submitted as part of the **University of Surrey’s COM1032 module**.  
You may reference or reuse portions for **educational purposes** with attribution.
