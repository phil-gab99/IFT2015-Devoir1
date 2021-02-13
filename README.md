# IFT2015-Devoir1

[Project Instructions](https://ift2015h21.wordpress.com/2021/02/01/projet-1-planter-un-arbre-dans-lordinateur/#more-136)

## Project Details

This project was mainly built in Java and generates Lindenmayer-systems for
which specifications are detailed in a *.json* file format. Two methods have
been implemented to create a an L-System corresponding to a given json file:

- The first method uses an instance of *EPSTurtle* to write a PostScript file.
The PostScript file associated with this Turtle instance mainly relies on the
commands *moveto* and *lineto* with constantly changing coordinates describing
the L-System this turtle is drawing.
- The second method uses instead an instance of *EPSTurtleOptimized* which
similarly writes a PostScript file. However, the PostScript commands mainly
used to achieve this differ from the previous turtle. The commands used are
mainly *scale*, *rotate*, and *translate*. The idea is that each movement or
line drawing consists of a unit displacement while the coordinate system's
origin constantly changes in order to align the movements or drawings according
to the sequence to draw.

## Running the application

This application can be run from the command line/terminal using the command:
```
java -jar lindenmayer.jar file.json iterations
```
where the first argument *file.json* is any *json* file path with the necessary
information to construct an L-System, and the second argument specifies the
number of iterations to take over the specified axiom in the *json* file which
is the starting sequence before beginning to draw the end result.

If running on a windows machine, one can run the following ghostscript command
to convert an *.eps* file to a *.pdf*:
```
epstopdf file.eps --output file.pdf
```

If running on a UNIX machine however, one can run the same ghostscript command
with a minor variation:
```
epstopdf file.eps > file.pdf
```
where *file* denotes the file path of interest.