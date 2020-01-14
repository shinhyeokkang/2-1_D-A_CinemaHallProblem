
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class TestClass {
   static List<Integer> cs; //첫째줄 경우의 수 저장한 어레이 리스트
   static int k; // 조건 k 
   static int mod = 1000000007; //조건 mod
   static int store[][][]; //재귀에서 효율을위해(다이나믹코딩) 반복되는 값을 저장할 배열

  public static void main(String args[]) {
      Scanner sc = new Scanner(System.in); //input을 위한 스캐너
      int n = sc.nextInt(); // n 입력
      int m = sc.nextInt(); // m 입력
      k = sc.nextInt(); // k입력
      
      cs = new ArrayList<Integer>(); // 첫째줄의 경우의수 저장할 어레이리스트를 초기화 해준다
      
      int arrayc[] = new int[m]; // 자리수를 비트로 치환해서 검증할 배열을 만들어준다
      arrayc[0] = 1; // 배열 처음을 1로 채운다
      // 전체 배열을 1칸씩왼쪽으로 밀어서 첫자리만 1이 되게한다.
      for(int i=1;i<m;i++) {
         arrayc[i] = arrayc[i-1] << 1;
      }
      
      for(int i=0;i<(int)Math.pow(2, m);i++)//2^m승까지의 i
      { 
         int num = 0;
         boolean side = true; //연속되는 자리를 거르기 위한 변수
         for(int j=0;j<m;j++)
         {
            if((arrayc[j]&i) > 0) // 자리가 차있을 경우
            {
               num++; //num 카운트
               if(j>0&&((arrayc[j-1]&i)>0))
               {
                  side = false;//이전자리가 차있고 지금자리가 차있는경우 중복임으로 false
               }
            }
         }
         if(num>=2&&side)//side가 트루(중복이 아니면서)이면서 최소 2명 이상 앉는경우 
            cs.add(num);//숫자를 cs 배열에 입력한다.
         
      }
      
      // 현재 cs 배열에는 첫줄에 들어갈수있는 인원수의 합이 들어있다.
      
      store = new int[2][n+1][k+1]; // 3차원 배열을 생성한다. 첫번째 자리는 k보다 큰 조건을 만족했는지 아닌지 이고 나머지 두개는 n개의 줄과 현재까지 원소의 합을 나타낸다.
      for(int i=0;i<2;i++)
         for(int j=0;j<n+1;j++)
         Arrays.fill(store[i][j], -1); // 배열들을 -1 로 초기화 시켜준다.
      
      System.out.println(solve(n,0,false)); // 준비된 배열을 solve함수로 보낸다.

      
   }
   
// solve 함수의 인수로는 재귀함수를 위한 n 과 최솟값 비교를 위한 add 그리고 현재 k조건을 만족했는지 확인을 위한 불리언 형태 minimum 이 들어간다.
   static int solve(int nn ,int add , boolean minimum) {
      int count = 0; // 경우의 수를 세기 위한 변수
      int kenough = 0; // 만약 k를 충족했을경우 반복되는 계산을 피하기위한 변수
      if(minimum) // minimum이 true 인경우 kenough 변수를 1로 바꾸어 준다
         kenough = 1;
   
      if(nn==0&&minimum){return 1;} // 마지막 재귀에서 k조건이 만족할때 1 반환
      else if(nn==0)   {return 0;} // 마지막재귀이나 k조건이 만족하지 않으면 0 반환
      else
      {
         if((store[kenough][nn][add]!=-1)) //만약 앞으로 가야할 경우의 수들이 저장되어있다면 그 부분을 가지 않고 저장된 값을 불러오는것으로 재귀를 종료
            return store[kenough][nn][add]; 
            //반복값 반환
         for(int i=0;i<cs.size();i++) // 현재 cs 어레이리스트 에 있는 수만큼 반복
         {
            count += solve(nn-1,Math.min(k, add+cs.get(i)), minimum | (add+cs.get(i)>=k)); 
            // n-1을 넣줌으로써 회차를 줄여나가고 두번째 인수 add는 현재까지의 합과 k를 비교해서 둘중 작은것을 반환한다, 세번째는 만일 k조건을 만족했을경우 그 값을 쌓지 않고 불리언 minimum만 true로 반환한다.
            // k값을 위해 모든값을 저장할 경우 계속해서 연산하기때문에 속도 저하가 일어난다. 따라서 boolean minimum을 이용해 한번 k가 만족되면 true값만 전송한다.
            // 마지막 반복이 끝났을때 그전 반복으로 돌아가고 아래 store에서 저장된 값을 불러오므로써 반복을 피한다. 원래는 m^n의 반복이 되어야 하나 m*n의 반복으로 속도를 최적화 시킨다.
            // count또한 마지막에 합을가지고 셀수 없으므로 단계별로 하나씩 세주고 minimum이 일단 만족되는순간 전부 반복처리를 한다. 이때 합을 저장했다면 k가 커질경우 k조건을 만족할때까지
            //  계속되어야 하므로 연산시간이 늘어나는데 3차원배열에서 kenough변수를 사용해줌으로써 k조건을 만족하든안하든 반복되는 부분을 처리해준다.
            if (count > mod)//mod 조건
               count -= mod;
         }
      }
      store[kenough][nn][add] = count; // 한바퀴의 반복이 끝났을때 이전단계 재귀로 가기전에 값을 저장해서 반복을 피해준다.
      //반복값 저장
      return count; //모든 반복이 끝났을때 count를 리턴해준다.
   }

}
