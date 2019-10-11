#include <stdio.h>

typedef struct song{
	char *title;
	char *artist;
	int time;
}Song;


/**
 * 结构体指针 
 */
void show(Song *s){
	printf("title : %s\nartist : %s\ntime : %d\n",
			(*s).title,
			s -> artist, //指针所指向的结构体的成员
			s -> time);
}


int main(){
	struct song song1 = {"红豆", "王菲", 120};
	Song *sp = &song1;
	
	show(sp);

	Song songs[] = {song1};
	show(&songs[0]); //指向第一个元素
	return 0;
}




