/*
Author: Alejandro Lopez
Class: CSIS 3810 L01
Started Working on Project: 09/24/2021
Stopped Working on Project: 10/18/2021
 */

import groovy.lang.GString;

import java.io.*;
import java.util.Scanner;


public class MainMemory {

    //SET UP TO TEST - PLEASE READ ALL

    // File + Starting location for program
    public String[] Programs = new String[] {"src/Resource/Program1.txt 10", "src/Resource/Program2.txt 36", "src/Resource/Program3.txt 70"
            ,"src/Resource/ProgramRequired1.txt 100", "src/Resource/ProgramRequired2.txt 500", "src/Resource/ProgramRequired3.txt 600"}; //The ProgramRequired(n) are the ones listed on Design_Prog_Project_Assign_1.pdf

    // THE ARRIVAL TIME IS BASED OFF THE MICRO INSTRUCTIONS ON EACH PROGRAM
    // THE FIRST PROGRAM MUST ARRIVE AT -1

    public int[] arrivalTimes = new int[] {-1, 6, 11, 17, 23, 29};


    //Registers
    public int ACC;
    public int PSIAR;
    public int TMPR;
    public int CSIAR;
    public int IR;
    public int MIR ;
    public int SDR;
    public int SAR;

    //Varibles USED
    public String raw_data;
    public String op_code;
    public String value;
    public String[] memory = new String[1024];
    public String[] Instructions = new String[1024];
    public int[] Timer = new int[2000];
    public int[] PSIARS = new int[Programs.length];
    public int stores = 0;
    public int[] STORES = new int[1024];
    public boolean jobDone;
    public String fileNameOrig;
    public int startProgramIndex;
    public int startingPoint;
    public int programNumber;
    public int arrivalTimeNumber;

    //Variables NOT USED
    public int endingPoint; //Kept in case might need later


    public void SharkOS()
    {
        INIT_System();
        RUN_SHARKOS();
    }

    void INIT_System()
    {
        //Initializing variables
        stores = 0;
        fileNameOrig = "";
        startProgramIndex = 0;
        startingPoint = 0;
        endingPoint = 0;
        programNumber = 0;
        raw_data = "";
        op_code = "";
        value = "";
        jobDone = false;
        arrivalTimeNumber = 3;


        //Initialize Registers
        ACC = 0;
        PSIAR = 0;
        TMPR = 0;
        CSIAR = 0;
        IR = 0;
        MIR = 0;
        SDR = 0;
        SAR = 0;



        //Initializing Arrays
        for(int i = 0; i < memory.length; i++)
        {
            memory[i] = "";
        }
        for(int i = 0; i < Timer.length; i++)
        {
            Timer[i] = 0;
        }
        for(int i = 0; i < STORES.length; i++)
        {
            STORES[i] = 0;
        }

    }

    void RUN_SHARKOS()
    {
        System.out.println("                ** SharkOS **                ");
        System.out.println("---------------------------------------------");
        System.out.print("Loading programs.....\n");
        System.out.println("---------------------------------------------");

        //Load Programs
        LOAD_SHARKOS_PROGRAMS();

        //Printing array after the programs are loaded
        for(int i = 0; i < 1024; i++)
        {
            for(int j = 0; j <PSIARS.length; j++)
            {
                if(i == (PSIARS[j] - 1))
                {
                    System.out.println("\nProgram " + (j+1) + " is loaded:\n");
                }
                else
                {
                    continue;
                }
            }
            if(memory[i] != null && memory[i] != "")
            {
                System.out.println((i) + ": " + memory[(i)]);
            }
        }
        System.out.println();

        //Run Programs
        INSTRUCTION_SETUP();

        //Display after running programs
        System.out.println("\n---------------------------------------------");
        System.out.println("Fetching Results.....");
        System.out.println("---------------------------------------------");

        int repeat1 = 0;
        int repeat2 = 0;


        System.out.println("Store points with the results: ");
        for(int i = 0; i < 1024; i++)
        {
            if(i == 29)
            {
                System.out.println("\nProgram 1 results:\n");
            }
            else if(i == 54)
            {
                System.out.println("\nProgram 2 results:\n");
            }
            else if(i == 59)
            {
                System.out.println("\nProgram 3 results:\n");
            }
            else if(i == 199)
            {
                System.out.println("\nProgram 4 results:\n");
            }
            else if(i == 201)
            {
                System.out.println("\nProgram 5 results:\n");
                System.out.println((i) + ": " + memory[(i)]);
            }
            else if(i == 300)
            {
                System.out.println("\nProgram 6 results:\n");
            }

            for(int j = 0; j < STORES.length; j++)
            {
                if((302 == STORES[j]) & (302 == i))
                {
                    repeat1 += 1;
                }
                else if((304 == STORES[j]) & (i == 304))
                {
                    repeat2 += 1;
                }
                if((i == STORES[j]) & (STORES[j] != 0) & (STORES[j] != 302) & (STORES[j] != 304))
                {
                    System.out.println(i + ": " + memory[STORES[j]]);
                }
                if((repeat1 < 2) & (i == 302) & (STORES[j] == 302))
                {
                    System.out.println(i + ": " + memory[STORES[j]]);
                }
                else if((repeat2 < 2) & (i == 304) & (STORES[j] == 304))
                {
                    System.out.println(i + ": " + memory[STORES[j]]);
                }

            }
        }

        System.out.println("\n---------------------------------------------");
        System.out.println("All the populated memory: ");
        System.out.println("---------------------------------------------");
        for(int i = 0; i < 1024; i++)
        {
            if(memory[i] != null && memory[i] != "")
            {
                System.out.println((i) + ": " + memory[(i)]);
            }
        }



        EXIT_SHARKOS();
    }

