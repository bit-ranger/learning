#include <stdio.h>
#include <time.h>
#define Time struct tm


int main(){
	Time t;
	t.tm_sec = 10;
	t.tm_min = 10;
	t.tm_hour = 6;
	t.tm_mday = 25;
	t.tm_mon = 0;
	t.tm_year = 115;
	t.tm_wday = 0;

	char *time_str = asctime(&t);
	puts(time_str);

	time_t cur;
	//time(&cur); 与下行等价
	cur = time(NULL);
	puts(ctime(&cur));
	return 0;
}
