
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class TestClass {
   static List<Integer> cs; //ù°�� ����� �� ������ ��� ����Ʈ
   static int k; // ���� k 
   static int mod = 1000000007; //���� mod
   static int store[][][]; //��Ϳ��� ȿ��������(���̳����ڵ�) �ݺ��Ǵ� ���� ������ �迭

  public static void main(String args[]) {
      Scanner sc = new Scanner(System.in); //input�� ���� ��ĳ��
      int n = sc.nextInt(); // n �Է�
      int m = sc.nextInt(); // m �Է�
      k = sc.nextInt(); // k�Է�
      
      cs = new ArrayList<Integer>(); // ù°���� ����Ǽ� ������ ��̸���Ʈ�� �ʱ�ȭ ���ش�
      
      int arrayc[] = new int[m]; // �ڸ����� ��Ʈ�� ġȯ�ؼ� ������ �迭�� ������ش�
      arrayc[0] = 1; // �迭 ó���� 1�� ä���
      // ��ü �迭�� 1ĭ���������� �о ù�ڸ��� 1�� �ǰ��Ѵ�.
      for(int i=1;i<m;i++) {
         arrayc[i] = arrayc[i-1] << 1;
      }
      
      for(int i=0;i<(int)Math.pow(2, m);i++)//2^m�±����� i
      { 
         int num = 0;
         boolean side = true; //���ӵǴ� �ڸ��� �Ÿ��� ���� ����
         for(int j=0;j<m;j++)
         {
            if((arrayc[j]&i) > 0) // �ڸ��� ������ ���
            {
               num++; //num ī��Ʈ
               if(j>0&&((arrayc[j-1]&i)>0))
               {
                  side = false;//�����ڸ��� ���ְ� �����ڸ��� ���ִ°�� �ߺ������� false
               }
            }
         }
         if(num>=2&&side)//side�� Ʈ��(�ߺ��� �ƴϸ鼭)�̸鼭 �ּ� 2�� �̻� �ɴ°�� 
            cs.add(num);//���ڸ� cs �迭�� �Է��Ѵ�.
         
      }
      
      // ���� cs �迭���� ù�ٿ� �����ִ� �ο����� ���� ����ִ�.
      
      store = new int[2][n+1][k+1]; // 3���� �迭�� �����Ѵ�. ù��° �ڸ��� k���� ū ������ �����ߴ��� �ƴ��� �̰� ������ �ΰ��� n���� �ٰ� ������� ������ ���� ��Ÿ����.
      for(int i=0;i<2;i++)
         for(int j=0;j<n+1;j++)
         Arrays.fill(store[i][j], -1); // �迭���� -1 �� �ʱ�ȭ �����ش�.
      
      System.out.println(solve(n,0,false)); // �غ�� �迭�� solve�Լ��� ������.

      
   }
   
// solve �Լ��� �μ��δ� ����Լ��� ���� n �� �ּڰ� �񱳸� ���� add �׸��� ���� k������ �����ߴ��� Ȯ���� ���� �Ҹ��� ���� minimum �� ����.
   static int solve(int nn ,int add , boolean minimum) {
      int count = 0; // ����� ���� ���� ���� ����
      int kenough = 0; // ���� k�� ����������� �ݺ��Ǵ� ����� ���ϱ����� ����
      if(minimum) // minimum�� true �ΰ�� kenough ������ 1�� �ٲپ� �ش�
         kenough = 1;
   
      if(nn==0&&minimum){return 1;} // ������ ��Ϳ��� k������ �����Ҷ� 1 ��ȯ
      else if(nn==0)   {return 0;} // ����������̳� k������ �������� ������ 0 ��ȯ
      else
      {
         if((store[kenough][nn][add]!=-1)) //���� ������ ������ ����� ������ ����Ǿ��ִٸ� �� �κ��� ���� �ʰ� ����� ���� �ҷ����°����� ��͸� ����
            return store[kenough][nn][add]; 
            //�ݺ��� ��ȯ
         for(int i=0;i<cs.size();i++) // ���� cs ��̸���Ʈ �� �ִ� ����ŭ �ݺ�
         {
            count += solve(nn-1,Math.min(k, add+cs.get(i)), minimum | (add+cs.get(i)>=k)); 
            // n-1�� �������ν� ȸ���� �ٿ������� �ι�° �μ� add�� ��������� �հ� k�� ���ؼ� ���� �������� ��ȯ�Ѵ�, ����°�� ���� k������ ����������� �� ���� ���� �ʰ� �Ҹ��� minimum�� true�� ��ȯ�Ѵ�.
            // k���� ���� ��簪�� ������ ��� ����ؼ� �����ϱ⶧���� �ӵ� ���ϰ� �Ͼ��. ���� boolean minimum�� �̿��� �ѹ� k�� �����Ǹ� true���� �����Ѵ�.
            // ������ �ݺ��� �������� ���� �ݺ����� ���ư��� �Ʒ� store���� ����� ���� �ҷ����Ƿν� �ݺ��� ���Ѵ�. ������ m^n�� �ݺ��� �Ǿ�� �ϳ� m*n�� �ݺ����� �ӵ��� ����ȭ ��Ų��.
            // count���� �������� ���������� ���� �����Ƿ� �ܰ躰�� �ϳ��� ���ְ� minimum�� �ϴ� �����Ǵ¼��� ���� �ݺ�ó���� �Ѵ�. �̶� ���� �����ߴٸ� k�� Ŀ����� k������ �����Ҷ�����
            //  ��ӵǾ�� �ϹǷ� ����ð��� �þ�µ� 3�����迭���� kenough������ ����������ν� k������ �����ϵ���ϵ� �ݺ��Ǵ� �κ��� ó�����ش�.
            if (count > mod)//mod ����
               count -= mod;
         }
      }
      store[kenough][nn][add] = count; // �ѹ����� �ݺ��� �������� �����ܰ� ��ͷ� �������� ���� �����ؼ� �ݺ��� �����ش�.
      //�ݺ��� ����
      return count; //��� �ݺ��� �������� count�� �������ش�.
   }

}
