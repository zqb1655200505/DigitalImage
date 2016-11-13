package work4;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by zqb on 2016/11/10.
 */
public class GaussianLowFilter {
    protected void GaussianLowFilter(BufferedImage img,String save_path) {
        int width = img.getWidth();
        int height = img.getHeight();
        float[][] kernal = get2DKernalData(5, 4);

        BufferedImage temp = new BufferedImage(width + kernal.length, height + kernal[0].length, img.getType());
        //扩展外圈为0
        for (int i = kernal.length / 2; i < width + kernal.length / 2; i++) {
            for (int j = kernal[0].length / 2; j < height + kernal[0].length / 2; j++) {
                temp.setRGB(i, j, img.getRGB(i - kernal.length / 2, j -  kernal[0].length / 2));
            }
        }

        int[][] R = new int[width + kernal.length][height + kernal[0].length];
        int[][] G = new int[width + kernal.length][height + kernal[0].length];
        int[][] B = new int[width + kernal.length][height + kernal[0].length];
        //计算卷积
        for (int i = kernal.length / 2; i < width + kernal.length / 2; i++) {
            for (int j = kernal[0].length / 2; j < height + kernal[0].length / 2; j++) {
                double sum_r = 0;
                double sum_g = 0;
                double sum_b = 0;
                for (int k = -kernal.length / 2; k <=kernal.length / 2; k++) {
                    for (int h = -kernal[0].length / 2; h <=kernal[0].length / 2; h++) {
                        int rgb = temp.getRGB(i + k, j + k);
                        int r = (rgb >> 16) & 0xff;
                        int g = (rgb >> 8) & 0xff;
                        int b = rgb & 0xff;
                        sum_r +=  r * kernal[k + kernal.length / 2][h + kernal[0].length / 2];
                        sum_g +=  g * kernal[k + kernal.length / 2][h + kernal[0].length / 2];
                        sum_b +=  b * kernal[k + kernal.length / 2][h + kernal[0].length / 2];
                    }
                }
                R[i][j] = 10*(int)sum_r;
                G[i][j] = 10*(int)sum_g;
                B[i][j] = 10*(int)sum_b;
                //System.out.println(B[i][j]);
            }
        }

        //归一化
        int r_max=0,g_max=0,b_max=0;
        int r_min=255,g_min=255,b_min=255;
        for(int i=0;i<width+kernal.length;i++)
        {
            for(int j=0;j<height+kernal[0].length;j++)
            {
                if(R[i][j]<r_min)
                {
                    r_min=R[i][j];
                }
                if(G[i][j]<g_min)
                {
                    g_min=G[i][j];
                }
                if(B[i][j]<b_min)
                {
                    b_min=B[i][j];
                }

                if(R[i][j]>r_max)
                {
                    r_max=R[i][j];
                }
                if(G[i][j]>g_max)
                {
                    g_max=G[i][j];
                }
                if(B[i][j]>b_max)
                {
                    b_max=B[i][j];
                }
            }
        }
        System.out.println("b_max"+b_max+" g_max="+g_max+" b_max="+b_max);
        System.out.println("b_min"+b_min+" g_min="+g_min+" b_min="+b_min);
        System.out.println("\t ---------------------------");
        double r_rate=256.0/(r_max-r_min);
        double g_rate=256.0/(g_max-g_min);
        double b_rate=256.0/(b_max-b_min);
        System.out.println("r_rate="+r_rate+" g_rate="+g_rate+" b_rate="+b_rate);
        BufferedImage newImg=new BufferedImage(width,height,img.getType());
        for (int i = kernal.length / 2; i < width + kernal.length / 2; i++)
        {
            for (int j = kernal[0].length / 2; j < height + kernal[0].length / 2; j++)
            {
                int r=Math.min((int) (R[i][j]*r_rate),255);
                int g=Math.min((int) (G[i][j]*g_rate),255);
                int b=Math.min((int) (B[i][j]*b_rate),255);
                //System.out.println("r="+r+" g="+g+" b="+b);
                int rgb=new Color(r,g,b).getRGB();
                newImg.setRGB(i-kernal.length / 2,j-kernal[0].length / 2,rgb);
            }
        }
        File result=new File(save_path);
        try {
            ImageIO.write(newImg,"jpg",result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //生成高斯操作数
    private float[][] get2DKernalData(int n, float sigma)
    {
        int size = 2*n +1;
        float sigma22 = 2*sigma*sigma;
        float sigma22PI = (float)Math.PI * sigma22;
        float[][] kernalData = new float[size][size];
        int row = 0;
        for(int i=-n; i<=n; i++) {
            int column = 0;
            for(int j=-n; j<=n; j++) {
                float xDistance = i*i;
                float yDistance = j*j;
                kernalData[row][column] = (float)Math.exp(-(xDistance + yDistance)/sigma22)/sigma22PI;
                column++;
            }
            row++;
        }

        for(int i=0; i<size; i++) {
            for(int j=0; j<size; j++) {
                System.out.print("\t" + kernalData[i][j]);
            }
            System.out.println();
            System.out.println("\t ---------------------------");
        }
        return kernalData;
    }
}
