import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.InputMismatchException;
 
public class A {
	public static void main(String args[]) {
		InputReader in = new InputReader(System.in);
 
		PrintWriter out = new PrintWriter(System.out);
 
		int t = 1;
 
		while (t-- > 0) {
			n = in.nextInt();
			m = in.nextInt();
			int k = in.nextInt();
			total = k;
			dp = new long[n + 1][k+1];
 
			ways = new long[m + 1][m];
 
			ways[0][0] = 1;
			ways[1][1] = 1;
 
			for (int i = 1; i <= m; i++) {
 
				for (int j = 0; j <= (m + 1) / 2; j++) {
 
					if (i - 2 >= 0 && j - 1 >= 0)
						ways[i][j] += ways[i - 2][j - 1];
 
					ways[i][j] += ways[i - 1][j];
 
					ways[i][j] %= MOD;
 
					// System.out.println(i+" "+j+" "+ ways[i][j]);
 
				}
 
			}
 
			for (int i = 0; i < n; i++)
				Arrays.fill(dp[i], -1);
			/*
			 * for (int i = 1; i <= (m+1)/2; i++) { for (int j = 1; j <=m; j++)
			 * ways[m][i] += ways[j][i]; ways[m][i] %= MOD;
			 * 
			 * }
			 */
			long ans = ways(0, 0);
			out.println(ans);
		}
 
		out.close();
 
	}
 
	static int total;
	static long ways[][];
	static int n;
	static int m;
 
	static long dp[][];
 
	static long ways(int i, int k) {
		if (i == n && k >= total)
			return 1;
		if (i == n && k < total)
			return 0;
		if (k >= total)
			k = total;
			
		if (dp[i][k] != -1)
			return dp[i][k];
		
		long ans = 0;
		long cnt = 0;
		for (int j = 2; j <= (m + 1) / 2; j++) {
			cnt = ways[m][j];
			ans = ans + (ways(i + 1, k + j) % MOD * cnt % MOD) % MOD;
			ans = ans % MOD;
		}
 
		return dp[i][k] = ans;
	}
 
	static int MOD = (int) (1e9 + 7);
 
	static int digSum(long x) {
		int res = 0;
		while (x > 0) {
			res += x % 10;
			x /= 10;
		}
		return res;
	}
 
	static class InputReader {
		private InputStream stream;
		private byte[] buf = new byte[1024];
		private int curChar;
		private int numChars;
		private SpaceCharFilter filter;
 
		public InputReader(InputStream stream) {
			this.stream = stream;
		}
 
		public static boolean isWhitespace(int c) {
			return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
		}
 
		public int read() {
			if (numChars == Long.MIN_VALUE)
				throw new InputMismatchException();
			if (curChar >= numChars) {
				curChar = 0;
				try {
					numChars = stream.read(buf);
				} catch (IOException e) {
					throw new InputMismatchException();
				}
				if (numChars <= 0)
					return -1;
			}
			return buf[curChar++];
		}
 
		public int nextInt() {
			int c = read();
			while (isSpaceChar(c))
				c = read();
			int sgn = 1;
			if (c == '-') {
				sgn = -1;
				c = read();
			}
			int res = 0;
			do {
				if (c < '0' || c > '9')
					throw new InputMismatchException();
				res *= 10;
				res += c - '0';
				c = read();
			} while (!isSpaceChar(c));
			return res * sgn;
		}
		
  
		public long nextLong() {
			int c = read();
			while (isSpaceChar(c))
				c = read();
			int sgn = 1;
			if (c == '-') {
				sgn = -1;
				c = read();
			}
			long res = 0;
			do {
				if (c < '0' || c > '9')
					throw new InputMismatchException();
				res *= 10;
				res += c - '0';
				c = read();
			} while (!isSpaceChar(c));
			return res * sgn;
		}
 
		public String nextToken() {
			int c = read();
			while (isSpaceChar(c))
				c = read();
			StringBuilder res = new StringBuilder();
			do {
				res.appendCodePoint(c);
				c = read();
			} while (!isSpaceChar(c));
			return res.toString();
		}
 
		public boolean isSpaceChar(int c) {
			if (filter != null)
				return filter.isSpaceChar(c);
			return isWhitespace(c);
		}
 
		public interface SpaceCharFilter {
			public boolean isSpaceChar(int ch);
		}
	}
}