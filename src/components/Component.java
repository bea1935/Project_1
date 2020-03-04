package components;

import java.util.ArrayList;
import java.util.Collection;

/**
 * This is the Component class which serves as the basis
 * for all other components involved with the system
 *
 * @author Beck Anderson
 */

public abstract class Component {

    protected final String name;
    protected Component source;
    protected int draw;
    protected boolean engaged;
    protected Collection<Component> loads;
    protected int rating;

    /**
     * This function will create a new Component for the
     * system.
     *
     * @param name the name of the component
     * @param source the components that the component is
     *               connected to
     */
    public Component(String name, Component source){
        this.name = name;
        this.source = source;
        draw = 0;
        engaged = false;
        loads = new ArrayList<>();
        rating = 0;
    }

    /**
     * This will retrieve the name of the component
     *
     * @return the name of the component
     */
    public String getName(){
        return name;
    }

    /**
     * This will attach a component to the system and
     * if the part is on, engage the newly connected
     * component
     *
     * @param load the part being added to the system
     */
    protected void attach(Component load){
        loads.add(load);
        if(engaged){
            load.engaged = true;
        }
    }

    /**
     * This function will change the draw of the system
     * and print out a message of the change
     *
     * @param delta the amount that is being changed
     */
    protected void changeDraw(int delta){
        draw += delta;
        Reporter.report(this, Reporter.Msg.DRAW_CHANGE, delta);
    }

    /**
     * this will engage a component and all parts connected to it
     */
    public void engage(){
        Reporter.report(this, Reporter.Msg.ENGAGING);
        engageLoads();
    }

    /**
     * this will disengage a component and all parts connected to it
     */
    public void disengage(){
        Reporter.report(this, Reporter.Msg.DISENGAGING);
        disengageLoads();
    }

    /**
     * This will set the draw of the component to the specified amount
     * and print a message about it.
     *
     * @param draw the newly determined draw
     */
    protected void setDraw(int draw){
        int temp = this.draw;
        this.draw = draw;
        Reporter.report(this, Reporter.Msg.DRAW_CHANGE, temp*-1);
    }

    /**
     * This will return the draw of the part
     * @return the draw of the part
     */
    protected int getDraw(){
        return draw;
    }

    /**
     * This will return whether the part is engaged or not
     *
     * @return whether the part is engaged or not
     */
    protected boolean engaged(){
        return engaged;
    }

    /**
     * This will engage all of the parts connected to the component
     */
    protected void engageLoads(){
        for(Component load:loads) {
            if (load instanceof PowerSource) {
                load.engage();
                for (Component outlet : load.loads) {
                    outlet.engage();
                    for (Component appliance : outlet.loads) {
                        appliance.engage();
                    }
                }
            }
            if (load instanceof CircuitBreaker) {
                load.engage();
                for (Component outlet : load.loads) {
                    for (Component appliance : outlet.loads) {
                        appliance.engage();
                    }
                }
            }
            if (load instanceof Outlet) {
                load.engage();
                for (Component appliance : load.loads) {
                    appliance.engage();
                }
            }
        }
    }

    /**
     * This will disengage all of the parts connected to the component
     */
    protected void disengageLoads(){
        for(Component load:loads) {
            if (load instanceof PowerSource) {
                for (Component outlet : load.loads) {
                    outlet.disengage();
                    for (Component appliance : outlet.loads) {
                        appliance.disengage();
                    }
                }
            }
            if (load instanceof CircuitBreaker) {
                for (Component outlet : load.loads) {
                    for (Component appliance : outlet.loads) {
                        appliance.disengage();
                    }
                }
            }
            if (load instanceof Outlet) {
                for (Component appliance : load.loads) {
                    appliance.disengage();
                }
            }if(load instanceof Appliance){
                load.disengage();
            }
        }
    }

    /**
     * This will print out a system showing the parts
     */
    public void display(){
        System.out.println("");
        System.out.println(" + " + this.toString());
        for(Component breaker: loads) {
            System.out.println("     + " + breaker.toString());
            for (Component outlet : breaker.loads) {
                System.out.println("         + " + outlet.toString());
                for (Component appliance : outlet.loads) {
                    System.out.println("             + " + appliance.toString());
                }
            }
        }
        System.out.println("");
    }

    @Override
    public String toString(){
        return Reporter.identify(this);
    }

    public abstract void turnOn();

    public abstract void turnOff();
}
