package battleship;

public class Ship {

    final private String name;
    final private int size;

    Ship(String name, int size){
        this.name = name;
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public int getSize() {
        return size;
    }
}
