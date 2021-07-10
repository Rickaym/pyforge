package interfaces;

public class Main {
    /**
     * Usage of the object factory design pattern by making calls to the
     * createXYZ methods.
     *
     * @see `Alien`, `Human` and `SimpleFactory`
     */
    public static void main(String[] args) {
        SimpleFactory factory = new SimpleFactory();

        Human ricky = factory.createMale("Ricky", 12);
        SimpleFactory.interpretHuman(ricky);

        System.out.println("\n");

        Alien jorgan = factory.createLizzie("Jorgan", 102, "lizzies");
        SimpleFactory.interpretAlien((jorgan));
    }
}