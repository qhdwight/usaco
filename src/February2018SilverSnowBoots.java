import java.io.*;
import java.util.StringTokenizer;

public class February2018SilverSnowBoots {

	private static class Boots {

		private final int maxDepth, maxStep;

		private Boots(int maxDepth, int maxStep) {
			this.maxDepth = maxDepth;
			this.maxStep = maxStep;
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("snowboots.in"));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken()), B = Integer.parseInt(st.nextToken());
		int[] depths = new int[N];
		st = new StringTokenizer(br.readLine());
		for (int n = 0; n < N; n++)
			depths[n] = Integer.parseInt(st.nextToken());
		Boots[] boots = new Boots[B];
		for (int b = 0; b < B; b++) {
			st = new StringTokenizer(br.readLine());
			boots[b] = new Boots(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
		}
		br.close();

		boolean[][] dpReachable = new boolean[B][N];
		int minBootsDiscarded = Integer.MAX_VALUE;
		for (int b = 0; b < B && minBootsDiscarded == Integer.MAX_VALUE; b++) {
			for (int n = 0; n < N; n++) {
				if (boots[b].maxDepth < depths[n]) { // Mark tile unreachable if current tile has more snow than current boots can withstand
					dpReachable[b][n] = false;
					continue;
				}
				if (b == 0 && n == 0) dpReachable[b][n] = true; // First tile is reachable by problem definition
				for (int nPrev = 0; nPrev < n; nPrev++) // Mark tile reachable if a path exists with current boots
					if (dpReachable[b][nPrev] && boots[b].maxStep >= n - nPrev) {
						dpReachable[b][n] = true;
						break;
					}
				for (int bPrev = 0; bPrev < b; bPrev++) // Mark tile reachable if it was reachable with boots higher on the stack
					if (dpReachable[bPrev][n]) {
						dpReachable[b][n] = true;
						break;
					}
				// Detect if we are on last square and it is reachable
				// Guaranteed to be minimal discarded since we iterate from topmost pair to bottommost pair
				if (n == N - 1 && dpReachable[b][n]) {
					// Amount discarded is same as current boot
					// For example, if boot one can make it all the way to the end, one boot, namely boot zero, had been discarded
					minBootsDiscarded = b;
					break;
				}
			}
		}

		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("snowboots.out")));
		String answer = Integer.toString(minBootsDiscarded);
		pw.write(answer);
		System.out.println(answer);
		pw.close();
	}
}
