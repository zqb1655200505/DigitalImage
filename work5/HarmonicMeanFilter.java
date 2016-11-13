package work5;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by zqb on 2016/11/13.
 */
public class HarmonicMeanFilter {
    private double r=0.0,g=0.0,b=0.0;

    protected void harmonicMeanFilter(BufferedImage image,String save_path)
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
                    int newrgb=new Color((int)(9.0/r),(int)(9.0/g),(int)(9.0/b)).getRGB();
                    r=g=b=0.0;
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
        r+=1.0/((rgb>>16)&0xff);
        g+=1.0/((rgb>>8)&0xff);
        b+=1.0/(rgb&0xff);
    }
}
