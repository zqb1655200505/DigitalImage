package work6;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

/**
 * Created by zqb on 2016/11/23.
 */
public class Refining {
    /*
    一：算法介绍
        Zhang-Suen细化算法通常是一个迭代算法，整个迭代过程分为两步：
        Step One：循环所有前景像素点，对符合如下条件的像素点标记为删除：
        1.      2 <= N(p1) <=6
        2.      S(P1) = 1
        3.      P2 * P4 * P6 = 0
        4.      P4 * P6 * P8 = 0
        其中N(p1)表示跟P1相邻的8个像素点中，为前景像素点的个数
        S(P1)表示从P2 ～ P9 ～P2像素中出现0～1的累计次数，其中0表示背景，1表示前景
        完整的P1 ～P9的像素位置与举例如下：
        P9   P2   P3  |   0   0   1
        P8   P1   P4  |   1   P1  0
        P7   P6   P5  |   1   0   1

        其中 N(p1) = 4, S(P1) = 3, P2*P4*P6=0*0*0=0, P4*P6*P8=0*0*1=0, 不符合条件，无需标记为删除。
        Step Two：跟Step One很类似，条件1、2完全一致，只是条件3、4稍微不同，满足如下条件的像素P1则标记为删除，条件如下：
        1.      2 <= N(p1) <=6
        2.      S(P1) = 1
        3.      P2 * P4 * P8 = 0
        4.      P2 * P6 * P8 = 0
        循环上述两步骤，直到两步中都没有像素被标记为删除为止，输出的结果即为二值图像细化后的骨架。
     */
    int images[][];
    int flag[][];
    protected void refining(BufferedImage img,String save_path)
    {
        img=Binaryzation.binaryzation(img);
        int width=img.getWidth();
        int height=img.getHeight();
        images=new int[width][height];
        flag=new int[width][height];
        for(int i=0;i<width;i++)
        {
            Arrays.fill(flag[i], 0);
        }
        for(int i=0;i<width;i++)
        {
            for(int j=0;j<height;j++)
            {
                int rgb=img.getRGB(i,j);
                int r=(rgb>>16)&0xff;//已知是二值图片，故可以简化为只检验一个通道
                if(r==255)
                {
                    images[i][j]=1;
                }
                else
                {
                    images[i][j]=0;
                }
            }
        }
        boolean stop = false;
        while(!stop)
        {

            boolean res1=step1_scan();
            img=delete_with_flag(img);

            for(int i=0;i<width;i++)
            {
                for(int j=0;j<height;j++)
                {
                    int rgb=img.getRGB(i,j);
                    int r=(rgb>>16)&0xff;//已知是二值图片，故可以简化为只检验一个通道
                    if(r==255)
                    {
                        images[i][j]=1;
                    }
                    else
                    {
                        images[i][j]=0;
                    }
                }
            }
            boolean res2=step2_scan();
            img=delete_with_flag(img);

            if(res1&&res2)
            {
                stop=true;
            }

        }

        File result=new File(save_path);
        try {
            ImageIO.write(img,"png",result);//此处必须为png否则放大查看时会出现连通区域被模糊而不同
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean step1_scan()
    {
        boolean stop = true;
        int width=images.length;
        int height=images[0].length;
        int p1=0, p2=0, p3=0;
        int p4=0, p5=0, p6=0;
        int p7=0, p8=0, p9=0;

        //0表示前景，1表示背景
        for(int i=1;i<width-1;i++)
        {
            for(int j=1;j<height-1;j++)
            {
                //如果当前点为背景，则跳过
                if(images[i][j]==1)
                {
                    continue;
                }
                p2=images[i-1][j];
                p3=images[i-1][j+1];
                p4=images[i][j+1];
                p5=images[i+1][j+1];
                p6=images[i+1][j];
                p7=images[i+1][j-1];
                p8=images[i][j-1];
                p9=images[i-1][j-1];
                int np=8-(p2+p3+p4+p5+p6+p7+p8+p9);

                String sequence=""+p2+p3+p4+p5+p6+p7+p8+p9+p2;
                int index1 = sequence.indexOf("10");
                int index2 = sequence.lastIndexOf("10");
                if(np>=2&&np<=6&&(index1==index2)&&p2+p4+p6>0&&p4+p6+p8>0)
                {
                    flag[i][j]=1;
                    stop=false;
                }
            }
        }
        return stop;
    }


    private boolean step2_scan()
    {
        boolean stop = true;
        int width=images.length;
        int height=images[0].length;
        int p2=0, p3=0;
        int p4=0, p5=0, p6=0;
        int p7=0, p8=0, p9=0;

        //0表示前景，1表示背景
        for(int i=1;i<width-1;i++)
        {
            for(int j=1;j<height-1;j++)
            {
                //如果当前点为背景，则跳过
                if(images[i][j]==1)
                {
                    continue;
                }
                p2=images[i-1][j];
                p3=images[i-1][j+1];
                p4=images[i][j+1];
                p5=images[i+1][j+1];
                p6=images[i+1][j];
                p7=images[i+1][j-1];
                p8=images[i][j-1];
                p9=images[i-1][j-1];
                int np=8-(p2+p3+p4+p5+p6+p7+p8+p9);

                String sequence=""+p2+p3+p4+p5+p6+p7+p8+p9+p2;
                int index1 = sequence.indexOf("10");
                int index2 = sequence.lastIndexOf("10");
                if(np>=2&&np<=6&&index1==index2&&p2+p4+p6>0&&p4+p6+p8>0)
                {
                    flag[i][j]=1;
                    stop=false;
                }

            }
        }
        return stop;
    }

    private BufferedImage delete_with_flag(BufferedImage img)
    {
        for(int i=0;i<images.length;i++)
        {
            for(int j=0;j<images[0].length;j++)
            {
                if(flag[i][j]==1)
                {
                    img.setRGB(i,j,0xffffffff);
                }
            }
        }
        return img;
    }
}
