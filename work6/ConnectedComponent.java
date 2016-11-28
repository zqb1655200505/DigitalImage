package work6;

import sun.awt.image.SurfaceManager;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

/**
 * Created by zqb on 2016/11/23.
 */
public class ConnectedComponent {
    int images[][];
    int counter=1;//记录连通数量和区分各个分量,为了区分二值化的1，必须从大于等于1开始

    protected void connected_component(BufferedImage img,String save_path)
    {
        int width=img.getWidth();
        int height=img.getHeight();
        images=new int[width][height];
        for(int i=0;i<width;i++)
        {
            for(int j=0;j<height;j++)
            {
                int rgb=img.getRGB(i,j);
                int r=(rgb>>16)&0xff;
                int g=(rgb>>8)&0xff;
                int b=rgb&0xff;
                if(r==255&&g==255&&b==255)
                {
                    images[i][j]=1;
                }
                else
                {
                    images[i][j]=0;
                }
            }
        }
        for(int i=0;i<width;i++)
        {
            for (int j = 0; j < height; j++)
            {
                if(images[i][j]==1)
                {
                    counter++;
                    dealBwlabe(i,j);
                }
            }
        }
        //System.out.println(counter);
        BufferedImage newImg=paint_color();
        File result=new File(save_path);
        try {
            ImageIO.write(newImg,"png",result);//此处必须为png否则放大查看时会出现连通区域被模糊而不同
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //搜索连通区域
    private void dealBwlabe(int i, int j)
    {
        //上
        if(i-1>=0&&j<images[0].length)
        {
            if (images[i-1][j] == 1) {
                images[i-1][j] = counter;
                dealBwlabe(i-1, j);
            }
        }

        //左
        if(j-1>=0&&i<images.length)
        {
            if (images[i][j-1] == 1)
            {
                images[i][j-1] = counter;
                dealBwlabe(i, j-1);
            }
        }
        //下
        if(i+1<images.length&&j<images[0].length)
        {
            if (images[i+1][j] == 1) {
                images[i+1][j] = counter;
                dealBwlabe(i+1, j);
            }
        }

        //右
        if(j+1<images[0].length&&i<images.length)
        {
            if (images[i][j+1] == 1) {
                images[i][j+1] = counter;
                dealBwlabe(i, j+1);
            }
        }

        //上左
        if(i-1>=0&&j-1>=0)
        {
            if (images[i-1][j-1] == 1) {
                images[i-1][j-1] = counter;
                dealBwlabe(i-1, j-1);
            }
        }

        //上右
        if(i-1>=0&&j+1<images[0].length)
        {
            if (images[i-1][j+1] == 1) {
                images[i-1][j+1] = counter;
                dealBwlabe(i-1, j+1);
            }
        }

        //下左
        if(i+1<images.length&&j-1>=0)
        {
            if (images[i+1][j-1] == 1) {
                images[i+1][j-1] = counter;
                dealBwlabe(i+1, j-1);
            }
        }

        //下右
        if(i+1<images.length&&j+1<images[0].length)
        {
            if (images[i+1][j+1] == 1) {
                images[i+1][j+1] = counter;
                dealBwlabe(i+1, j+1);
            }
        }

    }


    private BufferedImage paint_color()
    {
        int colors[]=new int[counter+1];
        //随机生成颜色
        Random random=new Random();
        for(int i=0;i<counter+1;i++)
        {
            //colors[i]=(int) (0xff000000+Math.random()*0xffffff);
            int red=random.nextInt(255);
            int green=random.nextInt(255);
            int blue=random.nextInt(255);
            colors[i]=new Color(red,green,blue).getRGB();
        }
        BufferedImage image=new BufferedImage(images.length,images[0].length, 5);
        for(int i=0;i<image.getWidth();i++)
        {
            for(int j=0;j<image.getHeight();j++)
            {
                if(images[i][j]>1)
                {
                    image.setRGB(i,j,colors[images[i][j]]);
                }
                else
                {
                    image.setRGB(i,j,0xff000000);
                }
            }
        }
        return image;
    }
}