    void LOAD_SHARKOS_PROGRAMS()
    {
        //Values to go through 2d array containing program and PSIAR
        String fileName = "";

        for(int i = 0; i < Programs.length; i++)
        {
            divideProgram(i);
            PSIAR = startProgramIndex;
            fileName = fileNameOrig;

            //Getting all the PSIARS
            PSIARS[i] = startProgramIndex;

            //Getting location of first program ran and last program ran
            if(i == 0)
            {
                startingPoint = startProgramIndex;
            }
            else if(i == (Programs.length - 1))
            {
                endingPoint = startProgramIndex;
            }

            if(arrivalTimes[i] == -1)
            {
                startingPoint = startProgramIndex;
            }


            //Variables for the length of the file
            int addInstruction = 0;

            //Variables to store data as ints
            String OpCode = "";

            //Start logic
            File file = new File(fileName);
            System.out.println("Program Loaded: " + fileName + "\n\t◘ Location: " + PSIARS[i] + "\n\t◘ Scheduled Arrival Time: " + arrivalTimes[i]);
            addInstruction = PSIAR;

            try
            {
                Scanner scanner = new Scanner(file);
                while (scanner.hasNextLine())
                {
                    // Variables to store the procedures
                    raw_data = scanner.nextLine();

                    //Divide the string
                    divideInstruction();

                    //Determining which instruction needs to run
                    OpCode = GET_OPCODE(OpCode, op_code);


                    //Storing inside the array
                    memory[(addInstruction)] = value;
                    Instructions[(addInstruction)] = OpCode;
                    //System.out.println((addInstruction) + ": " + Instructions[addInstruction] + memory[(addInstruction)]);
                    addInstruction += 1;
                }
                jobDone = true;
            }
            catch(FileNotFoundException e)
            {
                e.printStackTrace();
            }
            if(programNumber < Programs.length)
            {
                programNumber += 1;
            }
        }

    }

