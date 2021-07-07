# `java-jython-integration` with a working example—

# Integration

Let's walk through the process of Java to Jython integration.

## Design Pattern

> Perhaps the most widely used technique used today for incorporating Jython code within Java applications is the object factory design pattern. This idea basically enables seamless integration between Java and Jython via the use of object factories. There are various different implementations of the logic, but all of them do have the same result in the end. Implementations of the object factory paradigm allow one to include Jython modules within Java applications without the use of an extra compilation step. Moreover, this technique allows for a clean integration of Jython and Java code through usage of Java interfaces [...continues here..](https://jython.readthedocs.io/en/latest/JythonAndJavaIntegration/?highlight=generics#object-factories)

This tells us that the class object has to inherit from a Java object called `object` or an `interface`.

Below is the example from the jython docs <sup>[[1]](https://jython.readthedocs.io/en/latest/JythonAndJavaIntegration/?highlight=generics#chapter-10-jython-and-java-integration)</sup>.

````py
Fish.py
class Fish(object):
    """ Fish Object """
    def __init__(self, type, salt_or_fresh):
        self.type = type
        self.salt_or_fresh = salt_or_fresh
    def getType(self):
        "@sig public String getType()"
        return self.type
    def getSaltOrFresh(self):
        "@sig public String getSaltOrFresh()"
        return self.salt_or_fresh
        ```
````

###### to be continued -- check `/jamboard.md` and `/examples/chapter-10`(working examples from the docs <sup>[[src]](https://jython.readthedocs.io/en/latest/JythonAndJavaIntegration/)</sup>)...
