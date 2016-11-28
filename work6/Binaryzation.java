package work6;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by zqb on 2016/11/23.
 */
public class Binaryzation
{
    /*方法一：
    该方法非常简单，对RGB彩色图像灰度化以后，扫描图像的每个像素值，值小于127的将像素值设为0(黑色)，
    值大于等于127的像素值设为255(白色)。该方法的好处是计算
    量少速度快。缺点更多首先阈值为127没有任何理由可以解释，其次完全不考虑图像的
    像素分布情况与像素值特征。可以说该方法是史最弱智的二值处理方法一点也不为过。

    方法二：
    最常见的二值处理方法是计算像素的平均值K，扫描图像的每个像素值如像素值大于K
    像素值设为255(白色)，值小于等于K像素值设为0(黑色)。该方法相比方法一，阈值的
    选取稍微有点智商，可以解释。但是使用平均值作为二值化阈值同样有个致命的缺点，
    可能导致部分对象像素或者背景像素丢失。二值化结果不能真实反映源图像信息。

    方法三：
    使用直方图方法来寻找二值化阈值，直方图是图像的重要特质，直方图方法选择二值
    化阈值主要是发现图像的两个最高的峰，然后在阈值取值在两个峰之间的峰谷最低处。
    该方法相对前面两种方法而言稍微精准一点点。结果也更让人可以接受。

    方法四：http://en.wikipedia.org/wiki/Thresholding_(image_processing)
    使用近似一维Means方法寻找二值化阈值，该方法的大致步骤如下：
    1.一个初始化阈值T，可以自己设置或者根据随机方法生成。
    2.根据阈值图每个像素数据P(n,m)分为对象像素数据G1与背景像素数据G2。(n为
      行，m为列)
    3.G1的平均值是m1, G2的平均值是m2
    4.一个新的阈值T’ = (m1 + m2)/2
    5.回到第二步，用新的阈值继续分像素数据为对象与北京像素数据，继续2～4步，
      直到计算出来的新阈值等于上一次阈值

    */
    public static BufferedImage binaryzation(BufferedImage img)
    {
        int width=img.getWidth();
        int height=img.getHeight();

        BufferedImage newImg=new BufferedImage(width,height,img.getType());
        //法一
        for(int i=0;i<width;i++)
        {
            for(int j=0;j<height;j++)
            {
                int rgb=img.getRGB(i,j);
                int r=(rgb>>16)&0xff;
                int g=(rgb>>8)&0xff;
                int b=rgb&0xff;
                int gray=(int)(r*0.3+g*0.59+b*0.11);
                if(gray<=127)
                {
                    newImg.setRGB(i,j,0xff000000);
                    //r=g=b=0;
                }
                else
                {
                    newImg.setRGB(i,j,0xffffffff);
                    //r=g=b=255;
                }
                //newImg.setRGB(i,j,  (r << 16) | (g << 8) | b);
            }
        }



        return newImg;
    }
}