    void INSTRUCTION_SETUP()
    {
        int instruction = 0;
        System.out.println("---------------------------------------------");
        System.out.println("Instructions execution begin.....");
        System.out.println("---------------------------------------------");
        LOAD_SHARKOS_PROGRAMS();
        System.out.println();
        PSIAR = startingPoint;
        int i = 0;
        int j = 0;
        int k = 0;
        int l = 0;
        int[] PSIARTemp = new int[arrivalTimes.length];
        int[] ACCTemp = new int[arrivalTimes.length];
        boolean printNextLine = true;
        boolean interrupt = false;
        boolean working = true;

        //If there are still programs to be ran then keep working
            while(working)
            {
                Timer[j] = j;

                // Check if a program arrived and based on its arrival time, save the previous PSIAR and ACC as TEMP and continue to the program that arrived
                for(int m = 0; m < arrivalTimes.length; m++)
                {
                    if( (Timer[j] == arrivalTimes[m]) && (PSIAR != startingPoint) )
                    {
                        PSIARTemp[k] = PSIAR;
                        ACCTemp[k] = ACC;
                        printNextLine = false;
                        interrupt = true;
                        System.out.println("\n---------------------------------------------");
                        System.out.println("INTERRUPT DUE TO ARRIVAL AT TIME: " + Timer[j] + "\nSAVE PSIAR TEMP: " + PSIARTemp[k]);
                        PSIAR = GETPSIAR(Timer[j]);
                        System.out.println("STARTING PROGRAM AT PSIAR: " + PSIAR);
                        System.out.println("---------------------------------------------\n");
                        k++;
                    }
                }


                //Get CSIAR and display current instruction being executed
                printNextLine = true;
                System.out.println("Time: " + Timer[j]);
                instruction = Integer.parseInt(memory[PSIAR]);
                System.out.println("Instruction from memory " + Instructions[PSIAR] + instruction + " at location " + PSIAR);
                CSIAR = Integer.parseInt(Instructions[PSIAR]);

                if(CSIAR != 0)
                {
                    SDR = Integer.parseInt(memory[PSIAR]);
                }
                else
                {
                    SDR = 0;
                }
                //Get CSIAR to go through each microinstruction based on its OpCode
                switch (CSIAR)
                {
                    case 1:
                        if(printNextLine)
                        {
                            System.out.println(CSIAR + " - ADD, " + "VALUE - " + instruction);
                        }
                        ADD();
                        j++;
                        break;
                    case 2:
                        if(printNextLine)
                        {
                            System.out.println(CSIAR + " - STR, " + "VALUE - "  + instruction);
                        }
                        STR();
                        STORES[stores] = instruction;
                        stores++;

                        j++;
                        break;
                    case 3:
                        if(printNextLine)
                        {
                            System.out.println(CSIAR + " - LDI, " + "VALUE - " + instruction);
                        }
                        LDI();
                        j++;
                        break;
                    case 4:
                        if(printNextLine) {
                            System.out.println(CSIAR + " - LDA, " + "VALUE - " + instruction);
                        }
                        LDA();
                        j++;
                        break;
                    case 5:
                        if(printNextLine) {
                            System.out.println(CSIAR + " - SUB, " + "VALUE - " + instruction);
                        }
                        SUB();
                        j++;
                        break;
                    case 6:
                        if(printNextLine) {
                            System.out.println(CSIAR + " - CBR, " + "VALUE - " + instruction);
                        }
                        CBR();
                        j++;
                        break;
                    case 7:
                        if(printNextLine) {
                            System.out.println(CSIAR + " - BRH, " + "VALUE - " + instruction);
                        }
                        BRH();
                        j++;
                        break;

                        //Determine if there are still programs to be ran
                    case 8:
                        j++;
                        i++;
                        programNumber -= 1;
                        System.out.println(CSIAR + " - HALT, " + "VALUE - "  + instruction);
                        working = HALT();

                        //If there is no interrupt then go to the next program
                        if(working && !interrupt)
                        {
                            PSIAR = PSIARS[i];
                        }

                        //If there is an interrupt BUT all the files that interrupted finished running, resume working on programs that did not finish executing
                        else
                        {
                            PSIAR = PSIARTemp[l];
                            ACC = ACCTemp[l];
                            if(PSIAR != 0)
                            {
                                System.out.println("---------------------------------------------");
                                System.out.println("RESUME PROGRAM AT PSIAR: " + PSIAR);
                                System.out.println("---------------------------------------------\n");
                            }
                            if( l < (PSIARTemp.length - 1))
                            {
                                l++;
                            }
                        }


                        break;

                }
            }

    }


    //Get the starting position for each program
    int GETPSIAR(int arTime)
    {
        int temp = 0;

        for(int i = 0; i < arrivalTimes.length; i++)
        {
            if (arTime == arrivalTimes[i])
            {
                if( arrivalTimes[i] == -1)
                {
                    continue;
                }
                temp = PSIARS[i];
                return temp;
            }
        }
        temp = 0;
        System.out.println("System ERROR: ABORT: " + temp);
        System.exit(0);
        return temp;
    }



    //FOR THE MICRO INSTRUCTIONS
    void divideInstruction()
    {
        //Variables for dividing strings
        int begin = 1;
        int end = 0;

        // Cutting the string in half
        while (end != -1)
        {
            end = raw_data.indexOf(" ", begin);
            if ((end < raw_data.length()) && ((begin < end)))
            {
                op_code = raw_data.substring(begin - 1, end + 1);
            }
            else
            {
                value = raw_data.substring(begin - 1);
            }
            begin = end + 2;
            if(raw_data.length() == 4)
            {
                op_code = "HALT";
                value = "0";
            }
        }
    }

