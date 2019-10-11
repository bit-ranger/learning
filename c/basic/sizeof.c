#include <stdio.h>


int ave(int nums[], int len){
	int sum = 0;
	for(int i=0; i<len; i++){
		sum += nums[i];
	}
	return sum/len;
}


int main(){
	int nums[] = {1,2,3,4,5};
	int len = sizeof(nums)/sizeof(int); //数组长度
	printf("%d", ave(nums, len));
}

