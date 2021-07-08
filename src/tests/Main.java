package tests;

import jython.rickaym.minecraftpy.PyModLoader;

public class Main {
    public static void main(String[] args) {
        for (String module : PyModLoader.loads()) {
            System.out.println(module);
        }
    }
}
