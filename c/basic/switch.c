#include <stdio.h>

int main(){
	int weekday = 3;
	switch(weekday)
	{
		case 1 :
			printf("Monday");
			break;
		case 5 :
			printf("Friday");
			break;
		default :
			printf("error");
			break;
	}
}
