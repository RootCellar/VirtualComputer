#!/bin/bash
#Simple build script

echo
echo BUILDING...
echo

#move to base
cd ..

#move to source
cd Docs

pdflatex -output-directory=../build VirtualComputer.tex
