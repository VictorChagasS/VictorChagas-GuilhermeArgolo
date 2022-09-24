import java.util.Set;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  For each existing exit, the room 
 * stores a reference to the neighboring room.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */

public class Room 
{
    private String description;
    private HashMap<String, Room> exits;        // stores exits of this room.
    private String state;
    private boolean passed;
    
    
    
    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */

    Item item = new Item();
    
    public Room(String description, String state)
    {
        this.description = description;
        this.state = state;
        item = new Item(state);
        exits = new HashMap<>();
    }


    public Room(String description) {
        this.state = null;
        this.description = description;
        exits = new HashMap<>(); 
    }

    public void setPassou(boolean passed) {
        this.passed = passed;    
    }

    public void alreadyPassed() {
        if (passed)
            System.out.println("Você já passou por aqui...");
    } 

    public boolean getPassed() {
        return passed;
    }

    public String getState(){
       return state;
    }

    public void matesQtd() {
        String pluralMate = "s";
        String pluralVerb = "m";

        if (item.getAllMates() == 1) {
            pluralMate="";
            pluralVerb = "";}

        if (item.getAllMates() > 0)
            System.out.println("Falta" + pluralVerb + " " + item.getAllMates() + " companheiro"+ pluralMate + " para completar a jornada");
        else
            System.out.println("Não faltam companheiros");
    }

    

    public void getSubPrints() {
        alreadyPassed();
      
    }

    /**
     * Define an exit from this room.
     * @param direction The direction of the exit.
     * @param neighbor  The room to which the exit leads.
     */
    public void setExit(String direction, Room neighbor) 
    {
        exits.put(direction, neighbor);
        
    }

    /**
     * @return The short description of the room
     * (the one that was defined in the constructor).
     */
    public String getShortDescription()
    {
        return description;
    }

    /**
     * Return a description of the room in the form:
     *     You are in the kitchen.
     *     Exits: north west
     * @return A long description of this room
     */
    public String getLongDescription()
    {
        return description + getExitString() + "\n";
    }

    /**
     * Return a string describing the room's exits, for example
     * "Exits: north west".
     * @return Details of the room's exits.
     */
    private String getExitString()
    {
        String returnString = "";
       
        returnString = "\nExits:";
        Set<String> keys = exits.keySet();
        for(String exit : keys) {
            returnString += " " + exit;
        }
        return returnString;

}


    /**
     * Return the room that is reached if we go from this room in direction
     * "direction". If there is no room in that direction, return null.
     * @param direction The exit's direction.
     * @return The room in the given direction.
     */
    public Room getExit(String direction) 
    {
        return exits.get(direction);
    }

}

