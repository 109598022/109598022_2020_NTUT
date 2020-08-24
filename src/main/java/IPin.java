public class IPin extends Device
{
    private boolean inputValue;

    @Override
    public void setInput(boolean value)
    {
        this.inputValue = value;
    }

    @Override
    public boolean getOutput()
    {
        return inputValue;
    }
}
