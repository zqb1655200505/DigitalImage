package main;

import java.io.IOException;
import java.util.Date;

/**
 * Created by zqb on 2016/10/1.
 */
public class MainTest {

    public static void main(String[] args) throws IOException
    {
       String path=System.getProperty("user.dir")+"/res/result_logo_grey.jpg";
       String des_path=System.getProperty("user.dir")+"/res/logo_grey.jpg";
//        String des_path_self=System.getProperty("user.dir")+"/res/result_trans.jpg";
        //转为灰色
        ImageToGrey img=new ImageToGrey();
        img.RGBToGray(path,des_path);

        //放大和缩小
        //BiggerAndSmaller biggerAndSmaller_img=new BiggerAndSmaller();
        //double time=1.8;
        //调用库函数
        //biggerAndSmaller_img.BiggerOrSmaller(path,des_path,time);
        //自己实现
        //biggerAndSmaller_img.BiggerOrSmaller_self(path,des_path_self,time);
        //biggerAndSmaller_img.BiggerOrSmaller_line(path,des_path_self,time);

        //平移
        //Transation transation=new Transation();
        //transation.transations(path,des_path,-80,-80);

        //RGBToColor rgbToColor=new RGBToColor();
        //rgbToColor.RGBToColor(path,des_path,5);




//        String path=System.getProperty("user.dir")+"/res/res.mp4";
//        String des_path=System.getProperty("user.dir")+"/res/result25_999.jpg";
//        VideoGrabImage videoGrabImage=new VideoGrabImage();
//        videoGrabImage.video_grap_image(path,des_path);
        //videoGrabImage.makeScreenCut(des_path,(640*480)+"",10,path);
    }
}
