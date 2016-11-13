package work1;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by zqb on 2016/10/5.
 */
public class Rotation {

    private int  width=0;
    private int height=0;
    private BufferedImage bufferedImage=null;
    public Rotation(File img_file)
    {
        try {
            bufferedImage= ImageIO.read(img_file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        width=bufferedImage.getWidth();
        height=bufferedImage.getHeight();
    }

    protected void rotation(double degree,String des_path) throws IOException {
        int size=(int)Math.floor(Math.sqrt(width*width+height*height));
        double radius=Math.PI*degree/180;
        BufferedImage newImage=new BufferedImage(size,size,bufferedImage.getType());
        int center_x=size/2;
        for(int i=0;i<size;i++)
        {
            for(int j=0;j<size;j++)
            {
                int x=(int)((i-center_x)*Math.cos(radius)+(j)*Math.sin(radius));
                int y=(int)((j)*Math.cos(radius)-(i-center_x)*Math.sin(radius));
                //int x=(int)(i*Math.cos(radius)+j*Math.sin(radius));
                //int y=(int)(j*Math.cos(radius)-i*Math.sin(radius));
                if(x>=0&&y>=0&&x<width&&y<height)
                {
                    newImage.setRGB(i,j,bufferedImage.getRGB(x,y));
                }
            }
        }
        File result=new File(des_path);
        ImageIO.write(newImage,"jpg",result);
    }

}
