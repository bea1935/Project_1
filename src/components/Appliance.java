package components;

public class Appliance extends Component{

    public Appliance(String name, Component source, int rating) {
        super(name, source);
        System.out.println(toString() +  " creating");
        attach();
        this.rating = rating;
    }

    public void attach(){
        System.out.println(source.toString() + " attaching --> " + this.toString());
        source.attach(this);
    }

    public int getRating(){
        return rating;
    }

    public void turnOn(){
        engaged = true;
        System.out.println(toString() + " switching on");
        source.changeDraw(rating);
    }

    public void turnOff(){
        engaged = false;
        System.out.println(toString() + " switching off");
        if(isSwitchOn()) {
            source.changeDraw(rating * -1);
        }
    }

    public void changeDraw(int rating){
        draw += rating;
        System.out.println(toString() +  " changing draw by " + rating);
        source.changeDraw(rating);
    }

    public boolean isSwitchOn(){
        return engaged();
    }

}
