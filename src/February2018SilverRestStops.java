import java.io.*;
import java.util.StringTokenizer;

public class February2018SilverRestStops {

	private static class Rest {

		public final int x, c;
		public boolean isMaximalFromRight;

		private Rest(int x, int c) {
			this.x = x;
			this.c = c;
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("reststops.in"));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int L = Integer.parseInt(st.nextToken()), N = Integer.parseInt(st.nextToken()),
				rF = Integer.parseInt(st.nextToken()), rB = Integer.parseInt(st.nextToken());
		Rest[] rests = new Rest[N];
		for (int n = 0; n < N; n++) {
			st = new StringTokenizer(br.readLine());
			rests[n] = new Rest(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
		}
		br.close();

		int mC = Integer.MIN_VALUE;
		for (int n = N - 1; n >= 0; n--) {
			Rest r = rests[n];
			if (r.c > mC) {
				mC = r.c;
				// For this rest stop, it is the maximum of itself and all rest stops to the right
				r.isMaximalFromRight = true;
			}
		}

		long tastiness = 0, xB = 0, xF = 0;
		for (int n = 0; n < N; n++)
			// Greedy algorithm, no reason to stop at a rest stop with a lower tastiness value
			// So, until there are no more stops, always travel to the maximal rightmost rest stop and wait for the farmer
			if (rests[n].isMaximalFromRight) {
				Rest r = rests[n];
				if (r == null) break;
				long bessieDistanceToStop = r.x - xB, bessieTravelToStopDuration = bessieDistanceToStop * rB;
				long farmerDistanceToStop = r.x - xF, farmerTravelToStopDuration = farmerDistanceToStop * rF;
				// Add the tastiness while Bessie waits for the farmer to catch up to the rest stop
				tastiness += (farmerTravelToStopDuration - bessieTravelToStopDuration) * r.c;
				xF = xB = r.x;
			}

		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("reststops.out")));
		String answer = Long.toString(tastiness);
		pw.write(answer);
		System.out.println(answer);
		pw.close();
	}
}
