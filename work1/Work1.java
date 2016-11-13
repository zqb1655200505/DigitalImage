package work1;

import java.util.Scanner;

/**
 * Created by zqb on 2016/10/2.
 */
public class Work1 {
    public static void main(String[] args)
    {
        Scanner scanner=new Scanner(System.in);

        System.out.println("请输入第一个点坐标：");
        Double x1=scanner.nextDouble();
        Double y1=scanner.nextDouble();
        Double z1=scanner.nextDouble();
        System.out.println();

        System.out.println("请输入第二个点坐标：");
        Double x2=scanner.nextDouble();
        Double y2=scanner.nextDouble();
        Double z2=scanner.nextDouble();
        System.out.println("两点之间的欧氏距离为："+Math.sqrt((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2)+(z1-z2)*(z1-z2)));
    }
}
