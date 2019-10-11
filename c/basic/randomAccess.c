//
// Created by Administrator on 2016/1/1.
//

#include "randomAccess.h"
#include <stdio.h>

long fileSize(FILE *file){
    long offset, length;
    offset = ftell(file);
    fseek(file, 0L, SEEK_END);
    length = ftell(file);
    fseek(file, 0L, SEEK_SET);
    return length;
}

int mainRandomAccess(void){
    char *fileName = "a.txt";
    FILE *file;
    file = fopen(fileName,"w+");
    fprintf(file, "hello world");
    printf("size of %s is %d\n", fileName, fileSize(file));
    fclose(file);
    return 0;
}



