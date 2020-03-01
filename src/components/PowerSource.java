package components;

public class PowerSource extends Component{

    public PowerSource(String name) {
        super(name, null);
        System.out.println("PowerSource " + name + " (draw " + this.getDraw() + "): creating");
    }

}
