package work4;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by zqb on 2016/11/4.
 */
public class Main {
    /*
        1、给定一副A*B的图像，对其进行扩充（补0），使其长和宽变为大于或等于自身长度的最小2的整数次幂。
        2、对扩充后的图像进行移动中心操作，即乘以（-1）^(x+y)。
        3、计算第二步的FFT（利用一维快速傅里叶变换实现二维）【生成一个大小和扩充图像一致的二维复数数组F（u，v）】
        4、生成一个频率域的滤波器H（u，v）【注意这个是复数，大小和第三步的结果一样】
        5、点乘，令g（u，v）= H（u，v）·F（u，v）
        6、对g做快速傅里叶逆变换IFFT
        7、对第6步的结果取实部
        8、队第7步的结果乘以（-1）^(x+y)再次移动中心。
        9、剪裁获得与原图大小一致的结果。
    */
    public static void main(String[] args)
    {
        String path=System.getProperty("user.dir")+"/res/logo.jpg";
        String des_path=System.getProperty("user.dir")+"/res/filter_gaussian_high.jpg";
        BufferedImage img=getBufferedImage(path);
        if(img!=null)
        {
            //Filter filter=new Filter();
            //filter.FFT(img,des_path);
            new GaussianLowFilter().GaussianLowFilter(img,des_path);
        }
    }

    private static BufferedImage getBufferedImage(String path)
    {
        File file=new File(path);
        if(file.exists())
        {
            try
            {
                BufferedImage image= ImageIO.read(file);
                return image;
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        System.out.println("文件路径"+path+"不存在");
        return null;
    }
}
