#!/bin/bash

echo "File Name: $0"
echo "First Parameter : $1"
echo "First Parameter : $2"
echo "Quoted Values: $@"
echo "print each param from \"\$@\""
for var in "$@"
do
    echo "$var"
done
echo "print each param from \"\$*\""
echo "Quoted Values: $*"
for var in "$*"
do
    echo "$var"
done
echo "Total Number of Parameters : $#"
