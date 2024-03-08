public class Michele { //1.7
    public static boolean scan(String s) {
        int state = 0;
        int i = 0;
        boolean c=false;
        while (state >= 0 && i < s.length()) {
            final char ch = s.charAt(i++);
            switch (state) {
                case 0:
                    if(ch=='m')
                        state=1;
                    else if(c)
                        state=-1;
                    else {state=1; c=true;}
                    break;
                case 1:
                    if(ch=='i')
                        state=2;
                    else if(c)
                        state=-1;
                    else {state=2; c=true;}
                    break;
                case 2:
                    if(ch=='c')
                        state=3;
                    else if(c)
                        state=-1;
                    else {state=3; c=true;}
                    break;
                case 3:
                    if(ch=='h')
                        state=4;
                    else if(c)
                        state=-1;
                    else {state=4; c=true;}
                    break;
                case 4:
                    if(ch=='e')
                        state=5;
                    else if(c)
                        state=-1;
                    else {state=5; c=true;}
                    break;
                case 5:
                    if(ch=='l')
                        state=6;
                    else if(c)
                        state=-1;
                    else {state=6; c=true;}
                    break;
                case 6:
                    if(ch=='e')
                        state=7;
                    else if(c)
                        state=-1;
                    else {state=7; c=true;}
                    break;
                case 7:
                    if(ch!='\0')
                        state=-1;
                    break;



            }
        }
        return state==7;
    }
    public static void main(String[]args){

        System.out.println("Accettate:");
        System.out.println(scan("michele") ? "OK" : "NOPE");
        System.out.println(scan("mmchele") ? "OK" : "NOPE");
        System.out.println(scan("*ichele") ? "OK" : "NOPE");
        System.out.println(scan("michel*") ? "OK" : "NOPE");
        System.out.println(scan("micpele") ? "OK" : "NOPE");

        System.out.println();
        System.out.println("NON accettate:");
        System.out.println(scan("rachele") ? "OK" : "NOPE");
        System.out.println(scan("mickeoe") ? "OK" : "NOPE");
        System.out.println(scan("aaaaaaa") ? "OK" : "NOPE");

    }

}
