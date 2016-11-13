package work4;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by zqb on 2016/11/4.
 */
public class Filter {
    /*
        这个文件里包含两个DFT函数，一个是主DFT，在这里完成一个二维图片的
        扩充、中心移动、傅里叶变换、滤波（包括滤波器的处理等）、逆变换、取实部、
        移回中心等操作并返回一个处理完后的二维图片。另一个DFT是指普通的（慢速）
        傅里叶变换，输入一个二维数组、u、v并返回一个值F（u、v）。
        除此之外，本文件还提供：
        ·get2PowerEdge：用来计算一个图像边的最小2的整数次幂扩展；
        ·showFouriedImage：返回一个傅里叶变换后的频谱图（取模、取log再量化）
        ·fft：一维快速傅里叶变换
        ·ifft：一维快速傅里叶逆变换
        ·convolve：一维卷积。
    */

    protected void FFT(BufferedImage image, String save_path)
    {
        int width=image.getWidth();
        int height=image.getHeight();
        int m=get2PowerEdge(width);
        int n=get2PowerEdge(height);
        BufferedImage desImg=new BufferedImage(width,height,image.getType());
        int[][] last = new int[m][n];
        Complex[][] next = new Complex[m][n];

        //----------------------------------------------------------------------------------------------------
        //first扩展图片并乘以（-1）^(x+y)
        for(int i=0;i<m;i++)
        {
            for(int j=0;j<n;j++)
            {
                if(i<width&&j<height)
                {
                    int pixel =image.getRGB(i,j);
                    if((i+j)%2==0)
                    {
                        last[i][j]=pixel&0x00ff0000>>16;
                    }
                    else
                    {
                        last[i][j]=-(pixel&0x00ff0000>>16);
                    }
                }
                else
                {
                    last[i][j]=0;
                }
            }
        }

        //-----------------------------------------------------------------------------------------------------
        // second: Fourier Transform 离散傅里叶变换

        // 先把所有的行都做一维傅里叶变换，再放回去
        Complex[] temp1 = new Complex[n];
        for(int i=0;i<m;i++)
        {
            for(int j=0;j<n;j++)
            {
                temp1[j]=new Complex(last[i][j],0);
            }
            next[i] = fft(temp1);
        }

        // 再把所有的列（已经被行的一维傅里叶变换所替代）都做一维傅里叶变换
        Complex[] temp2 = new Complex[m];
        for (int y = 0; y < n; y++)
        {
            for (int x = 0; x < m; x++)
            {
                temp2[x] = next[x][y];
            }
            temp2 = fft(temp2);
            //将变换好的重新放回next，后续要用到
            for (int i = 0; i < m; i++)
            {
                next[i][y] = temp2[i];
            }
        }


        //-------------------------------------------------------------------------------------------------
        // third: Generate the frequency filter and filter the
        // image in frequency domain 生成频率域滤波器并滤波
        // 构造原始滤波函数
        Complex[][] filter = new Complex[m][n];
        //下面这个是拉普拉斯滤波
//        filter[0][0] = new Complex(0, 0);
//        filter[0][1] = new Complex(-1, 0);
//        filter[0][2] = new Complex(0, 0);
//        filter[1][0] = new Complex(-1, 0);
//        filter[1][1] = new Complex(4, 0);
//        filter[1][2] = new Complex(-1, 0);
//        filter[2][0] = new Complex(0, 0);
//        filter[2][1] = new Complex(-1, 0);
//        filter[2][2] = new Complex(0, 0);

//        //均值滤波
//        for (int x = 0; x < m; x++)
//        {
//            for (int y = 0; y < n; y++)
//            {
//                if (x < 3 && y < 3)
//                {
//                    if ((x+y)%2==0)
//                    {
//                        filter[x][y] = new Complex(1/9d, 0); // double 后面赋值数字记得加d！！！！！！！
//                    }
//                    else
//                    {
//                        filter[x][y] = new Complex(-1/9d, 0);
//                    }
//                }
//                else
//                {
//                    filter[x][y] = new Complex(0, 0);
//                }
//            }
//        }

        //理想高通
//        for(int i=0;i<m;i++)
//        {
//            for(int j=0;j<n;j++)
//            {
//                if(Math.sqrt((i-m/2)*(i-m/2)+(j-n/2)*(j-n/2))-40>0.000001)
//                {
//                    filter[i][j]=new Complex(1,0);
//                }
//                else
//                {
//                    filter[i][j]=new Complex(0,0);
//                }
//            }
//        }


        //理想低通
//        for(int i=0;i<m;i++)
//        {
//            for(int j=0;j<n;j++)
//            {
//                if(Math.sqrt((i-m/2)*(i-m/2)+(j-n/2)*(j-n/2))-180>0.000001)
//                {
//                    filter[i][j]=new Complex(0,0);
//                }
//                else
//                {
//                    filter[i][j]=new Complex(1,0);
//                }
//            }
//        }


        //巴特沃斯低通
//        for(int i=0;i<m;i++)
//        {
//            for(int j=0;j<n;j++)
//            {
//                double d=Math.sqrt((i-m/2)*(i-m/2)+(j-n/2)*(j-n/2));
//                double h=1.0/(1+(d/50.0)*(d/50.0));
//                filter[i][j]=new Complex(h,0);
//            }
//        }


        //巴特沃斯高通
//        for(int i=0;i<m;i++)
//        {
//            for(int j=0;j<n;j++)
//            {
//                double d=Math.sqrt((i-m/2)*(i-m/2)+(j-n/2)*(j-n/2));
//                double h=1.0/(1+(80.0/d)*(80.0/d));
//                filter[i][j]=new Complex(h,0);
//            }
//        }
        //高斯低通
//        for(int i=0;i<m;i++)
//        {
//            for(int j=0;j<n;j++)
//            {
//                double d=(i-m/2)*(i-m/2)+(j-n/2)*(j-n/2);
//                double h=Math.exp((-d)/(2*50*50));
//                filter[i][j]=new Complex(h,0);
//            }
//        }
        //高斯高通
        for(int i=0;i<m;i++)
        {
            for(int j=0;j<n;j++)
            {
                double d=(i-m/2)*(i-m/2)+(j-n/2)*(j-n/2);
                double h=1-Math.exp((-d)/(2*80*80));
                filter[i][j]=new Complex(h,0);
            }
        }
        // 傅里叶变换 转换为频率域(转换中心)
//        for (int x = 0; x < m; x++)
//        {
//            for (int y = 0; y < n; y++)
//            {
//                temp1[y] = new Complex(filter[x][y].getR(), filter[x][y].getI());
//            }
//            filter[x] = fft(temp1);
//        }
//
//        for (int y = 0; y < n; y++)
//        {
//            for (int x = 0; x < m; x++)
//            {
//                temp2[x] = new Complex(filter[x][y].getR(), filter[x][y].getI());
//            }
//            temp2 = fft(temp2);
//            for (int i = 0; i < m; i++)
//            {
//                filter[i][y] = temp2[i];
//            }
//        }
        //点乘
        Complex[][] r = new Complex[m][n];
        for (int x = 0; x < m; x++)
        {
            for (int y = 0; y < n; y++)
            {
                r[x][y] =  filter[x][y].times(next[x][y]);
            }
        }


//--------------------------------------------------------------------------------------------------------
        // fourth: use IDFT to get the image 傅里叶逆变换
        for (int x = 0; x < m; x++)
        {
            for (int y = 0; y < n; y++)
            {
                temp1[y] = new Complex(r[x][y].getR(), r[x][y].getI());
            }
            r[x] = ifft(temp1);
        }


        for (int y = 0; y < n; y++)
        {
            for (int x = 0; x < m; x++)
            {
                temp2[x] = r[x][y];
            }
            temp2 = ifft(temp2);
            for (int i = 0; i < m; i++)
            {
                r[i][y] = temp2[i];
            }
        }

//-----------------------------------------------------------------------------------------------
        // fifth：取实部
        for (int x = 0; x < m; x++)
        {
            for (int y = 0; y < n; y++)
            {
                last[x][y] = (int)r[x][y].getR();
            }
        }


//--------------------------------------------------------------------------------------------------------------
        // sixth: move the image back and cut the image 乘以(-1)^(x+y)再剪裁图像

        int newalpha = (-1) << 24;
        for (int i = 0; i < width; i++)
        {
            for (int j = 0; j < height; j++)
            {
                if((i+j)%2!=0)
                {
                    last[i][j]=-last[i][j];
                }
                int newblue = last[i][j];
                int newgreen = last[i][j] << 8;
                int newred = last[i][j] << 16;
                int newrgb = newalpha | newred | newgreen | newblue;
                desImg.setRGB(i, j, newrgb);
            }
        }



        File result=new File(save_path);
        try {
            ImageIO.write(desImg,"jpg",result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //归一化
//    private int[][] nomalization(int[][] a)
//    {
//        int max = a[0][0];
//        int min = a[0][0];
//        for (int i = 0; i < a.length; i++) {
//            for (int j = 0; j < a[0].length; j++) {
//                if (a[i][j] > max)
//                    max = a[i][j];
//                if (a[i][j] < min)
//                    min = a[i][j];
//            }
//        }
//        double interval = 256.0/(max - min) ;
//        for(int i=0;i<a.length;i++)
//        {
//            for(int j=0;j<a[0].length;j++)
//            {
//                a[i][j]=(int)((a[i][j]-min)*interval);
//            }
//        }
//        return a;
//    }

    //==============================================================================================================
    // 根据图像的长获得2的整数次幂
    private int get2PowerEdge(int e) {
        if (e == 1)
            return 1;
        int cur = 1;
        while(true) {
            if (e > cur && e <= 2 * cur)
                return 2*cur;
            else
                cur *= 2;
        }
    }




    //=============================================================================================================
    // 快速一维傅里叶变换
    private Complex[] fft(Complex[] x) {
        int N = x.length;
        if (N == 1)
        {
            return x;
        }
        // radix 2 Cooley-Turkey FFT
        if (N % 2 != 0) {
            throw new RuntimeException("N is not a power of 2");
        }

        // fft of even terms
        Complex[] even = new Complex[N/2];
        for (int k = 0; k < N/2; k++) {
            even[k] = x[2*k];
        }
        Complex[] q = fft(even);

        // fft of odd terms
        Complex[] odd = new Complex[N/2];
        for (int k = 0; k < N/2; k++) {
            odd[k] = x[2*k+1]; //DEBUG  之前这里忘记+1  差点搞死我
        }
        Complex[] r = fft(odd);

        // combine
        Complex[] y = new Complex[N];
        for (int k = 0; k < N/2; k++)
        {
            double kth = -2 * k * Math.PI / N;
            Complex wk = new Complex(Math.cos(kth), Math.sin(kth));
            y[k] = q[k].plus(wk.times(r[k]));
            y[k + N/2] = q[k].minus(wk.times(r[k]));
        }
        return y;
    }
//=========================================================================================================
    // 快速一维傅里叶逆变换
    private Complex[] ifft(Complex[] x)
    {
        int N = x.length;
        Complex[] y = new Complex[N];

        // take conjugate
        for (int i = 0; i < N; i++) {
            y[i] = x[i].conjugate();
        }

        // compute forward fft
        y = fft(y);

        // take conguate again，取共轭
        for (int i = 0; i < N; i++) {
            y[i] = y[i].conjugate();
        }

        // divide by N
        for (int i = 0; i < N; i++) {
            y[i] = y[i].times(1.0/N);
        }

        return y;
    }

//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // complex inner class
    //复数操作类
    public class Complex {
        private final double r;//实部
        private final double i;//虚部

        public Complex (double r, double i) {
            this.r = r;
            this.i = i;
        }

        //取模运算
        public double abs() { // return sqrt(r^2 +i^2)
            return Math.hypot(r, i);
        }

        public double phase() {
            return Math.atan2(i, r);
        }

        public Complex plus (Complex c) {
            return new Complex (this.r + c.r, this.i + c.i);
        }

        public Complex minus (Complex c) {
            return new Complex (this.r - c.r, this.i - c.i);
        }
        //复数乘复数
        public Complex times (Complex c)
        {
            return new Complex (this.r * c.r - this.i * c.i,
                    this.r * c.i + this.i * c.r);
        }
        //复数乘实数
        public Complex times (double d) {
            return new Complex (this.r * d, this.i * d);
        }

        //取共轭
        public Complex conjugate()
        {
            return new Complex (r, -i);
        }
        //返回实部
        public double getR () {
            return r;
        }
        //返回虚部
        public double getI () {
            return i;
        }

        public Complex exp() {
            return new Complex(Math.exp(r) * Math.cos(i),
                    Math.exp(r) * Math.sin(i));
        }
    }
}
