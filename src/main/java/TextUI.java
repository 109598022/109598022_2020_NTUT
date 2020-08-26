import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Vector;

public class TextUI
{
    public void displayMenu()
    {
        int command = 0;
        Scanner scanner = new Scanner(System.in);
        LogicSimulator ls = new LogicSimulator();
        boolean isLoad = false;

        while(command != 4)
        {
            System.out.print("1. Load logic circuit file\r\n" +
                    "2. Simulation\r\n" +
                    "3. Display truth table\r\n" +
                    "4. Exit\r\n" +
                    "Command:");
            try
            {
                command = scanner.nextInt();
            } catch (InputMismatchException e)
            {
                System.out.println("This command is illegal!!\n");
                command = 4;
            }

            if(command > 0  && command < 4) // 1 2 3 -> processCommand
            {
                isLoad = processCommand(command, scanner, ls, isLoad);
            }
            else if(command == 4) //4 -> Exit
            {
                System.out.println("Goodbye, thanks for using LS.\n");
                break;
            }
            else
            {
                System.out.println("This command does not exist!! Please try again!!\n");
            }
        }

        scanner.close();
    }

    public boolean processCommand(int command, Scanner scanner, LogicSimulator ls, boolean isLoad)
    {
        switch(command)
        {
            case 1: //1. Load logic circuit file
            {
                System.out.print("Please key in a file path:");
                String fileName = scanner.next();

                //System.out.println(fileName);

                ls.clear();
                isLoad = ls.load(fileName);
                String output = "Circuit: " + ls.getIPinSize() + " input pins, " + ls.getOPinSize() + " output pins and " + ls.getCircuitSize() + " gates\n";
                System.out.println(output);

                break;
            }

            case 2: //2. Simulation
            {
                if(isLoad)
                {
                    int iPinNum = ls.getIPinSize();
                    Vector<Boolean> booleans = new Vector<>();

                    for(int i = 1; i <= iPinNum; i++)
                    {
                        System.out.print("Please key in the value of input pin " + i + ": ");

                        String inputNum = "";

                        try
                        {
                            inputNum = scanner.next();
                        }
                        catch (java.util.InputMismatchException e)
                        {
                            System.out.println("The value of input pin must be number");
                        }

                        if(inputNum.equals("0"))
                            booleans.add(false);
                        else if(inputNum.equals("1"))
                            booleans.add(true);
                        else
                        {
                            System.out.println("The value of input pin must be 0/1");
                            i--;
                        }
                    }

                    System.out.print(ls.getSimulationResult(booleans) + "\n");
                }
                else
                    System.out.println("Please load an lcf file, before using this operation.\n");
                break;
            }
            case 3: //3. Display truth table
            {
                if(isLoad)
                {
                    System.out.print(ls.getTruthTable() + "\n");
                }
                else
                    System.out.println("Please load an lcf file, before using this operation.\n");
                break;
            }
        }

        return isLoad;
    }
}
