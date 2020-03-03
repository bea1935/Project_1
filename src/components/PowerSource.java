package components;

public class PowerSource extends Component{

    public PowerSource(String name) {
        super(name, null);
        System.out.println("PowerSource " + name + " (draw " + getDraw() + "): creating");
    }

    public void engage() {
        System.out.println(toString() + " engaging");
        engaged = true;
        for(Component part:loads){
            System.out.println(part.toString() + " engaging");
            part.engaged = true;
        }
    }
}
