package work1;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by zqb on 2016/10/5.
 */
public class HorizentalOffset {
    private int  width=0;
    private int height=0;
    private BufferedImage bufferedImage=null;
    public HorizentalOffset(File img_file)
    {
        try {
            bufferedImage= ImageIO.read(img_file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        width=bufferedImage.getWidth();
        height=bufferedImage.getHeight();
    }

    protected void horizental_offset(String des_path,int degree) throws IOException
    {
        double radius=Math.PI*degree/180;
        int newheight=height+(int)Math.abs(width*Math.tan(radius));
        BufferedImage newImage=new BufferedImage(width,newheight,bufferedImage.getType());
        int offset=0;
        if(degree<0)
        {
            offset=(int)(width*Math.tan(radius));
        }
        for(int i=0;i<width;i++)
        {
            for(int j=0;j<newheight;j++)
            {
                int x;
                x=j-(int)(i*Math.tan(radius))+offset;
                if(x>=0&&x<width)
                {
                    newImage.setRGB(i, j, bufferedImage.getRGB(i, x));
                }
            }
        }
        File res=new File(des_path);
        ImageIO.write(newImage,"jpg",res);
    }
}
