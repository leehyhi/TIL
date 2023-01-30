#include <stdio.h>
#define MAX 10000

int main(void) {
    //파일에 쓰기
    // char line[MAX]; //char line[10000];
    // FILE * file = fopen("test.txt", "wb"); //wb = 쓰기
    // //wb는 파일을 작성(이미 있는 파일도 내용을 전부 지움)
    // //이미 있는 파일에 대해서 계속 사용하려면 a를 사용
    // if(file == NULL) {
    //     printf("파일 열기 실패\n");
    //     return 1;
    // }

    // fputs("fputs 를 이용해서 작성\n", file);
    // fputs("확인\n", file);

    // //파일 읽기
    // char line[MAX]; //char line[10000];
    // FILE * file = fopen("test.txt", "rb"); //rb = 읽기
    // if(file == NULL) {
    //     printf("파일 열기 실패\n");
    //     return 1;
    // }

    // while(fgets(line, MAX, file) != NULL) {
    //     printf("%s", line);
    // }

    // fclose(file);

    //fprintf, fscanf
    int num[6] = { 0, 0, 0, 0, 0, 0};
    int bonus = 0;
    char str1[MAX];
    char str2[MAX];
    FILE * file = fopen("test1.txt", "wb");
    if(file == NULL) {
        printf("파일 열기 실패\n");
        return 1;
    }
    fprintf(file, "%s %d %d %d %d %d %d\n", "추첨번호", 1, 2, 3, 4, 5, 6);
    fprintf(file, "%s %d\n", "보너스번호 ", 7);

    fclose(file);
}
