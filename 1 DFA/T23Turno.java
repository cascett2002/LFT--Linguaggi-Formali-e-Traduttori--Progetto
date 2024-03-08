public class T23Turno {  //1.5 Cognome seguito dal nome
    public static boolean scan(String s) {
        int state = 0;
        int i = 0;
        while (state >= 0 && i < s.length()) {
            final char ch = s.charAt(i++);
            switch (state) {
                case 0:
                    if(AK(ch))
                        state=1;
                    else if(LZ(ch))
                        state=2;
                    else if(ch>='0' && ch<='9')
                        state=-1;
                    else state=-1;
                    break;
                case 1:
                    if(lettera(ch))
                        state=1;
                    else if(pari(ch))
                        state=3;
                    else if(dispari(ch))
                        state=5;
                    else state=-1;
                    break;
                case 2:
                    if(lettera(ch))
                        state=2;
                    else if(dispari(ch))
                        state=4;
                    else if(pari(ch))
                        state=6;
                    else state=-1;
                    break;
                case 3:
                    if(pari(ch))
                        state=3;
                    else if(lettera(ch))
                        state=-1;
                    else if(dispari(ch))
                        state=5;
                    else state=-1;
                    break;
                case 4:
                    if(dispari(ch))
                        state=4;
                    else if(lettera(ch))
                        state=-1;
                    else if(pari(ch))
                        state=6;
                    else state=-1;
                    break;
                case 5:
                    if(pari(ch))
                        state=3;
                    else if(dispari(ch))
                        state=5;
                    else if(lettera(ch))
                        state=-1;
                    else state=-1;
                    break;
                case 6:
                    if(dispari(ch))
                        state=4;
                    else if(pari(ch))
                        state=6;
                    else if(lettera(ch))
                        state=-1;
                    else state=-1;
                    break;

            }
        }
        return state==3||state==4;
    }
    public static void main(String[] args){
        System.out.println(scan("Bianchi123456") ? "OK" : "NOPE");
        System.out.println(scan("Rossi654321") ? "OK" : "NOPE");
        System.out.println(scan("Bianchi654321") ? "OK" : "NOPE");
        System.out.println(scan("Rossi123456") ? "OK" : "NOPE");
    }

    public static boolean AK(char ch){
        if((ch>='a' && ch<='k')||(ch>='A' && ch<='K')) return true;
        else return false;
    }
    public static boolean LZ(char ch){
        if((ch>='l' && ch<='z')||(ch>='L' && ch<='Z')) return true;
        else return false;
    }
    public static boolean pari(char ch){
        if(ch=='0'||ch=='2'||ch=='4'||ch=='6'||ch=='8') return true;
        else return false;
    }
    public static boolean dispari(char ch){
        if(ch=='1'||ch=='3'||ch=='5'||ch=='7'||ch=='9') return true;
        else return false;
    }
    public static boolean lettera(char ch){
        if(ch>='a' && ch<='z') return true;
        else if(ch>='A' && ch<='Z') return true;
        else return false;
    }

}
