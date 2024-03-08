public class TurnoT23 { //1.3 Numero seguito da nome
    public static boolean scan(String s) {
        int state = 0;
        int i = 0;
        while (state >= 0 && i < s.length()) {
            final char ch = s.charAt(i++);
            switch (state) {
                case 0:
                    if(pari(ch))
                        state=1;
                    else if(dispari(ch))
                        state=2;
                    else state=-1;
                    break;
                case 1:
                    if(pari(ch))
                        state=1;
                    else if(LZ(ch))
                        state=-1;
                    else if(dispari(ch))
                        state=2;
                    else if(AK(ch))
                        state=3;
                    else state=-1;
                    break;
                case 2:
                    if(dispari(ch))
                        state=2;
                    else if(LZ(ch))
                        state=3;
                    else if(AK(ch))
                        state=-1;
                    else if(pari(ch))
                        state=1;
                    else state=-1;
                    break;
                case 3:
                    if(AK(ch)||LZ(ch))
                        state=3;
                    else if(dispari(ch)||pari(ch))
                        state=-1;
                    else state=-1;


            }
        }
        return state==3;
    }

    public static void main(String[] args){  //T2:AK,pari
        System.out.println(scan("123456Bianchi") ? "OK" : "NOPE");
        System.out.println(scan("654321Rossi") ? "OK" : "NOPE");
        System.out.println(scan("654321Bianchi") ? "OK" : "NOPE");
        System.out.println(scan("123456Rossi") ? "OK" : "NOPE");
    }

    public static boolean pari(char ch){
        if(ch=='0'||ch=='2'||ch=='4'||ch=='6'||ch=='8') return true;
        else return false;
    }
    public static boolean dispari(char ch){
        if(ch=='1'||ch=='3'||ch=='5'||ch=='7'||ch=='9') return true;
        else return false;
    }
    public static boolean AK(char ch){
        if((ch>='a'&&ch<='k')||(ch>='A'&&ch<='K')) return true;
        else return false;
    }
    public static boolean LZ(char ch){
        if((ch>='l'&&ch<='z')||(ch>='L'&&ch<='Z')) return true;
        else return false;
    }

}
