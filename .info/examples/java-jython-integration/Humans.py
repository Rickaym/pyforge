from interfaces import Human  # type: ignore (ignore this — surpressing lint warnings)


class Male(Human):
    """
    Male sex subclass of the `Human` interface as an example
    """

    def __init__(self, name, age):
        self.name = name
        self.age = age

    def getName(self):
        """
        @Override-ing the basic methods offered from the
        Interface
        """
        return self.name

    def getAge(self):
        """
        @Override-ing the basic methods offered from the
        Interface
        """
        return self.age

    def getSex(self):
        """
        Adding a new method to a pre-defined interface
        """
        return "male"


class Female(Human):
    """
    Female sex subclass of the `Human` interface as an example
    """

    def __init__(self, name, age):
        self.name = name
        self.age = age

    def getName(self):
        """
        @Override-ing the basic methods offered from the
        Interface
        """
        return self.name

    def getAge(self):
        """
        @Override-ing the basic methods offered from the
        Interface
        """
        return self.age

    def getSex(self):
        """
        Adding a new method to a pre-defined interface
        """
        return "female"
