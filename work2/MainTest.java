package work2;

import java.io.File;

/**
 * Created by zqb on 2016/10/15.
 */
public class MainTest {
    public static void main(String[] args)
    {
        String path1=System.getProperty("user.dir")+"/res/result0.jpg";
        String path2=System.getProperty("user.dir")+"/res/result1.jpg";
        String path3=System.getProperty("user.dir")+"/res/result2.jpg";
        if(check(path1)&&check(path2)&&check(path3))
        {
            /*
                像素比较法
             */
            PixelCompare compare=new PixelCompare();
            compare.pixel_compare(path1,path2,path3);

            /*
                直方图比较法
             */
            //生成直方图
            //HistogramSubtract histogramSubtract=new HistogramSubtract();
            //histogramSubtract.histogram_subtract(path1,path2,path3);
            //比较直方图
        }
    }
    private static boolean check(String path)
    {
        File file=new File(path);
        if(!file.exists())
        {
            System.out.println("文件路径"+path+"不存在");
            return false;
        }
        return true;
    }
}
