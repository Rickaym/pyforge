###### Document is still in progress.. granted as a refined idea board of things understood and established so far.

# Minecraft Java Edition

<p align="center">
<img src="../icons/Minecraft-Java_Keyart_255x383.jpg"
    alt="Minecraft Java Edition"
    style="align: left; padding-right: 30px;" /></p>

Surprisingly enough the Minecraft Java Edition is written in **[Java By Oracle](https://www.java.com/)**. Since it's classic release back in 2009 there has been a decade rich of updates over a few dozens of versions **([see here](https://minecraft.fandom.com/wiki/Java_Edition_version_history))**. This edition allows for cross-platform play between Windows, Linux and macOS — but as far as compatibility goes, there are no OS dependent issues to be in notice.

To be considerably useful, it would be best to innovate something that suffices for most versions if not all.

Starting from the `1.16.0` MDK provided by [MinecraftForge](<(https://github.com/MinecraftForge/MinecraftForge)>).

## Java

Perhaps this will explain why it's required for a typical installation of Minecraft Java Edition to have a version of **Java** in the past.

#### **Minecraft Java Edition RUNS! On Java**

It's important to note that Minecraft as of present is packaged with it's own Java to run on. Although this isn't shared with bundled mods as you will find out further down the document.

#### JVM and hybrid complications

Fortunately, this isn't a Java bootcamp course so I'll be skiing through the details.

Java is known to be an interpreted and compiled language and what does this actually mean?

#### To conceptualize it.

1.  The compiler translates Java programs `.java` into Java Bytecode `.class`. This is a form of machine language for the imaginary **[Java Virtual Machine](https://simple.wikipedia.org/wiki/Java_virtual_machine)**.
2.  Java Bytecode programs are then interpreted / executed at runtime by **[an interpreter](https://www.javatpoint.com/java-interpreter)**.

#### What about Minecraft Modding?

> Mojang and Microsoft provide little official support for this (for example, the game provides no modding API for Java, although Mojang does provide methods for deobfuscating the game), but the EULA permits non-commercial mods.

If there are no official modding APIs what do we do?!?!! Introducing 3rd party modding APIs.

#### Minecraft Modding APIs

There is a variety of modding APIs to choose from — as of currently, to name a few; fabric, forge, sponge etc.. This project focuses on creating a language adapter for **[MinecraftForge](https://github.com/MinecraftForge/MinecraftForge)**.

To implement a language adapter for MinecraftForge we must first understand how the modding process works in perspective of how the interactions between Forge, third party mods and the main Minecraft program interacts with each other.

Luckily, MinecraftForge has an official documentation for modding **[here](https://mcforge.readthedocs.io/en/latest/gettingstarted/)** that explains a great deal of information regarded to modding.

#### MinecraftForge modding

In summary (general development);
<br>

1. Install a JDK (java development kit)
2. Obtain an MDK (mod development kit) from Forge <sup> [[1]](https://files.minecraftforge.net/) </sup>
3. Extract the MDK to an folder and pick out necessary files for mod development, namely; `build.gradle`, `gradlew.bat`, `gradlew` and the `gradle` folder.
4. Move files listed above to a new folder. (this will be your mod projects folder)
5. Choose an IDE
6. Generating IDE Launch/Run Configurations with ForgeGradle

###### to be continued -- check `/jamboard.md`...
