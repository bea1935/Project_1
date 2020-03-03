package components;

public class Outlet extends Component{

    public Outlet(String name, Component source) {
        super(name, source);
        attach();
        System.out.println(toString() +  " creating");
    }

    public void attach(){
        System.out.println(source.toString() + " attaching --> " + this.toString());
        source.attach(this);
    }

    public void changeDraw(int rating){
        draw += rating;
        System.out.println(this.toString() +  " changing draw by " + rating);
        source.changeDraw(rating);
    }

}
