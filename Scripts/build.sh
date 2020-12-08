#!/bin/bash
#builds a lot faster than the other build.sh file. Useful for actively developing.

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

