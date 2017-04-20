# Lightning

Software engineering part 4 project. Visualisation tool for quantum computing result.

Introduction


This project lightening is a software tool been developed using JAVA. The purpose of this project is to provide the visualization functions of graph minor embedding algorithms implied in D-Wave quantum computing system. The software displays the graph of the input file on the left panel and basic information about the graph on the right panel. The left panel allows the users to view the graph in different ways and some interactions can be involved. There is also a drop down menu at the top of the two panel which is used for file processing.

Requirements

Input file must be correct format, please see MWISexample.dwave, MWISexample.QUBO, dw2x.alist for standard input file.

.alist file should be load by load hold function
.dwave / .QUBO files should be open by open function

Installation

Ensure you have Java7 environment running on your system, open the Lightning.jar file directly

Functions and operations	

Use Open File function to choose an input file (.dwave / .QUBO) from the local directory.
Use load host function to choose an host file (.alist) from the local directory.
The left panel will show the graph of the input file after loading. 
The right panel will update the information of the input file.
Click on a line or point in the left panel will show the basic information about the chosen set.
Change in color of the edges indicates different type of edges:
Blue Line: the active edges that connect the physical qubits to form logical qubits.
Green Line: the edges that connect logical qubits.
Grey Line: the host connections in the system.
Red Line: the chosen edge by the user. (Turning red when clicked)

Further development

For the visualization part, a smarter scaler can be added to the zoom in and zoom out function to allow the user to see the graph in a more specific way. The size of the total qubits of the D-Wave system is growing due to the development of the quantum computer; the graph size may  also  increase so the plotting function also needs to be scaled up in the future.  

Modification function should allow user to adjust the algorithm while using Lightning. In the condition of clicking one point, Lightning should show possible solutions for minor embedding. This is designed to be “drag and release” operation mode, which increase the ease of operation. The brief structure of program should remain unchanged after add this function. This is under the consideration about unity of user interface between different versions of software.

The modification by the users should be reflected to the users in a more effective way so they can realize the changes they have done to the graph. Also, there should be an algorithm to generate the possible modifications can be done to each part of the graph which is a  complicated  task in the future development.  

More operations should available to user. Currently the zoom in and zoom out function are fixed in three scaling levels. This is for the convenience of points coordinate calculation. Later on development of Lightning should consider the operating smoothness by using some advanced coordinate calculation algorithm. 

Customization function should also be added to Lightning. In the situation of colour-blind, or client’s visual preference, user should be allowed to customize the user interface. On the other hand, user may want to customize the information’s arrangement on interface, supporting the movable window elements is a great idea to increase the usability of Lightning.

There also should be some test cases added in the future because the functions are getting more complicated due to the new add on. We have not written the unit test for each function in our software due to the limitation of time. However, there should be unit tests to validate the correctness and secure the robustness of the software.
