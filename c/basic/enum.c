#include <stdio.h>

/* 每个元素值为前一个元素的值+1
 * 若前面没有元素，其默认值为0，
 * 在本例中Sat默认值为5,Sun默认为6，
 * Sat设置为6后，Sun为7。
 */
enum weekday{Mon, Tue, Wed, Thu, Fri,Sat = 6,Sun} day=Sun ;

int main(){
	
	enum weekday day1 = Wed ,day2;
	day2 = Mon;

	printf("%d\n",day);
	printf("%d\n",day1);
	printf("%d\n",day2);
	return 0;
}
