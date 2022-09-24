/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */

public class Game 
{
    private Parser parser;
    private Room currentRoom;
        
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room east_blue, nami, zoro, buggy,arlong,vila_da_nami,barba_negra,baratie,don_krieg, sanji,usopp,akainu,vila_do_usopp,kuro,one_Piece;
    
      
        // create the rooms
        east_blue = new Room("Inicio da jornada. Você está no mar dos fracos. Há inimigos por perto.");
        vila_da_nami = new Room("Você chegou na vila da Nami. Ela está desenhando mapas ao leste. Ir pela direção errada pode ter drasticas consequencias.");
        buggy = new Room("Você encontrou o buggy e perdeu","villain");
        arlong = new Room("Você encontrou o arlong e perdeu","villain");
        akainu = new Room("Você encontrou o akainu e perdeu","villain");
        nami = new Room("Você encontrou a Nami, a navegadora do bando","mate");
        barba_negra = new Room("Você encontrou o Barba negra e perdeu","villain");
        baratie = new Room("Você no restaurante do Sanji, o cozinheiro do bando. Ele está nos fundos. Há inimigos por perto.");
        zoro = new Room("Você encontrou Zoro, o espadachim do bando,. Há inimigos por perto","mate");
        sanji = new Room("Você encontrou Sanji, o cozinheiro do bando","mate");
        don_krieg = new Room("Você encontrou o Don Krieg e perdeu","villain");
        vila_do_usopp = new Room("Você chegou na vila do usopp, continue seguindo para encontrar o tesouro. Mas antes disso , precisará estender sua rota para encontrar mais um companheiro");
        usopp = new Room("Você encontrou o Usopp, atirador do bando","mate");
        one_Piece = new Room("Eis o maior tesouro do mundo", "end");
        kuro = new Room("Você encontrou o Kuro e perdeu","villain");

        vila_do_usopp.setExit("oeste", kuro);
        // initialise room exits
        east_blue.setExit("leste", vila_da_nami);
        east_blue.setExit("oeste", buggy);
        east_blue.setExit("norte", zoro);

        vila_da_nami.setExit("oeste", east_blue);
        vila_da_nami.setExit("norte", arlong);
        vila_da_nami.setExit("leste", nami);
        vila_da_nami.setExit("sul", akainu);

        zoro.setExit("oeste", barba_negra);
        zoro.setExit("leste", baratie);
        zoro.setExit("norte",vila_do_usopp);
        zoro.setExit("sul", east_blue);

        baratie.setExit("sul", sanji);
        baratie.setExit("norte", don_krieg);
        baratie.setExit("oeste", zoro);

        vila_do_usopp.setExit("leste",usopp);
        vila_do_usopp.setExit("sul",zoro);
  

        usopp.setExit("oeste",vila_do_usopp);
        usopp.setExit("leste",one_Piece);

        sanji.setExit("norte",baratie);
        nami.setExit("oeste",vila_da_nami);

        one_Piece.setExit("oeste", usopp);

        currentRoom = east_blue;  // start game outside
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished && !Item.getFinished()) {
            Command command = parser.getCommand();
            finished = processCommand(command);
            Item.checkState(currentRoom.getState(),currentRoom.getPassed());
       
        }
          
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Bem vindo(a) ao mundo de One piece");
        System.out.println("Sua missão é reunir todos os seus " + currentRoom.item.getAllMates() + " companheiros e encontrar o One piece, o maior tesouro do mundo, mas ATENÇÃO, há varios inimigos no seu caminho, tome CUIDADO!");
        System.out.println("Digite '" + CommandWord.HELP + "' se você precisar de ajuda");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

        switch (commandWord) {
            case UNKNOWN:
                System.out.println("I don't know what you mean...");
                break;

            case HELP:
                printHelp();
                break;

            case GO:
                goRoom(command);
                break;

            case QUIT:
                wantToQuit = quit(command);
                break;
        }
        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    /** 
     * Try to go in one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {

        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Vai para onde?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("Não há saída");
        }
        else {
            currentRoom.setPassou(true);
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
            currentRoom.getSubPrints();
        }
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }

  
}
