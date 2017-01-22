import java.util.ArrayList;
import java.util.List;

/**
 Find numbers divisible by 3 not 9 (without using remainder and division)
 */
public class Div3Not9 {
    public List<Integer> findElementsDiv3Not9(int n) {
        List<Integer> res = new ArrayList<>();
        if (n < 3) return res;

        boolean[] flags = new boolean[n+1];
        flags[3] = true;

        int i = 1;
        while (i * 3 <= n) {
            if (! flags[i])
                res.add(i * 3);

            flags[i * 3] = true;
            i++;
        }

//        for (int i1=1; i<flags.length;i++)
//            System.out.print(new Integer(i1).toString() + flags[i1] + " ");
//        System.out.println();

        return res;
    }

    public static void main(String[] args) {
        List<Integer> list = (new Div3Not9()).findElementsDiv3Not9(100);
        System.out.println(list);
    }
}
