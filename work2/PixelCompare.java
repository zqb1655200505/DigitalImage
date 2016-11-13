package work2;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by zqb on 2016/10/15.
 */
public class PixelCompare {

    protected void pixel_compare(String path1,String path2,String path3)
    {
        try {
            BufferedImage image1= ImageIO.read(new File(path1));
            BufferedImage image2=ImageIO.read(new File(path2));
            BufferedImage image3=ImageIO.read(new File(path3));
            int width=image1.getWidth();
            int height=image1.getHeight();
            int cnt1=0;
            int cnt2=0;
            for(int i=0;i<width;i++)
            {
                for(int j=0;j<height;j++)
                {
                    int rgb1=image1.getRGB(i,j);
                    int rgb2=image2.getRGB(i,j);
                    int rgb3=image3.getRGB(i,j);

                    int r1=(rgb1>>16)&0xff;
                    int g1=(rgb1>>8)&0xff;
                    int b1=rgb1&0xff;
                    int gray1=(int)(r1*0.3+g1*0.59+b1*0.11);

                    int r2=(rgb2>>16)&0xff;
                    int g2=(rgb2>>8)&0xff;
                    int b2=rgb2&0xff;
                    int gray2=(int)(r2*0.3+g2*0.59+b2*0.11);

                    int r3=(rgb3>>16)&0xff;
                    int g3=(rgb3>>8)&0xff;
                    int b3=rgb3&0xff;
                    int gray3=(int)(r3*0.3+g3*0.59+b3*0.11);

                    int gap12=Math.abs(gray1-gray2);
                    int gap13=Math.abs(gray1-gray3);
                    if(gap12>25)
                    {
                        cnt1++;
                    }
                    if(gap13>25)
                    {
                        cnt2++;
                    }
                }
            }
            System.out.println("图像像素总共为"+width*height);
            System.out.println("cnt1="+cnt1);
            System.out.println("cnt2="+cnt2);
            if(cnt1>width*height*0.1)
            {
                System.out.println("图像1和图像2不属于同一帧");
            }
            else
            {
                System.out.println("图像1和图像2属于同一帧");
            }
            if(cnt2>width*height*0.1)
            {
                System.out.println("图像1和图像3不属于同一帧");
            }
            else
            {
                System.out.println("图像1和图像3属于同一帧");
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("读取图片文件出错");
            return;
        }

    }


}
