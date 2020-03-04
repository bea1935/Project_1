package components;

/**
 * This class represent an outlet and contains the
 * special uses of the functions attach and change draw,
 * along with its constructor
 *
 * @author Beck Anderson
 */

public class Outlet extends Component{

    /**
     * This function will construct an outlet using a name
     * and the source it is connected to and connect it to
     * the system.
     *
     * @param name the name of the component
     * @param source the component that the outlet is
     *               attached to
     */
    public Outlet(String name, Component source) {
        super(name, source);
        attach();
        Reporter.report(this, Reporter.Msg.CREATING);
    }

    /**
     * This function will send a message saying that the
     * component is being attached and attach it
     */
    public void attach(){
        Reporter.report(this.source,this, Reporter.Msg.ATTACHING);
        source.attach(this);
    }

    /**
     * This function will change the draw of the outlet depending on
     * the appliances attached and return the value to the
     * components the outlet is connected to
     *
     * @param rating the amount that the draw is changing by
     */
    public void changeDraw(int rating){
        draw += rating;
        Reporter.report(this, Reporter.Msg.DRAW_CHANGE, rating);
        source.changeDraw(rating);
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
