#include <stdio.h>
#include "practice/HashMap.h"
#include "basic/randomAccess.h"

typedef struct{
    char * content;
} Key;


static int hash(void *key){
    Key * realKey = key;
    char * content = realKey -> content;
    int code = 0;
    for (int i =0;;i++) {
        int val = *(content + i);
        if(val == '\0'){
            break;
        }
        code += val;
    }
    return code;
}

static bool equal(void *key1, void *key2) {
    return ((Key *) key1)->content == ((Key *) key2)->content;
}


int main(){
    HashMap hashMap = createHashMap(2, &hash, &equal);
    Key key1, key2, key3;
    key1.content = "asdsadsadad";
    key2.content = "kjljlkj";
    key3.content = "ghfgjhghj";
    int  val1 = 998;
    char *val2 = "233";
    float val3 = 666.666;
    putIntoHashMap(hashMap, &key1, &val1);
    putIntoHashMap(hashMap, &key2, val2);
    putIntoHashMap(hashMap, &key3, &val3);
    printf("%d\n%s\n%f\n%d\n",
           *(int *)(getFromHashMap(hashMap, &key1)),
           (char *) getFromHashMap(hashMap, &key2),
           *(float *) getFromHashMap(hashMap, &key3),
           sizeOfHashMap(hashMap));
    removeFromHashMap(hashMap, &key1);
    printf("%d\n%d\n", getFromHashMap(hashMap, &key1), sizeOfHashMap(hashMap));
    destroyHashMap(hashMap);

    mainRandomAccess();

    return 0;
}


