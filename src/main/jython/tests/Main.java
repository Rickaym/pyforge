package tests;

import rickaym.pyminecraft.PyModLoader;


import java.io.File;
import java.nio.file.Paths;


public class Main {
    public static void main(String[] args) {
        PyModLoader.loadMod(Paths.get("src")
                .toAbsolutePath()
                .toString(), "examplemod");
    }
}
