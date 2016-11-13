package work3;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by zqb on 2016/10/23.
 */
public class Prewitt_sharpen {
    protected void Prewitt_sharpen(BufferedImage img, String save_path)
    {
        int width=img.getWidth();
        int height=img.getHeight();
        BufferedImage newImage=new BufferedImage(width,height,img.getType());
        for(int i=0;i<width;i++)
        {
            for(int j=0;j<height;j++)
            {
                if(i-1>=0&&j-1>=0&&i+1<width&&j+1<height)//忽略了最外一圈
                {
                    int rgb1=img.getRGB(i-1,j-1);
                    int r1=(rgb1>>16)&0xff;
                    int g1=(rgb1>>8)&0xff;
                    int b1=rgb1&0xff;

                    int rgb2=img.getRGB(i-1,j);
                    int r2=(rgb2>>16)&0xff;
                    int g2=(rgb2>>8)&0xff;
                    int b2=rgb2&0xff;

                    int rgb3=img.getRGB(i-1,j+1);
                    int r3=(rgb3>>16)&0xff;
                    int g3=(rgb3>>8)&0xff;
                    int b3=rgb3&0xff;

                    int rgb4=img.getRGB(i+1,j-1);
                    int r4=(rgb4>>16)&0xff;
                    int g4=(rgb4>>8)&0xff;
                    int b4=rgb4&0xff;

                    int rgb5=img.getRGB(i+1,j);
                    int r5=(rgb5>>16)&0xff;
                    int g5=(rgb5>>8)&0xff;
                    int b5=rgb5&0xff;

                    int rgb6=img.getRGB(i+1,j+1);
                    int r6=(rgb6>>16)&0xff;
                    int g6=(rgb6>>8)&0xff;
                    int b6=rgb6&0xff;

                    int rgb7=img.getRGB(i,j-1);
                    int r7=(rgb7>>16)&0xff;
                    int g7=(rgb7>>8)&0xff;
                    int b7=rgb7&0xff;

                    int rgb8=img.getRGB(i,j+1);
                    int r8=(rgb8>>16)&0xff;
                    int g8=(rgb8>>8)&0xff;
                    int b8=rgb8&0xff;
//                    G(i)=|[f(i-1,j-1)+f(i-1,j)+f(i-1，j+1)]-[f(i+1,j-1)+f(i+1，j)+f(i+1，j+1)]|
//                    G(j)=|[f(i-1,j+1)+f(i,j+1)+f(i+1，j+1)]-[f(i-1,j-1)+f(i,j-1)+f(i+1，j-1)]|
//                    则 P(i,j)=max[G(i),G(j)]或 P(i,j)=G(i)+G(j)
                    int R1=Math.abs((r1+r2+r3)-(r4+r5+r6));
                    int G1=Math.abs((g1+g2+g3)-(g4+g5+g6));
                    int B1=Math.abs((b1+b2+b3)-(b4+b5+b6));

                    int R2=Math.abs((r3+r8+r6)-(r1+r7+r4));
                    int G2=Math.abs((g3+g8+g6)-(g1+g7+g4));
                    int B2=Math.abs((b3+b8+b6)-(b1+b7+b4));

                    int r=Math.min(Math.max(R1,R2),255);
                    int g=Math.min(Math.max(G1,G2),255);
                    int b=Math.min(Math.max(B1,B2),255);
//                    int r=R1+R2;
//                    int g=G1+G2;
//                    int b=B1+B2;

                    int rgb=new Color(r,g,b).getRGB();
                    newImage.setRGB(i,j,rgb);
                }
            }
        }
        File prewitt_result=new File(save_path);
        try {
            ImageIO.write(newImage,"jpg",prewitt_result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
