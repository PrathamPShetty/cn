import java.util.Scanner;

public class BellmanFord {
    private static final int MAX_VALUE = 999;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Input number of vertices
        System.out.print("Enter the number of vertices: ");
        int num_ver = scanner.nextInt();
        if (num_ver <= 0) {
            System.out.println("Invalid number of vertices.");
            return;
        }

        // Input adjacency matrix
        int[][] A = new int[num_ver + 1][num_ver + 1];
        System.out.println("Enter the adjacency matrix:");
        for (int i = 1; i <= num_ver; i++) {
            for (int j = 1; j <= num_ver; j++) {
                int input = scanner.nextInt();
                if (i != j && input == 0) { // 0 means no edge
                    A[i][j] = MAX_VALUE;
                } else {
                    A[i][j] = input;
                }
            }
        }

        // Input source vertex
        System.out.print("Enter the source vertex: ");
        int source = scanner.nextInt();
        if (source < 1 || source > num_ver) {
            System.out.println("Invalid source vertex.");
            return;
        }

        // Bellman-Ford Algorithm
        int[] D = new int[num_ver + 1];
        for (int i = 1; i <= num_ver; i++) {
            D[i] = MAX_VALUE;
        }
        D[source] = 0;

        for (int i = 1; i < num_ver; i++) {
            for (int u = 1; u <= num_ver; u++) {
                for (int v = 1; v <= num_ver; v++) {
                    if (A[u][v] != MAX_VALUE && D[u] != MAX_VALUE && D[u] + A[u][v] < D[v]) {
                        D[v] = D[u] + A[u][v];
                    }
                }
            }
        }

        // Check for negative edge cycles
        for (int u = 1; u <= num_ver; u++) {
            for (int v = 1; v <= num_ver; v++) {
                if (A[u][v] != MAX_VALUE && D[u] != MAX_VALUE && D[u] + A[u][v] < D[v]) {
                    System.out.println("The Graph contains a negative edge cycle.");
                    scanner.close();
                    return;
                }
            }
        }

        // Print shortest distances
        for (int i = 1; i <= num_ver; i++) {
            if (D[i] == MAX_VALUE) {
                System.out.println("Distance from source " + source + " to vertex " + i + " is infinity.");
            } else {
                System.out.println("Distance from source " + source + " to vertex " + i + " is " + D[i]);
            }
        }

        scanner.close();
    }
}
