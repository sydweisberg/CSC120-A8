public interface Contract {

    void grab(Item item);
    String drop(Item item);
    void examine(Item item);
    void use(Item item);
    boolean walk(String direction);
    boolean fly(int x, int y);
    Number shrink();
    Number grow();
    void rest();
    void undo();

}