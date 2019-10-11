#include "HashMap.h"

/**
 * 最大容量 ,必须是2的n次方
 */
static unsigned int MAXIMUM_CAPACITY =  1 << 30;

/**
 * 默认加载因子, 必须为正数
 */
static float DEFAULT_LOAD_FACTOR =  0.75f;


/**
 * 链表中的节点
 */
typedef struct NestedEntryStruct{
    /**
     * 下一个结点
     */
    struct NestedEntryStruct *next;
    char *key;
    void *value;

}EntryStruct;

/**
 * 指向EntryStruct的指针
 */
typedef EntryStruct* Entry;

/**
 * 指向Entry的指针
 */
typedef Entry* EntryPtr;

/**
 * HashMap结构
 */
typedef struct{
    /**
     * 已存在元素个数
     */
    unsigned int size;

    /**
     * 加载因子
     */
    float loadFactor;

    /**
     * 表容量
     */
    unsigned int tableSize;

    /**
     * 表头
     */
    EntryPtr tableHead;

    /**
     * hash函数
     */
    int (* hash)(void * key);

    /**
     * 相等函数
     */
    bool (* equal)(void * var1, void * var2);

} _HashMapStruct;

/**
 * 指向_HashMapStruct的指针
 */
typedef _HashMapStruct* _HashMap;

/**
 * 计算有效容量
 */
static unsigned int tableSizeFor(unsigned int cap) {
    int n = cap - 1;
    n |= n >> 1;
    n |= n >> 2;
    n |= n >> 4;
    n |= n >> 8;
    n |= n >> 16;
    return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
}

/**
 * 创建HashMap
 */
static _HashMap doCreate(unsigned int initSize,
                         float loadFactor,
                         int (* hash)(void * key),
                         bool (* equal)(void * var1, void * var2)){
    _HashMap _hashMap = malloc(sizeof(_HashMapStruct));
    unsigned int tableSize = tableSizeFor(initSize);
    _hashMap -> size = 0;
    _hashMap -> loadFactor = loadFactor;
    _hashMap -> tableSize = tableSize;
    _hashMap -> tableHead = calloc(tableSize, sizeof(Entry));
    _hashMap -> hash = hash;
    _hashMap -> equal = equal;
    return _hashMap;
}

/**
 * 销毁HashMap
 */
static void doDestroy(_HashMap _hashMap){
    for (int i = 0; i < _hashMap -> tableSize; i++) {
        Entry willFree = *(_hashMap -> tableHead + i);
        for (;;) {
            if (willFree == NULL) {
                break;
            }
            Entry next = willFree -> next;
            free(willFree);
            willFree = next;
        }
    }
    free(_hashMap -> tableHead);
    free(_hashMap);
}

/**
 * 获取链表首节点指针
 */
static EntryPtr getListHeadEntryPtr(_HashMap _hashMap, char *key){
    int hash = _hashMap -> hash(key);
    hash = hash < 0 ? -hash : hash;
    int tbSize = _hashMap -> tableSize;
    int i = hash & (tbSize - 1);
    return  _hashMap -> tableHead + i;
}

/**
 * 获取链表节点指针
 */
static EntryPtr getEntryPtr(_HashMap _hashMap, char *key){
    EntryPtr entryPtr = getListHeadEntryPtr(_hashMap, key);
    for(;;){
        Entry entry = *entryPtr;
        if(entry == NULL){
            return entryPtr;
        } else if(_hashMap -> equal(key, entry -> key)){
            return entryPtr;
        } else {
            entryPtr = &(entry -> next);
        }
    }
}

/**
 * 向HashMap中放入元素
 * @return 若key已存在，则返回被覆盖的value，否则返回NULL
 */
static void * putVal(_HashMap _hashMap, void *key, void * value){
    EntryPtr entryPtr = getEntryPtr(_hashMap, key);
    void * replacedValue = NULL;

    if(*entryPtr == NULL){
        Entry entry = malloc(sizeof(EntryStruct));
        entry -> key = key;
        entry -> value = value;
        entry -> next = NULL;
        *entryPtr = entry;
        _hashMap -> size++;
    } else {
        replacedValue = (*entryPtr) -> value;
        (*entryPtr) -> value = value;
    }

    return replacedValue;
}

/**
 * HashMap扩容
 * 旧容器销毁
 * 返回新容器
 */
static _HashMap scale(_HashMap _hashMap){
    _HashMap new_HashMap = doCreate(_hashMap -> tableSize << 1, _hashMap -> loadFactor, _hashMap -> hash, _hashMap -> equal);
    for(int i=0; i<_hashMap->tableSize; i++){
        Entry entry = *(_hashMap -> tableHead + i);
        for(;;){
            if(entry == NULL){
                break;
            }
            putVal(new_HashMap, entry -> key, entry -> value);
            entry = entry -> next;
        }
    }
    doDestroy(_hashMap);
    return new_HashMap;
}

/**
 * 获取外部HashMap的真实目标
 */
static _HashMap realTarget(HashMap hashMap){
    return *(_HashMap*)hashMap;
}

/**
 * 将外部HashMap链接到真实目标
 */
static void link(HashMap hashMap, _HashMap _hashMap){
    *(_HashMap*)hashMap = _hashMap;
}


//~~~~~ public

/**
 * 创建HashMap
 * @param capacity 初始化容量
 * @param hash hash函数
 * @param equal equal函数
 */
HashMap createHashMap(unsigned int capacity,
                      int (* hash)(void * key),
                      bool (* equal)(void * var1, void * var2)){
    HashMap hmPtr = malloc(sizeof(_HashMap));
    link(hmPtr, doCreate(capacity, DEFAULT_LOAD_FACTOR, hash, equal));
    return hmPtr;
}

/**
 * 销毁HashMap
 */
void destroyHashMap(HashMap hashMap){
    doDestroy(realTarget(hashMap));
    free(hashMap);
}

/**
 * 将元数据放入HashMap
 * @param hashMap 对象
 * @param key 键
 * @param value 值
 * @return 被覆盖的值，如果没有键对应的值，则返回NULL
 */
void *putIntoHashMap(HashMap hashMap, void *key, void *value){
    _HashMap _hashMap  = realTarget(hashMap);
    if(_hashMap -> size + 1 >= _hashMap -> tableSize * _hashMap -> loadFactor){
        _hashMap = scale(_hashMap);
        link(hashMap, _hashMap);
    }
    return putVal(_hashMap, key, value);
}

/**
 * 从HashMap中获取值
 * @param hashMap 数据对象
 * @param key 键
 * @return 值
 */
void *getFromHashMap(HashMap hashMap, void *key){
    Entry entry = *getEntryPtr(realTarget(hashMap), key);
    if(entry == NULL){
        return NULL;
    } else {
        return entry -> value;
    }
}

/**
 * 从HashMap中删除键
 * @param hashMap 数据对象
 * @param key 键
 * @return 被删除元素的值
 */
void *removeFromHashMap(HashMap hashMap, void *key){
    _HashMap _hashMap = realTarget(hashMap);
    EntryPtr entryPtr = getEntryPtr(_hashMap, key);
    Entry entry = *entryPtr;
    void * value = NULL;
    if(entry != NULL){
        value = entry -> value;
        *entryPtr = entry -> next;
        free(entry);
        _hashMap -> size--;
    }
    return value;
}

/**
 * 已存放元素个数
 */
unsigned int sizeOfHashMap(HashMap hashMap){
    return realTarget(hashMap) -> size;
}


