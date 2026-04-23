package demo;

/**
 * @author duoyian
 * @date 2026/4/23
 */
public class 汉诺塔 {

    public static void main(String[] args) {
        handle(7, "A", "C", "B");
        System.out.println("count:" + count);
    }

    private static int count = 0;

    public static void handle(int n, String cur, String next, String other) {
        if (n == 1) {
            count++;
            System.out.println(cur + "->" + next);
            return;
        }
        handle(n- 1, cur,other, next );
        count++;
        System.out.println(cur + "->" + next);
        handle(n- 1, other, next, cur);
    }
}
