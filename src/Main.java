import java.util.Scanner;

public class Main {
    static int size = 4;
    static int a[][] = new int[size][size];

    static final String UP = "w";
    static final String DOWN = "s";
    static final String LEFT = "a";
    static final String RIGHT = "d";

    public static void main(String args[]){



        Scanner in = new Scanner(System.in);
        System.out.println("enter 'u' for up, 'd' for down, 'r' fsor right, 'l' for left");
        create();
        create();
        print();
        while (!isGameOver()){
            char choice =in.next().charAt(0);
            boolean moved = move(choice+"");
            boolean removed = removeZeros(choice+"");
            System.out.println("enter 'u' for up, 'd' for down, 'r' for right, 'l' for left");

            if(moved || removed){create();}
            print();
        }
        System.out.println("GAME OVER");

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


    public static boolean move(String choice){
        boolean somethingMoved = false;
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

//                System.out.println("old val" + a[i0][j0]);
//                System.out.println("val" + a[i][j]);
//                System.out.println("ij" +i+ j);
                if (a[i0][j0] == a[i][j]) {
                    somethingMoved = true;
                    a[i0][j0] *= 2;
                    a[i][j] = 0;
                }
                j0 = j;
                i0 = i;
                //print();
            }
        }
        return somethingMoved;
    }

    public static boolean removeZeros(String choice){
        boolean somethingMoved = false;

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
                    //System.out.println("ij"+i+j);
                    jEmpty = j;
                    iEmpty = i;
                }
                else if(jEmpty != -1 && iEmpty != -1 && a[i][j]!=0){
                    somethingMoved = true;
                    a[iEmpty][jEmpty] = a[i][j];
                    a[i][j]=0;
                    iEmpty += iMove ;
                    jEmpty += jMove;
                }

            }
        }
        return somethingMoved;
    }

    public static boolean isGameOver(){
        for(int i =0;i<size;i++){
            for(int j = 0;j<size;j++){
                if(a[i][j]==0){return false;}
                if(i!=size-1 &&(a[i][j]==a[i+1][j])){return false;}
                if(j!=size-1 &&(a[i][j]==a[i][j+1])){return false;}
                if(i!=0 &&(a[i][j]==a[i-1][j])){return false;}
                if(j!=0 &&(a[i][j]==a[i][j-1])){return false;}
            }
        }
        return true;
    }

    public static void print(){
        for(int i = 0;i<size;i++){
            for(int j  = 0;j<size;j++){
                if(a[i][j]!=0)
                System.out.print(a[i][j]+" \t\t");
                else System.out.print(". \t\t");
            }
            System.out.println();
        }
    }
}
