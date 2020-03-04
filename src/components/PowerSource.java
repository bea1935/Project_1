package components;

public class PowerSource extends Component{

    public PowerSource(String name) {
        super(name, null);
        Reporter.report(this, Reporter.Msg.CREATING);
    }

    public void engage() {
        Reporter.report(this, Reporter.Msg.ENGAGING);
        engaged = true;
        for(Component part:loads){
            Reporter.report(part, Reporter.Msg.ENGAGING);
            part.engaged = true;
        }
    }

    /**
     * This is here to make Overload happy
     */
    @Override
    public void turnOn() {
        System.out.println("this wont happen");
    }

    /**
     * This is here to make Overload happy
     */
    @Override
    public void turnOff() {
        System.out.println("this wont happen");
    }
}
