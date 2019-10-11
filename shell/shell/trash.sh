#!/bin/bash

readonly trash_home=/tmp/trash

for target in "$@" 
do
    dest=${trash_home}`realpath $target | xargs dirname`

    if [ ! -e ${dest} ]; then 
        mkdir -p ${dest};
    fi

    if [ -d $target ]; then
        mv -i $target ${dest};
    elif [ -f $target ]; then
        mv -i $target ${dest};
    fi
done
