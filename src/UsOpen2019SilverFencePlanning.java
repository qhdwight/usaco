
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.StringTokenizer;

public class UsOpen2019SilverFencePlanning {

	static class Edges {

		public final List<Integer> edges = new ArrayList<>();
		public final Point point;

		Edges(Point point) {
			this.point = point;
		}
	}

	static class Point {

		public final int x, y;

		Point(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("fenceplan.in"));

		StringTokenizer st = new StringTokenizer(br.readLine());
		int n = Integer.parseInt(st.nextToken()), m = Integer.parseInt(st.nextToken());

		// Create graph where vertices are cows at a given position and edges represent a link in the social network
		List<Edges> graph = new ArrayList<>(n);
		for (int i = 0; i < n; i++) {
			st = new StringTokenizer(br.readLine());
			graph.add(new Edges(new Point(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()))));
		}
		for (int i = 0; i < m; i++) {
			st = new StringTokenizer(br.readLine());
			int v = Integer.parseInt(st.nextToken()) - 1, w = Integer.parseInt(st.nextToken()) - 1;
			// Bidirectional graph
			graph.get(v).edges.add(w);
			graph.get(w).edges.add(v);
		}

		br.close();

		boolean[] marked = new boolean[n];
		int bestPm = -1;
		for (int s = 0; s < n; s++) {
			if (marked[s]) continue;
			marked[s] = true;
			Stack<Integer> stack = new Stack<>(); // Depth-first search to find grouped networks
			stack.add(s);

			int xm = graph.get(s).point.x, ym = graph.get(s).point.y, xM = xm, yM = ym;
			while (!stack.isEmpty()) {
				int v = stack.pop();
				Point p = graph.get(v).point;
				// Expand bounding box to encompass new point if necessary
				xm = Math.min(xm, p.x);
				xM = Math.max(xM, p.x);
				ym = Math.min(ym, p.y);
				yM = Math.max(yM, p.y);
				for (int w : graph.get(v).edges) {
					if (marked[w]) continue;
					stack.add(w);
					marked[w] = true;
				}
			}
			// Compute perimeter and update best if smaller
			int pm = 2 * (xM - xm) + 2 * (yM - ym);
			if (bestPm == -1 || pm < bestPm) {
				bestPm = pm;
			}
		}

		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("fenceplan.out")));
		String answer = Integer.toString(bestPm);
		pw.write(answer);
		System.out.println(answer);
		pw.close();
	}
}
