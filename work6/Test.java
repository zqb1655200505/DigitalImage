package work6;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by zqb on 2016/11/23.
 */
public class Test {
    public static void main(String[] args)
    {
        String path=System.getProperty("user.dir")+"/res/d.png";
        String des_path=System.getProperty("user.dir")+"/res/skeletion_extracting.png";
        BufferedImage img=getBufferedImage(path);
        if(img!=null)
        {
            //new ConnectedComponent().connected_component(img,des_path);
            //new Refining().refining(img,des_path);
            //new Roughening().roughening(img,des_path);
            //new ConvexHull().convex_hull(img,des_path);
            new SkeletonExtracting().skeletion_extracting(img,des_path);
        }
    }

    private static BufferedImage getBufferedImage(String path)
    {
        File file=new File(path);
        if(file.exists())
        {
            try
            {
                BufferedImage image= ImageIO.read(file);
                return image;
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        System.out.println("文件路径"+path+"不存在");
        return null;
    }
}
