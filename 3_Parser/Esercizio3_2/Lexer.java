import java.io.*; 
import java.util.*;

public class Lexer {

    public static int line = 1;
    private char peek = ' ';
    
    private void readch(BufferedReader br) {
        try {
            peek = (char) br.read();
        } catch (IOException exc) {
            peek = (char) -1; // ERROR
        }
    }

    public Token lexical_scan(BufferedReader br) {
        while (peek == ' ' || peek == '\t' || peek == '\n'  || peek == '\r') {
            if (peek == '\n') line++;
            readch(br);
        }
        
        switch (peek) {
            case '!':
                peek = ' ';
                return Token.not;

	        // ... gestire i casi di ( ) [ ] { } + - * / ; , ... //

            case '(':
                peek = ' ';
                return Token.lpt;

            case ')':
                peek = ' ';
                return Token.rpt;
            
            case '[':
                peek = ' ';
                return Token.lpq;

            case ']':
                peek = ' ';
                return Token.rpq;

            case '{':
                peek = ' ';
                return Token.lpg;

            case '}':
                peek = ' ';
                return Token.rpg;

            case '+':
                peek = ' ';
                return Token.plus;

            case '-':
                peek = ' ';
                return Token.minus;

            case '*':
                peek = ' ';
                return Token.mult;

            //Esercizio2_3
            case '/':
                readch(br);
                if(peek == '/') {
                    while (peek != '\n' && peek != (char) -1) {
                        readch(br);
                    }
                    return lexical_scan(br);
                }else if(peek == '*') {
                    int state = 0;
                    readch(br);
                    while(state >= 0 && state != 2) {
                        switch (state) {
                            case 0:
                                if (peek == '*')
                                    state = 1;
                                else
                                    state = 0;
                                break;
                            case 1:
                                if (peek == '*')
                                    state = 1;
                                else if (peek == '/')
                                    state = 2;
                                else
                                    state = 0;
                                break;
                        }
                        readch(br);
                    }
                    if(state == 2)
                        return lexical_scan(br);
                    return null;
                }else{
                    return Token.div;
                }

            case ';':
                peek = ' ';
                return Token.semicolon;

            case ',':
                peek = ' ';
                return Token.comma;
	
            case '&':
                readch(br);
                if (peek == '&') {
                    peek = ' ';
                    return Word.and;
                } else {
                    System.err.println("Erroneous character"
                            + " after & : "  + peek );
                    return null;
                }

	        // ... gestire i casi di || < > <= >= == <> ... //

            case '|':
                readch(br);
                if (peek == '|') {
                    peek = ' ';
                    return Word.or;
                } else {
                    System.err.println("Erroneous character"
                            + " after | : "  + peek );
                    return null;
                }

            case '<':
                readch(br);
    
                switch(peek){
					case '=':
						peek = ' ';
						return Word.le;
					case '>':
						peek = ' ';
						return Word.ne;
					default:
						return Word.lt;	
				}

            case '>':
                readch(br);

                switch(peek){
					case '=':
						peek = ' ';
						return Word.ge;
					default:
						return Word.gt;	
				}

            case '=':
                readch(br);
                if (peek == '=') {
                    peek = ' ';
                    return Word.eq;
                } else {
                    System.err.println("Erroneous character"
                            + " after = : "  + peek );
                    return null;
                }

            case (char)-1:
                return new Token(Tag.EOF);

            default:
                if (Character.isLetter(peek) || peek == '_') {
	            // ... gestire il caso degli identificatori e delle parole chiave //
                    String s = "";
                    int state = 0;
					
					while(state >= 0 && Character.isLetter(peek) || (Character.isDigit(peek)) || peek == '_'){

                        //Esercizio2_2
                        switch(state){
                            case 0:
                                if((peek >= 'a' && peek <= 'z') ||
                                   (peek >= 'A' && peek <= 'Z'))
                                    state = 2;
                                else if(peek == '_')
                                    state = 1;
                                else
                                    state = -1;
                            break;

                            case 1:
                                if((peek >= 'a' && peek <= 'z') || (peek >= 'A' && peek <= 'Z') ||
                                   (peek >= '0' && peek <= '9'))
                                    state = 2;
                                else if(peek == '_')
                                    state = 1;
                                else
                                    state = -1;
                            break;

                            case 2:
                                if((peek >= 'a' && peek <= 'z') || (peek >= '0' && peek <= '9') ||
                                   (peek >= 'A' && peek <= 'Z') || peek == '_')
                                    state = 2;
                                else 
                                    state = -1;
                            break;
                        }
                        s = s + peek;
                        readch(br);
					}	
                    
                    //key words
                    if(state == 2){
                        if(s.compareTo("assign") == 0){
                            return Word.assign;
                        }else if(s.compareTo("to") == 0){
                            return Word.to;
                        }else if(s.compareTo("conditional") == 0){
                            return Word.conditional;
                        }else if(s.compareTo("option") == 0){
                            return Word.option;
                        }else if(s.compareTo("do") == 0){
                            return Word.dotok;
                        } else if(s.compareTo("else") == 0){
                            return Word.elsetok;
                        } else if(s.compareTo("while") == 0){
                            return Word.whiletok;
                        }else if(s.compareTo("begin") == 0){
                            return Word.begin;
                        }else if(s.compareTo("end") == 0){
                            return Word.end;
                        }else if(s.compareTo("print") == 0){
                            return Word.print;
                        } else if(s.compareTo("read") == 0){
                            return Word.read;
                        } else
                            return new Word(Tag.ID, s);   
                    }

                } else if (Character.isDigit(peek)) {
	            // ... gestire il caso dei numeri ... //
                    String n = "";
                    int i = 0;
                    int state = 0;

                    while (state >= 0 && Character.isDigit(peek)) {
                        
                        switch(state){
                            case 0:
                                if(peek == '0')
                                    state = 1;
                                else if(peek >= '1' && peek <= '9')
                                    state = 2;
                                else 
                                    state = -1;
                            break;

                            case 1:
                                if(peek >= '0' && peek <= '9')
                                    state = -1;
                            break;

                            case 2:
                                if(peek >= '0' && peek <= '9')
                                    state = 2;
                                else 
                                    state = -1;
                            break;
                            
                                
                        }
                        n = n + peek;
                        readch(br);
                    }
                    if(state == 1 || state == 2)
                        i=Integer.parseInt(n);
                        return new NumberTok(Tag.NUM, i); 

                    } else {
                        System.err.println("Erroneous character: " + peek );
                        return null;
                    }
        }
        return null;
    }
		
    public static void main(String[] args) {
        Lexer lex = new Lexer();
        String path = "C:/Users/HP/Desktop/ProgettoLFT/2_Lexer/path.txt"; // il percorso del file da leggere
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            Token tok;
            do {
                tok = lex.lexical_scan(br);
                System.out.println("Scan: " + tok);
            } while (tok.tag != Tag.EOF);
            br.close();
        } catch (IOException e) {e.printStackTrace();}    
    }

}