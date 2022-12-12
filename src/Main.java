import java.util.Scanner;

public class Main {
    //size of the board
    static int size = 4;
    //board
    static int a[][] = new int[size][size];

    //these are so that I can change the controls easily
    static final String UP = "w";
    static final String DOWN = "s";
    static final String LEFT = "a";
    static final String RIGHT = "d";

    public static void main(String args[]){
        Scanner in = new Scanner(System.in);
        //doing it once outside to loop so that it is easier to check if anything moved when in the loop
        System.out.println("enter '"+UP+"' for up, '"+DOWN+"' for down, '"+RIGHT+"' for right, '"+LEFT+"' for left");
        //to have 2 numbers in the beginning
        create();
        create();
        print();
        //keep running until game is over
        while (!isGameOver()){
            //the choice
            char choice =in.next().charAt(0);
            //if anything was added
            boolean added = add(choice+"");
            //if any zeroes were removed
            boolean removed = removeZeros(choice+"");
            System.out.println("enter '"+UP+"' for up, '"+DOWN+"' for down, '"+RIGHT+"' for right, '"+LEFT+"' for left");
            //if any zeroes were removed or anything was added,(there was a change in the state of the board) create a new number, otherwise don't do anything
            if(added || removed){create();}
            print();
        }
        System.out.println("GAME OVER");

    }



    public static void create(){                                        //creates a new number at a random empty position on the board
        boolean created = false;
        int ctr=0;
        for(int i =0;i<size*size;i++){                                                  //in a loop so that if the first position is not empty, it can pick again
            ctr++;
            int p =(int)(Math.random()*(double)(size*size));                        //picks a random position to add a value to
            if(a[p/size][p%size]==0){
                a[p/size][p%size]= ((int)(Math.random()*10.0) == 9)?4:2;            //90% chance for the number to be 2, 10% chance for the number to be 4
                created = true;
                break;                                                              //when value is added break out of loop
            }
        }

        // System.out.println("ctr:"+ctr);
        //this is just so that the previous loop doesn't run for too long, not likely but possible
        if(!created){                                                               //if after size*size number of attempts a position is not found, just iterate through the array, till a position is found
            outer:
            for(int i = 0;i<size;i++){
                for(int j = 0;j<size;j++){
                    if(a[i][j]==0){
                        a[i][j]= ((int)(Math.random()*10.0) == 9)?4:2;
                        break outer;
                    }
                }
            }
        }

    }


    //it would have been much easier to make add() and removeZeroes() into 4 methods each, but I didn't want to do that

    public static boolean add(String choice){      //adds pieces, without removing the 0's between them, returns true when something was successfully added
        boolean somethingAdded = false;
        for(int m =0;m<size;m++) {
            //stores the previous position where !0 was found
            int j0=0;
            int i0=0;

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
                //if the value is 0 skip the rest of the loop
                if (a[i][j] == 0) {
                    continue;
                }
                //only non zero values

                //if the current value is equal to the previous non zero number, add them
                if (a[i0][j0] == a[i][j]) {
                    somethingAdded = true;
                    a[i0][j0] *= 2;
                    a[i][j] = 0;
                }
                //if a non zero value is found, updating the previous non zero position to be the current position
                j0 = j;
                i0 = i;
            }
        }
        return somethingAdded;
    }

    //TODO: improve for loop logic in add and removeZeroes
    //TODO: add more comments

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
            //stores the position of the previous empty (0) cell
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

                //if the current position is empty (0), and no previous position has been empty, set the previous empty position to be the current position
                if(a[i][j]==0 && (jEmpty ==-1 || iEmpty ==-1)){
                    jEmpty = j;
                    iEmpty = i;
                }
                //if this cell is not empty, and there is a previous empty cell, then move the current value to the empty cell, and set current cell to 0
                else if(jEmpty != -1 && iEmpty != -1 && a[i][j]!=0){
                    somethingMoved = true;
                    a[iEmpty][jEmpty] = a[i][j];
                    a[i][j]=0;

                    //moving the previous empty cell position to the position of the next empty cell.
                    iEmpty += iMove ;
                    jEmpty += jMove;
                }

            }
        }
        return somethingMoved;
    }

    public static boolean isGameOver(){             //game is over when there are no empty cells and there are no cells with the same number next to each other
        for(int i =0;i<size;i++){
            for(int j = 0;j<size;j++){
                if(a[i][j]==0){return false;}       //checks if there is an empty cell
                //checks if a cell has a neighbour with the same value, if it does game is not over so return false
                if(i!=size-1 &&(a[i][j]==a[i+1][j])){return false;}//checks the cell below, unless it is the last row
                if(j!=size-1 &&(a[i][j]==a[i][j+1])){return false;}//checks the cell to the right, unless it is the last column
                if(i!=0 &&(a[i][j]==a[i-1][j])){return false;}     //checks the cell above, unless it is the first row
                if(j!=0 &&(a[i][j]==a[i][j-1])){return false;}      //checks the cell to the left, unless it is the first column
            }
        }
        return true;
    }

    public static void print(){
        int spaceBetweenNumbers=6;
        for(int i = 0;i<size;i++){
            for(int j  = 0;j<size;j++){

                if(a[i][j]!=0)
                System.out.print(a[i][j]);
                else System.out.print(".");
                for(int k =0;k<spaceBetweenNumbers-(a[i][j]+"").length();k++){
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }
}
