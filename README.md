The given zip file contains of the following java files
1. AddToRouting.java
2. ForwardTable.java
3. Process.java
4. ProcessDown.java
5. ProcessInterface.java
6. ProcessListen.java
7. ProcessListenInterface.java
8. RouteValues.java
9. TriggerUpdate.java
10. UpdateRouting.java

To run the program, put all the java files in a folder and compile them. Then start the Process.java, this file contains the main program for the server. Be sure that the port 6363 and 6364 on the machine on which you are running the program are free and no other process is running on these ports, as they will be used by the program. When the Process.java starts, it will inform the IP on which the RIP Router process is running. 
It will then ask you to add a link(which is analagous to adding a neighbour) to the current router or print the current routing table of the router running on the current machine. To add a link you need to enter a single character 'y', and to print the current routing table of the router running on the machine you need to enter the single character 'p'. If you enter any other character, it will give you an incorrect input output, which means you have entered something else and you need to enter the right input 'y' or 'p' (without the quotes). After you enter 'y', then the process will ask you to enter the IP address of the router to whom you want to add the link, and then the weight of the link you want to add. Once you have entered all the corrent input details, then the distance vector routing process will start.
The process.java starts the router, which in turns starts the listening process also. When you add a new neighbour, it then adds the details of the new added neighbour to its details, its neighbour list which is maintained to know all the neighbours and updates its routing table. It also alerts the neighbour about itself and starts the exchange of routing table between the neighbour and itself.
When all the neighbours are added, it keeps on updating the routing table for every router process in the backend and it can be checked by printing the routing table of any router at any time. 
When you want to fail a router, you need to stop the process by doing Control+C, this will stop the router process at the system on which the router process was running and it will disconnect the links with all its neighbours. 
When a router realizes that its neighbour has gone down, it then starts the process of removing that router from its routing table, since it is no longer accessible and then triggers an update for all its remaining neighbours, which removes all the links based on the router which has failed. This fastens the process of cconvergence and prevent the problem of count to infinity.

