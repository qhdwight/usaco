import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class January2019SilverIcyPerimeter {

	private static class Index {

		public final int r, c;

		Index(int r, int c) {
			this.r = r;
			this.c = c;
		}
	}

	private static class Blob implements Comparable<Blob> {

		public final int a, p;

		private Blob(int a, int p) {
			this.a = a;
			this.p = p;
		}

		@Override public int compareTo(Blob o) {
			if (a == o.a) return Integer.compare(o.p, p);
			return Integer.compare(a, o.a);
		}
	}

	private static final int EMPTY = 0, UNGROUPED = 1;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("perimeter.in"));

		int n = Integer.parseInt(br.readLine());
		int[][] grid = new int[n][n];
		for (int r = 0; r < n; r++) {
			char[] l = br.readLine().toCharArray();
			for (int c = 0; c < l.length; c++)
				// 0 is empty, 1 is ungrouped ice cream, any other means ice cream with that id
				grid[r][c] = l[c] == '#' ? UNGROUPED : EMPTY;
		}

		br.close();

		byte[][] edges = new byte[n][n];
		for (int r = 0, id = 2; r < n; r++)
			for (int c = 0; c < n; c++) {
				// Compute edges for each cell, sum up to find perimeter of given blob later
				if (r - 1 < 0 || grid[r - 1][c] == EMPTY) edges[r][c]++;
				if (c - 1 < 0 || grid[r][c - 1] == EMPTY) edges[r][c]++;
				if (r + 1 >= n || grid[r + 1][c] == EMPTY) edges[r][c]++;
				if (c + 1 >= n || grid[r][c + 1] == EMPTY) edges[r][c]++;
				// Depth first search to find connected ice cream blobs
				Stack<Index> visit = new Stack<>();
				if (grid[r][c] == EMPTY || grid[r][c] > UNGROUPED) continue;
				visit.add(new Index(r, c));
				while (!visit.empty()) {
					Index i = visit.pop();
					grid[i.r][i.c] = id;
					if (i.r - 1 >= 0 && grid[i.r - 1][i.c] == UNGROUPED) visit.add(new Index(i.r - 1, i.c));
					if (i.c - 1 >= 0 && grid[i.r][i.c - 1] == UNGROUPED) visit.add(new Index(i.r, i.c - 1));
					if (i.r + 1 < n && grid[i.r + 1][i.c] == UNGROUPED) visit.add(new Index(i.r + 1, i.c));
					if (i.c + 1 < n && grid[i.r][i.c + 1] == UNGROUPED) visit.add(new Index(i.r, i.c + 1));
				}
				// On to the next blob
				id++;
			}

		// Find area and perimeter for each group
		Map<Integer, Blob> blobs = new HashMap<>();
		for (int r = 0; r < n; r++)
			for (int c = 0; c < n; c++) {
				if (grid[r][c] == UNGROUPED) continue;
				final int fr = r, fc = c;
				blobs.compute(grid[r][c], (id, blob) -> new Blob(
						(blob != null ? blob.a : 0) + 1,
						(blob != null ? blob.p : 0) + edges[fr][fc]));
			}
		// Find blob with maximal area and if tied minimal perimeter
		Blob blob = blobs.values().stream().max(Blob::compareTo).get();

		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("perimeter.out")));
		String f = String.format("%d %d", blob.a, blob.p);
		pw.write(f);
		System.out.println(f);
		pw.close();
	}
}
