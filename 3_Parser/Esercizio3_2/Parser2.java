import java.io.*;

public class Parser2 {
    private Lexer lex;
    private BufferedReader pbr;
    private Token look;

    public Parser2(Lexer l, BufferedReader br) {
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

    public void prog(){
        switch(look.tag) {
            case Tag.ASSIGN:
            case Tag.PRINT:
            case Tag.READ:
            case Tag.WHILE:
            case Tag.COND:
            case '{':
                statlist();
                match(Tag.EOF);
                break;
            default:
                error("error prog()");
        }
    }

    private void statlist(){
        switch(look.tag) {
            case Tag.ASSIGN:
            case Tag.PRINT:
            case Tag.READ:
            case Tag.WHILE:
            case Tag.COND:
            case '{':
                stat();
                statlistp();
                break;
            default:
                error("error statlist()");
        }
    }

    private void statlistp(){
        switch(look.tag){
            case ';':
                match(';');
                stat();
                statlistp();
                break;
            case Tag.EOF:
            case '}':
                break;
            default:
                error("error statlistp()");
        }
    }

    private void stat(){
        switch(look.tag){
            case Tag.ASSIGN:
                move();
                expr();
                match(Tag.TO);
                idlist();
                break;
            case Tag.PRINT:
                move();
                match(Token.lpq.tag);
                exprlist();
                match(Token.rpq.tag);
                break;
            case Tag.READ:
                move();
                match(Token.lpq.tag);
                idlist();
                match(Token.rpq.tag);
                break;
            case Tag.WHILE:
                move();
                match(Token.lpt.tag);
                bexpr();
                match(Token.rpt.tag);
                stat();
                break;
            case Tag.COND:
                move();
                match(Token.lpq.tag);
                optlist();
                match(Token.rpq.tag);
        
                if(look.tag == Tag.END)
                    match(Tag.END);
                else if(look.tag == Tag.ELSE){
                    match(Tag.ELSE);
                    stat();
                    match(Tag.END);
                }
                break;
            case '{':
                move();
                statlist();
                match(Token.rpg.tag);
                break;
            default: 
                error("error stat()");
				break;
        }
    }

    private void idlist(){
        switch(look.tag){
            case Tag.ID:
                match(Tag.ID);
                idlistp();
                break;
            default:
                error("error idlist()");
        }
    }

    private void idlistp(){
        switch(look.tag){
            case ',':
                match(',');
                match(Tag.ID);
                idlistp();
                break;
            case ']':
            case '}':
            case Tag.OPTION:
            case Tag.END:
            case Tag.EOF:
            case ';':
                break;
            default:
                error("error idlistp()");
        }
    }

    private void optlist(){
        switch(look.tag){
            case Tag.OPTION:
                optitem();
                optlistp();
                break;
            default:
                error("error optlist()");
        }
    }

    private void optlistp(){
        switch(look.tag){
            case Tag.OPTION:
                optitem();
                optlistp();
                break;
            case ']':
                break;
            default:
                error("error optlistp()");
        }
    }

    private void optitem(){
        switch(look.tag){
            case Tag.OPTION:
                match(Tag.OPTION);
                match('(');
                bexpr();
                match(')');
                match(Tag.DO);
                stat();
                break;
            default:
                error("error optitem()");
        }
    }

    private void bexpr(){
        switch(look.tag){
            case Tag.RELOP:
                match(Tag.RELOP);
                expr();
                expr();
                break;
            default:
                error("error bexpr()");
        }
    }

    private void expr(){
        switch(look.tag){
            case '+':
                move();
                match(Token.lpt.tag);
                exprlist();
                match(Token.rpt.tag);
                break;
            case '*':
                move();
                match(Token.lpt.tag);
                exprlist();
                match(Token.rpt.tag);
                break;
            case '-':
                move();
                expr();
                expr();
                break;
            case '/':
                move();
                expr();
                expr();
                break;
            case Tag.NUM:
                match(Tag.NUM);
                break;
            case Tag.ID:
                match(Tag.ID);
                break;
            default: 
                error("error expr()");
        }
    }

    private void exprlist(){
        switch(look.tag){
            case '+':
            case '-':
            case '*':
            case '/':
            case Tag.NUM:
            case Tag.ID:
                expr();
                exprlistp();
                break;
            default:
                error("error exprlist()");
        }
    }

    private void exprlistp(){
        switch(look.tag){
            case ',':
                match(',');
                expr();
                exprlistp();
                break;
            case ']':
            case ')':
                break;
            default:
                error("error exprlistp()");
        }
    }


    public static void main(String[] args) {
        Lexer lex = new Lexer();
        String path = "C:/Users/HP/Desktop/ProgettoLFT/3_Parser/Esercizio3_2/path.txt"; // il percorso del file da leggere
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            Parser2 parser = new Parser2(lex, br);
            parser.prog();
            System.out.println("Input OK");
            br.close();
        } catch (IOException e) {e.printStackTrace();}
    }
}