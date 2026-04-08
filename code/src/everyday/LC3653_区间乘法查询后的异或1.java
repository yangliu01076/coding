package everyday;

/**
 * @author duoyian
 * @date 2026/4/8
 */
public class LC3653_区间乘法查询后的异或1 {

    public static void main(String[] args) {
        Solution solution = new LC3653_区间乘法查询后的异或1().new Solution();
        int[] nums = {780};
        // [[0,0,1,13],[0,0,1,17],[0,0,1,9],[0,0,1,18],[0,0,1,16],[0,0,1,6],[0,0,1,4],[0,0,1,11],[0,0,1,7],[0,0,1,18],[0,0,1,8],[0,0,1,15],[0,0,1,12]]
        int[][] queries = {{0, 0, 1, 13}, {0, 0, 1, 17}, {0, 0, 1, 9}, {0, 0, 1, 18}, {0, 0, 1, 16}, {0, 0, 1, 6}, {0, 0, 1, 4}, {0, 0, 1, 11}, {0, 0, 1, 7}, {0, 0, 1, 18}, {0, 0, 1, 8}, {0, 0, 1, 15}, {0, 0, 1, 12}};
        int i = solution.xorAfterQueries(nums, queries);
        System.out.println(i);
    }

    class Solution {
        public int xorAfterQueries(int[] nums, int[][] queries) {
            for (int[] query : queries) {
                query(nums, query);
            }
            return xor(nums);
        }

        private void query(int[] nums, int[] query) {
            for (int i = query[0]; i <= query[1]; i += query[2]) {
                nums[i] = (int)(((long) nums[i] * query[3]) % (1000000007));
            }
        }

        private int xor(int[] nums) {
            int res = 0;
            for (int num : nums) {
                res ^= num;
            }
            return res;
        }
    }
}
