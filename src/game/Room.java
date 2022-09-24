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
    private String estado;
    private boolean passed = false;
    private static int allMatesinGame;
    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description, String estado)
    {
        this.description = description;
        this.estado = estado;
        exits = new HashMap<>();

        if (checkMate())
            allMatesinGame++;
    }

    public int getAllMates() {
        return allMatesinGame;
    }

    public boolean checkMate() {
        if (estado == "mate")
            return true;
        else
            return false;
    }

    public boolean checkOnePiece() {
        if (estado == "end")
            return true;
        else
            return false;
    }

    public boolean checkEndGame() {
        if (checkOnePiece() && getAllMates() == 0)
            return true;
        else   
            return false;
    }

    public void endGame() {
       if (checkEndGame())
            System.out.println("Você encontrou o maior tesouro do mundo. Fim de jogo.");
        
        warningMates();
    }

    public void jaPassou() {
        if (passed)
            System.out.println("Você já passou por aqui...");
   
    } 

    public void matesQtd() {
        String pluralMate = "s";
        String pluralVerb = "m";

        if (getAllMates() == 1) {
            pluralMate="";
            pluralVerb = "";}

        if (getAllMates() > 0)
            System.out.println("Falta" + pluralVerb + " " + allMatesinGame + " companheiro"+ pluralMate + " para completar a jornada");
        else
            System.out.println("Não faltam companheiros");
    }

    public void villainPrint() {
        if (foundVillain())
            System.out.println("Infelizmente você perdeu mas mantenha sua determinação e tente novamente");  
    }
    public void warningMates()  {
        if (checkOnePiece() && !(getAllMates() == 0)) {
            System.out.println("Um verdadeiro rei dos piratas precisa de todos seus amigos para concluir o objetivo final, cerifique-se disto");
            matesQtd();}
    }

    public void setPassou(boolean passou) {
        this.passed = passou;    
    }

    public void mateFound() {
        if (checkMate()) {
           if (!passed)
            allMatesinGame--;
            matesQtd();
        }
    }


    public boolean foundVillain() {
        if(checkVillian())
           return true;
        else
            return false;
    }


    public void getSubPrints() {
        jaPassou();
        mateFound();
        villainPrint();
        endGame();
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

    public boolean checkVillian() {
        if (estado == "villain")
            return true;
        else
            return false;
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
        if (!checkVillian()) {
        returnString = "\nExits:";
        Set<String> keys = exits.keySet();
        for(String exit : keys) {
            returnString += " " + exit;
        }}
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