    //FOR THE FILE NAME AND STARTING POINT
    void divideProgram(int i)
    {
        //Variables for dividing strings
        int begin = 1;
        int end = 0;
        String tempString = "";

        // Cutting the string in half

        while (end != -1)
        {
            end = Programs[i].indexOf(" ", begin);
            if ((end < Programs[i].length()) && ((begin < end)))
            {
                fileNameOrig = Programs[i].substring(begin - 1, end + 1);
                fileNameOrig = fileNameOrig.replaceAll("\\s", "");
            }
            else
            {
                tempString = Programs[i].substring(begin - 1);
                startProgramIndex = Integer.parseInt(tempString);
            }
            begin = end + 2;
        }


    }

    // Assign an OPCode to each instruction
    String GET_OPCODE(String opCode, String opCodeOrig)
    {
        if( opCodeOrig.equals("ADD "))
        {
            opCode = "1";
        }
        else if( opCodeOrig.equals("STR "))
        {
            opCode = "2";
        }
        else if( opCodeOrig.equals("LDI "))
        {
            opCode = "3";
        }
        else if( opCodeOrig.equals("LDA "))
        {
            opCode = "4";
        }
        else if( opCodeOrig.equals("SUB "))
        {
            opCode = "5";
        }
        else if( opCodeOrig.equals("CBR "))
        {
            opCode = "6";
        }
        else if( opCodeOrig.equals("BRH "))
        {
            opCode = "7";
        }
        else
        {
            opCode = "8";
        }

        return opCode;
    }

    void EXIT_SHARKOS()
    {
        System.exit(0);
    }

    void ADD()
    {
        String SDR_TEMP;
        TMPR=ACC;
        ACC=PSIAR+1;
        PSIAR=ACC;
        ACC=TMPR;
        TMPR=SDR;
        SAR=TMPR;
        SDR_TEMP = String.valueOf(SDR);
        SDR_TEMP = memory[SAR];
        SDR = Integer.parseInt(SDR_TEMP);
        TMPR=SDR;
        ACC=ACC+TMPR;
        CSIAR=0;
    }
    void STR()
    {
        String SDR_TEMP;
        TMPR = ACC;
        ACC = PSIAR + 1;
        PSIAR = ACC;
        ACC = TMPR;
        TMPR = SDR;
        SAR = TMPR;
        SDR = ACC;
        SDR_TEMP = String.valueOf(SDR);
        memory[SAR] = SDR_TEMP;
        SDR = Integer.parseInt(SDR_TEMP);
        CSIAR = 0;
    }

    public void LDA()
    {
        String SDR_TEMP;

        ACC = PSIAR + 1;
        PSIAR = ACC;
        TMPR = SDR;
        SAR = TMPR;
        SDR_TEMP = String.valueOf(SDR);
        SDR_TEMP = memory[SAR];
        SDR = Integer.parseInt(SDR_TEMP);
        ACC = SDR;
        CSIAR = 0;
    }

    public void BRH()
    {
        PSIAR=SDR;
        CSIAR=0;
    }

    public void CBR()
    {
        if(ACC == 0)
        {
            PSIAR = SDR;
        }
        else
        {
            TMPR = ACC;
            ACC = PSIAR + 1;
            PSIAR = ACC;
            ACC = TMPR;
            CSIAR = 0;
        }
    }

    void LDI()
    {
        ACC = PSIAR + 1;
        PSIAR = ACC;
        ACC = SDR;
        CSIAR = 0;
    }

    void SUB()
    {
        String SDR_TEMP;

        TMPR = ACC;
        ACC = PSIAR + 1;
        PSIAR = ACC;
        ACC = TMPR;
        TMPR = SDR;
        SAR = TMPR;
        SDR_TEMP = String.valueOf(SDR);
        SDR_TEMP = memory[SAR];
        SDR = Integer.parseInt(SDR_TEMP);
        TMPR = SDR;
        ACC = ACC - TMPR;
        CSIAR = 0;
    }

    boolean HALT()
    {
        if(programNumber != 0)
        {
            System.out.println("\n---------------------------------------------");
            System.out.println("YIELD TO NEXT PROGRAM");
            System.out.println("---------------------------------------------\n");
            return true;
        }
        else
        {
            return false;
        }
    }

}
