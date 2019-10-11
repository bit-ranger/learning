#include <stdio.h>

int main(){
	/* 指针数组，每个元素都是一个指针 */
	char *strs[3] = {
		"abc",
		"def",
		"ghi"
	};
	
	/* 指向指针的指针
	 * 这表示，对p求值，得到的还是一个指针,得到的指针类型为char
	 */
	char **p = NULL;

	for(int i=0; i<3; i++){
		//p是一个指向指针的指针，
		//所以等号右边也必须为一个指向指针的指针，
		p = strs + i;
		char *s = *p;       //取出p指向的那个指针，并赋值给s, 现在这个指针有了两份, 新的一份存于s中。
		printf("%s\n", s);  //s中存放p所指向的指针
		printf("%p\n", p);  //p指向一个指针
		printf("%p\n", &*p);//p指向一个指针
		printf("%p\n", *p); //*p是被p指向的那个指针，这个指针指向某个位置
		printf("%p\n", s);  //s 与*p指向同一个位置
	}


}
