# Bachelor thesis faculty project
_**Single-page**_ web application for organizing mini football appointments.
* /frontend directory represents the client application which is implemented by using _**React**_ JavaScript library.
* /backend directory represents the server application which is implemented in _**Java**_ programming language and _**Spring/Spring Boot**_ frameworks.
  * Building _**REST**_ APIs that expose resources to the client-server communication.
  * Multilayer architecture: Controller, Service, Repository (Data Access Layer).
  * Exception handling (mapping exceptions to appropriate HTTP responses using Spring built-in mechanism).
* Data is being persisted in _**MySQL**_ database.

Short description of how the application works:
* There are two types of users: 1) player, 2) manager of the sports facility.
* The only job of a sports facility manager is to add his/hers sports facility (its location, name, price per hour, etc.).
* Each player registered in the system has the option to create a group of players. Group of players is nothing more than a set of players which form that group. When some player creates a group of players, that player is automatically the administrator of that group.
* Within the group of players, the group administrator can create appointments. When creating an appointment, the group administrator specifies the date and the time of the event, its privacy (private - accessible only to the players who are members of the group, public - accessible to all visitors of the web application), capacity and price.
* After the appointment is finished, the group administrator has the option to add games within that appointment. When adding games, the group administrator specifies which players played in a blue team, which players played in a red team, and which players scored goals.
* Also, there is an option for the group/appointment administrator to change the appointment's privacy to "public" at any time. The purpose of this is to create a way to attract other players to apply to the appointment, when there aren't enough players from the group who applied.

The development of the application included multiple phases:
1) User requirements analysis.
2) Use-case modeling.
3) Modeling class diagram.
4) Making the prototype of the user interface (designing UI mockups using _**Figma**_ online tool).
5) Implementation.
