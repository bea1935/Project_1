package components;

/**
 * This class represents an appliance connected to the
 * system and contains the special uses attach and changeDraw function
 * and implements the unique functions of getRating, turnOn, turnOff,
 * and isSwitchOn
 *
 * @author Beck Anderson
 */
public class Appliance extends Component{

    /**
     * This is the constructor for the Appliance class
     *
     * @param name the name of the appliance
     * @param source the component the appliance is connected to
     * @param rating the amount of draw the appliance has
     */
    public Appliance(String name, Component source, int rating) {
        super(name, source);
        Reporter.report(this, Reporter.Msg.CREATING);
        attach();
        this.rating = rating;
    }

    /**
     * This will attach this component to the system and print a message
     * to accompany it
     */
    public void attach(){
        Reporter.report(source, this, Reporter.Msg.ATTACHING);
        source.attach(this);
    }

    /**
     * This will get the rating of the appliance
     *
     * @return the rating of the appliance
     */
    public int getRating(){
        return rating;
    }

    /**
     * This will turn on the appliance, change the draw and print a
     * message to accompany it
     */
    public void turnOn(){
        engaged = true;
        Reporter.report(this, Reporter.Msg.SWITCHING_ON);
        source.changeDraw(rating);
    }

    /**
     *This will turn on the appliance, change the draw and print a
     * message to accompany it
     */
    public void turnOff(){
        engaged = false;
        Reporter.report(this, Reporter.Msg.SWITCHING_OFF);
        if(isSwitchOn()) {
            source.changeDraw(rating * -1);
        }
    }

    /**
     * This will change the draw of the system and print a message to
     * accompany it
     *
     * @param rating the amount that the draw is being changed by
     */
    public void changeDraw(int rating){
        draw += rating;
        Reporter.report(this, Reporter.Msg.DRAW_CHANGE, rating);
        System.out.println(toString() +  " changing draw by " + rating);
        source.changeDraw(rating);
    }

    /**
     * This will return if the appliance is engaged or not
     *
     * @return if the appliance is engaged or not
     */
    public boolean isSwitchOn(){
        return engaged();
    }

}
