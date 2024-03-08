public class floatingesp { //1.8
    public static boolean scan(String s) {
        int state = 0;
        int i = 0;
        while (state >= 0 && i < s.length()) {
            final char ch = s.charAt(i++);
            boolean e=false;
            boolean p=false;
            switch (state) {
                case 0:
                    if(ch=='+'||ch=='-')
                        state=1;
                    else if(numero(ch))
                        state=2;
                    else if(ch=='.')
                        state=3;
                    else if(ch=='e')
                        state=-1;
                    else state=-1;
                    break;
                case 1:
                    if(numero(ch))
                        state=2;
                    else if(ch=='.')
                        state=3;
                    else if(ch=='+'||ch=='-'||ch=='e')
                        state=-1;
                    else state=-1;
                    break;
                case 2:
                    if(numero(ch))
                        state=2;
                    else if(ch=='.')
                        state=3;
                    else if(ch=='e'){
                        if(e) state=-1;
                        else {state=5; e=true;}
                    }
                    else if(ch=='+'||ch=='-')
                        state=-1;
                    else state=-1;
                    break;
                case 3:
                    if(numero(ch))
                        state=4;
                    else if(ch=='+'||ch=='-'||ch=='e'||ch=='.')
                        state=-1;
                    else state=-1;
                    break;
                case 4:
                    if(numero(ch))
                        state=4;
                    else if(ch=='e'){
                        if(e) state=-1;
                        else {state=5; e=true;}
                    }
                    else if(ch=='+'||ch=='-'||ch=='.')
                        state=-1;
                    else state=-1;
                    break;
                case 5:
                    if(numero(ch))
                        state=7;
                    else if(ch=='+'||ch=='-')
                        state=6;
                    else if(ch=='.'||ch=='e')
                        state=-1;
                    else state=-1;
                    break;
                case 6:
                    if(numero(ch))
                        state=7;
                    else if(ch=='.'||ch=='e'||ch=='+'||ch=='-')
                        state=-1;
                    else state=-1;
                    break;
                case 7:
                    if(numero(ch))
                        state=7;
                    else if(ch=='.'){
                        if(p) state=-1;
                        else{state=8; p=true;}
                    }
                    else if(ch=='e'||ch=='+'||ch=='-')
                        state=-1;
                    else state=-1;
                    break;
                case 8:
                    if(numero(ch))
                        state=7;
                    else if(ch=='+'||ch=='-'||ch=='e'||ch=='.')
                        state=-1;
                    else state=-1;
                    break;
            }
        }
        return state==2||state==4||state==7;
    }

    public static void main(String[] args){

        System.out.println("Accettate:");
        System.out.println(scan("123") ? "OK" : "NOPE");
        System.out.println(scan("123.5") ? "OK" : "NOPE");
        System.out.println(scan(".567") ? "OK" : "NOPE");
        System.out.println(scan("+7.5") ? "OK" : "NOPE");
        System.out.println(scan("-.7") ? "OK" : "NOPE");
        System.out.println(scan("67e10") ? "OK" : "NOPE");
        System.out.println(scan("1e-2") ? "OK" : "NOPE");
        System.out.println(scan("-.7e2") ? "OK" : "NOPE");
        System.out.println(scan("1e2.3") ? "OK" : "NOPE");

        System.out.println();
        System.out.println("NON accettate:");
        System.out.println(scan(".") ? "OK" : "NOPE");
        System.out.println(scan("e3") ? "OK" : "NOPE");
        System.out.println(scan("123.") ? "OK" : "NOPE");
        System.out.println(scan("+e6") ? "OK" : "NOPE");
        System.out.println(scan("1.2.3") ? "OK" : "NOPE");
        System.out.println(scan("4e5e6") ? "OK" : "NOPE");
        System.out.println(scan("++3") ? "OK" : "NOPE");

    }
    public static boolean numero(char ch){
        if(ch>='0' && ch<='9') return true;
        else return false;
    }
}
