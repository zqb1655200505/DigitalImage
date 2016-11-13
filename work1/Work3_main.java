package work1;


import java.io.File;
import java.io.IOException;

/**
 * Created by zqb on 2016/10/5.
 */
public class Work3_main {

    public static void main(String[] args) throws IOException {
        String path=System.getProperty("user.dir")+"/res/logo.jpg";
        String des_path=System.getProperty("user.dir")+"/res/result_logo_rotation_375.jpg";
        File img_file=check(path);//检查文件是否存在
        if(img_file!=null)
        {
            //平移
            //Transation transation=new Transation(img_file);
            //transation.transations(path,des_path,-80,-80);


            //缩放
            //Enlarge_lessen enlarge_lessen=new Enlarge_lessen(img_file);
            //①不插值缩放
            //enlarge_lessen.enlarge_lessen_no_insert(des_path,1.2);
            //②插值缩放
            //enlarge_lessen.enlarge_lessen_insert(des_path,1.2);
            //enlarge_lessen.enlarge_lessen_self_insert(des_path,1.2);
            //enlarge_lessen.enlarge_lessen_insert_mapping(des_path,5);
            //enlarge_lessen.RGBToColor(des_path,5);

            //旋转
            Rotation rotation=new Rotation(img_file);
            rotation.rotation(-45,des_path);

            //垂直变换
            //VerticalOffset verticalOffset=new VerticalOffset(img_file);
            //verticalOffset.vertical_offset(des_path,-30);


            //水平变换
            //HorizentalOffset horizentalOffset=new HorizentalOffset(img_file);
            //horizentalOffset.horizental_offset(des_path,-30);
        }

    }

    public static File check(String path)
    {
        File img_file=new File(path);
        if(!img_file.exists())
        {
            System.out.print("找不到文件");
            return null;
        }
        return img_file;
    }
}
