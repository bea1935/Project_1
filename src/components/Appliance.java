package components;

public class Appliance extends Component{

    private int rating;

    public Appliance(String name, Component source, int rating) {
        super(name, source);
        System.out.println(this.toString() +  " creating");
        attach();
        this.rating = rating;
    }

    public void attach(){
        System.out.println(source.toString() + " --> " + this.toString());
        source.attach(this);
    }

    public int getRating(){
        return rating;
    }

    public String onOrOff(){
        if(engaged()){
            return "on";
        }else{
            return "off";
        }
    }

    public void turnOn(){
        engage();
        System.out.println(toString() + " switching on");
        source.changeDraw(rating);
    }

    public void turnOff(){
        System.out.println(toString() + " disengaging");
        disengage();
        System.out.println(toString() + " switching off");
        source.changeDraw(rating*-1);
    }

    public boolean isSwitchOn(){
        return engaged();
    }

    @Override
    public String toString(){
        return "Appliance " + getName() +  " (" + onOrOff() + "; rating " + rating + ")";
    }

}
