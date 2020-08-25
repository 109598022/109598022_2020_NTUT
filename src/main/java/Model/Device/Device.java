import java.util.Vector;

public class Device
{
    protected Vector<Device> iPins;

    public Device()
    {
        iPins = new Vector<>();
    }

    public void addInputPin(Device iPin)
    {
        iPins.add(iPin);
    }

    public void setInput(boolean value)
    {

    }

    public boolean getOutput()
    {
        return false;
    }
}