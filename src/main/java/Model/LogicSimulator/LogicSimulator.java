import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

public class LogicSimulator
{
    private Vector<Device> circuits = new Vector<>();
    private Vector<Device> iPin = new Vector<>();
    private Vector<Device> oPin = new Vector<>();

    public LogicSimulator()
    {

    }

    public boolean load(String file)
    {
        boolean isLoad = false;
        FileReader fileReader = null;
        StringBuilder contentBuilder = new StringBuilder();
        String command;

        System.out.println(file);

        try
        {
            fileReader = new FileReader(file);
        }
        catch(FileNotFoundException e)
        {
            System.out.println("File not found or file format error!!\n");
            return isLoad;
        }

        try (BufferedReader bufferReader = new BufferedReader(fileReader))
        {

            String sCurrentLine;
            while ((sCurrentLine = bufferReader.readLine()) != null)
            {
                contentBuilder.append(sCurrentLine).append("\n");
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        command = contentBuilder.toString();
        String[] commandSplit = command.split("\n");

        //for(int i = 0; i < commandSplit.length; i++)
        //    System.out.println(commandSplit[i]);

        setIPin(commandSplit[0]);

        int gateNumber = Integer.parseInt(commandSplit[1]);

        //System.out.println(gateNumber);

        boolean[] isOut = new boolean[gateNumber];
        for(int i = 0; i < gateNumber; i++)
            isOut[i] = false;

        for(int i = 0; i < gateNumber; i++)
            setDevice(commandSplit[i + 2]);
        for(int i = 0; i < gateNumber; i++)
            setGate(commandSplit[i + 2], i, isOut);

        for(int i = 0; i < gateNumber; i++)
        {
            if(!isOut[i])
            {
                OPin oPin1 = new OPin();
                oPin1.addInputPin(circuits.get(i));
                oPin.add(oPin1);
            }
        }

        isLoad = true;
        return isLoad;
    }

    public boolean setIPin(String command)
    {
        boolean isSet = false;

        int iPinNumber = Integer.parseInt(command);
        //System.out.println(iPinNumber);

        for(int i = 0; i < iPinNumber; i++)
        {
            IPin iPin1 = new IPin();
            iPin.add(iPin1);
        }

        return isSet;
    }

    public boolean setDevice(String command)
    {
        boolean isSet = false;
        String[] commandSplit = command.split(" ");
        Device device = null;

        //System.out.println(command);

        switch (commandSplit[0])
        {
            case "1":
                device = new GateAND();
                //System.out.println(gateNum + ": AND");
                break;
            case "2":
                device = new GateOR();
                //System.out.println(gateNum + ": OR");
                break;
            case "3":
                device = new GateNOT();
                //System.out.println(gateNum + ": NOT");
                break;
        }

        circuits.add(device);

        return isSet;
    }

    public boolean setGate(String command, int gateNum, boolean[] isOut)
    {
        boolean isSet = false;
        String[] commandSplit = command.split(" ");
        int count = 1;

        while(!commandSplit[count].equals("0"))
        {
            double doubleCommandLine = Double.parseDouble(commandSplit[count]);
            int commandLine = (int)doubleCommandLine;

            //System.out.println(commandLine);

            if(commandLine < 0)
            {
                commandLine *= -1;
                circuits.get(gateNum).addInputPin(iPin.get(--commandLine));
            }
            else
            {
                commandLine -= 1;

                //System.out.println(circuits.get(commandLine).toString());

                circuits.get(gateNum).addInputPin(circuits.get(commandLine));
                isOut[commandLine] = true;
            }

            count++;
        }

        return isSet;
    }

    public String getSimulationResult(Vector<Boolean> booleans)
    {
        String simulationResult = "";

        simulationResult += "Simulation Result:\n";

        //System.out.println("iPin size: " + iPin.size());

        for(int i = 0; i < iPin.size(); i++)
            simulationResult += "i ";
        simulationResult += "|";
        for(int i = 0; i < oPin.size(); i++)
            simulationResult += " o";
        simulationResult += "\n";

        for(int i = 1; i <= iPin.size(); i++)
            simulationResult = simulationResult + i + " ";
        simulationResult += "|";
        for(int i = 1; i <= oPin.size(); i++)
            simulationResult += " " + i;
        simulationResult += "\n";

        for(int i = 0; i < iPin.size(); i++)
            simulationResult += "--";
        simulationResult += "+";
        for(int i = 0; i < oPin.size(); i++)
            simulationResult += "--";
        simulationResult += "\n";

        for(int i = 0; i < iPin.size(); i++)
            iPin.get(i).setInput(booleans.get(i));

        int result;

        for(int i = 0; i < iPin.size(); i++)
        {
            if(iPin.get(i).getOutput())
                result = 1;
            else
                result = 0;

            simulationResult = simulationResult + result + " ";
        }

        simulationResult += "|";

        for(int i = 0; i < oPin.size(); i++)
        {
            if(oPin.get(i).getOutput())
                result = 1;
            else
                result = 0;

            simulationResult = simulationResult + " " + result;
        }

        simulationResult += "\n";

        System.out.println(simulationResult);

        return simulationResult;
    }

    public String getTruthTable()
    {
        String truthTable = "";

        truthTable += "Truth table:\n";

        for(int i = 0; i < iPin.size(); i++)
            truthTable += "i ";
        truthTable += "|";
        for(int i = 0; i < oPin.size(); i++)
            truthTable += " o";
        truthTable += "\n";

        for(int i = 1; i <= iPin.size(); i++)
            truthTable = truthTable + i + " ";
        truthTable += "|";
        for(int i = 1; i <= oPin.size(); i++)
            truthTable += " " + i;
        truthTable += "\n";

        for(int i = 0; i < iPin.size(); i++)
            truthTable += "--";
        truthTable += "+";
        for(int i = 0; i < oPin.size(); i++)
            truthTable += "--";
        truthTable += "\n";

        boolean[] booleans = new boolean[iPin.size()];

        for(int i = 0; i < iPin.size(); i++)
            booleans[i] = false;

        for(int i = 0; i < Math.pow(2, iPin.size()); i++)
        {
            if(i != 0)
            {
                for (int k = iPin.size() - 1; k >= 0; k--)
                {
                    if (!booleans[k])
                    {
                        booleans[k] = true;
                        break;
                    }
                    else
                    {
                        booleans[k] = false;
                    }
                }
            }

            for(int j = 0; j < iPin.size(); j++)
                iPin.get(j).setInput(booleans[j]);

            int result;

            for(int j = 0; j < iPin.size(); j++)
            {
                if(iPin.get(j).getOutput())
                    result = 1;
                else
                    result = 0;

                truthTable = truthTable + result + " ";
            }

            truthTable += "|";

            for(int j = 0; j < oPin.size(); j++)
            {
                if(oPin.get(j).getOutput())
                    result = 1;
                else
                    result = 0;

                truthTable = truthTable + " " + result;
            }

            truthTable += "\n";
        }

        System.out.println(truthTable);

        return truthTable;
    }

    public String outLine()
    {
        String output = "";

        output += "Circuit: " + iPin.size() + " input pins, " + oPin.size() + " output pins and " + circuits.size() + " gates ";

        return output;
    }
}