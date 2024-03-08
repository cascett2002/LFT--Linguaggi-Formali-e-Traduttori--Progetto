public class ab { //1.6
    public static boolean scan(String s) {
        int state = 0;
        int i = 0;
        while (state >= 0 && i < s.length()) {
            final char ch = s.charAt(i++);
            switch (state) {
                case 0:
                    if(ch=='b')
                        state=0;
                    else if(ch=='a')
                        state=1;
                    else state=-1;
                    break;
                case 1:
                    if(ch=='a')
                        state=1;
                    else if(ch=='b')
                        state=2;
                    else state=-1;
                    break;
                case 2:
                    if(ch=='a')
                        state=1;
                    else if(ch=='b')
                        state=3;
                    else state=-1;
                    break;
                case 3:
                    if(ch=='b')
                        state=0;
                    else if(ch=='a')
                        state=1;
                    else state=-1;
                    break;
            }
        }
        return state==1||state==2||state==3;
    }

    public static void main(String[] args){
        System.out.println("Accettate:");
        System.out.println(scan("abb") ? "OK" : "NOPE");
        System.out.println(scan("bbaba") ? "OK" : "NOPE");
        System.out.println(scan("baaaaaaa") ? "OK" : "NOPE");
        System.out.println(scan("aaaaaaa") ? "OK" : "NOPE");
        System.out.println(scan("a") ? "OK" : "NOPE");
        System.out.println(scan("ba") ? "OK" : "NOPE");
        System.out.println(scan("bba") ? "OK" : "NOPE");
        System.out.println(scan("aa") ? "OK" : "NOPE");
        System.out.println(scan("a") ? "OK" : "NOPE");
        System.out.println(scan("bbbababab") ? "OK" : "NOPE");
        System.out.println();
        System.out.println("NON accettate:");
        System.out.println(scan("bbabbbbbbbb") ? "OK" : "NOPE");
        System.out.println(scan("abbbbbb") ? "OK" : "NOPE");
        System.out.println(scan("b") ? "OK" : "NOPE");

    }
}
