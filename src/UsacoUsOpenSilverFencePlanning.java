import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.StringTokenizer;

public class UsacoUsOpenSilverFencePlanning {

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

		List<Edges> graph = new ArrayList<>(n);
		for (int in = 0; in < n; in++) {
			st = new StringTokenizer(br.readLine());
			graph.add(new Edges(new Point(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()))));
		}
		for (int im = 0; im < m; im++) {
			st = new StringTokenizer(br.readLine());
			int i = Integer.parseInt(st.nextToken()) - 1, j = Integer.parseInt(st.nextToken()) - 1;
			graph.get(i).edges.add(j);
			graph.get(j).edges.add(i);
		}

		boolean[] marked = new boolean[n];
		int bestPm = -1;
		for (int s = 0; s < n; s++) {
			if (marked[s]) continue;
			marked[s] = true;
			Stack<Integer> stack = new Stack<>();
			stack.add(s);

			int xm = graph.get(s).point.x, ym = graph.get(s).point.y, xM = xm, yM = ym;
			while (!stack.isEmpty()) {
				int v = stack.pop();
				Point p = graph.get(v).point;
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
			int pm = 2 * (xM - xm) + 2 * (yM - ym);
			if (bestPm == -1 || pm < bestPm) {
				bestPm = pm;
			}
		}

		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("fenceplan.out")));
		pw.write(Integer.toString(bestPm));
		System.out.println(bestPm);

		pw.close();
	}
}
