package everyday;

import java.util.Arrays;

/**
 * @author duoyian
 * @date 2026/4/3
 */
public class LC3661_可以被机器人摧毁的最大墙壁数目 {
    public static void main(String[] args) {
        int[] robots = {17,59,32,11,72,18};
        int[] distance = {5,7,6,5,2,10};
        int[] walls = {17,25,33,29,54,53,18,35,39,37,20,14,34,13,16,58,22,51,56,27,10,15,12,23,45,43,21,2,42,7,32,40,8,9,1,5,55,30,38,4,3,31,36,41,57,28,11,49,26,19,50,52,6,47,46,44,24,48};
        System.out.println(maxWalls(robots, distance, walls));
        System.out.println(maxWallsLc(robots, distance, walls));
    }
    public static int maxWalls(int[] robots, int[] distance, int[] walls) {
        int num = robots.length;
        int wNum = walls.length;
        int[][] rd = new int[num][8];
        for(int i = 0; i < num; i++) {
            rd[i][0] = robots[i];
            rd[i][1] = distance[i];
        }
        Arrays.sort(rd,(a,b)->a[0] - b[0]);
        Arrays.sort(walls);
        int count = 0;
        int index = 0;

        for (int i = 0; i < num; i++) {
            if (index >= wNum) {
                break;
            }
            int left = Integer.MIN_VALUE;
            if (i > 0) {
                left = Math.max(rd[i - 1][0] + 1,left);
            }
            left = Math.max(left, rd[i][0] - rd[i][1]);
            int right = Integer.MAX_VALUE;
            if (i < num - 1) {
                right = Math.min(right, rd[i+ 1][0] - 1);
            }
            right = Math.min(right, rd[i][0] + rd[i][1]);
            rd[i][2] = left;
            rd[i][3] = right;
        }
        for(int i = 0; i < num; i++) {
            if (index >= wNum) {
                break;
            }
            int cur = walls[index];
            int left = rd[i][2];
            int right = rd[i][3];
            int loc = rd[i][0];
            int leftDup = Integer.MIN_VALUE;
            if (i > 0) {
                leftDup = rd[i - 1][3];
            }
            int rightDup = Integer.MAX_VALUE;
            if (i < num - 1) {
                rightDup = rd[i + 1][2];
            }
            while(index < wNum) {
                if (cur < left) {
                    index++;
                }
                if (cur > right) {
                    break;
                }
                if (cur >= left && cur <= loc) {
                    rd[i][4]++;
                }
                if (cur <= right && cur >= loc) {
                    rd[i][5]++;
                }
                if (i > 0 && cur  <= leftDup && cur >= left) {
                    rd[i][6]++;
                }
                if (i < num - 1 && cur >= rightDup && cur <= right) {
                    rd[i][7]++;
                }
                index++;
            }
        }
        int dp[][] = new int[num][2];
        dp[0][0] = rd[0][4];
        dp[0][1] = rd[0][5];
        for (int i = 1; i < num; i++) {
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] - rd[i][6]) + rd[i][4];
            dp[i][1] = Math.max(dp[i - 1][0], dp[i - 1][1]) + rd[i][5];
        }

        return Math.max(dp[num - 1][0], dp[num - 1][1]);
    }

    public static int maxWallsLc(int[] robots, int[] distance, int[] walls) {
        int n = robots.length;
        int[][] robotDist = new int[n][2];
        for (int i = 0; i < n; i++) {
            robotDist[i][0] = robots[i];
            robotDist[i][1] = distance[i];
        }
        Arrays.sort(robotDist, (a, b) -> a[0] - b[0]);
        Arrays.sort(walls);

        int m = walls.length;
        int rightPtr = 0, leftPtr = 0, curPtr = 0, robotPtr = 0;

        int prevLeft = 0, prevRight = 0, prevNum = 0;
        int subLeft = 0, subRight = 0;

        for (int i = 0; i < n; i++) {
            int robotPos = robotDist[i][0];
            int robotDistVal = robotDist[i][1];

            while (rightPtr < m && walls[rightPtr] <= robotPos) {
                rightPtr++;
            }
            int pos1 = rightPtr;

            while (curPtr < m && walls[curPtr] < robotPos) {
                curPtr++;
            }
            int pos2 = curPtr;

            int leftBound = robotPos - robotDistVal;
            if (i >= 1) {
                leftBound = Math.max(robotPos - robotDistVal, robotDist[i - 1][0] + 1);
            }
            while (leftPtr < m && walls[leftPtr] < leftBound) {
                leftPtr++;
            }
            int leftPos = leftPtr;
            int currentLeft = pos1 - leftPos;

            int rightBound = robotPos + robotDistVal;
            if (i < n - 1) {
                rightBound = Math.min(robotPos + robotDistVal, robotDist[i + 1][0] - 1);
            }
            while (rightPtr < m && walls[rightPtr] <= rightBound) {
                rightPtr++;
            }
            int rightPos = rightPtr;
            int currentRight = rightPos - pos2;

            int currentNum = 0;
            if (i > 0) {
                while (robotPtr < m && walls[robotPtr] < robotDist[i - 1][0]) {
                    robotPtr++;
                }
                int pos3 = robotPtr;
                currentNum = pos1 - pos3;
            }

            if (i == 0) {
                subLeft = currentLeft;
                subRight = currentRight;
            } else {
                int newsubLeft = Math.max(subLeft + currentLeft, subRight - prevRight + Math.min(currentLeft + prevRight, currentNum));
                int newsubRight = Math.max(subLeft + currentRight, subRight + currentRight);
                subLeft = newsubLeft;
                subRight = newsubRight;
            }

            prevLeft = currentLeft;
            prevRight = currentRight;
            prevNum = currentNum;
        }

        return Math.max(subLeft, subRight);
    }
}
