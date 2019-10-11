#include <stdio.h>
#include <string.h>

int main(){
	//容量很重要
	char str[50] = "lovec";

	for (int i=0; i<sizeof(str); i++){
		printf("%c",str[i]);
	}
	printf("%d\n",sizeof(str));

	//模拟拼接
	memmove(str + 5, " learnc", 8);
	printf("%s\n", str);

	strcat(str, " enjoyc");
	printf("%s\n", str);

	printf("%s\n",strchr(str,'c'));
	printf("%d\n",strchr(str,'c') - str);
	printf("%d\n",strlen(str));
	return 0;
}



