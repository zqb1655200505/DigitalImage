package work1;

import java.util.LinkedList;

/**
 * Created by zqb on 2016/10/4.
 */
public class Work2 {
    private static final boolean[][] map={
            {false,false,false,false,false,false,false,false,false,false},
            {false,false,false,true,false,false,false,true,false,false},
            {false,false,false,true,false,false,true,true,false,false},
            {false,false,true,false,false,true,true,false,false,false},
            {false,false,true,false,false,true,false,false,false,false},
            {false,false,true,true,true,false,false,false,false,false},
            {false,false,false,false,false,false,false,false,false,false}
    };
    private static boolean[][] vis=new boolean[7][10];
    public static void main(String[] args)
    {
        //vis记录点是否被访问过
        for(int i=0;i<7;i++)
        {
            for(int j=0;j<10;j++)
            {
                vis[i][j]=false;
            }
        }
        Work2_struct st=new Work2_struct();
        st.x=1; st.y=3; st.count=0;
        Work2_struct ed=new Work2_struct();
        ed.x=1;ed.y=7;
        int[] dx={1,-1,0,0,-1,-1,1,1};
        int[] dy={0,0,1,-1,-1,1,-1,1};
        Work2_struct now;
        LinkedList<Work2_struct> list=new LinkedList<>();
        list.add(st);
        vis[st.x][st.y]=true;
        int head=0;
        int foot=1;
        int flag;
        while(head<foot)
        {
            now=list.get(head);
            if(now.x==ed.x&&now.y==ed.y)
            {
                System.out.println("距离为"+now.count);
                break;
            }
            flag=0;//记录四邻域是否有通路
            for(int i=0;i<4;i++)
            {
                Work2_struct next=new Work2_struct();
                next.x=now.x+dx[i];
                next.y=now.y+dy[i];
                next.count=now.count+1;
                if(check(next))
                {
                    flag=1;
                    list.add(next);
                    vis[next.x][next.y]=true;
                    foot++;
                }
            }
            if(flag==0)//四邻域无通路，找寻四对角邻域
            {
                for(int i=4;i<8;i++)
                {
                    Work2_struct next=new Work2_struct();
                    next.x=now.x+dx[i];
                    next.y=now.y+dy[i];
                    next.count=now.count+1;
                    if(check(next))
                    {
                        list.add(next);
                        vis[next.x][next.y]=true;
                        foot++;
                    }
                }
            }
            head++;
        }
    }
    private static boolean check(Work2_struct next)
    {
        if(next.x>=0&&next.x<7&&next.y>=0&&next.y<10&&!vis[next.x][next.y]&&map[next.x][next.y])
        {
            return true;
        }
        return false;
    }
}
