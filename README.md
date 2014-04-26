CaptainBern-Reflection-Framework
==========

A Java-reflection framework. (in-dev) 7

The framework consists of 3 parts:
1: The standard Reflection framework
⋅⋅* This is just a basic Reflection framework with some extra tools
2: The Fuzzy-Reflection framework
⋅⋅* This part of the framework can be used to obtain classes, fields and methods with only a specific amount
    of arguments. Eg: You need to obtain a specific method, you know it's arguments and return-type but not it's name
    then the fuzzy reflection will search for the method that matches the given pattern.
3: A Bytecode library
⋅⋅* The Bytecode library provides basic tools to read/parse classes and edit their bytecode.
    It also allows dynamic code-generation.

Contributing
============

Everyone can contribute code to their liking. When making PR's please provide a brief description of
what your code does, how it works and why it should be pulled.

* I generally follow the Sun/Oracle coding conventions.
* Feel free to ignore the "80 column limit"
* Curly braces should always be on the same line. (with one space in between)
* Make sure your code works
* When adding new classes please make sure you've added the license text above the class's package declaration.
* If you even consider contributing; Thanks!

