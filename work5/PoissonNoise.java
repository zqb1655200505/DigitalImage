package work5;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

/**
 * Created by zqb on 2016/11/8.
 */
public class PoissonNoise {
    private double _mNoiseFactor = 100;
    public final static double MEAN_FACTOR = 2.0;
    protected void PoissonNoise(BufferedImage img, String save_path)
    {
        int width=img.getWidth();
        int height=img.getHeight();
        BufferedImage newImg=new BufferedImage(width,height,img.getType());
        Random random=new Random();
        for(int i=0;i<width;i++)
        {
            for(int j=0;j<height;j++)
            {
                int rgb=img.getRGB(i,j);
                int alpha = (rgb >> 24)& 0xff; //透明度通道
                int r=(rgb>>16)&0xff;
                int g=(rgb>>8)&0xff;
                int b=rgb&0xff;
                r=addPoissonNoise(r,random);
                g=addPoissonNoise(g,random);
                b=addPoissonNoise(b,random);
                int newrgb=(alpha << 24) | (r << 16) | (g << 8) | b;
                newImg.setRGB(i,j,newrgb);
            }
        }
        File result=new File(save_path);
        try {
            ImageIO.write(newImg,"jpg",result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private int addPoissonNoise(int pixel, Random random) {
        // init:
        double L = Math.exp(-_mNoiseFactor * MEAN_FACTOR);
        int k = 0;
        double p = 1;
        do {
            k++;
            // Generate uniform random number u in [0,1] and let p ← p × u.
            p *= random.nextDouble();
        } while (p >= L);
        double retValue = Math.max((pixel + (k - 1) / MEAN_FACTOR - _mNoiseFactor), 0);
        return (int)retValue;
    }
}
