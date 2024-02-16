import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Percolation {

    private int n;
    private int[][] grid;
    private WeightedQuickUnionUF UF;
    private WeightedQuickUnionUF UF2;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int num) {
        if (num <= 0) {
            throw new IllegalArgumentException("n cannot be less than or equal to 0");
        }
        this.n = num;
        grid = new int[n][n];
        UF = new WeightedQuickUnionUF(n * n + 2);
        UF2 = new WeightedQuickUnionUF(n * n + 1);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                grid[i][j] = 0;
            }
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        row = row - 1;
        col = col - 1;
        if (row < 0 || col < 0 || row > n - 1 || col > n - 1) {
            throw new IllegalArgumentException("row and col cannot be less than 1");
        }

        int id1 = row * n + col;
        grid[row][col] = 1;
        int[] xind = { -1, 1, 0, 0 };
        int[] yind = { 0, 0, -1, 1 };
        for (int i = 0; i < xind.length; i++) {
            if (!(row + xind[i] < 0 || row + xind[i] > n - 1 || col + yind[i] < 0
                    || col + yind[i] > n - 1)) {
                if (grid[row + xind[i]][col + yind[i]] == 1) {

                    int id2 = (row + xind[i]) * n + (col + yind[i]);
                    UF.union(id1, id2);
                    UF2.union(id1, id2);
                }
            }
        }
        if (row == 0) {
            UF.union(id1, n * n);
            UF2.union(id1, n * n);
        }
        if (row == n - 1) {
            UF.union(id1, n * n + 1);
        }

    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        row = row - 1;
        col = col - 1;
        if (row < 0 || col < 0 || row > n - 1 || col > n - 1) {
            throw new IllegalArgumentException("row and col cannot be less than 1.");
        }
        return grid[row][col] == 1;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        row = row - 1;
        col = col - 1;
        if (row < 0 || col < 0 || row > n - 1 || col > n - 1) {
            throw new IllegalArgumentException("row and col cannot be less than 1");
        }
        int id1 = row * n + col;
        return UF2.find(id1) == UF2.find(n * n);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        int numOpen = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    numOpen++;
                }
            }
        }
        return numOpen;
    }


    // does the system percolate?
    public boolean percolates() {
        return UF.find(n * n) == UF.find(n * n + 1);

    }

    // test client (optional)
    public static void main(String[] args) {
        String filename = "input1.txt";
        String fname = "test_files//" + filename;
        try {
            Scanner scan = new Scanner(new File(fname));
            int num = scan.nextInt();
            Percolation perc = new Percolation(num);
            while (scan.hasNext()) {
                int row = scan.nextInt();
                int col = scan.nextInt();
                perc.open(row, col);
            }
            scan.close();
            System.out.println("Number of Open Sites: " + perc.numberOfOpenSites());
            System.out.println("Percolates: " + perc.percolates());
        }
        catch (FileNotFoundException e) {
            System.out.println("Error: File Not Found");
        }

    }
}
