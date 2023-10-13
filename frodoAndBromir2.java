import java.util.Arrays;
import java.util.Scanner;

public class frodoAndBromir2 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n;
        int max = 0;
        int fixedX = 0;
        int fixedY = 0;
        n = scanner.nextInt();
        // int max = 0;
        int[][] arr = new int[(2 * n + 1)][];
        int[] fixed = new int[100];
        int fi = 0;
        arr[0] = new int[n + 2];
        arr[2 * n] = new int[n + 2];
        for (int i = 0; i <= n + 1; i++) { // making out homes
            arr[0][i] = -2;
            arr[2 * n][i] = -2;
        }

        for (int i = 1; i <= n; i++) { // scaning fors
            arr[i] = new int[i + n + 1];
            arr[i][0] = -2;
            arr[i][arr[i].length - 1] = -2;
            for (int j = 1; j < arr[i].length - 1; j++) {
                arr[i][j] = scanner.nextInt();
                if (arr[i][j] == 1) {
                    fixedX = j;
                    fixedY = i;
                }
                if (arr[i][j] != 0 && arr[i][j] != -1) {
                    if (max < arr[i][j])
                        max = arr[i][j];
                    fixed[fi] = arr[i][j];
                    fi++;
                }

            }
        }


        for (int i = n + 1; i <= (2 * n) - 1; i++) { // scaning fors
            // System.out.println(i);

            arr[i] = new int[2 * n - (i - n - 1)];
            arr[i][0] = -2;
            arr[i][arr[i].length - 1] = -2;
            for (int j = 1; j < arr[i].length - 1; j++) {
                arr[i][j] = scanner.nextInt();
                if (arr[i][j] == 1) {
                    fixedX = j;
                    fixedY = i;
                }
                if (arr[i][j] != 0 && arr[i][j] != -1) {
                    if (max < arr[i][j])
                        max = arr[i][j];
                    fixed[fi] = arr[i][j];
                    fi++;
                }
            }
        }

        int[] fixed2 = Arrays.copyOf(fixed, fi); // the end index has 0 value
        Arrays.sort(fixed2);
     
        solveIt(arr, fixed2, n, fixedX, fixedY, max, 1);

    }


    static int solveIt(int[][] arr, int[] fixed, int n, int x, int y, int max, int fi) {


 

        if (fixed[fi-1] == max) {
            printArr(arr);
            System.exit(0);
        }

        if (arr[y][x] == fixed[fi] - 1 && neighberXOfaNUM(arr, n, x, y, fixed[fi]) == 1) {
  
            int[] px = new int[1];
            int[] py = new int[1];
            finedSheshzelie(arr, fixed[fi], px, py);
            fi++;
            solveIt(arr, fixed, n, px[0], py[0], max, fi);
            return 0;
        }

        int neighbersi[] = new int[6];
        int neighbersj[] = new int[6];
        makeOurNeighber(arr, y, x, n, neighbersi, neighbersj);

        for (int i = 0; i < neighbersj.length; i++) {
            if (neighbersi[i] == 0)
                continue;
            if (checkTheFixedHomes(fixed, arr[y][x] + 1) == 1) {
                int temp = arr[neighbersi[i]][neighbersj[i]];
                if (temp == 0) {
                    arr[neighbersi[i]][neighbersj[i]] = arr[y][x] + 1;
                    solveIt(arr, fixed, n, neighbersj[i], neighbersi[i], max, fi);
                    arr[neighbersi[i]][neighbersj[i]] = temp;
                }
            } else if (checkTheFixedHomes(fixed, arr[y][x] + 1) == 0) {
                return 0;
            }
        }
        return 0;
    }

    static void printArr(int[][] arr) {
        for (int j = 1; j < arr.length-1; j++) {
            for (int j2 = 1; j2 < arr[j].length-1; j2++) {
                if (j2 != 1)
                    System.out.print(" ");
                System.out.printf("%d", arr[j][j2]);
            }
            System.out.println();
        }
    }

    static int makeOurNeighber(int[][] arr, int i, int j, int n, int[] neighbersi, int[] neighbersj) {
        if (i < n) {
            if (arr[i][j - 1] == 0) {
                neighbersi[0] = i;
                neighbersj[0] = j - 1;
            }

            if (arr[i][j + 1] == 0) {
                neighbersi[1] = i;
                neighbersj[1] = j + 1;
            }

            if (arr[i - 1][j - 1] == 0) {
                neighbersi[2] = i - 1;
                neighbersj[2] = j - 1;
            }

            if (arr[i - 1][j] == 0) {
                neighbersi[3] = i - 1;
                neighbersj[3] = j;
            }

            if (arr[i + 1][j] == 0) {
                neighbersi[4] = i + 1;
                neighbersj[4] = j;
            }

            if (arr[i + 1][j + 1] == 0) {
                neighbersi[5] = i + 1;
                neighbersj[5] = j + 1;
            }
        } else if (i > n) {

            if (arr[i][j - 1] == 0) {
                neighbersi[0] = i;
                neighbersj[0] = j - 1;

            }
            if (arr[i][j + 1] == 0) {
                neighbersi[1] = i;
                neighbersj[1] = j + 1;

            }
            if (arr[i - 1][j + 1] == 0) {
                neighbersi[2] = i - 1;
                neighbersj[2] = j + 1;

            }
            if (arr[i - 1][j] == 0) {
                neighbersi[3] = i - 1;
                neighbersj[3] = j;

            }
            if (arr[i + 1][j] == 0) {
                neighbersi[4] = i + 1;
                neighbersj[4] = j;

            }
            if (arr[i + 1][j - 1] == 0) {
                neighbersi[5] = i + 1;
                neighbersj[5] = j - 1;
            }

        } else if (i == n) {

            if (arr[i][j - 1] == 0) {
                neighbersi[0] = i;
                neighbersj[0] = j - 1;
            }
            if (arr[i][j + 1] == 0) {
                neighbersi[1] = i;
                neighbersj[1] = j + 1;

            }
            if (arr[i - 1][j - 1] == 0) {
                neighbersi[2] = i - 1;
                neighbersj[2] = j - 1;
            }
            if (arr[i - 1][j] == 0) {
                neighbersi[3] = i - 1;
                neighbersj[3] = j;
            }
            if (arr[i + 1][j] == 0) {
                neighbersi[4] = i + 1;
                neighbersj[4] = j;
            }
            if (arr[i + 1][j - 1] == 0) {
                neighbersi[5] = i + 1;
                neighbersj[5] = j - 1;
            }
        }

        return 0;
    }

    static int checkTheFixedHomes(int[] fixed, int a) {

        for (int j = 0; j < fixed.length; j++) {
            if (a == fixed[j])
                return 0;
        }

        return 1;
    }

    static int neighberXOfaNUM(int[][] arr, int n, int curentx, int curenty, int target) {
        if (curenty < n) {
            if (arr[curenty][curentx - 1] == target || arr[curenty][curentx + 1] == target
                    || arr[curenty - 1][curentx] == target || arr[curenty - 1][curentx - 1] == target
                    || arr[curenty + 1][curentx] == target || arr[curenty + 1][curentx + 1] == target)
                return 1;
        } else if (curenty > n) {
            if (arr[curenty][curentx - 1] == target || arr[curenty][curentx + 1] == target
                    || arr[curenty - 1][curentx] == target || arr[curenty - 1][curentx + 1] == target
                    || arr[curenty + 1][curentx] == target || arr[curenty + 1][curentx - 1] == target)
                return 1;
        } else if (curenty == n) {
            if (arr[curenty][curentx - 1] == target || arr[curenty][curentx + 1] == target
                    || arr[curenty - 1][curentx] == target || arr[curenty - 1][curentx - 1] == target
                    || arr[curenty + 1][curentx] == target || arr[curenty + 1][curentx - 1] == target)
                return 1;
        }

        return 0;
    }

    static int finedSheshzelie(int arr[][], int tar, int[] x, int[] y) {
        for (int i = 1; i < arr.length - 1; i++) {
            for (int j = 1; j < arr[i].length - 1; j++) {
                if (arr[i][j] == tar) {
                    x[0] = j;
                    y[0] = i;
                    return 0;
                }
            }
        }

        return 0;
    }

}
