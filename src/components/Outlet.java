package components;

public class Outlet extends Component{

    public Outlet(String name, Component source) {
        super(name, source);
        System.out.println(toString() +  " creating");
        attach();
    }

    public void attach(){
        System.out.println(source.toString() + " attaching --> " + this.toString());
        source.attach(this);
    }

    @Override
    public String toString(){
        return "Outlet " + getName() +  " (draw " + getDraw() + ")";
    }

    public String displayView(){
        return "         + " + toString();
    }

}
