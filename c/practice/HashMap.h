#ifndef _RAINYALLEY_HASH_MAP_H_
#define _RAINYALLEY_HASH_MAP_H_

#include <stdlib.h>
#include <stdbool.h>

#endif

/**
 * HashMap 数据对象的类型
 */
typedef void* HashMap;

/**
 * 创建HashMap
 * @param capacity 初始化容量
 * @param hash hash函数
 * @param equal equal函数
 * @return 数据对象
 */
HashMap createHashMap(unsigned int capacity,
                      int (* hash)(void * key),
                      bool (* equal)(void * var1, void * var2));

/**
 * 将元数据放入HashMap
 * @param hashMap 数据对象
 * @param key 键
 * @param value 值
 * @return 被覆盖的值，如果没有键对应的值，则返回NULL
 */
void *putIntoHashMap(HashMap hashMap, void *key, void *value);

/**
 * 从HashMap中获取值
 * @param hashMap 数据对象
 * @param key 键
 * @return 值
 */
void *getFromHashMap(HashMap hashMap, void *key);

/**
 * 从HashMap中删除键
 * @param hashMap 数据对象
 * @param key 键
 * @return 被删除元素的值
 */
void *removeFromHashMap(HashMap hashMap, void *key);

/**
 * 销毁HashMap
 */
void destroyHashMap(HashMap hashMap);

/**
 * 已存放元素个数
 */
unsigned int sizeOfHashMap(HashMap hashMap);