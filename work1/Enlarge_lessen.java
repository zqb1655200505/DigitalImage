package work1;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by zqb on 2016/10/5.
 */
public class Enlarge_lessen {
    private int  width=0;
    private int height=0;
    private BufferedImage bufferedImage=null;

    public Enlarge_lessen(File img_file)
    {
        try {
            bufferedImage= ImageIO.read(img_file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        width=bufferedImage.getWidth();
        height=bufferedImage.getHeight();
    }

    //调用库函数插值缩放
    protected void enlarge_lessen_insert(String des_path,double time) throws IOException
    {
        Image image = bufferedImage.getScaledInstance((int)(width * time), (int)(height * time), Image.SCALE_DEFAULT);
        BufferedImage biggerImg=new BufferedImage((int)(width*time),(int)(height*time),bufferedImage.getType());
        Graphics2D graphics = biggerImg.createGraphics();
        graphics.drawImage(image,0,0,null);
        graphics.dispose();
        File res=new File(des_path);
        ImageIO.write(biggerImg,"jpg",res);
    }

    //实现插值缩放--最邻近插值，将放大后某一像素周围的的像素以该像素的值填充
    protected void enlarge_lessen_self_insert( String des_path,double time)  throws IOException
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


    //不插值缩放
    protected void enlarge_lessen_no_insert(String des_path,double time) throws IOException
    {
         BufferedImage newImage=new BufferedImage((int)(width*time),(int)(height*time),bufferedImage.getType());
        for(int i=0;i<width;i++)
        {
            for(int j=0;j<height;j++)
            {
                newImage.setRGB((int)(time*i),(int)(time*j),bufferedImage.getRGB(i,j));
            }
        }
        File result=new File(des_path);
        ImageIO.write(newImage,"jpg",result);

    }
    //插值缩放--反向映射(最邻近插值)
    protected void enlarge_lessen_insert_mapping(String des_path,double time) throws IOException
    {
        BufferedImage newImage=new BufferedImage((int)(width*time),(int)(height*time),bufferedImage.getType());
        for(int i=0;i<(int)(width*time);i++)
        {
            for(int j=0;j<(int)(height*time);j++)
            {
                if(((int)Math.round(i/time)>=0)&&((int)Math.round(i/time)<width)&&(((int)Math.round(j/time))>=0)&&(((int)Math.round(j/time))<height))
                    newImage.setRGB(i,j,bufferedImage.getRGB((int)Math.round(i/time),(int)Math.round(j/time)));
            }
        }
        File result=new File(des_path);
        ImageIO.write(newImage,"jpg",result);

    }

    //双线性插值
    protected void RGBToColor(String des_path,double time) throws IOException
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
