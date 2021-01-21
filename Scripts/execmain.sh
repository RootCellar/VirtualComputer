#!/bin/sh
#Execute a class file in ../build

cd ../build
java -Xmx2G VirtualComputer.VirtualComputer $1 $2 $3
