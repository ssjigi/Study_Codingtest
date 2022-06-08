/////////////////////////////////////////////////////////////////////////////////////////////
// 기본 제공코드는 임의 수정해도 관계 없습니다. 단, 입출력 포맷 주의
// 아래 표준 입출력 예제 필요시 참고하세요.
// 표준 입력 예제
// int a;
// double b;
// char g;
// String var;
// long AB;
// a = sc.nextInt();                           // int 변수 1개 입력받는 예제
// b = sc.nextDouble();                        // double 변수 1개 입력받는 예제
// g = sc.nextByte();                          // char 변수 1개 입력받는 예제
// var = sc.next();                            // 문자열 1개 입력받는 예제
// AB = sc.nextLong();                         // long 변수 1개 입력받는 예제
/////////////////////////////////////////////////////////////////////////////////////////////
// 표준 출력 예제
// int a = 0;                            
// double b = 1.0;               
// char g = 'b';
// String var = "ABCDEFG";
// long AB = 12345678901234567L;
//System.out.println(a);                       // int 변수 1개 출력하는 예제
//System.out.println(b); 		       						 // double 변수 1개 출력하는 예제
//System.out.println(g);		       						 // char 변수 1개 출력하는 예제
//System.out.println(var);		       				   // 문자열 1개 출력하는 예제
//System.out.println(AB);		       				     // long 변수 1개 출력하는 예제
/////////////////////////////////////////////////////////////////////////////////////////////
import java.io.FileInputStream;
import java.util.Scanner;
import java.util.function.DoubleToIntFunction;

/*
   사용하는 클래스명이 Solution 이어야 하므로, 가급적 Solution.java 를 사용할 것을 권장합니다.
   이러한 상황에서도 동일하게 java Solution 명령으로 프로그램을 수행해볼 수 있습니다.
 */
class Solution {

    static class info {
        int h, w; // 맵상의 위치
        int height; // 현재 지점의 높이
        boolean usedK; // 공사를 진행한 적이 있다면 1 없다면 0
        int len; // 현재까지의 등산로 길이
        public info() {

        }

        public info(info n) {
            h = n.h;
            w = n.w;
            height = n.height;
            usedK = n.usedK;
            len = n.len;
        }
    }

    static int N; // 맵의 사이즈
    static int K; // 최대 공사 가능 깊이
    static int map[][] = new int[10][10]; // 맵의 정보를 저장할 배열
    static boolean visit[][] = new boolean[10][10]; // 방문 했었는지 체크할 배열

    static int maxHeight; // 가장 높은 봉우리의 높이
    static int answer;  // 정답을 저장하는 변수

    static final int dir[][] = { { -1, 0}, {1, 0,}, {0, -1}, { 0, 1} };
    static void solution(info cur) {
        // 길이가 더 길어졌을 때만 정답으로 저장
        answer = answer > cur.len ? answer : cur.len;

        // dir 배열을 이용해 [상, 하, 좌, 우] 순으로 탐색 진행
        for (int d = 0; d < 4; d++) {
            // 현재 위치(h,w)에 이동 방향을 더해 다음 방문할 위치와 높이 구하기
            info next = new info(cur);
            next.h += dir[d][0];
            next.w += dir[d][1];

            // 주어진 맵의 사이즈의 범위를 벗어날 경우 탐색을 이어갈 수 없음
            if (next.h < 0 || next.h >= N || next.w < 0 || next.w >= N) {
                continue;
            }

            next.height = map[next.h][next.w];

            // 이미 방문을 한경우 탐색을 이어갈 수 없음
            if (visit[next.h][next.w]) {
                continue;
            }

            // 방문 예정인 위치의 높이가 현재의 높이보다 작은 경우
            if (next.height < cur.height) {
                // 방문 표시
                visit[next.h][next.w] = true;
                // 등산로 길이를 1 증가
                next.len++;
                // next를 기점으로 다음 탐색을 위해 함수 호출
                solution(next);
                // 다시 방문 표시를 해제
                visit[next.h][next.w] = false;
            } else {
                // 현재까지 공사가 이루어지지 않았고
                // 다음 높이에 대해 K 만큼 공사를 했을 때 현재보다 작을 경우에만 가능
                if (!cur.usedK && next.height - K < cur.height) {
                    visit[next.h][next.w] = true;
                    next.len++;
                    //공사 했을을 표시
                    next.usedK = true;
                    // 최대한 긴 등산로를 만들고 싶으르로 K만큼의 높이를 다 깍을 필요 없이
                    // 현재 높이보다 1만 작게 깎아야 가장 긴 등산로를 만들 수 있다.
                    next.height = cur.height-1;
                    solution(next);
                    visit[next.h][next.w] = false;
                }
            }
        }
    }

    public static void main(String args[]) throws Exception {
		/*
		   아래의 메소드 호출은 앞으로 표준 입력(키보드) 대신 input.txt 파일로부터 읽어오겠다는 의미의 코드입니다.
		   여러분이 작성한 코드를 테스트 할 때, 편의를 위해서 input.txt에 입력을 저장한 후,
		   이 코드를 프로그램의 처음 부분에 추가하면 이후 입력을 수행할 때 표준 입력 대신 파일로부터 입력을 받아올 수 있습니다.
		   따라서 테스트를 수행할 때에는 아래 주석을 지우고 이 메소드를 사용하셔도 좋습니다.
		   단, 채점을 위해 코드를 제출하실 때에는 반드시 이 메소드를 지우거나 주석 처리 하셔야 합니다.
		 */
        System.setIn(new FileInputStream("res/sample_input.txt"));

		/*
		   표준입력 System.in 으로부터 스캐너를 만들어 데이터를 읽어옵니다.
		 */
        Scanner sc = new Scanner(System.in);
        int T;
        T = sc.nextInt();
		/*
		   여러 개의 테스트 케이스가 주어지므로, 각각을 처리합니다.
		*/

        for (int test_case = 1; test_case <= T; test_case++) {
            N = sc.nextInt();
            K = sc.nextInt();

            maxHeight = 0;
            answer = 0;

            System.out.println(N + " " + K);

            for (int h = 0; h < N; h++) {
                for (int w = 0; w < N; w++) {
                    map[h][w] = sc.nextInt();

                    // 가장 높은 봉우리 찾기
                    if (map[h][w] > maxHeight) {
                        maxHeight = map[h][w];
                    }
                    System.out.print(map[h][w] + " ");
                }
                System.out.println("");
            }

            System.out.println("max_height = " + maxHeight);

            int result = 0;
            for (int h = 0; h < N; h++) {
                for (int w = 0; w < N; w++) {
                  // 가장 높은 봉우리일 경우에만 탐색을 시작
                    if (maxHeight == map[h][w]) {
                        visit[h][w] = true; // 방문을 표시

                        info cur = new info();
                        cur.h = h;
                        cur.w = w;

                        cur.height = map[h][w];
                        cur.usedK = false;
                        cur.len = 1;
                        solution(cur);
                        visit[h][w] = false; // 다시 방문 표시 해제

                    }
                }
            }

            System.out.println("#" + test_case + " " + answer);
        }
    }
}