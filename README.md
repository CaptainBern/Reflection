CaptainBern-Reflection-Framework
================================

This is a Java-Reflection and Bytecode Engineering library.

The goal of this project is to provide an easy way to work with Reflection on both a high and low level.

The Bytecode Engineering Lib (as for now "JBEL") can be used for Class-analysis and to create dynamic code on runtime.

One can also use it to, as example, parse a class, visit it with the build in visitor-api and dissemble the class.

The reflection framework was originally created to easily set and get fields and to invoke methods and constructors easier.

Content
=======

The Reflection framework is located in the `Reflection` folder and the Bytecode-library is located in the `JBEL` folder. That wasn't hard at all, was it?

Contributing
============

Prerequisites:

* When contributing to JBEL you need to have a basic understanding of bytecode.
    * Spelling errors and grammar fixes don't require this so feel free to PR a fix for those.
* Some code contains basic documentation, some doesn't contain any documentation at all. Feel free to expand the documentation and please always document the code you add.
* Keep the side-notes in mind. When you notice a weird piece of code that doesn't make any sense to you, then please check if it has a side-note, if it has one then that will probably make clear what is happening there.

Everyone can contribute code to their liking. When making PR's please provide a brief description of what your code does, how it works and why it should be pulled.

* I generally follow the Sun/Oracle coding conventions.
* Feel free to ignore the "80 column limit"
* Curly braces should always be on the same line. (with one space in between)
* Make sure your code works
* When adding new classes please make sure you've added the license text above the class's package declaration.
* If you even consider contributing; Thanks!