Turing machine
==========================

This is a project for the Zurich university of applied sciences course 'Computer Science II'

It provides a simple graphical implementation of a multi band TM for Multiplication and Fractorial (using the Multiplication TM).
Since the course is held in German all labels are german, too. Sorry for the inconvenience.

Example:
-------------

In the initial screen of the application you can select "Machine in action" and afterwards "Multiplizuieren" for multiplication or "Fakultät" for fractorial.

![alt text](https://raw.githubusercontent.com/mxmo0rhuhn/Turing-machine/master/doc/overview.png "Overview")

After selecting one of these options you have to provide the input (two numbers that shall be multiplied or one number for that the fractorial should be calculated)

![alt text](https://raw.githubusercontent.com/mxmo0rhuhn/Turing-machine/master/doc/input_fractorial.png "Fractorial input")

The computation can either be run step by step (using "Nächster Schritt" for each step) or automaticly (after selecting "Automatisch"). A automatic computation can be paused using "Stop". To select the speed of the automatic computation use "Machine in action" and choose timeout afterwards. The Timeout is given in milliseconds between each step of the TM.

While the computation is running you can view the state of the turing machine in a state diagram.

![alt text](https://raw.githubusercontent.com/mxmo0rhuhn/Turing-machine/master/doc/computation.png "Configuration while a  computation")

You can also look at the different bands and the movement of the heads (green if the head went to the right or orange if it went to the left)

![alt text](https://raw.githubusercontent.com/mxmo0rhuhn/Turing-machine/master/doc/running_fractorial.png "Running computation")

As soon as the computation is finished you can see the result as well as the number of steps taken in the top of the GUI.


Results:
------------

```
With GUI:
0!: Result: 1       Steps: 1             Elapsed time:   < 1 Second
1!: Result: 1       Steps: 6             Elapsed time:   < 1 Second
2!: Result: 2       Steps: 44            Elapsed time:   < 1 Second
3!: Result: 6       Steps: 185           Elapsed time:   < 1 Second
4!: Result: 24      Steps: 1'276         Elapsed time:   < 1 Second
5!: Result: 120     Steps: 21'021        Elapsed time:   < 1 Second
6!: Result: 720     Steps: 678'668       Elapsed time:   < 1 Second
7!: Result: 5040    Steps: 32'610'385    Elapsed time:     4 Seconds
8!: Result: 40320   Steps: 2'081'079'132 Elapsed time:   291 Seconds

Without Without:
0!: Result: 1       Steps: 1             Elapsed time:   < 1 Second
1!: Result: 1       Steps: 6             Elapsed time:   < 1 Second
2!: Result: 2       Steps: 44            Elapsed time:   < 1 Second
3!: Result: 6       Steps: 185           Elapsed time:   < 1 Second
4!: Result: 24      Steps: 1'276         Elapsed time:   < 1 Second
5!: Result: 120     Steps: 21'021        Elapsed time:   < 1 Second
6!: Result: 720     Steps: 678'668       Elapsed time:   < 1 Second
7!: Result: 5040    Steps: 32'610'385    Elapsed time:     1 Second
8!: Result: 40320   Steps: 2'081'079'132 Elapsed time:   121 Seconds
9!: Result: 362880  Steps: 1'002'179'117 Elapsed time: 20645 Seconds (5h 41m 5s)
```
Please note that the number of steps in the computation of 9! is obviously wrong. Most likely there is an integer overflow in the counter.

License:
-------------

This project is free software: You can redistribute it and/or modify it under the terms of the European Union Public Licence (EUPL v.1.1) or – as soon they will be approved by the European Commission - subsequent versions of the EUPL (the "Licence"); You may not use this work except in compliance with the Licence.

The project is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
See the Licence for the specific language governing permissions and limitations under the Licence.
