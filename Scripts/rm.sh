#!/bin/bash

echo
echo REMOVING UNNEEDED FILES...

cd ..
find -name "*.class" -exec rm {} +
find -name "*.temp" -exec rm {} +
find -name "*.jar" -exec rm {} +
find -name "*.txt" -exec rm {} +


rm -r build/Logs
