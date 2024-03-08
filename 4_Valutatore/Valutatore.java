import java.io.*;

public class Valutatore {
    private Lexer lex;
    private BufferedReader pbr;
    private Token look;

    public Valutatore(Lexer l, BufferedReader br) {
        lex = l;
        pbr = br;
        move();
    }

    void move() {
        // come in Esercizio 3.1
        look = lex.lexical_scan(pbr);
        System.out.println("token = " + look);
    }

    void error(String s) {
        // come in Esercizio 3.1
        throw new Error("near line " + lex.line + ": " + s);
    }

    void match(int t) {
        // come in Esercizio 3.1
        if (look.tag == t) {
	        if (look.tag != Tag.EOF) move();
	    } else error("syntax error");
    }

    public void start() {
        int expr_val;
        switch(look.tag){
            case '(':
            case Tag.NUM: {
                expr_val = expr();
                match(Tag.EOF);
                System.out.println(expr_val);
                break;
            }
            default:
                error("error start()");
        }
    }

    private int expr() {
        int term_val, exprp_val;
        switch(look.tag){
            case '(':

            case Tag.NUM: {
                term_val = term();
                exprp_val = exprp(term_val);
                break;
            }
            default:
                error("error expr()");
                return 0;
            }
            return exprp_val;
    }

    private int exprp(int exprp_i) {
        int term_val, exprp_val;
        switch (look.tag) {
        case '+':
            match('+');
            term_val = term();
            exprp_val = exprp(exprp_i + term_val);
            break;
        case '-':
            match('-');
            term_val = term();
            exprp_val = exprp(exprp_i - term_val);
            break;
        case ')':
			case Tag.EOF:	
			exprp_val = exprp_i; 	
			break;
        default:
            error("error exprp()");
            return 0;
        }
        return exprp_val;
    }

    private int term() {
        int  term_val, termp_val;
        switch(look.tag){
            case '(':
            case Tag.NUM: {
                termp_val = fact();
                term_val = termp(termp_val);
                break;
            }
            default:
                error("error term()");
                return 0;
        }
        return term_val;
    }

    private int termp(int termp_i) {
        int termp_val, fact_val;
        switch (look.tag) {
        case '*':
            match('*');
            fact_val = fact();
            termp_val = termp(termp_i * fact_val);
            break;
        case '/':
            match('/');
            fact_val = fact();
            termp_val = termp(termp_i / fact_val);
            break;
        case '+':
        case '-':
        case ')':
        case Tag.EOF:	
			termp_val = termp_i;
			break;
        default:
            error("error termp()");
            return 0;
        }
        return termp_val;
    }

    private int fact() {
        int fact_val;
        switch(look.tag){
			
		case '(':	
            match('(');
			fact_val= expr();
			match(')');
			break;
			
		case Tag.NUM:	{
						
		    fact_val = ((NumberTok)look).lexeme;
			match(Tag.NUM);
			break; 
		}
			
		default: 
            error("error fact()");
			return 0;
			
		}
		return fact_val;
    }

    public static void main(String[] args) {
        Lexer lex = new Lexer();
        String path ="C:/Users/HP/Desktop/ProgettoLFT/4_Valutatore/path.txt"; // il percorso del file da leggere
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            Valutatore valutatore = new Valutatore(lex, br);
            valutatore.start();
            br.close();
        } catch (IOException e) {e.printStackTrace();}
    }
}
