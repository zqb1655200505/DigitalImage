package work5;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

/**
 * Created by zqb on 2016/11/8.
 */
public class GaussianNoise {
    private double _mNoiseFactor = 25;
    protected void GaussianNoise(BufferedImage img,String save_path)
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
                r=addGaussianNoise(r,random);
                g=addGaussianNoise(g,random);
                b=addGaussianNoise(b,random);
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

    private int addGaussianNoise(int channel, Random random)
    {
        int v, ran;
        boolean inRange = false;
        do {
            ran = (int)Math.round(random.nextGaussian()*_mNoiseFactor);
            v = channel + ran;
            // check whether it is valid single channel value
            inRange = (v>=0 && v<=255);
            if (inRange)
            {
                channel = v;
            }
        } while (!inRange);
        return channel;
    }
}
