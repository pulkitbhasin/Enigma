# Enigma

The files here are a suggested skeleton for your solution.  Change
them in any way you want, as long as the command

     java enigma.Main

runs your program.

The command

    gmake style

runs style61b on all the .java files in the enigma directory.  You
must get a clean audit before you submit your program.  The style
check enforces certain "neatness" criteria.  It is also useful as a
kind of "to-do list".  The style checks don't allow "//" comments, so
we have used them to indicate things that you'll need to fill in (if
you use our skeleton).  You can use them the same way to remind
yourself of things you still need to do.


Files in this distribution:

Makefile
          A configuration file for the 'gmake' program.  This allows
          you to perform common tasks: compiling your program, running
          tests and performing style checks on it, cleaning up
          unneeded files.  See the comment at the top of the file for 
          details.
test-correct
          A bash test script for testing correct inputs to the enigma program.
test-error
          A bash test script for testing erroneous inputs to the enigma program.

enigma    Directory containing the enigma package:

  enigma/Main.java
              Contains the main procedure: enigma.Main.main.
  enigma/Machine.java
              A suggested skeleton file for objects modeling Enigma
              machines.
  enigma/Rotor.java
              A suggested skeleton file for objects modeling rotors.
  enigma/Reflector.java
              A suggested skeleton file for objects modeling reflectors.

  enigma/rotors.txt
              Not part of the program.  Contains textual descriptions
              of the rotors.  You may find them useful for
              implementing the rotors.
              
tests      Directory for test files.
              Contains files *.inp, *.out, and *.err, intended as test input
              files, expected output files, and erroneous input files
              for the enigma program.
              The Makefile is set up so that 'gmake check' runs each
              .inp file through your program and compares the
              results to the corresponding .out file.  It also runs each
              .err file through your program and checks that the program 
              reports an error according to the project specification.

   tests/trivial.in, tests/trivial.out
              Sample test file input and output.
