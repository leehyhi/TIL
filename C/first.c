#include <stdio.h>

int main() {
    int num;
    int sum = 0;
    
    printf("10개의 숫자를 입력하세요: \n");
    for(int i = 0; i < 10; i++) {
        scanf("%d", &num);
        sum += num;
    }
    printf("합은: %d입니다.\n", sum);
}