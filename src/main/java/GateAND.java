public class GateAND extends Device
{
    @Override
    public boolean getOutput()
    {
        boolean outputValue = iPins.get(0).getOutput();

        for(int i = 1; i < iPins.size(); i++)
        {
            outputValue = outputValue & iPins.get(i).getOutput();
        }

        return outputValue;
    }
}
