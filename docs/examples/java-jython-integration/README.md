# `java-jython-integration` with a working example

# Integration

Let's walk through the process of Java to Jython integration.

## Design Pattern

> Perhaps the most widely used technique used today for incorporating Jython code within Java applications is the object factory design pattern. This idea basically enables seamless integration between Java and Jython via the use of object factories. There are various different implementations of the logic, but all of them do have the same result in the end. Implementations of the object factory paradigm allow one to include Jython modules within Java applications without the use of an extra compilation step. Moreover, this technique allows for a clean integration of Jython and Java code through usage of Java interfaces [...continues here..](https://jython.readthedocs.io/en/latest/JythonAndJavaIntegration/?highlight=generics#object-factories)

You will see an implementation of this design pattern as above. The code will be self documenting.