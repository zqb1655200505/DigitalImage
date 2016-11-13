package main;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zqb on 2016/10/11.
 */
public class VideoGrabImage {
    private boolean checkFileExist(String path)
    {
        File src_file=new File(path);
        if(!src_file.exists())
        {
            System.out.println(path);
            System.out.println("文件不存在");
            return false;
        }
        return true;
    }
    protected void video_grap_image(String video_path,String des_image_path)
    {
        if(checkFileExist(video_path))
        {
            List<String> commend = new ArrayList<>();
            commend.add("H:\\ffmpeg\\ffmpeg.exe");
            commend.add("-i");
            commend.add(video_path);
            commend.add("-r");
            commend.add("10");

            commend.add("-ss");
            commend.add("00:00:25[.999]");
            commend.add("-t");
            commend.add("00:00:05");
            commend.add("-s");
            commend.add("800x600");
            commend.add(des_image_path);

            try {
                ProcessBuilder builder = new ProcessBuilder();
                builder.command(commend);
                builder.redirectErrorStream(true);
                System.out.println("视频截图开始...");
                 builder.start();
                Process process = builder.start();
                InputStream in =process.getInputStream();
                byte[] re = new byte[1024];
                System.out.print("正在进行截图，请稍候");
                while (in.read(re) != -1) {
                    System.out.print(".");
                }
                System.out.println("");
                in.close();
                System.out.println("视频截图完成...");
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("视频截图失败！");
            }
        }
    }
}
