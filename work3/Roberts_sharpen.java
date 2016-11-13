package work3;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by zqb on 2016/10/23.
 */
public class Roberts_sharpen {

    protected void Roberts_sharpen(BufferedImage img, String save_path)
    {
        int width=img.getWidth();
        int height=img.getHeight();
        BufferedImage newImage=new BufferedImage(width,height,img.getType());
        for(int i=0;i<width;i++)
        {
            for(int j=0;j<height;j++)
            {
                if(i+1<width&&j+1<height)
                {
                    int rgb1=img.getRGB(i,j);
                    int r1=(rgb1>>16)&0xff;
                    int g1=(rgb1>>8)&0xff;
                    int b1=rgb1&0xff;

                    int rgb2=img.getRGB(i+1,j);
                    int r2=(rgb2>>16)&0xff;
                    int g2=(rgb2>>8)&0xff;
                    int b2=rgb2&0xff;

                    int rgb3=img.getRGB(i,j+1);
                    int r3=(rgb3>>16)&0xff;
                    int g3=(rgb3>>8)&0xff;
                    int b3=rgb3&0xff;

                    int rgb4=img.getRGB(i+1,j+1);
                    int r4=(rgb4>>16)&0xff;
                    int g4=(rgb4>>8)&0xff;
                    int b4=rgb4&0xff;
                    //三通道分别算
                    int r14=Math.abs(r1-r4);
                    int r23=Math.abs(r2-r3);
                    int g14=Math.abs(g1-g4);
                    int g23=Math.abs(g2-g3);
                    int b14=Math.abs(b1-b4);
                    int b23=Math.abs(b2-b3);

                    //f(x,y)=|f(x,y)-f(x+1,y+1)|+|f(x+1,y)-f(x,y+1)|
                    //或f(x,y)=max(|f(x,y)-f(x+1,y+1)|,|f(x+1,y)-f(x,y+1)|)
                    int r=Math.min(Math.max(r14,r23),255);
                    int g=Math.min(Math.max(g14,g23),255);
                    int b=Math.min(Math.max(b14,b23),255);
                    int rgb=new Color(r,g,b).getRGB();
                    newImage.setRGB(i,j,rgb);
                }
            }
        }
        File roberts_result=new File(save_path);
        try {
            ImageIO.write(newImage,"jpg",roberts_result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
