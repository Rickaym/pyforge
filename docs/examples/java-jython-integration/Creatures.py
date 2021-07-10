# -*- coding: utf-8 -*-
from interfaces import Human, Alien  # type: ignore

"""
Exemplifies the class structure for Python classes that are needed to be integrated into java.
 
`Human` is a Java interface under which is defined after finalizing Male class details.

In order for the integration to be compatible Python classes must be subclassed under the 
desired interfaces, in this case the `Human` interface.

You will see docstrings that looks like => 
@sig <scope> <return type> <func name> ()
They are provided for means of comparison to its methods found in the
Java interface.

Always keep in mind to use 2.x syntax.
"""


class Male(Human):
    """
    Male sex class that implements `HUMAN` interface
    """

    def __init__(self, name, age):
        """@sig default Male (String name, int age) {...}"""
        self.name = name
        self.age = age

    def getHumanName(self):
        """@sig String getHumanName ();"""
        return self.name

    def getHumanAge(self):
        """@sig int getHumanAge ();"""
        return self.age

    def getHumanSex(self):
        """@sig String getHumanSex ();"""
        return "male"


class Lizzies(Alien):
    """
    Lizzie subclass of the `Alien` interface as an example
    """

    def __init__(self, name, age, race):
        """@sig default Female (String name, int age) {...}"""
        self.name = name
        self.age = age
        self.race = race

    def getAlienName(self):
        """@sig String getAlienName();"""
        return self.name

    def getAlienAge(self):
        """@sig int getAlienAge();"""
        return self.age

    def getAlienRace(self):
        """@sig String getAlienRace();"""
        return "Lizzie"