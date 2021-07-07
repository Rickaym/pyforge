# -*- coding: utf-8 -*-
from interfaces import Human  # type: ignore

"""
Examplifies the Python classes that is intended to be integrated into Java,
`Human` is a Java interface that acts as a shadow class in the form of a 
Java interface to integrate the classes seamlessly.

As you will see, the Python classes are subclassed under the `Human` interface,
this is necessary for the object factory design pattern integration to work.

@sig <scope> <return type> <func name> () 
    is provided for means of comparison to its Java counterpart

Always keep in mind to use 2.x syntax.
"""


class Male(Human):
    """
    Male sex subclass of the `Human` interface as an example
    """

    def __init__(self, name, age):
        """@sig default Male (String name, int age) {...}"""
        self.name = name
        self.age = age

    def getHumanName(self):
        """@sig public String getHumanName ();"""
        return self.name

    def getHumanAge(self):
        """@sig public int getHumanAge ();"""
        return self.age

    def getHumanSex(self):
        """@sig public String getHumanSex ();"""
        return "male"

    @staticmethod
    def isWhat():
        return "human"


class Female(Human):
    """
    Female sex subclass of the `Human` interface as an example
    """

    def __init__(self, name, age):
        """@sig default Female (String name, int age) {...}"""
        self.name = name
        self.age = age

    def getHumanName(self):
        """@sig public String getHumanName ();"""
        return self.name

    def getHumanAge(self):
        """@sig public int getHumanAge ();"""
        return self.age

    def getHumanSex(self):
        "@sig public String getHumanSex ();"
        return "female"

    @staticmethod
    def isWhat():
        return "human"