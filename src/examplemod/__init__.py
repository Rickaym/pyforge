from mymod import MyMod

"""
Readily returns an instance of the mod class.

This is necessary inside the __init__ file to steer the mod loader into
the right class without much noise.

Notice the name `getModClass` - stick with that name.
"""

getModClass = lambda: MyMod()
