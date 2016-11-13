package work3;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by zqb on 2016/10/23.
 */
public class MainTest {
    public static void main(String[] args)
    {
        String path=System.getProperty("user.dir")+"/res/111.jpg";
        String des_path=System.getProperty("user.dir")+"/res/result_111.jpg";
        String des_smooth_path=System.getProperty("user.dir")+"/res/result_smooth.jpg";
        BufferedImage image=getBufferedImage(path);
        if(image!=null)
        {
            //roberts算子实现边界锐化
            Roberts_sharpen roberts_sharpen=new Roberts_sharpen();
            roberts_sharpen.Roberts_sharpen(image,des_path);

            //prewitt算子
            //Prewitt_sharpen prewitt_sharpen=new Prewitt_sharpen();
            //prewitt_sharpen.Prewitt_sharpen(image,des_path);

            //sobel算子锐化
            //Sobel_sharpen sobel_sharpen=new Sobel_sharpen();
            //sobel_sharpen.Prewitt_sharpen(image,des_path);

            //lapacian算子
            //Lapacian_sharpen lapacian_sharpena=new Lapacian_sharpen();
            //lapacian_sharpena.Prewitt_sharpen(image,des_path);

//            Smooth smooth=new Smooth();
//            smooth.smooth(image,des_smooth_path);
//            BufferedImage img=getBufferedImage(des_smooth_path);
//            if(img!=null)
//            {
//                Lapacian_sharpen lapacian_sharpena=new Lapacian_sharpen();
//                lapacian_sharpena.Prewitt_sharpen(img,des_path);
//            }


        }
    }

    private static BufferedImage getBufferedImage(String path)
    {
        File file=new File(path);
        if(file.exists())
        {
            try {
                BufferedImage image=ImageIO.read(file);
                return image;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("文件路径"+path+"不存在");
        return null;
    }
}
