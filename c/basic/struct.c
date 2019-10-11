#include <stdio.h>

/**
 * 定义结构体
 */
struct date{
	int year;
	int month;
	int day;
}
date1={2015,1,1},
	date2={2015,9,9};

/**
 * 定义类型
 **/
typedef struct song {
	char *title;
	char *artist;
	int time;
	struct date published;
}Song;



void show(Song s){

	printf("title : %s\nartist : %s\ntime : %d\npublished : %d-%d-%d\n\n",
			s.title,
			s.artist,
			s.time,
			s.published.year,
			s.published.month,
			s.published.day);
}

int main(){
	Song songs[] = {{"红豆", "王菲", 180, date1},
		{"我的歌声里", "曲婉婷", 180, {2015,7,4}}};

	int len = sizeof(songs)/sizeof(Song);

	for(int i=0; i<len; i++){
		show(songs[i]);
	}

	struct song test;
	test.title = "asdgf";
	test.artist = "aaaaa";
	test.time = 998;
	test.published = date2;
	show(test);

	//不能将一个数组赋值给另一个数组
	//这意味着，在非定义域中，一个字符串也不能赋值给数组
	//char arr1[] = "abs",arr2=arr1;
	//arr2 = arr1; 
	//arr2 = "abs";


	return 0;
}
