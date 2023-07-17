package tests;

import rickaym.pyminecraft.PyModLoader;


import java.io.File;
import java.nio.file.Paths;


public class Main {
    static String package_path = "C:/Users/User/Documents/Programming/Java/far_away_package";

    public static void main(String[] args) {
        PyModLoader.loadMod(Paths.get("src")
                .toAbsolutePath()
                .toString(), "examplemod");
    }
}
