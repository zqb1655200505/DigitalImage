package work6;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by zqb on 2016/11/24.
 */
public class SkeletonExtracting {
    //索引数组
    private  Integer[] array = {
            0,0,1,1,0,0,1,1,1,1,0,1,1,1,0,1,
            1,1,0,0,1,1,1,1,0,0,0,0,0,0,0,1,
            0,0,1,1,0,0,1,1,1,1,0,1,1,1,0,1,
            1,1,0,0,1,1,1,1,0,0,0,0,0,0,0,1,
            1,1,0,0,1,1,0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
            1,1,0,0,1,1,0,0,1,1,0,1,1,1,0,1,
            0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
            0,0,1,1,0,0,1,1,1,1,0,1,1,1,0,1,
            1,1,0,0,1,1,1,1,0,0,0,0,0,0,0,1,
            0,0,1,1,0,0,1,1,1,1,0,1,1,1,0,1,
            1,1,0,0,1,1,1,1,0,0,0,0,0,0,0,0,
            1,1,0,0,1,1,0,0,0,0,0,0,0,0,0,0,
            1,1,0,0,1,1,1,1,0,0,0,0,0,0,0,0,
            1,1,0,0,1,1,0,0,1,1,0,1,1,1,0,0,
            1,1,0,0,1,1,1,0,1,1,0,0,1,0,0,0};

    protected void skeletion_extracting(BufferedImage img,String save_path)
    {
        img=Xihua(img,array);
        File result=new File(save_path);
        try {
            ImageIO.write(img,"png",result);//此处必须为png否则放大查看时会出现连通区域被模糊而不同
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public  boolean isWhite(int colorInt) {
        Color color = new Color(colorInt);
        if (color.getRed() + color.getGreen() + color.getBlue() > 400) {
            return true;
        }
        return false;
    }

    public  BufferedImage VThin(BufferedImage image,Integer[] array){
        int h = image.getHeight();
        int w = image.getWidth();
        int NEXT = 1;
        for(int i=0;i<h;i++){
            for(int j=0;j<w;j++){
                if (NEXT == 0){
                    NEXT = 1;
                }else{
                    int M ;
                    if( 0<j&&j<w-1){
                        if(isBlack(image.getRGB(j-1,i))&&isBlack(image.getRGB(j,i))&&isBlack(image.getRGB(j+1,i))){
                            M=0;
                        }else{
                            M=1;
                        }
                    }else {
                        M = 1;
                    }
                    if(isBlack(image.getRGB(j,i))&&M!=0){
                        int[] a = {0,0,0,0,0,0,0,0,0};
                        for(int k=0;k<3;k++){
                            for(int l=0;l<3;l++){
                                if ((-1<(i-1+k)&&(i-1+k)<h) && (-1<(j-1+l)&&(j-1+l)<w) && isWhite(image.getRGB(j-1+l,i-1+k))){
                                    a[k*3+l] = 1;
                                }
                            }
                        }
                        int sum = a[0]*1+a[1]*2+a[2]*4+a[3]*8+a[5]*16+a[6]*32+a[7]*64+a[8]*128;
                        if(array[sum]==0){
                            image.setRGB(j, i, Color.black.getRGB());
                        }else{
                            image.setRGB(j, i, Color.white.getRGB());
                        }
                        if (array[sum] == 1){
                            NEXT = 0;
                        }
                    }
                }
            }
        }
        return image;
    }

    public  BufferedImage HThin(BufferedImage image,Integer[] array){
        int h = image.getHeight();
        int w = image.getWidth();
        int NEXT = 1;
        for(int j=0;j<w;j++){
            for(int i=0;i<h;i++){
                if (NEXT == 0){
                    NEXT = 1;
                }else{
                    int M;
                    if(0<i&&i<h-1){
                        if(isBlack(image.getRGB(j,i-1))&&isBlack(image.getRGB(j,i))&&isBlack(image.getRGB(j,i+1))){
                            M=0;
                        }else{
                            M=1;
                        }
                    }else{
                        M = 1;
                    }
                    if (isBlack(image.getRGB(j,i)) && M != 0){
                        int[] a = {0,0,0,0,0,0,0,0,0};
                        for(int k=0;k<3;k++){
                            for(int l=0;l<3;l++){
                                if ((-1<(i-1+k)&&(i-1+k)<h) && (-1<(j-1+l)&&(j-1+l)<w )&& isWhite(image.getRGB(j-1+l,i-1+k))){
                                    a[k*3+l] = 1;
                                }
                            }
                        }
                        int sum = a[0]*1+a[1]*2+a[2]*4+a[3]*8+a[5]*16+a[6]*32+a[7]*64+a[8]*128;
                        if(array[sum]==0){
                            image.setRGB(j, i, Color.black.getRGB());
                        }else{
                            image.setRGB(j, i, Color.white.getRGB());
                        }
                        if (array[sum] == 1){
                            NEXT = 0;
                        }
                    }
                }
            }
        }
        return image;
    }

    public  BufferedImage Xihua(BufferedImage image,Integer[] array){
        int num=10;
        BufferedImage iXihua = image;
        for(int i=0;i<num;i++){
            VThin(iXihua,array);
            HThin(iXihua,array);
        }
        return iXihua;
    }

    public  BufferedImage Two(BufferedImage image){
        int w = image.getWidth();
        int h = image.getHeight();
        BufferedImage iTwo = image;
        for(int i=0;i<h;i++){
            for(int j=0;j<w;j++){
                if(isBlack(image.getRGB(j,i))){
                    iTwo.setRGB(j, i, Color.BLACK.getRGB());
                }else{
                    iTwo.setRGB(j, i, Color.WHITE.getRGB());
                }
            }
        }
        return iTwo;
    }

    public  boolean isBlack(int colorInt) {
        Color color = new Color(colorInt);
        if (color.getRed() + color.getGreen() + color.getBlue() <= 400) {
            return true;
        }
        return false;
    }
}
