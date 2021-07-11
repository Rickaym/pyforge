package tests;

import main.jython.rickaym.minecraftpy.PyClassLoader;

import javax.management.AttributeNotFoundException;

public class Main {
    public static void main(String[] args) throws AttributeNotFoundException {
        PyClassLoader.loads();
    }
}
