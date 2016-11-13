package work5;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

/**
 * Created by zqb on 2016/11/13.
 */
public class InverseHarmonicMean {
    private double r=0.0,g=0.0,b=0.0,r1=0,g1=0,b1=0;
    //生成Q ( -5~5 )
    Random random=new Random();
    double Q=random.nextDouble()*10-5;
    protected void inverseHarmonicMean(BufferedImage image,String save_path)
    {
        int width=image.getWidth();
        int height=image.getHeight();
        BufferedImage newImg=new BufferedImage(width,height,image.getType());
        for(int i=0;i<width;i++)
        {
            for(int j=0;j<height;j++)
            {
                if(i-1>=0&&j-1>=0&&i+1<width&&j+1<height)
                {
                    for(int k=i-1;k<=i+1;k++)
                    {
                        for(int h=j-1;h<=j+1;h++)
                        {
                            count(image.getRGB(k,h));
                        }
                    }
                    int newrgb=new Color((int)(r1/r),(int)(g1/g),(int)(b1/b)).getRGB();
                    r=g=b=r1=g1=b1=0;
                    newImg.setRGB(i,j,newrgb);
                }
                else
                {
                    newImg.setRGB(i,j,image.getRGB(i,j));
                }
            }
        }
        File result=new File(save_path);
        try {
            ImageIO.write(newImg,"jpg",result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void count(int rgb)
    {
        r+=Math.pow((rgb>>16)&0xff,Q);
        g+=Math.pow((rgb>>8)&0xff,Q);
        b+=Math.pow(rgb&0xff,Q);
        r1+=Math.pow((rgb>>16)&0xff,Q+1);
        g1+=Math.pow((rgb>>8)&0xff,Q+1);
        b1+=Math.pow(rgb&0xff,Q+1);
    }
}
