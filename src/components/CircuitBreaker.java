package components;

/**
 * This class represents a circuit breaker and contains
 * the special uses of attach and changeDraw and implements
 * the unique ones of isSwitchedOn, getLimit, and turnOn
 *
 * @author Beck Anderson
 */

public class CircuitBreaker extends Component{

    protected int limit;

    /**
     * This is the constructor for the CircuitBreaker class
     *
     * @param name the name of the component
     * @param source the component that this one is connected to
     * @param limit the maximum draw that the circuit breaker can endure
     */
    public CircuitBreaker(String name, Component source, int limit) {
        super(name, source);
        Reporter.report(this, Reporter.Msg.CREATING);
        attach();
        this.limit = limit;
    }

    /**
     * This function will attach this to a component and print
     * out a message to accompany it
     */
    public void attach(){
        Reporter.report(this.source, this, Reporter.Msg.ATTACHING);
        source.attach(this);
    }

    /**
     * This will return if the circuit breaker is on or not
     *
     * @return whether it is on or not
     */
    public boolean isSwitchOn(){
        return engaged();
    }

    /**
     * This will return the limit of the circuit breaker
     *
     * @return the limit of the circuit breaker
     */
    public int getLimit(){
        return limit;
    }

    /**
     * This function will turn on the circuit breaker and any
     * things attached to it what were on to begin with
     */
    public void turnOn(){
        Reporter.report(this, Reporter.Msg.SWITCHING_ON);
        for(Component part:loads){
            part.engage();
            for (Component appliance : part.loads) {
                appliance.engage();
                if(appliance.engaged){
                    appliance.changeDraw(appliance.rating);
                }
            }
        }
    }

    /**
     * This is here to make Overload happy
     */
    @Override
    public void turnOff() {
        System.out.println("This won't happen");
    }

    /**
     * This declares whether the limit has been exceeded
     *
     * @return whether the limit has been exceeded
     */
    public boolean isOverload(){
        return draw > limit;
    }

    /**
     * This function will change the draw based on the declared
     * amount and  if the limit has been exceeded, execute the
     * Overload function
     *
     * @param rating the amount that the draw is changing
     */
    public void changeDraw(int rating) {
        draw += rating;
        if(isOverload()){
            Reporter.report(this, Reporter.Msg.BLOWN, draw);
            overload();
        }else{
            Reporter.report(this, Reporter.Msg.DRAW_CHANGE, rating);
            source.changeDraw(rating);
        }
    }

    /**
     * This function turns off the circuit breaker, resets the
     * draw, and disengages all attached components
     */
    public void overload(){
        Reporter.report(this, Reporter.Msg.SWITCHING_OFF);
        setDraw(0);
        source.setDraw(0);
        for(Component load:loads) {
            if (load instanceof Outlet) {
                Reporter.report(this, Reporter.Msg.DISENGAGING);
                engaged = false;
                for (Component appliance : load.loads) {
                    appliance.disengage();
                    Reporter.report(appliance, Reporter.Msg.DRAW_CHANGE, appliance.rating*-1);
                    Reporter.report(appliance.source, Reporter.Msg.DRAW_CHANGE, appliance.rating*-1);
                    appliance.source.draw = appliance.source.draw + -1*appliance.rating;
                }
            }if(load instanceof Appliance){
                disengage();
            }
        }
    }
}