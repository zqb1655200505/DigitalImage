package work6;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

/**
 * Created by zqb on 2016/11/23.
 */
public class ConvexHull {
    int flag[][];
    int images[][];
    protected void convex_hull(BufferedImage img,String save_path)
    {
        //img=Binaryzation.binaryzation(img);
        BufferedImage temp=img;
        int width=img.getWidth();
        int height=img.getHeight();

        BufferedImage newImg=new BufferedImage(width,height,img.getType());

        //最终形成的4个分量
        int image1[][]=new int[width][height];
        int image2[][]=new int[width][height];
        int image3[][]=new int[width][height];
        int image4[][]=new int[width][height];

        flag=new int[width][height];
        images=new int[width][height];
        boolean stop = false;
        for(int k=0;k<4;k++)
        {
            for(int i=0;i<width;i++)
            {
                Arrays.fill(flag[i], 0);
            }
            img=temp;
            for(int i=0;i<width;i++)
            {
                for(int j=0;j<height;j++)
                {
                    int rgb=img.getRGB(i,j);
                    int r=(rgb>>16)&0xff;//已知是二值图片，故可以简化为只检验一个通道
                    if(r==255)
                    {
                        System.out.println("i="+i+" j="+j);
                        images[i][j]=1;
                    }
                    else
                    {

                        images[i][j]=0;
                    }
                }
            }
            stop = false;
            while(!stop)
            {
                stop=step(k);
                img=add_with_flag(img);
                //初始化images
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
            }

            for(int i=0;i<width;i++)
            {
                for(int j=0;j<height;j++)
                {
                    if(k==0)
                    {
                        image1[i][j]=images[i][j];
                    }
                    else if(k==1)
                    {
                        image2[i][j]=images[i][j];
                    }
                    else if(k==2)
                    {
                        image3[i][j]=images[i][j];
                    }
                    else
                    {
                        image4[i][j]=images[i][j];
                    }
                }
            }

        }

        for(int i=0;i<width;i++)
        {
            for(int j=0;j<height;j++)
            {
                if(image1[i][j]+image2[i][j]+image3[i][j]+image4[i][j]==0)
                {
                    newImg.setRGB(i,j,0xff000000);
                }
                else
                {
                    newImg.setRGB(i,j,0xffffffff);
                }
            }
        }


        File result=new File(save_path);
        try {
            ImageIO.write(newImg,"png",result);//此处必须为png否则放大查看时会出现连通区域被模糊而不同
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //左
    private boolean step(int k)
    {
        boolean stop=true;
        int cnt=0;
        for(int i=1;i<images.length-1;i++)
        {
            for(int j=1;j<images[0].length-1;j++)
            {
                //1表示前景，0表示背景
                if(images[i][j]==1)//该点本就是前景,不需考虑
                {
                    continue;
                }
                //左
                if(k==0)
                {
                    if(images[i-1][j-1]==1&&images[i-1][j]==1&&images[i-1][j+1]==1)
                    {
                        cnt++;
                        flag[i][j]=1;
                        stop=false;
                    }
                }
                //上
                else if(k==1)
                {
                    if(images[i-1][j-1]==1&&images[i][j-1]==1&&images[i+1][j-1]==1)
                    {
                        flag[i][j]=1;
                        stop=false;
                    }
                }
                //右
                else if(k==2)
                {
                    if(images[i+1][j-1]==1&&images[i+1][j]==1&&images[i+1][j+1]==1)
                    {
                        flag[i][j]=1;
                        stop=false;
                    }
                }
                //下
                else
                {
                    if(images[i-1][j+1]==1&&images[i][j+1]==1&&images[i+1][j+1]==1)
                    {
                        flag[i][j]=1;
                        stop=false;
                    }
                }
            }
        }
        System.out.println(cnt);
        return stop;
    }


    private BufferedImage add_with_flag(BufferedImage img)
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
