# Elevator System Simulator

## About/Overview
This program simulates an elevator system within a multi-floor building. It models the operation of elevators responding to user requests either to ascend from the ground floor or to descend from the top floor. The primary goal is to offer a visual and interactive simulation of elevator traffic management and behavior in response to various user-generated requests.

## Features
- **Start**: Initializes the system, setting all elevators to operational status from an initial out-of-service state.
- **Step**: Executes a single step in the simulation, representing a unit of time or operation within the system.
- **N Steps**: Advances the simulation by a user-specified number of steps, allowing for extended observation of dynamics without manual input for each step.
- **Request**: Sends a user-defined request from one floor to another, inputting specific start and destination floors.
- **N Requests**: Automatically generates a specified number of requests, evenly split between ascent and descent.
- **Stop**: Immediately sends all elevators to the ground floor and sets them to a non-operational state, halting further requests.
- **Shutdown**: Moves all elevators to the ground floor and deactivates the system.
- **Restart**: Reboots the system to operational status after a shutdown or stop.

## How To Run
### Running the JAR File
1. Ensure Java is installed by running `java -version` in your terminal. If not installed, download it from [Oracle's Java SE](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html).
2. Navigate to the directory containing the JAR file: `cd path/to/jar`.
3. Run the JAR file: `java -jar CS5004_Final_Project.jar [number of floors] [number of elevators] [number of requests]`.
    - Example: `java -jar CS5004_Final_Project.jar 10 2 10`.

### Required Arguments
- **number of floors** (`int`): Specifies the total floors in the building.
- **number of elevators** (`int`): Indicates how many elevators are in the building.
- **number of requests** (`int`): Sets the limit on simultaneous requests an elevator can handle.

## How to Use the Program
1. Start the program using the above run instructions.
2. The interface displays the current elevator statuses.
3. Begin operation by clicking `Start`.
4. To manually simulate elevator movement, click `Step` repeatedly or use `N Step` to progress multiple steps.
5. Generate elevator requests using the dropdown menus for `from floor` and `to floor`, then send by clicking `send request`.
6. Automatically generate requests by inputting the number of requests and clicking `Generate N requests`.
7. Use `Stop` to halt all elevator activity or `Shutdown` to power down the system.
8. Restart the system by clicking `Restart`, which only appears after a stop or shutdown.
9. Exit the program via the 'exit' button.

## Design/Model Changes
### Version 2.0
- Introduced a Controller and graphical View to enhance user interaction and system manageability.
- **Rationale**: These components were added to visually represent the elevator operations and to facilitate easier user interaction with the simulation.

## Assumptions
- Assumes that all elevators and the building's infrastructure operate without mechanical faults.
- Assumes user interaction for request generation and system commands.

## Limitations
- Elevators can only accept new requests from the top or bottom floors.
- Requests are generated without a real-time algorithm, potentially affecting efficiency.
- The system does not track elevator occupancy, limiting its realism.

## Citations
- Lee, A. (2024). [Java GUI Tutorial - Make a Login GUI]. Retrieved from [YouTube](https://www.youtube.com/watch?v=iE8tZ0hn2Ws&ab_channel=AlexLee).
- Bro Code (2021). [Java GUI: Full Course ☕]. Retrieved from [YouTube](https://www.youtube.com/watch?v=Kmgo00avvEw&t=20s).

No additional external resources were consulted for the development of this project beyond the above-listed citation.
