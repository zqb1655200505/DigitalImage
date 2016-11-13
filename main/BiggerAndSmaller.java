package main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by zqb on 2016/10/1.
 */
public class BiggerAndSmaller {
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
        bufferedImage=ImageIO.read(img_file);
        width=bufferedImage.getWidth();
        height=bufferedImage.getHeight();
        return true;
    }
    //调用库函数实现
    protected void BiggerOrSmaller(String path, String des_path,double time)  throws IOException
    {
        if(check(path))
        {
            Image image = bufferedImage.getScaledInstance((int)(width * time), (int)(height * time), Image.SCALE_DEFAULT);
            BufferedImage biggerImg=new BufferedImage((int)(width*time),(int)(height*time),bufferedImage.getType());
            Graphics2D graphics = biggerImg.createGraphics();
            graphics.drawImage(image,0,0,null);
            graphics.dispose();
            File res=new File(des_path);
            ImageIO.write(biggerImg,"jpg",res);
        }
    }

    //自己写算法实现
    protected void BiggerOrSmaller_self(String path, String des_path,double time)  throws IOException
    {
        if(check(path))
        {
            BufferedImage newImage=new BufferedImage((int)(width*time),(int)(height*time),bufferedImage.getType());

            for(int i=0;i<width;i++)
            {
                for(int j=0;j<height;j++)
                {
                    int rgb=bufferedImage.getRGB(i,j);
                    for(int k=(int)Math.floor(i*time);k<(int)Math.floor((i+1)*time);k++)
                    {
                        for(int h=(int)Math.floor(j*time);h<(int)Math.floor((j+1)*time);h++)
                        {
                            newImage.setRGB(k,h,rgb);
                        }
                    }
                }
            }
            File result=new File(des_path);
            ImageIO.write(newImage,"jpg",result);
        }
    }
    //双线性插值
    protected void BiggerOrSmaller_line(String path, String des_path,double time)  throws IOException
    {
        if(check(path))
        {
            int[][] array_rgb=new int[(int)(width*time)][(int)(height*time)];
            BufferedImage newImage=new BufferedImage((int)(width*time),(int)(height*time),bufferedImage.getType());
            //array_rgb=bufferedImage.getRGB(0,0,width,height,array_rgb,0,width);
            for(int i=0;i<width;i++)
            {
                for(int j=0;j<height;j++)
                {
                    int rgb=bufferedImage.getRGB(i,j);
                    int r=(rgb>>16)&0xff;
                    int g=(rgb>>8)&0xff;
                    int b=rgb&0xff;
                    int ARGB =((r << 16) & 0x00FF0000)
                            | ((g << 8) & 0x0000FF00)
                            | (b & 0x000000FF);

                    for(int k=(int)Math.floor(i*time);k<(int)Math.floor((i+1)*time);k++)
                    {
                        for(int h=(int)Math.floor(j*time);h<(int)Math.floor((j+1)*time);h++)
                        {
                            newImage.setRGB(k,h,ARGB);
                            array_rgb[k][h]=ARGB;
                        }
                    }
                }
            }

            for(int i=0;i<(int)(width*time);i++)
            {
                for(int j=0;j<(int)(height*time);j++)
                {
                    newImage.setRGB(i,j,array_rgb[i][j]);
                }
            }


            File result=new File(des_path);
            ImageIO.write(newImage,"jpg",result);
        }
    }
}
