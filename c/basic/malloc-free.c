#include <stdio.h>
#include <stdlib.h>

int main(){
	char *c = NULL;
	c = (char *)malloc(1); //分配内存

	/* 分配内存后如果指针还未NULL就是没有分配成功 */
	if(c == NULL){
		printf("malloc failed");
		exit(-1);
	}

	*c = 'c';
	printf("*c == %c\n", *c);
	free(c);

	return 0;
}
