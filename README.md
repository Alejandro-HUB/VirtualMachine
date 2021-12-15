# VirtualMachine

A simulated virtual machine called SharkOS that has registers that work to go around the array(memory) where all the microinstructions are stored. The programs start loading the programs from txt files that have the microinstructions inside them, and with the static value of a starting point as an index, the programs get stored inside the memory array. Once the programs are loaded, the timer will start which means that microinstructions have begun to make operations inside the machine’s memory through the Programs that were loaded in. Each program has an arrival time, which was supposed to work as a round robin operation, however, due to the time scope allocated I could not implement that the same way. Instead, based on the program’s arrival time whichever one is first starts running, then when a program arrives at a certain arrival time, the initial program gets interrupted. The initial program saves a temporary PSIAR and ACC value to resume working after all the jobs finish arriving. Each time a new job “arrives”, the previous one gets interrupted and lets the one that arrived run. Once all arrival times are finished, the machine will resume working on the programs that were interrupted.