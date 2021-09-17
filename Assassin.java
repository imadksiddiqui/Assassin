
import java.util.List;

public class Assassin {

    AssassinNode killRing;
    AssassinNode graveyard;
    int ringSize = 0;
    int yardSize = 0;
    
    public Assassin(List<String> names){
        if(names==null || names.isEmpty())
            throw new IllegalArgumentException();
        killRing = new AssassinNode(names.get(0));
        ringSize += 1;
        AssassinNode temp = killRing;
        for(int i = 1; i<names.size(); i++){
            temp.next = new AssassinNode(names.get(i));
            temp = temp.next;
            ringSize += 1;
        }
        temp.next = killRing;
    }
    
    public String killRing(){
        String output = "";
        AssassinNode temp = killRing;
        if(isGameOver())
            return temp.name + " won the game!";
        for(int i = 0; i<ringSize; i++){
            output +=  "  " + temp.name + " is stalking " + temp.next.name + "\n";
            temp = temp.next;
        }
        return output;
    }
    
    public String graveyard(){
        String output = "";
        AssassinNode temp = graveyard;
        for(int i = 0; i<yardSize; i++){
            output +=  "  " + temp.name + " was killed by " + temp.killer+ "\n";
            temp = temp.next;
        }
        return output;
    }
    
    public boolean killRingContains(String name){
        AssassinNode temp = killRing;
        for(int i = 0; i<ringSize; i++){
            if(temp.name.toLowerCase().equals(name.toLowerCase()))
                return true;
            temp = temp.next;
        }
        return false;
    }
    
    public boolean graveyardContains(String name){
        AssassinNode temp = graveyard;
        while(temp!=null){
            if(temp.name.toLowerCase().equals(name.toLowerCase()))
                return true;
            temp = temp.next;
        }
        return false;
    }
    
    public boolean isGameOver(){
        return ringSize==1;
    }
    
    public String winner(){
        if(isGameOver())
            return killRing.name;
        return null;
    }
    
    public void kill(String name){
        if(isGameOver())
            throw new IllegalStateException();
        if(!killRingContains(name))
            throw new IllegalArgumentException();
        AssassinNode temp = killRing;
        while(!temp.next.name.toLowerCase().equals(name.toLowerCase()))
            temp = temp.next;
        AssassinNode node = new AssassinNode(temp.next.name);
        node.killer = temp.name;
        if(graveyard==null)
            graveyard = node;
        else{
            node.next = graveyard;
            graveyard = node;
        }
        yardSize += 1;
        if(killRing.name.toLowerCase().equals(name.toLowerCase()))
            killRing = killRing.next;
        AssassinNode tempNext = temp.next.next;
        temp.next = tempNext;
        ringSize -= 1;
        
    }
    
    private static class AssassinNode {
        public final String name;  
        public String killer;      
        public AssassinNode next;   
        
        public AssassinNode(String name) {
            this(name, null);
        }

        public AssassinNode(String name, AssassinNode next) {
            this.name = name;
            this.killer = null;
            this.next = next;
        }
        
    }
}
