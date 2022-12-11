import java.util.Random;
import java.util.Scanner;

public class Main {
    static int size = 4;
    static int a[][] = new int[size][size];
    static int prevVal = 0;

    static final String UP = "u";
    static final String DOWN = "d";
    static final String LEFT = "l";
    static final String RIGHT = "r";

    public static void main(String args[]){
//            //create();
//            print();
//            a[0][0]=2;
//            a[0][2]= 2;
//            a[0][3]=1;
//
//            a[1][0]=2;
//            a[1][1] = 1;
//            a[1][2]= 2;
//            a[1][3]=1;
//
//            a[2][0]=2;
//            a[2][2]= 2;
//            a[2][3]=1;
//            print();
//            move(UP);
//            print();
//
//            print();
        Scanner in = new Scanner(System.in);
        while (true){
            System.out.println("enter 'u' for up, 'd' for down, 'r' for right, 'l' for left");
            create();
            print();
            char choice =in.next().charAt(0);
            move(choice+"");
            removeZeros(choice+"");

        }

    }

    public static void create(){
        while(true){
            int p =(int)(Math.random()*16.0);
            if(a[p/size][p%size]==0){
                a[p/size][p%size]= ((int)(Math.random()*10.0) == 9)?4:2;
                break;
            }
        }
    }


    public static void move(String choice){
        for(int m =0;m<size;m++) {
            int j0=0;
            int i0 =0;

            switch(choice){
                case LEFT:i0 = m; break;
                case RIGHT:i0=m;j0=size -1;break;
                case DOWN: j0=m;i0=size -1;break;
                case UP: j0=m;break;
                default:
                    System.out.println("why must you do this to me");
            }
            for (int n = 1; n < size; n++) {
                int i=0;
                int j =0;
                switch(choice){
                    case LEFT:i=m; j=n; break;
                    case RIGHT:i=m;j=size - n-1;break;
                    case DOWN: i =size - n-1;j=m;break;
                    case UP: i =n;j=m;break;
                    default:
                        System.out.println("why must you do this to me");
                }

                if (a[i][j] == 0) {
                    continue;
                }

                System.out.println("old val" + a[i0][j0]);
                System.out.println("val" + a[i][j]);
                System.out.println("ij" +i+ j);
                if (a[i0][j0] == a[i][j]) {
                    a[i0][j0] *= 2;
                    a[i][j] = 0;
                }
                j0 = j;
                i0 = i;
                //print();
            }
        }
        
    }

    public static void removeZeros(String choice){
        int jMove=0;
        int iMove = 0;
        switch(choice){
            case LEFT:jMove=1; break;
            case RIGHT:jMove = -1;break;
            case DOWN: iMove = -1;break;
            case UP: iMove = 1;break;
            default:
                System.out.println("why must you do this to me");
        }

        for(int m = 0;m<size;m++){
            int jEmpty = -1;
            int iEmpty =-1;
            for(int n =0;n<size ;n++){

                int i=0;
                int j =0;
                switch(choice){
                    case LEFT:i=m; j=n; break;
                    case RIGHT:i=m;j=size - n-1;break;
                    case DOWN: i =size - n-1;j=m;break;
                    case UP: i =n;j=m;break;
                    default:
                        System.out.println("why must you do this to me");
                }

                if(a[i][j]==0 && (jEmpty ==-1 || iEmpty ==-1)){
                    System.out.println("ij"+i+j);
                    jEmpty = j;
                    iEmpty = i;
                }
                else if(jEmpty != -1 && iEmpty != -1 && a[i][j]!=0){
                    a[iEmpty][jEmpty] = a[i][j];
                    a[i][j]=0;
                    iEmpty += iMove ;
                    jEmpty += jMove;
                }

            }
        }
    }

    public static void print(){
        for(int i = 0;i<size;i++){
            for(int j  = 0;j<size;j++){
                System.out.print(a[i][j]+" ");
            }
            System.out.println();
        }
    }
}
