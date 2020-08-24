import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

public class LogicSimulator
{
    private Vector<Device> circuits;
    private Vector<Device> iPin;
    private Vector<Device> oPin;

    public boolean load(String file){
        boolean isLoad = false;
        FileReader fileReader = null;
        StringBuilder contentBuilder = new StringBuilder();
        String command = "";

        try
        {
            fileReader = new FileReader(file);
        }
        catch(FileNotFoundException e)
        {
            System.out.println("File not found or file format error!!\n");
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

        setIPin(commandSplit[0]);

        int gateNumber = Integer.parseInt(commandSplit[1]);
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

        return isLoad;
    }

    public boolean setIPin(String command)
    {
        boolean isSet = false;
        int iPinNumber = Integer.parseInt(command);

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

        switch (commandSplit[0])
        {
            case "1":
                device = new GateAND();
                break;
            case "2":
                device = new GateOR();
                break;
            case "3":
                device = new GateNOT();
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
            int commandLine = Integer.parseInt(commandSplit[count]);

            if(commandLine < 0)
            {
                commandLine *= -1;
                circuits.get(gateNum).addInputPin(iPin.get(commandLine--));
            }
            else
            {
                circuits.get(gateNum).addInputPin(circuits.get(commandLine--));
                isOut[commandLine--] = true;
            }

            count++;
        }

        return isSet;
    }

    public String getSimulationResult(Vector<Boolean> booleans)
    {
        String simulationResult = "";



        return simulationResult;
    }

    public String getTruthTable()
    {
        String truthTable = "";



        return truthTable;
    }
}