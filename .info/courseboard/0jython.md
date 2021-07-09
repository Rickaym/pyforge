# JPython Integration

<p align="center">
<img src="../icons/jython.png"
    alt="Minecraft Java Edition"
    style="align: left; padding-right: 30px;" /></p>

Jython or JPython is a Java implementation of Python (the default Python implementation is CPython or Python) currently supports Version 2.7.2 (comparatively to CPython's 2.7.2 — [here](https://github.com/jython/jython)).

Most developers reading these docs are either Java devs migrating to the wonder that is Python, a land mostly absent of wiggly braces and semi demi colons, or Python devs simply learning how to make a mod on Minecraft.

Eitherways, regardless of if you are a Java dev or a Python dev, here lies the bridge that connects the vast capabilities of both worlds. I will neither be going in-depth with Python syntax or MinecraftForge docs. It will be written in a fool-proof way.

### Wording

Words can be ambiguious. I will be interpolating such abiguity to ensure that what is being said is in alignment with what is intended.

By;

1. `ခ` I mean other ways of spelling the same word I may use

2. `-` I mean differing word type suffixes

Using;

1. **Implement** _-ation_ _-ed_ _-ing_ is in reference of putting things into effect. (for Java's `implements`, I will explicitly say so)

2. **Python** _ခ py_ is in reference of the standard implementation of [Python](https://www.python.org/) [**CPython**](https://github.com/python/cpython).

3. **Jython** _ခ JPython_ is in reference of the Java implementation of [Python](don't_look_at_me_look_above_:p).

4. **Forge** is in reference of [Minecraft Forge](https://github.com/MinecraftForge/MinecraftForge) the modloader for Minecraft.

### Jython language and Syntax

Before we begin, make sure to check the Jython docs [here](https://jython.readthedocs.io/en/latest/). The definitive guide will explain things far better than I can afford.

**Jython has a replicate syntax as Python**

<details>
  <summary>spoiler:</summary>

> It's the same language — just a different implementation, _looks angrily as Iron Python_

</details>

Unfortunately, you will find to a great dismay that Jython as of its latest release is only equivalent to Python 2.7.2.

It's extremely important that you use Python **2.x** syntax. Be careful with using those paranthesis with the `print` statement!~ Presumably, this implementation includes the Python Data Model and everything that is elegant about python.

For those migrating from a different language, it is important that you understand the Python structure and organization.

> Another key factor in which Python differs from other languages is its coding structure. Back in the day, we had to develop programs based upon a very strict structure such that certain pieces must begin and end within certain punctuations. Python uses indentation rather than punctuation to define the structure of code. Unlike languages such as Java that use brackets to open or close a code block, Python uses spacing as to make code easier to read and also limit unnecessary symbols in your code. It strictly enforces ordered and organized code but it lets the programmer define the rules for indentation, although a standard of four characters exists.

Here's a comparison taken from the official docs;

```java
// Java if-statement

int x = 100;
if (x > 0) {
    System.out.println("Wow, this is Java");
} else {
    System.out.println("Java likes curly braces");
}
```

Now, let’s look at a similar block of code written in Python.

```py
# Python if-statement

x = 100
if x > 0:
    print 'Wow, this is elegant'
else:
    print 'Organization is the key'
```

### Java and Python Integration

Let's walk through the the process of Java and Python integration as supported by Jython with simple and brief examples.
#### Java to Python

Imagine that we have folder structure as such;
```
src ┓ 
    ┣━ Coffee.java
    ┗━ DeadSnakes.py
```
Assume the `Coffee.java` class as such;
```java
// inside src.Coffee
package src;

public class Coffee {
    public static void drink() {
        System.out.println("You dies of Heart failure.. someone might have poisoned your coffee");
    }
}
```
Let's say that we want to use the `Coffee` class from `Coffee.java` inside our `DeadSnakes.py` file.

To achieve that, it's as simple and easy as;
```python
import Coffee

Coffee.drink()

----------
# Console-output
>> You dies of Heart failure.. someone might have poisoned your coffee
```

#### Python to Java

Look at https://github.com/Rickaym/minecraft.py/tree/main/examples/java-jython-integration for now and read https://jython.readthedocs.io/en/latest/JythonAndJavaIntegration/?highlight=generics#chapter-10-jython-and-java-integration.

Will add my own brief definition and examples of this later on.

###### to be continued -- check `/jamboard.md`...
