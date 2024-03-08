import java.io.*;
public class Parser {
    private Lexer lex;
    private BufferedReader pbr;
    private Token look;

    public Parser(Lexer l, BufferedReader br) {
        lex = l;
        pbr = br;
        move();
    }

    void move() {
        look = lex.lexical_scan(pbr);
        System.out.println("token = " + look);
    }

    void error(String s) {
	    throw new Error("near line " + lex.line + ": " + s);
    }

    void match(int t) {
        if (look.tag == t) {
            if (look.tag != Tag.EOF) move();
        } else error("syntax error");
    }

    public void start() {
        switch(look.tag) {
            case '(':
            case Tag.NUM:
                expr();
                match(Tag.EOF);
                break;
            default:
                error("error start()");
        }
    }
    private void expr() {
        switch(look.tag) {
            case '(':
            case Tag.NUM:
                term();
                exprp();
                break;
            default:
                error("error expr()");
        }
    }

    private void exprp() {
        switch (look.tag) {
            case '+':
                move();
                term();
                exprp();
                break;
            case '-':
                move();
                term();
                exprp();
                break;
            case ')':
            case Tag.EOF:
                break;
            default:
                error("error exprp()");
        }
    }

    private void term() {
        switch(look.tag) {
            case '(':
            case Tag.NUM:
                fact();
                termp();
                break;
            default:
                error("error term()");
        }
    }

    private void termp() {
        switch (look.tag) {
            case '*':
                move();
                fact();
                termp();
                break;
            case '/':
                move();
                fact();
                termp();
                break;
            case ')':
            case Tag.EOF:
            case '+':
            case '-':
                break;
            default:
                error("error termp()");
        } 
    }

    private void fact() {
        switch(look.tag) {
            case '(':
                move();
                expr();
                match(')');
                break;
            case Tag.NUM:
                match(Tag.NUM);
                break;
            default:
                error("error term()");
        }
    }
		
    public static void main(String[] args) {
        Lexer lex = new Lexer();
        String path = "C:/Users/HP/Desktop/ProgettoLFT/3_Parser/Esercizio3_1/path.txt"; // il percorso del file da leggere
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            Parser parser = new Parser(lex, br);
            parser.start();
            System.out.println("Input OK");
            br.close();
        } catch (IOException e) {e.printStackTrace();}
    }
}