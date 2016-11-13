package work2;

import main.ImageToGrey;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by zqb on 2016/10/15.
 */
public class HistogramSubtract {
    protected void histogram_subtract(String path1,String path2,String path3)
    {
        try {
            BufferedImage image1 = ImageIO.read(new File(path1));
            BufferedImage image2 = ImageIO.read(new File(path2));
            BufferedImage image3 = ImageIO.read(new File(path3));
            int width = image1.getWidth();
            int height = image1.getHeight();
            int[] R1=new int[256];
            int[] R2=new int[256];
            int[] R3=new int[256];
            int[] G1=new int[256];
            int[] G2=new int[256];
            int[] G3=new int[256];
            int[] B1=new int[256];
            int[] B2=new int[256];
            int[] B3=new int[256];
            for(int i=0;i<width;i++)
            {
                for (int j = 0; j < height; j++)
                {
                    int rgb1 = image1.getRGB(i, j);
                    int r1 = (rgb1 >> 16) & 0xff;
                    int g1 = (rgb1 >> 8) & 0xff;
                    int b1 = rgb1 & 0xff;
                    R1[r1]++;
                    G1[g1]++;
                    B1[b1]++;

                    int rgb2 = image2.getRGB(i, j);
                    int r2 = (rgb2 >> 16) & 0xff;
                    int g2 = (rgb2 >> 8) & 0xff;
                    int b2 = rgb2 & 0xff;
                    R2[r2]++;
                    G2[g2]++;
                    B2[b2]++;

                    int rgb3 = image3.getRGB(i, j);
                    int r3 = (rgb3 >> 16) & 0xff;
                    int g3 = (rgb3 >> 8) & 0xff;
                    int b3 = rgb3 & 0xff;
                    R3[r3]++;
                    G3[g3]++;
                    B3[b3]++;
                }
            }
            int max=0;
            int temp;
            for(int i=0;i<256;i++)
            {
                temp=Max(R1[i],G1[i],B1[i]);
                if(temp>max)
                {
                    max=temp;
                }
                temp=Max(R2[i],G2[i],B2[i]);
                if(temp>max)
                {
                    max=temp;
                }
                temp=Max(R3[i],G3[i],B3[i]);
                if(temp>max)
                {
                    max=temp;
                }
            }
            int red= ImageToGrey.colorToRGB(1,255,0,0);
            int green=ImageToGrey.colorToRGB(1,0,255,0);
            int blue=ImageToGrey.colorToRGB(1,0,0,255);
            float rate=280.0f/max;
            BufferedImage newImg=new BufferedImage(256,280,image1.getType());
            for(int i=0;i<256;i++)
            {
                for(int j=0;j<280;j++)
                {
                    newImg.setRGB(i,j,-1);
                }
            }
            for(int i=0;i<256;i++)
            {
                for(int j=0,k=279;j<R1[i]*rate;j++,k--)
                {
                    newImg.setRGB(i,k,red);
                }
            }
            for(int i=0;i<256;i++)
            {
                for(int j=0,k=279;j<G1[i]*rate;j++,k--)
                {
                    newImg.setRGB(i,k,green);
                }
            }
            for(int i=0;i<256;i++)
            {
                for(int j=0,k=279;j<B1[i]*rate;j++,k--)
                {
                    newImg.setRGB(i,k,blue);
                }
            }
            File result=new File(System.getProperty("user.dir")+"/res/histogram.png");
            ImageIO.write(newImg,"png",result);

            BufferedImage newImg2=new BufferedImage(256,280,image1.getType());
            for(int i=0;i<256;i++)
            {
                for(int j=0;j<280;j++)
                {
                    newImg2.setRGB(i,j,-1);
                }
            }
            for(int i=0;i<256;i++)
            {
                for(int j=0,k=279;j<R2[i]*rate;j++,k--)
                {
                    newImg2.setRGB(i,k,red);
                }
            }
            for(int i=0;i<256;i++)
            {
                for(int j=0,k=279;j<G2[i]*rate;j++,k--)
                {
                    newImg2.setRGB(i,k,green);
                }
            }
            for(int i=0;i<256;i++)
            {
                for(int j=0,k=279;j<B2[i]*rate;j++,k--)
                {
                    newImg2.setRGB(i,k,blue);
                }
            }
            File result2=new File(System.getProperty("user.dir")+"/res/histogram2.png");
            ImageIO.write(newImg2,"png",result2);

            BufferedImage newImg3=new BufferedImage(256,280,image1.getType());
            for(int i=0;i<256;i++)
            {
                for(int j=0;j<280;j++)
                {
                    newImg3.setRGB(i,j,-1);
                }
            }
            for(int i=0;i<256;i++)
            {
                for(int j=0,k=279;j<R3[i]*rate;j++,k--)
                {
                    newImg3.setRGB(i,k,red);
                }
            }
            for(int i=0;i<256;i++)
            {
                for(int j=0,k=279;j<G3[i]*rate;j++,k--)
                {
                    newImg3.setRGB(i,k,green);
                }
            }
            for(int i=0;i<256;i++)
            {
                for(int j=0,k=279;j<B3[i]*rate;j++,k--)
                {
                    newImg3.setRGB(i,k,blue);
                }
            }
            File result3=new File(System.getProperty("user.dir")+"/res/histogram3.png");
            ImageIO.write(newImg3,"png",result3);


            BufferedImage histogram1=ImageIO.read(result);
            BufferedImage histogram2=ImageIO.read(result2);
            BufferedImage histogram3=ImageIO.read(result3);
            int width1=histogram1.getWidth();
            int height1=histogram1.getHeight();
            int flag=0;
            for(int i=0;i<width1;i++)
            {
                for(int j=0;j<height1;j++)
                {
                    if(histogram1.getRGB(i,j)!=histogram2.getRGB(i,j))
                    {
                        flag=1;
                        System.out.println("图像1和图像2不是同一帧");
                        System.out.println("i="+i+" j="+j);
                        break;
                    }
                }
                if(flag==1)
                {
                    break;
                }
            }
            if(flag==0)
            {
                System.out.println("图像1和图像2是同一帧");
            }
            flag=0;
            for(int i=0;i<width1;i++)
            {
                for(int j=0;j<height1;j++)
                {
                    if(histogram1.getRGB(i,j)!=histogram3.getRGB(i,j))
                    {
                        flag=1;
                        System.out.println("图像1和图像3不是同一帧");
                        System.out.println("i="+i+" j="+j);
                        break;
                    }
                }
                if(flag==1)
                {
                    break;
                }
            }
            if(flag==0)
            {
                System.out.println("图像1和图像3是同一帧");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private int Max(int a,int b,int c)
    {
        if(a>=b&&a>=c)
        {
            return a;
        }
        if(b>=a&&b>=c)
        {
            return b;
        }
        return c;
    }
}
