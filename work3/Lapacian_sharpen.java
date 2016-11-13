package work3;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by zqb on 2016/10/23.
 */
public class Lapacian_sharpen {
    protected void Prewitt_sharpen(BufferedImage img, String save_path)
    {
        int width=img.getWidth();
        int height=img.getHeight();
        BufferedImage newImage=new BufferedImage(width,height,img.getType());
        //锐化
        for(int i=0;i<width;i++)
        {
            for(int j=0;j<height;j++)
            {
                if(i-1>=0&&j-1>=0&&i+1<width&&j+1<height)
                {
                    int rgb1=img.getRGB(i,j);
                    int r1=(rgb1>>16)&0xff;
                    int g1=(rgb1>>8)&0xff;
                    int b1=rgb1&0xff;

                    int rgb2=img.getRGB(i-1,j);
                    int r2=(rgb2>>16)&0xff;
                    int g2=(rgb2>>8)&0xff;
                    int b2=rgb2&0xff;

                    int rgb3=img.getRGB(i+1,j);
                    int r3=(rgb3>>16)&0xff;
                    int g3=(rgb3>>8)&0xff;
                    int b3=rgb3&0xff;

                    int rgb7=img.getRGB(i,j-1);
                    int r7=(rgb7>>16)&0xff;
                    int g7=(rgb7>>8)&0xff;
                    int b7=rgb7&0xff;

                    int rgb8=img.getRGB(i,j+1);
                    int r8=(rgb8>>16)&0xff;
                    int g8=(rgb8>>8)&0xff;
                    int b8=rgb8&0xff;

                    int r=Math.max(Math.min(r1+(r2+r3+r7+r8-4*r1),255),0);
                    int g=Math.max(Math.min(g1+(g2+g3+g7+g8-4*g1),255),0);
                    int b=Math.max(Math.min(b1+(b2+b3+b7+b8-4*b1),255),0);

//                    int r=Math.abs(r2+r3+r7+r8-4*r1);
//                    int g=Math.abs(g2+g3+g7+g8-4*g1);
//                    int b=Math.abs(b2+b3+b7+b8-4*b1);
                    int rgb=new Color(r,g,b).getRGB();
                    newImage.setRGB(i,j,rgb);
                }
                else//将原色填充回去以去除黑边框
                {
                    newImage.setRGB(i,j,img.getRGB(i,j));
                }
            }
        }

        File lapacian_result=new File(save_path);
        try {
            ImageIO.write(newImage,"jpg",lapacian_result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
