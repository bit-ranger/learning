#include <stdio.h>


int sum(int n){
	if (1==n){
		return 1;
	} else {
		return n + sum(n-1);
	}
}

int main(){
	int n = 100;

	printf("%d", sum(n));
}
