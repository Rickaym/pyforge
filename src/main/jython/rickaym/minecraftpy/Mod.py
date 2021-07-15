import sys, os

from inspect import isclass
from Errors import BadModClass

sys.path.append(os.path.abspath("./"))

from main.jython.rickaym.minecraftpy.interfaces import IPyModClass

"""
Main Mod Loader Class
"""


def Mod(mod_id=None):
    """A Python implementation of a java annotation.

    Filters out bogus functions inside the namespace and lets the
    register method persist, and adds the mod metadata.

    The identifier will be later checked to differentiate the mod class.
    """
    def class_wrapper(mod_object):
        if not isclass(mod_object):
            raise BadModClass("Your decorated Mod Class must be a class ...")

        class DecoratedMod(IPyModClass):
            register = mod_object.register

            __mod_meta__ = (("mod_id", mod_id),
                            ("class_name", mod_object.__name__),
                            ("name_space", mod_object.__dict__.keys()))
        return DecoratedMod
    return class_wrapper
