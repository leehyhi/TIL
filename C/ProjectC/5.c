#include <stdio.h>
//구조체 예제
struct GameInfo {
    char * name;
    int year;
    int price;
    char * company;
};

typedef struct GameInformation {
    char * name;
    int year;
    int price;
    char * company;
} GAME_INFO; //GameInfomation을 GAME_INFO라는 별명을 가진 구조체로 사용

int main(void) {
    char * name = "이 게임";
    int year = 2017;
    int price = 50;
    char * company = "이 회사";

    char * name2 = "저 게임";
    int year2 = 2017;
    int price2 = 100;
    char * company2 = "저 회사";

    //구조체 사용
    struct GameInfo gameInfo1;
    gameInfo1.name = "이 게임";
    gameInfo1.year = 2017;
    gameInfo1.price = 50;
    gameInfo1.company = "이 회사";

    //구조체 출력
    printf("-- 게임 출시 정보 --\n");
    printf("  게임명 : %s\n", gameInfo1.name);
    printf("  발매년도 : %d\n", gameInfo1.year);
    printf("  가격 : %d\n", gameInfo1.price);
    printf("  제작사 : %s\n\n", gameInfo1.company);
    
    struct GameInfo gameInfo2 = {"저 게임", 2017, 100, "저 회사"};
    printf("-- 또 다른 게임 출시 정보 --\n");
    printf("  게임명 : %s\n", gameInfo2.name);
    printf("  발매년도 : %d\n", gameInfo2.year);
    printf("  가격 : %d\n", gameInfo2.price);
    printf("  제작사 : %s\n", gameInfo2.company);

    //구조체 배열
    struct GameInfo gameArray[2] = {
        { "이 게임", 2017, 50, "이 회사"},
        { "저 게임", 2017, 50, "저 회사"}
    };

    struct GameInfo * gamePtr;
    gamePtr = &gameInfo1;
    // printf("\n\n-- 다른 게임 출시 정보 --\n");
    // printf("  게임명 : %s\n", (*gamePtr).name);
    // printf("  발매년도 : %d\n", (*gamePtr).year);
    // printf("  가격 : %d\n", (*gamePtr).price);
    // printf("  제작사 : %s\n", (*gamePtr).company);
    printf("\n\n-- 다른 게임 출시 정보 --\n");
    printf("  게임명 : %s\n", gamePtr->name);
    printf("  발매년도 : %d\n", gamePtr->year);
    printf("  가격 : %d\n", gamePtr->price);
    printf("  제작사 : %s\n", gamePtr->company);

    //typedef = 자료형 별명 지정
    int i = 1;
    typedef int 정수;
    typedef float 실수;
    정수 정수변수 = 3;
    실수 실수변수 = 3.23f;
    printf("\n\n정수변수 : %d, 실수변수 % 2f\n\n", 정수변수, 실수변수);

    typedef struct GameInfo 게임정보;
    게임정보 game1;
    game1.name = "한글 게임";
    game1.year = 2017;

    GAME_INFO game2;
    game2.name = "게임2";
    game2.year = 2014;

    return 0;
}
