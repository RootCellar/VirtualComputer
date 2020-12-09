#!/bin/bash
#Simple build script

echo
echo BUILDING...
echo

#move to base
cd ..

#move to source
cd src

#build list of files
find -name "*.java" > ../info/find.temp

find -name "*.java" -exec javac -d ../build/ {} +
