public class TurnoT23Spazi { //1.4
    public static boolean scan(String s) {
        int state = 0;
        int i = 0;
        while (state >= 0 && i < s.length()) {
            final char ch = s.charAt(i++);
            switch (state) {
                case 0:
                    if(ch==32)
                        state=0;
                    else if(pari(ch))
                        state=1;
                    else if(dispari(ch))
                        state=2;
                    else if(lettera(ch))
                        state=-1;
                    else state=-1;
                    break;
                case 1:
                    if(pari(ch))
                        state=1;
                    else if(dispari(ch))
                        state=2;
                    else if(ch==' ')
                        state=5;
                    else if(AK(ch))
                        state=3;
                    else state=-1;
                    break;
                case 2:
                    if(dispari(ch))
                        state=2;
                    else if(LZ(ch))
                        state=3;
                    else if(ch==32)
                        state=4;
                    else if(pari(ch))
                        state=1;
                    else state=-1;
                    break;
                case 3:
                    if(lPiccola(ch))
                        state=3;
                    else if(ch==32)
                        state=6;
                    else if(ch>='0' && ch<='9')
                        state=-1;
                    else state=-1;
                    break;
                case 4:
                    if(LZ(ch))
                        state=3;
                    else if(ch==32)
                        state=4;
                    else state=-1;
                    break;
                case 5:
                    if(AK(ch))
                        state=3;
                    else if(ch==32)
                        state=5;
                    else state=-1;
                    break;
                case 6:
                    if(ch==32)
                       state=6;
                    else if(lGrande(ch))
                        state=7;
                    else if(lPiccola(ch)||(ch>='0' && ch<='9'))
                        state=-1;
                    else state=-1;
                    break;
                case 7:
                    if(lPiccola(ch))
                        state=7;
                    else if(ch==32)
                        state=6;
                    else if(lGrande(ch)||(ch>='0' && ch<='9'))
                        state=-1;
                    break;

            }

        }
        return state==3||state==6||state==7;
    }
    public static void main(String[] args){
        System.out.println(scan(" 654321 Rossi ") ? "OK" : "NOPE");
        System.out.println(scan(" 123456 Bianchi ") ? "OK" : "NOPE");
        System.out.println(scan(" 1234 56Bianchi ") ? "OK" : "NOPE");
        System.out.println(scan(" 123456Bia nchi ") ? "OK" : "NOPE");
        System.out.println(scan("123456De Gasperi") ? "OK" : "NOPE");//CognomeComposto



    }
    public static boolean AK(char ch){
        if(ch>='A' && ch<='K') return true;
        else return false;
    }
    public static boolean LZ(char ch){
        if(ch>='L' && ch<='Z') return true;
        else return false;
    }
    public static boolean lettera(char ch){
        if(ch>='a' && ch<='z') return true;
        else if(ch>='A' && ch<='Z') return true;
        else return false;
    }
    public static boolean lPiccola(char ch){
        if(ch>='a' && ch<='z') return true;
        else return false;
    }
    public static boolean lGrande(char ch){
        if(ch>='A' && ch<='Z') return true;
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


}