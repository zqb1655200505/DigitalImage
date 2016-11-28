package work6;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

/**
 * Created by zqb on 2016/11/24.
 */
public class Roughening {
    int images[][];
    int flag[][];
    protected void roughening(BufferedImage img,String save_path)
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

        boolean stop = false;
        while(!stop)
        {
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
            boolean res1=step1_scan();
            img=delete_with_flag(img);
            for(int i=0;i<width;i++)
            {
                Arrays.fill(flag[i], 0);
            }
            boolean res2=step2_scan();
            img=delete_with_flag(img);
            for(int i=0;i<width;i++)
            {
                Arrays.fill(flag[i], 0);
            }
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

        //1表示前景，0表示背景
        for(int i=1;i<width-1;i++)
        {
            for(int j=1;j<height-1;j++)
            {
                //如果当前点为背景，则跳过
                if(images[i][j]==0)
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
                int np=p2+p3+p4+p5+p6+p7+p8+p9;

                String sequence=""+p2+p3+p4+p5+p6+p7+p8+p9+p2;
                int index1 = sequence.indexOf("01");
                int index2 = sequence.lastIndexOf("01");
                if(np>=2&&np<=6&&index1==index2&&p2*p4*p6==0&&p4*p6*p8==0)
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
                if(images[i][j]==0)
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
                int np=p2+p3+p4+p5+p6+p7+p8+p9;

                String sequence=""+p2+p3+p4+p5+p6+p7+p8+p9+p2;
                int index1 = sequence.indexOf("01");
                int index2 = sequence.lastIndexOf("01");
                if((np>=2&&np<=6)&&index1==index2&&p2*p4*p6==0&&p4*p6*p8==0)
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
