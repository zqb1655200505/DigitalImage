package work5;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by zqb on 2016/11/8.
 */
public class Main {
    public static void main(String[] args)
    {
        String path=System.getProperty("user.dir")+"/res/result_gaussian.jpg";
        String des_path=System.getProperty("user.dir")+"/res/harmonicMeanFilter_gaussian.jpg";
        BufferedImage img=getBufferedImage(path);
        if(img!=null)
        {
            //GaussianNoise gaussianNoise=new GaussianNoise();
            //gaussianNoise.GaussianNoise(img,des_path);
            //new PoissonNoise().PoissonNoise(img,des_path);
            //new SaltAndPepper().SaltAndPepper(img,des_path);
            //new ArithmeticMeanValueFilter().ArithmeticMeanValueFilter(img,des_path);
            //new GeometricalMeanValueFilter().geometricalMeanValueFilter(img,des_path);
            new HarmonicMeanFilter().harmonicMeanFilter(img,des_path);
            //new InverseHarmonicMean().inverseHarmonicMean(img,des_path);
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
