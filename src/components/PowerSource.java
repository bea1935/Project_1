package components;

/**
 * This class represents the power source of the system
 * and contains a unique engage system.
 *
 * @author Beck Anderson
 */
public class PowerSource extends Component{

    /**
     * The constructor for the power source
     * @param name the name of the power source
     */
    public PowerSource(String name) {
        super(name, null);
        Reporter.report(this, Reporter.Msg.CREATING);
    }

    /**
     * This function will enable all engaged parts connected
     * to the power source
     */
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
