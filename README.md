# Elevator System Simulator

## About/Overview
This program is used to build a simulation of an elevator system. 
The elevator system consists of a building with multiple floors and a set of elevators that move between these floors. 
The program simulates the movement of the elevators in response to user requests. 
The elevators can accept requests to move up to a certain floor at the bottom of the building, 
or accept requests to move down to a certain floor at the top of the building.

The goal of the simulation is to simulate the simple elevator system and to provide a visual representation of the elevator movement.

## List of Features
- **start**: The elevator is default to at out of service status, it will start the system.
- **step**: This is one step movement for the system.
- **n step**: This is n steps movement for the system. n is a integer from user input.
- **request**: It will send a request from 'from floor' to 'to floor'. The certain floor is acquired from user input.
- **n request**: It will automatically generating n request where half is up and other is down. n is a integer from user input.
- **stop**: The elevator is set to stopping status and will immediately moving to ground floor. It will not take any request.
- **shutdown**: The elevator is set to out of service status and will immediately moving to ground floor. It will not take any request.
- **restart**: The elevator will restart the system and set to running status.


## How To Run
### Running the JAR File
1. Ensure Java is installed: Run `java -version` in your terminal. If Java is not installed, [download Java](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html).
2. Navigate to the JAR file directory: `cd path/to/jar`.
3. Execute the JAR file: `java -jar CS5004_Final_Project.jar [arguments]`.
4. If no arguments are provided, the program will run with default values.
5. Three arguments are required to run the program:
    - `java -jar CS5004_Final_Project.jar [number of floors] [number of elevators] [number of requests]`.
    - Example: `java -jar CS5004_Final_Project.jar 10 2 10`.

### Arguments
- **number of floors** (`int`): The number of floors of the building.
- **number of elevators** (`int`): The number of elevators of the building.
- **number of request** (`int`): The maximum number of requests that one elevator can take each time.

## How to Use the Program
1. Run the program using the instructions above.
2. The program will display the current status of the elevator system.
3. Click the button 'Start' to start the system.
4. If there is no request, click the 'step' button continuously to see the elevator movement.
5. From the dropdown menu, select the 'from floor' and 'to floor' to send a request.
6. Click the 'send request' button to send the request.
7. Click the 'stop' button to stop the elevator.
8. Click the 'shutdown' button to shut down the elevator.
9. The 'restart' button will show and click it to restart the system. Then it will disappear after the system is running.
10. Entering a input of n and click the 'n step' button to move n steps.
11. Entering a input of n and click the 'n request' button to generate n requests.

## Design/Model Changes
Document significant changes made from the initial design or previous versions. Explain why these changes were necessary.
- **Version 1.0**:
    - Initial release.
- **Version 2.0**:
    - **Change**: Description of what was changed.
    - **Reason**: Explanation of why this change was made.

## Assumptions
List assumptions made during the development and implementation:
- **Assumption 1**: Detail the assumption and its justification.
- **Assumption 2**: Detail the assumption and its justification.

## Limitations
Detail limitations of your program:
- **Limitation 1**: Description of the limitation and under what circumstances it might affect users.
- **Limitation 2**: Description of the limitation and under what circumstances it might affect users.

## Citations
Include references used during research and development. If no citations are necessary, indicate this explicitly.
- **Citation Format**: Author(s). (Year). Title of document. [Format description]. Retrieved from http://...
- If no external resources were used, state "No external citations were necessary for the development of this project."

