package components;

import javax.swing.*;

public class CircuitBreaker extends Component{

    protected int limit;

    public CircuitBreaker(String name, Component source, int limit) {
        super(name, source);
        Reporter.report(this, Reporter.Msg.CREATING);
        attach();
        this.limit = limit;
    }

    public void attach(){
        Reporter.report(this.source, this, Reporter.Msg.ATTACHING);
        source.attach(this);
    }

    public boolean isSwitchOn(){
        return engaged();
    }

    public int getLimit(){
        return limit;
    }

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

    public boolean isOverload(){
        return draw > limit;
    }

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