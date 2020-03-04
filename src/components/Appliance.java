package components;

public class Appliance extends Component{

    public Appliance(String name, Component source, int rating) {
        super(name, source);
        Reporter.report(this, Reporter.Msg.CREATING);
        attach();
        this.rating = rating;
    }

    public void attach(){
        Reporter.report(source, this, Reporter.Msg.ATTACHING);
        source.attach(this);
    }

    public int getRating(){
        return rating;
    }

    public void turnOn(){
        engaged = true;
        Reporter.report(this, Reporter.Msg.SWITCHING_ON);
        source.changeDraw(rating);
    }

    public void turnOff(){
        engaged = false;
        Reporter.report(this, Reporter.Msg.SWITCHING_OFF);
        if(isSwitchOn()) {
            source.changeDraw(rating * -1);
        }
    }

    public void changeDraw(int rating){
        draw += rating;
        Reporter.report(this, Reporter.Msg.DRAW_CHANGE, rating);
        System.out.println(toString() +  " changing draw by " + rating);
        source.changeDraw(rating);
    }

    public boolean isSwitchOn(){
        return engaged();
    }

}
