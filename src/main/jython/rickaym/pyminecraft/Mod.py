from inspect import isclass

from main.jython.rickaym.pyminecraft import IPyModClass

"""
Main Mod Loader Class
"""


def Mod(mod_id=None):
    """
    Unique Mod Identifier.

    The identifier will be later checked to differentiate the mod class.
    It takes in the class object of the mod, creates a new class with the backbone
    of IPyModClass and persists onto the __init__ function and register function only.

    @Mod + SomeModClass =-->
                            ModEntry(*args, **kwargs) +
                                                      +._ SomeModClass(*args, **kwargs).register
    """
    def class_wrapper(mod_object):
        if not isclass(mod_object):
            raise TypeError("your decorated Mod Class must be a class ...")

        class ModEntry(IPyModClass):
            def __init__(self, *args, **kwargs):
                self.register = mod_object(*args, **kwargs).register

            def __mod_meta__(self):
                return (("mod_id", mod_id),
                        ("class_name", mod_object.__name__),
                        ("name_space", mod_object.__dict__.keys()))

        return ModEntry
    return class_wrapper
