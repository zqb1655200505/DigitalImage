package main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by zqb on 2016/10/7.
 */
public class RGBToColor {
    private int  width=0;
    private int height=0;
    private BufferedImage bufferedImage=null;
    private boolean check(String path)  throws IOException
    {
        File img_file=new File(path);
        if(!img_file.exists())
        {
            System.out.print("找不到文件");
            return false;
        }
        bufferedImage= ImageIO.read(img_file);
        width=bufferedImage.getWidth();
        height=bufferedImage.getHeight();
        return true;
    }

    //双线性插值
    protected void RGBToColor(String path,String des_path,double time) throws IOException
    {
        if(check(path))
        {
            BufferedImage newImage=new BufferedImage((int)(width*time),(int)(height*time),bufferedImage.getType());
            for(int i=0;i<(int)(width*time);i++)
            {
                for(int j=0;j<(int)(height*time);j++)
                {
                    double x=i*1.0/time;
                    double y=j*1.0/time;
                    double dx=x-Math.floor(x);
                    double dy=y-Math.floor(y);

                    int rgb1,rgb2,rgb3,rgb4;
                    rgb1=rgb2=rgb3=rgb4=bufferedImage.getRGB((int)x,(int)y);
                    int red1 = (rgb1>>16)&0xff;
                    int green1=(rgb1>>8) &0xff;
                    int blue1= (rgb1)    &0xff;

                    if((int)x>0&&(int)x<width&&(int)y+1>0&&(int)y+1<height)
                    {
                        rgb2=bufferedImage.getRGB((int)x,(int)y+1);
                    }

                    int red2 = (rgb2>>16)&0xff;
                    int green2=(rgb2>>8) &0xff;
                    int blue2= (rgb2)    &0xff;
                    if((int)x+1>0&&(int)x+1<width&&(int)y>0&&(int)y<height)
                    {
                        rgb3=bufferedImage.getRGB((int)x+1,(int)y);
                    }
                    int red3 = (rgb3>>16)&0xff;
                    int green3=(rgb3>>8) &0xff;
                    int blue3= (rgb3)    &0xff;
                    if((int)x+1>0&&(int)x+1<width&&(int)y+1>0&&(int)y+1<height)
                    {
                        rgb4=bufferedImage.getRGB((int)x+1,(int)y+1);
                    }
                    int red4 = (rgb4>>16)&0xff;
                    int green4=(rgb4>>8) &0xff;
                    int blue4= (rgb4)    &0xff;

                    int red=(int)((1-dx)*(1-dy)*red1+(1-dx)*dy*red2+dx*(1-dy)*red3+dx*dy*red4);
                    int green=(int)((1-dx)*(1-dy)*green1+(1-dx)*dy*green2+dx*(1-dy)*green3+dx*dy*green4);
                    int blue=(int)((1-dx)*(1-dy)*blue1+(1-dx)*dy*blue2+dx*(1-dy)*blue3+dx*dy*blue4);

                    int rgb=new Color(red,green,blue).getRGB();
                    newImage.setRGB(i,j,rgb);
                }
            }
            File result=new File(des_path);
            ImageIO.write(newImage,"jpg",result);
        }
    }
}
