# Elevator System
This project simulates an elevator system that can handle requests from different floors. It includes the following components:

- ElevatorSystem: An interface defining the main operations of the elevator system.
- SystemOne: An implementation of the ElevatorSystem interface, managing multiple elevators and handling requests efficiently.
- Elevator: Represents an elevator with functionalities like moving, handling requests, and managing passengers.
- ElevatorVisualization: Provides a graphical representation of the elevator system, allowing users to interact with elevators by making requests.
- ElevatorInputer: GUI interface for users to input the number of elevators and the highest floor, initiating the simulation.
## Usage
To run the simulation:

- Execute the ElevatorInputer class.
- Enter the desired number of elevators and the highest floor.
- Press button to start the simulation.

The visualization window will display the elevators' current status and allow users to make requests. 
On arrival of the elevator, the decision where to go is chosen randomly.

Enjoy simulating your elevator system!

____________________________________________________________________________________________________________________________________________

## ElevatorSystem Interface
The ElevatorSystem interface defines the main operations that any implementation of an elevator system must support. These operations include:

- startSimulation(): Initiates the elevator system simulation.
- stopSimulation(): Stops the simulation.
- pickup(int floor, int direction): Registers a pickup request from a specified floor and direction (up or down).
- update(): Updates the status of the elevator system.
- step(): Moves the simulation one step forward.
- status(): Retrieves the current status of all elevators.
- getUpRequests(int floor): Retrieves the number of up requests from a specified floor.
- getDownRequests(int floor): Retrieves the number of down requests from a specified floor.
- getDestRequest(int floor): Retrieves the number of destination requests for a specified floor.
## SystemOne Implementation
The SystemOne class implements the ElevatorSystem interface, providing a concrete implementation of the elevator system. Key features of SystemOne include:

- Elevator Management: inspiration here was a [LOOK algorithm](https://www.geeksforgeeks.org/look-disk-scheduling-algorithm/), my idea here was to think direction-wise. 
While the elevator progresses in a particular direction, it selectively attends to requests aligned with that trajectory, optimizing for smooth flow and minimal interruption. As it exhausts immediate demands in its path, it gracefully pivots to accommodate any pending requests in the reverse direction, ensuring short waiting time fo all passengers.<br>
In instances where there are no pending passenger requests, the system seamlessly transitions into a poised state, maintaining its position until new requests emerge. This dynamic adaptation not only enhances operational fluidity but is also minimizing wait times and optimizing travel efficiency.
- Visualization: Utilizes the ElevatorVisualization class to provide a graphical representation of the elevator system, facilitating user interaction and monitoring.
- Dynamic Request Handling: Dynamically assigns pickup requests to the most suitable elevator based on load and direction.
## Elevator Class
The Elevator class represents an individual elevator within the system. It encapsulates functionalities such as:

- Movement: Controls the movement of the elevator between floors, considering pickup requests and passenger destinations.
- Request Handling: Manages pickup requests and assigns destinations for passengers.
- Load Management: Tracks the current load of the elevator and distributes passengers efficiently.

