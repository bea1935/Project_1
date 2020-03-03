package components;

import java.util.ArrayList;
import java.util.Collection;

public abstract class Component {

    protected final String name;
    protected Component source;
    protected int draw;
    protected boolean engaged;
    protected Collection<Component> loads;
    protected int rating;

    public Component(String name, Component source){
        this.name = name;
        this.source = source;
        draw = 0;
        engaged = false;
        loads = new ArrayList<>();
        rating = 0;
    }

    public String getName(){
        return name;
    }

    protected void attach(Component load){
        loads.add(load);
        if(engaged){
            load.engaged = true;
        }
    }

    protected void changeDraw(int delta){
        draw += delta;
        Reporter.report(this, Reporter.Msg.DRAW_CHANGE, delta);
    }

    public void engage(){
        Reporter.report(this, Reporter.Msg.ENGAGING);
        engageLoads();
    }

    public void disengage(){
        Reporter.report(this, Reporter.Msg.DISENGAGING);
        disengageLoads();
    }

    protected void setDraw(int draw){
        int temp = this.draw;
        this.draw = draw;
        Reporter.report(this, Reporter.Msg.DRAW_CHANGE, temp*-1);
    }

    protected int getDraw(){
        return draw;
    }

    protected Component getSource(){
        return source;
    }

    protected Collection<Component> getLoads(){
        return loads;
    }

    protected boolean engaged(){
        return engaged;
    }

    protected void addLoad(Component newLoad){
        attach(newLoad);
        draw += newLoad.draw;
    }

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
}
