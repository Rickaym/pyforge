package interfaces;

public class Main {
    public static void main(String[] args) {
        /**
         * Demonstrative usage of the object factory by making calls to the staticmethod create[SEX] with the
         * appropriate arguments.
         *
         * See: `Human` and `HumanFactory`
         */
        HumanFactory factory = new HumanFactory();

        Human ricky = factory.createMale("Ricky", 12);
        HumanFactory.interpret(ricky);

        Human sarah = factory.createFemale("sarah", 12);
        HumanFactory.interpret(sarah);

        System.out.println();
    }
}