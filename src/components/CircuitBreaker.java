package components;

public class CircuitBreaker extends Component{

    protected int limit;

    public CircuitBreaker(String name, Component source, int limit) {
        super(name, source);
        this.limit = limit;
    }

    public boolean isSwitchOn(){
        return engaged();
    }

    public int getLimit(){
        return limit;
    }

    public void turnOn(){
        System.out.println(this.toString() +  " disengaging");
        disengage();
        disengageLoads();
    }

    public void turnOff(){
        System.out.println(this.toString() +  " engaging");
        engage();
        engageLoads();
    }

    public void isOverload(){

    }

}