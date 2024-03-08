public class commenti { //1.10
    public static boolean scan(String s) {
        int state = 0;
        int i = 0;
        while (state >= 0 && i < s.length()) {
            final char ch = s.charAt(i++);
            switch (state) {
                case 0:
                    if(ch=='a'||ch=='*')
                        state=0;
                    else if(ch=='/')
                        state=1;
                    else state=-1;
                    break;
                case 1:
                    if(ch=='*')
                        state=2;
                    else if(ch=='a')
                        state=0;
                    else if(ch=='/')
                        state=1;
                    else state=-1;
                    break;
                case 2:
                    if(ch=='a'||ch=='/')
                        state=2;
                    else if(ch=='*')
                        state=3;
                    else state=-1;
                    break;
                case 3:
                    if(ch=='*')
                        state=3;
                    else if(ch=='a')
                        state=2;
                    else if(ch=='/')
                        state=4;
                    else state=-1;
                    break;
                case 4:
                    if(ch=='a'||ch=='*')
                        state=4;
                    else if(ch=='/')
                        state=5;
                    else state=-1;
                    break;
                case 5:
                    if(ch=='a'||ch=='/')
                        state=5;
                    else if(ch=='*')
                        state=2;
                    else state=-1;



            }
        }
        return state==0||state==1||state==4||state==5;
    }

    public static void main(String[] args){
        System.out.println("Accettate:");
        System.out.println(scan("aaa/****/aa") ? "OK" : "NOPE");
        System.out.println(scan("aa/*a*a*/") ? "OK" : "NOPE");
        System.out.println(scan("aaaa") ? "OK" : "NOPE");
        System.out.println(scan("/****/") ? "OK" : "NOPE");
        System.out.println(scan("/*aa*/") ? "OK" : "NOPE");
        System.out.println(scan("*/a") ? "OK" : "NOPE");
        System.out.println(scan("a/**/***a") ? "OK" : "NOPE");
        System.out.println(scan("a/**/***/a") ? "OK" : "NOPE");
        System.out.println(scan("a/**/aa/***/a") ? "OK" : "NOPE");

        System.out.println();

        System.out.println("NON accettate:");
        System.out.println(scan("aaa/*/aa") ? "OK" : "NOPE");
        System.out.println(scan("a/**//***a") ? "OK" : "NOPE");
        System.out.println(scan("aa/*aa") ? "OK" : "NOPE");

    }
}
