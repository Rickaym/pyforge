package interfaces;

public interface Human {
    /**
     *  Shadow interface for the incoming Python class.
     *
     *  This is made in a particular way that it overshadows the Python class and thus
     *  making it possible for the PyObject to be coerced with the interface flawlessly.
     */
    public String getHumanName();
    public int getHumanAge();
    public String getHumanSex();
    public static String isWhat() {
        return "Human";
    }
}
