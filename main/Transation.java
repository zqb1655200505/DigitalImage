package main;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by zqb on 2016/10/5.
 */
public class Transation {
    private int  width=0;
    private int height=0;
    private BufferedImage bufferedImage=null;
    private boolean check(String path)  throws IOException
    {
        File img_file=new File(path);
        if(!img_file.exists())
        {
            System.out.print("找不到文件");
            return false;
        }
        bufferedImage= ImageIO.read(img_file);
        width=bufferedImage.getWidth();
        height=bufferedImage.getHeight();
        return true;
    }
    protected void transations(String path,String des_path,int dx,int dy) throws IOException
    {
        if(check(path))
        {
            BufferedImage newImage=new BufferedImage(width,height,bufferedImage.getType());
            for(int i=0;i<width;i++)
            {
                for(int j=0;j<height;j++)
                {
                    int rgb=bufferedImage.getRGB(i,j);
                    if(i+dx<width&&j+dy<height&&i+dx>=0&&j+dy>=0)
                    {
                        newImage.setRGB(i+dx,j+dy,rgb);
                    }
                }
            }
            File res=new File(des_path);
            ImageIO.write(newImage,"jpg",res);
        }
    }
}
