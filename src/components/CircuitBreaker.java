package components;

public class CircuitBreaker extends Component{

    protected int limit;

    public CircuitBreaker(String name, Component source, int limit) {
        super(name, source);
        this.limit = limit;
        System.out.println(toString() +  " creating");
        attach();
    }

    public void attach(){
        System.out.println(source.toString() + " attaching --> " + this.toString());
        source.attach(this);
    }

    public String onOrOff(){
        if(engaged()){
            return "on";
        }else{
            return "off";
        }
    }

    public boolean isSwitchOn(){
        return engaged();
    }

    public int getLimit(){
        return limit;
    }

    public void turnOn(){
        engage();
        engageLoads();
    }

    public void turnOff(){
        System.out.println(this.toString() +  " disengaging");
        disengage();
        disengageLoads();
    }

    @Override
    public String toString(){
        return "CircuitBreaker " + getName() +
                " (" + onOrOff() + "; draw " + getDraw()
                + "; limit " + getLimit() +")";
    }

    public String displayView(){
        return "     + " + toString();
    }

    public void isOverload(){

    }

}