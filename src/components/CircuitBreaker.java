package components;

public class CircuitBreaker extends Component{

    protected int limit;

    public CircuitBreaker(String name, Component source, int limit) {
        super(name, source);
        System.out.println(toString() +  " creating");
        attach();
        this.limit = limit;
    }

    public void attach(){
        System.out.println(source.toString() + " attaching --> " + toString());
        source.attach(this);
    }

    public boolean isSwitchOn(){
        return engaged();
    }

    public int getLimit(){
        return limit;
    }

    public void turnOn(){
        System.out.println(toString() + " switching on");
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

    public boolean isOverload(){
        return draw > limit;
    }

    public void changeDraw(int rating) {
        draw += rating;
        if(isOverload()){
            System.out.println(toString() + ": has blown; current would be " + draw);
            overload();
        }else{
            System.out.println(this.toString() +  " changing draw by " + rating);
            source.changeDraw(rating);
        }
    }

    public void overload(){
        System.out.println(toString() +  " switching off");
        setDraw(0);
        source.setDraw(0);
        for(Component load:loads) {
            if (load instanceof Outlet) {
                System.out.println(toString() + " disengaging");
                engaged = false;
                for (Component appliance : load.loads) {
                    appliance.disengage();
                    System.out.println(appliance.toString() +
                            " changing draw by " + -1*appliance.rating);
                    System.out.println(appliance.source.toString() +  " changing draw by " + -1*appliance.rating);
                    appliance.source.draw = appliance.source.draw + -1*appliance.rating;
                }
            }if(load instanceof Appliance){
                disengage();
            }
        }
    }
}