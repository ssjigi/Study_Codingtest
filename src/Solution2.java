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
import java.util.Scanner;
import java.io.FileInputStream;

/*
   사용하는 클래스명이 Solution 이어야 하므로, 가급적 Solution.java 를 사용할 것을 권장합니다.
   이러한 상황에서도 동일하게 java Solution 명령으로 프로그램을 수행해볼 수 있습니다.
 */
class Solution2
{
    static final int[] U = { -1, 0 }; // up
    static final int[] D = { 1, 0 }; // down
    static final int[] LEFT = { 0, -1 }; // left
    static final int[] R = { 0, 1 }; // right

    static final int[][] T_ALL = { U, D, LEFT, R };
    static final int[][] T_UP_DOWN = { U, D };
    static final int[][] T_LEFT_RIGHT = { LEFT, R };
    static final int[][] T_UP_RIGHT = { U, R };
    static final int[][] T_DOWN_RIGHT = { D, R };
    static final int[][] T_DOWN_LEFT = { D, LEFT };
    static final int[][] T_UP_LEFT = { U, LEFT };

    static int N, M, L;
    static int[][] map;
    static boolean[][] visit;

    static int result = 1;

    public static void main(String args[]) throws Exception
    {
		/*
		   아래의 메소드 호출은 앞으로 표준 입력(키보드) 대신 input.txt 파일로부터 읽어오겠다는 의미의 코드입니다.
		   여러분이 작성한 코드를 테스트 할 때, 편의를 위해서 input.txt에 입력을 저장한 후,
		   이 코드를 프로그램의 처음 부분에 추가하면 이후 입력을 수행할 때 표준 입력 대신 파일로부터 입력을 받아올 수 있습니다.
		   따라서 테스트를 수행할 때에는 아래 주석을 지우고 이 메소드를 사용하셔도 좋습니다.
		   단, 채점을 위해 코드를 제출하실 때에는 반드시 이 메소드를 지우거나 주석 처리 하셔야 합니다.
		 */
        System.setIn(new FileInputStream("res/sample_input2.txt"));

		/*
		   표준입력 System.in 으로부터 스캐너를 만들어 데이터를 읽어옵니다.
		 */
        Scanner sc = new Scanner(System.in);
        int T;
        T=sc.nextInt();
		/*
		   여러 개의 테스트 케이스가 주어지므로, 각각을 처리합니다.
		*/

        for(int test_case = 1; test_case <= T; test_case++)
        {
            N = sc.nextInt(); // 세로 크기
            M = sc.nextInt(); // 가로 크기
            int R = sc.nextInt(); // 맨홀 뚜껑의 세로 위치
            int C = sc.nextInt(); // 맨홀 뚜껑의 가로 위치
            L = sc.nextInt(); // 소요된 시간

            System.out.println(N + " " + M + " " + R + " " + C + " " + L);

            map = new int[N][M];
            visit = new boolean[N][M];
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < M; j++) {
                    map[i][j] = sc.nextInt();
                    System.out.print(map[i][j] + " ");
                }
                System.out.println();
            }

            result = 0;
            Tile startTile = new Tile(R, C, 1);
            find(startTile, null);

            System.out.println("#" + test_case + " " + result);
        }
    }

    static void find(Tile tile, int[][] prevTerrnel) {
        // h, w 의 유효성 체크
        if (tile.x < 0 || tile.y < 0 || tile.x >= N || tile.y >= M) {
            //System.out.println("invalid tile");
            return;
        }

        // 이미 도달한 tile 일 경우 return
        if (visit[tile.x][tile.y]) {
            //System.out.println("visit");
            return;
        }

        // 가능한 방향의 ternnel 배열 찾기
        int[][] ternnel = getTernnel(map[tile.x][tile.y]);

        // 타일의 ternnel이 없으면 return
        if (ternnel == null) {
            //System.out.println("ternnel is null");
            return;
        }

        if (prevTerrnel != null && ternnel != T_ALL) {
            boolean connectable = false;
            for (int i = 0; i < ternnel.length; i++) {
                for (int j = 0; j < prevTerrnel.length; j++) {
                    if (ternnel[i][0] + prevTerrnel[j][0] == 0
                    && ternnel[i][1] + prevTerrnel[j][1] == 0) {
                        connectable = true;
                    }
                }

            }

            if (!connectable) {
                return;
            }
        }

        // 탈출할 시간이 max 보다 클 경우
        if (tile.l > L) {
            return;
        }


        // 위 조건을 모두 통과 했으므로 도달 가능 타일임
        result++;
        visit[tile.x][tile.y] = true;

        System.out.println("find " + tile + ", result = " + result);

        for (int i = 0; i < ternnel.length; i++) {
            int w = ternnel[i][0]; // 현재 타일의 터널에 연결 될 다음 타일 x 좌표
            int h = ternnel[i][1]; // 현재 타일의 터널에 연결 될 다음 타일 y 좌표
            Tile nextTile = new Tile(tile.x + w, tile.y + h, tile.l+1);
            // System.out.println("next tile = " + nextTile);

            find(nextTile, ternnel);
        }


    }

    static int[][] getTernnel(int type) {
        switch (type) {
            case 1:
                return T_ALL;
            case 2:
                return T_UP_DOWN;
            case 3:
                return T_LEFT_RIGHT;
            case 4:
                return T_UP_RIGHT;
            case 5:
                return T_DOWN_RIGHT;
            case 6:
                return T_DOWN_LEFT;
            case 7:
                return T_UP_LEFT;
        }

        return null;
    }

    static class Tile {
        int x, y;
        int l; // 현재 남은 시간
        boolean visit = false;

        int[][] ternnel;
        public Tile(int x, int y, int l) {
            this.x = x;
            this.y = y;
            this.l = l;
        }

        @Override
        public String toString() {
            return "Tile{" +
                    "x=" + x +
                    ", y=" + y +
                    ", l=" + l +
                    ", visit=" + visit +
                    '}';
        }
    }
}