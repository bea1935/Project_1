package components;

import java.util.ArrayList;
import java.util.Collection;

public abstract class Component {

    protected final String name;
    protected Component source;
    protected int draw;
    protected boolean engaged;
    protected Collection<Component> loads;
    protected String front;

    public Component(String name, Component source){
        this.name = name;
        this.source = source;
        draw = 0;
        engaged = false;
        loads = new ArrayList<>();
        front = "    ";
    }

    public String getName(){
        return name;
    }

    protected void attach(Component load){
        loads.add(load);
        if(engaged){
            load.engaged = true;
        }
    }

    protected void changeDraw(int delta){
        System.out.println(this.toString() +  " changing draw by " + delta);
        draw += delta;
    }

    public void engage(){
        engaged = true;

    }

    public void disengage(){
        engaged = false;
    }

    protected void setDraw(int draw){
        System.out.println(this.toString() +  " setting draw");
        this.draw = draw;
    }

    protected int getDraw(){
        return draw;
    }

    protected Component getSource(){
        return source;
    }

    protected Collection<Component> getLoads(){
        return loads;
    }

    protected boolean engaged(){
        return engaged;
    }

    protected void addLoad(Component newLoad){
        System.out.println(this.toString() +  " adding load");
        attach(newLoad);
        draw += newLoad.draw;
    }

    protected void engageLoads(){
        System.out.println(this.toString() +  " engaging");
        for(Component load:loads){
            load.engage();
        }
    }

    protected void disengageLoads(){
        for(Component load:loads){
            load.disengage();
        }
    }

    public void display(){
        System.out.println("");
        for(Component attachment: loads){
            System.out.println(" + " + this.toString());
            if(attachment.getLoads() == null) {
                System.out.println(front + " + " + attachment.toString());
            }else{
                System.out.println(front + " + " +  attachment.toString());
                attachment.front = this.front + "    ";
                attachment.display();
            }
        }
    }

    @Override
    public String toString(){
        return "PowerSource " + name + " (draw " + draw + ")";
    }
}
