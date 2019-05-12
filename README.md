# ZYBO-JavaKlient
A java program which was part of my exam project for ["62575 Security in Embedded Systems"](https://kurser.dtu.dk/course/62575). The Java program is simulating a piece of software, which requires 2-step authentication inform of a username/password and a special piece of hardware. The hardware is called a "software protection dongle" which the group implemented on the FPGA ZYBO board. The ZYBO's job is essentially to run a C program that makes serial communication over UART to the Java program and the C program will (when asked for it) do encryption/decryption of a string/ciphertext with the symmetric crypto algorithm "Chacha20" and return the result to the Java program.

The C program can do the algorithm in C OR it can do it with our hardware implementation in VHDL. These two different approaches to running the same algorithm with or without hardware-acceleration is the experiment of the project, and therefore we implemented a "benchmark" test, that the Java program can tell the ZYBO to start. Then the ZYBO will do a random amount of encrypts both in software and hardware and the java program will keep track of the time. This can be seen on the graph below. It is easy to see, that the hardware is 3-4 times faster than the software implementation on the "650 MHz dual-core Cortex™-A9 processor" of the [ZYBO](https://store.digilentinc.com/zybo-zynq-7000-arm-fpga-soc-trainer-board/)

<img src="https://github.com/ValarMarkhulis/ZYBO-JavaKlient/blob/master/pictures/benchmark.PNG" width="50%" height="50%">


--------------------------
Here is a picture of the login screen, where the user haven't plugged in the ZYBO board yet.

<img src="https://github.com/ValarMarkhulis/ZYBO-JavaKlient/blob/master/pictures/Login%20page%20with%20no%20connected%20ZYBO.PNG" width="50%" height="50%">

Here is a picture of the login screen, where the user have plugged in the ZYBO board and the program is trying to autoconnect to it.

<img src="https://github.com/ValarMarkhulis/ZYBO-JavaKlient/blob/master/pictures/Login%20page%20auto%20detection%20of%20%20ZYBO.PNG" width="50%" height="50%">


Here is a picture of the login screen, where the user have plugged in the ZYBO board and the program have succesfully been unlocked.

<img src="https://github.com/ValarMarkhulis/ZYBO-JavaKlient/blob/master/pictures/Login%20page%20with%20connected%20ZYBO.PNG" width="50%" height="50%">

------------------------------



Here is two pictures of the "control panel", where the user have asked the ZYBO to generate two different random numbers, and it can be seen that two strings have been encrypted and decrypted again. The strings are identical, besides the first char. Try to look, at the first octel of the ciphertext.. 

<img src="https://github.com/ValarMarkhulis/ZYBO-JavaKlient/blob/master/pictures/Program%20tab%201%20button%20pressed.PNG" width="60%" height="60%"><img src="https://github.com/ValarMarkhulis/ZYBO-JavaKlient/blob/master/pictures/Program%20tab%201%20button%20pressed%20new%20value.PNG" width="60%" height="60%">

The "tab 2" is just showing the communication between the java program and ZYBO:
<img src="https://github.com/ValarMarkhulis/ZYBO-JavaKlient/blob/master/pictures/Program%20tab%202%20data%20sent.PNG" width="100%" height="100%">

