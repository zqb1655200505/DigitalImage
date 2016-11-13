package work5;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

/**
 * Created by zqb on 2016/11/8.
 */
public class SaltAndPepper {

    protected void SaltAndPepper(BufferedImage img, String save_path)
    {
        int width=img.getWidth();
        int height=img.getHeight();
        Random random=new Random();
        double ran=random.nextDouble();
        int size= (int)(width*height * (1-ran));
        int x,y,temp,rgb;

        for(int i=0;i<size;i++)
        {
            //随机点添加
            x=random.nextInt(width);
            y=random.nextInt(height);
            temp=random.nextInt(100);
            if(temp%2==0)//加盐性噪声
            {
                rgb= (255 << 24) | (255 << 16) | (255 << 8) | 255;
            }
            else//加椒性噪声
            {
                rgb= (0 << 24) | (0 << 16) | (0 << 8) | 0;
            }
            img.setRGB(x,y,rgb);
        }

        File result=new File(save_path);
        try {
            ImageIO.write(img,"jpg",result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
