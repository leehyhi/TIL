#include <stdio.h>

int main(void) {
    // ++a는 ++동작을 먼저 수행을하고 문장을 끝냄.
    // ++a는 a = a + 1과 같음.
    // a++은 문장을 실행한 뒤 다음 문장으로 넘어갈 때 수행. 
    // for, while, do while
    // for(int i = 1; i <= 10; i++) {
    //     printf("Hello World %d\n", i);
    // }

    // for(int i=1; i<=9; i++) {
    //     printf("\n%d 단\n", i);
    //     for(int j=1; j<=9; j++) {
    //         printf("%d x %d = %d\n", i, j, i*j);
    //     }
    // }

    // 별
    // for(int i=0; i<5; i++) {
    //     for(int j=0; j<=i; j++) {
    //         printf("*");
    //     }
    //     printf("\n");
    // }

    /* 
            *
           **
          ***
         ****
        *****
    */
    // for(int i=0; i<5; i++) {
    //     for(int j=i; j<5-1; j++) {
    //         printf(" ");
    //     }
    //     for(int k=0; k<=i; k++) {
    //         printf("*");
    //     }
    //     printf("\n");
    // }
    
    // pyramid
    /*
         *
        ***
       *****
      *******
     *********    
    */
    int floor;
    printf("층 수 입력: ");
    scanf("%d", &floor);

    return 0;
}
