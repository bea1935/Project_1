package components;

public class Outlet extends Component{

    public Outlet(String name, Component source) {
        super(name, source);
        attach();
        Reporter.report(this, Reporter.Msg.CREATING);
    }

    public void attach(){
        Reporter.report(this.source,this, Reporter.Msg.ATTACHING);
        source.attach(this);
    }

    public void changeDraw(int rating){
        draw += rating;
        Reporter.report(this, Reporter.Msg.DRAW_CHANGE, rating);
        source.changeDraw(rating);
    }

}
