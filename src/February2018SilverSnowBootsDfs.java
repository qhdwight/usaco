import java.io.*;
import java.util.StringTokenizer;

public class February2018SilverSnowBootsDfs {

	private static class Boots {

		private final int maxDepth, maxStep;

		private Boots(int maxDepth, int maxStep) {
			this.maxDepth = maxDepth;
			this.maxStep = maxStep;
		}
	}

	private static int N, B;
	private static int minBootsDiscarded = Integer.MAX_VALUE;
	private static boolean[][] marked;
	private static int[] depths;
	private static Boots[] boots;

	private static void read() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("snowboots.in"));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		B = Integer.parseInt(st.nextToken());
		depths = new int[N];
		st = new StringTokenizer(br.readLine());
		for (int n = 0; n < N; n++)
			depths[n] = Integer.parseInt(st.nextToken());
		boots = new Boots[B];
		for (int b = 0; b < B; b++) {
			st = new StringTokenizer(br.readLine());
			boots[b] = new Boots(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
		}
		br.close();
	}

	private static void visit(int b, int n) {
		if (marked[b][n]) return;
		marked[b][n] = true;
		if (n == N - 1) {
			minBootsDiscarded = Math.min(minBootsDiscarded, b);
			return;
		}
		for (int nNext = n + 1; nNext < N && nNext - n <= boots[b].maxStep; nNext++)
			if (depths[nNext] <= boots[b].maxDepth) visit(b, nNext);
		for (int bNext = b; bNext < B; bNext++)
			if (depths[n] <= boots[bNext].maxDepth) visit(bNext, n);
	}

	public static void main(String[] args) throws IOException {
		read();

		marked = new boolean[B][N];
		visit(0, 0);

		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("snowboots.out")));
		String answer = Integer.toString(minBootsDiscarded);
		pw.write(answer);
		System.out.println(answer);
		pw.close();
	}
}
