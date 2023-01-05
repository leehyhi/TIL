#include <stdio.h>

int f(int n)
{
    int t1 = 0, t2 = 0;
    printf("시작: n = %d, t1 = %d, t2 = %d\n", n, t1, t2);
    if ((n == 2) || (n == 3) ) {
        printf("END_1 : f(%d) = 1 \n", n);
        return(1);
    }
    else {
        t1 = f (n-1);
        printf("R1 : n = %d, t1 = %d, t2 = %d\n", n, t1, t2);
        t2 = f (n-2);
        printf("R2 : n = %d, t1 = %d, t2 = %d\n", n, t1, t2);
    }
    printf("END_2 : f(%d) = %d\n", n, t1+t2);
    return (t1+t2);
}

int main() {
    f(5);
}
