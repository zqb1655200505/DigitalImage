package main;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by zqb on 2016/10/1.
 */
public class ImageToGrey {

    protected void RGBToGray(String pic_path,String pic_save_path) throws IOException
    {
        File img_file=new File(pic_path);
        if(!img_file.exists())
        {
            System.out.print("找不到文件");
            return;
        }
        BufferedImage bufferedImage= ImageIO.read(img_file);
        int width=bufferedImage.getWidth();
        int height=bufferedImage.getHeight();
        //BufferedImage grayImage=new BufferedImage(width,height,BufferedImage.TYPE_BYTE_GRAY);
        BufferedImage grayImage=new BufferedImage(width,height,bufferedImage.getType());
        for(int i=0;i<width;i++)
        {
            for(int j=0;j<height;j++)
            {
                int rgb=bufferedImage.getRGB(i,j);

                //直接设置rgb值,不加权
                //grayImage.setRGB(i,j,rgb);

                //先算加权值，再设置rgb(r:0.3  g:0.59   b:0.11);
                //32位图像，无alpha，rgb各8位
                int alpha = (rgb >> 24)& 0xff; //透明度通道
                int r=(rgb>>16)&0xff;
                int g=(rgb>>8)&0xff;
                int b=rgb&0xff;

                int gray=(int)(r*0.3+g*0.59+b*0.11);
                int newPixel=colorToRGB(alpha,gray,gray,gray);
                //System.out.println(gray);

                //与colorToRGB效果相同
//                int ARGB = ((gray << 16) & 0x00FF0000)
//                        | ((gray << 8) & 0x0000FF00)
//                        | (gray & 0x000000FF);
                grayImage.setRGB(i,j,newPixel);
            }
        }
        File result=new File(pic_save_path);
        ImageIO.write(grayImage,"jpg",result);

    }

    public static int colorToRGB(int alpha, int red, int green, int blue) {

        int newPixel = 0;
        newPixel += alpha;
        newPixel = newPixel << 8;
        newPixel += red;
        newPixel = newPixel << 8;
        newPixel += green;
        newPixel = newPixel << 8;
        newPixel += blue;
        return newPixel;

    }
}
