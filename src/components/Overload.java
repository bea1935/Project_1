package components;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * This function is the one that executes the main
 * method and finds any errors that would be associated
 * with either the read file or user input
 *
 * @author Beck Anderson
 */
public class Overload {

    public static final int BAD_ARGS = 1;
    public static final int FILE_NOT_FOUND = 2;
    public static final int BAD_FILE_FORMAT = 3;
    public static final int UNKNOWN_COMPONENT = 4;
    public static final int REPEAT_NAME = 5;
    public static final int UNKNOWN_COMPONENT_TYPE = 6;
    public static final int UNKNOWN_USER_COMMAND = 7;
    public static final int UNSWITCHABLE_COMPONENT = 8;

    private static final String WHITESPACE_REGEX = "\\s+";
    private static final String[] NO_STRINGS = new String[0];

    private static final String PROMPT = "? ";

    private static HashMap<String, Component> parts;
    private static ArrayList<Component> root;
    private static Appliance temp;

    /**
     * The main function to be executed
     * @param args the file to be read
     */
    public static void main(String[] args) {
        if (!(args.length == 1)) {
            Reporter.addError( BAD_ARGS, "Usage: java components.Overload <configFile>" );
        }parts = new HashMap<>();
        System.out.println("Overload Project, CS2");
        File starter = new File(args[0]);
        try {
            int created = 0;
            Scanner line = new Scanner(starter);
            while (line.hasNextLine()) {
                System.out.println("");
                create(line.nextLine());
                created += 1;
            }
            System.out.println(created + " component(s) created");
            line.close();
        } catch (FileNotFoundException e) {
            Reporter.addError( FILE_NOT_FOUND, "Config file not found" );
        }
        for(Component powerSource:root){
            powerSource.engage();
        }
        boolean condition = true;
        Scanner reader = new Scanner(System.in);
        while (condition) {
            String temp = reader.nextLine();
            String[] components = temp.split("", 4);
            if (temp.equals("quit")) {
                condition = false;
            }
            if (components[0].equals("toggle")) {
                toggle(parts.get(components[1]));
            }
            if (components[0].equals("connect")) {
                if(components.length == 4) {
                    connect(components[0], components[1], components[2], components[3]);
                }if(components.length == 3){
                    connect(components[0], components[1], components[2]);
                }
                System.out.println("thats neat");
            }
            if (components[0].equals("display")) {
                for(Component powersource:root) {
                    powersource.display();
                }
            }else{
                Reporter.addError(
                        UNKNOWN_USER_COMMAND,
                        "Unknown user command");
            }
        }reader.close();
    }

    /**
     * This will create a component to be added to a system
     *
     * @param part the part to be made
     */
    protected static void create(String part){
        String[] components = part.split(" ", 3);
        if(!(components.length==2 || components.length==3||components.length==4)){
            Reporter.addError( BAD_FILE_FORMAT, "Error in config file" );
        }else{
        if(components[0].equals("PowerSource")){
            String name = components[1];
            parts.put(name, new PowerSource(name));
            root.add(parts.get(name));
        }if(components[0].equals("CircuitBreaker")){
            String name = components[1];
            String source = components[2];
            int limit = Integer.parseInt(components[3]);
            parts.put(name, new CircuitBreaker(name, parts.get(source), limit));
        }if(components[0].equals("Outlet")){
            String name = components[1];
            String source = components[2];
            parts.put(name, new Outlet(name, parts.get(source)));
        }if(components[0].equals("Appliance")){
            String name = components[1];
            String source = components[2];
            int rating = Integer.parseInt(components[3]);
            parts.put(name, new CircuitBreaker(name, parts.get(source), rating));
        }else{
            Reporter.addError(
                    UNKNOWN_COMPONENT,
                    "Reference to unknown component in config file");
        }
        }
    }

    /**
     * This will turn a component on or off if it can be
     *
     * @param part the component to be toggled
     */
    protected static void toggle(Component part){
        if(part.getClass().toString().equals("PowerSource")||part.getClass().toString().equals("Outlet")){
            Reporter.addError(
                    UNSWITCHABLE_COMPONENT,
                    "That component can not be switched on or off");
        }else {
            if(part.getClass().toString().equals("Appliance")){
                if (part.engaged()) {
                    part.turnOff();
                } else {
                    part.turnOn();
                }
            }else {
                if (part.engaged()) {
                    part.disengage();
                } else {
                    part.engage();
                }
            }
        }
    }

    /**
     * This will connect a component to the system
     *
     * @param type the type of part being added
     * @param name the name of the component
     * @param source the component it is being attached to
     * @param other wither the limit of the breaker or
     *              the rating of the appliance
     */
    protected static void connect(String type, String name, String source, String other){
        if(parts.containsKey(name)){
            Reporter.addError(
                    REPEAT_NAME,
                    "Component name repeated in config file");
        }else {
            if (type.equals("CircuitBreaker")) {
                parts.put(name, new CircuitBreaker(name, parts.get(source), Integer.parseInt(other)));
            } else if (type.equals("Appliance")) {
                parts.put(name, new Appliance(name, parts.get(source), Integer.parseInt(other)));
            } else {
                Reporter.addError(
                        UNKNOWN_COMPONENT_TYPE,
                        "Reference to unknown type of component in config file");
            }
        }
    }

    /**
     * This will connect a component to the system
     *
     * @param type the type of part being added
     * @param name the name of the component
     * @param source the component it is being attached to
     */
    protected static void connect(String type, String name, String source) {
        if (parts.containsKey(name)) {
            Reporter.addError(
                    REPEAT_NAME,
                    "Component name repeated in config file");
        } else {
            if (type.equals("Outlet")) {
                parts.put(name, new Outlet(name, parts.get(source)));
            } else {
                Reporter.addError(
                        UNKNOWN_COMPONENT_TYPE,
                        "Reference to unknown type of component in config file");
            }
        }
    }
}