package work3;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by zqb on 2016/10/23.
 */
public class Smooth {
    protected void smooth(BufferedImage img,String save_path)
    {
        int width=img.getWidth();
        int height=img.getHeight();
        BufferedImage newImage=new BufferedImage(width,height,img.getType());
        //先进行平滑处理,最外圈补0
        for(int i=0;i<width;i++)
        {
            for(int j=0;j<height;j++)
            {
                int rgb1,rgb2,rgb3,rgb4,rgb5,rgb6,rgb7,rgb8,rgb9;
                int r1,r2,r3,r4,r5,r6,r7,r8,r9;
                int g1,g2,g3,g4,g5,g6,g7,g8,g9;
                int b1,b2,b3,b4,b5,b6,b7,b8,b9;

                if(j-1<0||i-1<0)
                {
                    r1=g1=b1=0;
                }
                else
                {
                    rgb1=img.getRGB(i-1,j-1);
                    r1=(rgb1>>16)&0xff;
                    g1=(rgb1>>8)&0xff;
                    b1=rgb1&0xff;
                }


                if(i-1<0)
                {
                    r2=g2=b2=0;
                }
                else
                {
                    rgb2=img.getRGB(i-1,j);
                    r2=(rgb2>>16)&0xff;
                    g2=(rgb2>>8)&0xff;
                    b2=rgb2&0xff;
                }

                if(j+1>=height||i-1<0)
                {
                    r3=g3=b3=0;
                }
                else
                {
                    rgb3=img.getRGB(i-1,j+1);
                    r3=(rgb3>>16)&0xff;
                    g3=(rgb3>>8)&0xff;
                    b3=rgb3&0xff;
                }

                if(i+1>=width||j-1<0)
                {
                    r4=g4=b4=0;
                }
                else
                {
                    rgb4=img.getRGB(i+1,j-1);
                    r4=(rgb4>>16)&0xff;
                    g4=(rgb4>>8)&0xff;
                    b4=rgb4&0xff;
                }

                if(j-1<0)
                {
                    r7=g7=b7=0;
                }
                else
                {
                    rgb7=img.getRGB(i,j-1);
                    r7=(rgb7>>16)&0xff;
                    g7=(rgb7>>8)&0xff;
                    b7=rgb7&0xff;
                }



                if(i+1>=width)
                {
                    r5=g5=b5=0;
                }
                else
                {
                    rgb5=img.getRGB(i+1,j);
                    r5=(rgb5>>16)&0xff;
                    g5=(rgb5>>8)&0xff;
                    b5=rgb5&0xff;
                }

                if(i+1<width&&j+1<height)
                {
                    rgb6=img.getRGB(i+1,j+1);
                    r6=(rgb6>>16)&0xff;
                    g6=(rgb6>>8)&0xff;
                    b6=rgb6&0xff;
                }
                else
                {
                    r6=g6=b6=0;
                }

                if(j+1<height)
                {
                    rgb8=img.getRGB(i,j+1);
                    r8=(rgb8>>16)&0xff;
                    g8=(rgb8>>8)&0xff;
                    b8=rgb8&0xff;
                }
                else
                {
                    r8=g8=b8=0;
                }

                rgb9=img.getRGB(i,j);
                r9=(rgb9>>16)&0xff;
                g9=(rgb9>>8)&0xff;
                b9=rgb9&0xff;

                int r=(r1+r2+r3+r4+r5+r6+r7+r8+r9)/9;
                int b=(b1+b2+b3+b4+b5+b6+b7+b8+b9)/9;
                int g=(g1+g2+g3+g4+g5+g6+g7+g8+g9)/9;
                int rgb=new Color(r,g,b).getRGB();
                newImage.setRGB(i,j,rgb);
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
