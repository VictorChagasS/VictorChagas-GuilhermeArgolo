public class Item {
    private static int allMatesinGame;
    private static boolean finish = false;

    public Item() {
};
    public Item(String state) {
        if (state == "mate")
            allMatesinGame++;
    }

    public static void checkState(String state, boolean passed){
        if (!(state == null)){
        switch (state) {
            case "mate":
                if (!passed)
                    allMatesinGame--;
                    matesQtd(allMatesinGame);
                break;

            case "villain":
                System.out.println("Infelizmente você perdeu mas mantenha sua determinação e tente novamente");
                finish = true; 
                break; 

            case "end":
                if (!(allMatesinGame == 0)){
                    System.out.println("Um verdadeiro rei dos piratas precisa de todos seus amigos para concluir o objetivo final, certifique-se disto");
                    matesQtd(allMatesinGame);
                }
                    
            else{
                System.out.println("Você encontrou o maior tesouro do mundo. Fim de jogo.");
                finish = true;}
        }}



    }

public int getAllMates(){
    return allMatesinGame;
}

public static boolean getFinished(){
    return finish;
}

    
private static void matesQtd(int mates) {
        String pluralMate = "s";
        String pluralVerb = "m";

        if (mates == 1) {
            pluralMate="";
            pluralVerb = "";}

        if (mates> 0)
            System.out.println("Falta" + pluralVerb + " " + allMatesinGame + " companheiro"+ pluralMate + " para completar a jornada");
        else
            System.out.println("Não faltam companheiros");
    }



}
