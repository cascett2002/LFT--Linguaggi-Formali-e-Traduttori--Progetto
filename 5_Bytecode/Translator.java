import java.io.*;

public class Translator {
    private Lexer lex;
    private BufferedReader pbr;
    private Token look;
    
    SymbolTable st = new SymbolTable();
    CodeGenerator code = new CodeGenerator();
    int count=0;

    public Translator(Lexer l, BufferedReader br) {
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

    public void prog() {
        switch (look.tag) {
            case Tag.ASSIGN:
            case Tag.PRINT:
            case Tag.READ:
            case Tag.WHILE:
            case Tag.COND:
            case '{':
            statlist();
            match(Tag.EOF);
            try {
                code.toJasmin();
            }
            catch(java.io.IOException e) {
                System.out.println("IO error\n");
            }
        }
    }

    private void statlist(){
        switch (look.tag) {
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
        switch (look.tag){
            case ';':
                match(';');
                stat();
                statlistp();
                break;
            case '}':
            case Tag.EOF:
                break;
            default:
                error("error statlistp()");
        } 
    }

    public void stat() {
        switch (look.tag) {
            // ... completare ...
            case Tag.ASSIGN:
                match(Tag.ASSIGN);
                expr();
                match(Tag.TO);
                idlist(OpCode.dup, -1);
                break;

            case Tag.PRINT:
                match(Tag.PRINT);
                match('[');
                exprlist(OpCode.invokestatic, 1);
                match(']');
                break;

            case Tag.READ:
                match(Tag.READ);
                match('[');
                idlist(OpCode.invokestatic, 0);
                match(']');
                break;

            case Tag.WHILE:
                int lwhile = code.newLabel();
                int lendwhile = code.newLabel();
                match(Tag.WHILE);
                match('(');
                code.emitLabel(lwhile);
                bexpr(lendwhile);
                match(')');
                stat();
                code.emit(OpCode.GOto, lwhile);
                code.emitLabel(lendwhile);
                break;

            case Tag.COND:
                int lnext = code.newLabel();
                match(Tag.COND);
                match('[');
                optlist(lnext);
                match(']');
                if (look.tag == Tag.ELSE) {
                    match(Tag.ELSE);
                    stat();
                }
                match(Tag.END);
                code.emitLabel(lnext);
                break;

            case '{':
                match('{');
                statlist();
                match('}');
                break;
            default:
                error("error stat()");
        }
    }
    
    private void idlist(OpCode opcode, int pm) {
        switch(look.tag) {
            case Tag.ID:
                int id_addr = st.lookupAddress(((Word) look).lexeme);
                if (id_addr == -1) {
                    id_addr = count;
                    st.insert(((Word) look).lexeme, count++);
                }
                match(Tag.ID);
                    idlistp(opcode, pm);
                    if(opcode == OpCode.invokestatic)
                        code.emit(OpCode.invokestatic,0);
                    code.emit(OpCode.istore, id_addr);
                    break;
            default:
                error("error idlist()");

        }
    }

    private void idlistp(OpCode opcode, int pm) {
        switch(look.tag) {
            case ',':
                match(',');
                int id_addr = st.lookupAddress(((Word) look).lexeme);
                if (id_addr == -1) {
                    id_addr = count;
                    st.insert(((Word) look).lexeme, count++);
                }
                match(Tag.ID);
                if (opcode == OpCode.dup)
                    code.emit(OpCode.dup, pm);
                idlistp(opcode, pm);
                if (opcode == OpCode.invokestatic)
                    code.emit(OpCode.invokestatic, 0);
                code.emit(OpCode.istore, id_addr);
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

    private void optlist(int lelse){
        switch(look.tag){
            case Tag.OPTION:
                int next_optitem=code.newLabel();
                optitem(next_optitem, lelse);
                code.emitLabel(next_optitem);
                optlistp(lelse);
                break;
            default:
                error("error optlist()");
        }
    }

    private void optlistp(int lelse){
        switch(look.tag){
            case Tag.OPTION:
                int next_optitem=code.newLabel();
                optitem(next_optitem, lelse);
                code.emitLabel(next_optitem);
                optlistp(lelse);
                break;
            case ']':
                break;
            default:
                error("error optlistp()");
        }
    }

    private void optitem(int lnext, int lelse){
        switch(look.tag){
            case Tag.OPTION:
                match(Tag.OPTION);
                match('(');
                bexpr(lnext);
                match(')');
                match(Tag.DO);
                stat();
                code.emit(OpCode.GOto, lelse);
                break;
            default:
                error("error optitem()");
        }
    }

    private void bexpr(int lfalse){
            if (look == Word.eq) {
                match(Tag.RELOP);
                expr();
                expr();
                code.emit(OpCode.if_icmpne, lfalse);

            }else if (look == Word.ne) {
                match(Tag.RELOP);
                expr();
                expr();
                code.emit(OpCode.if_icmpeq, lfalse);

            }else if (look == Word.ge) {
                match(Tag.RELOP);
                expr();
                expr();
                code.emit(OpCode.if_icmplt, lfalse);

            }else if (look == Word.le) {
                match(Tag.RELOP);
                expr();
                expr();
                code.emit(OpCode.if_icmpgt, lfalse);

            }else if (look == Word.gt) {
                match(Tag.RELOP);
                expr();
                expr();
                code.emit(OpCode.if_icmple, lfalse);

            }else if (look == Word.lt) {
                match(Tag.RELOP);
                expr();
                expr();
                code.emit(OpCode.if_icmpge, lfalse);

            }else error("error bexpr()");
        }

    private void expr() {
        switch(look.tag) {
            case '+':
                match('+');
                match('(');
                exprlist(OpCode.iadd, -1);
                match(')');
                break;
            case '-':
                match('-');
                expr();
                expr();
                code.emit(OpCode.isub);
                break;
            case '*':
                match('*');
                match('(');
                exprlist(OpCode.imul, -1);
                match(')');
                break;
            case '/':
                match('/');
                expr();
                expr();
                code.emit(OpCode.idiv);
                break;

            case Tag.NUM:
                int val = ((NumberTok)look).lexeme;
                match(Tag.NUM);
                code.emit(OpCode.ldc, val);
                break;

            case Tag.ID:
                int id_addr = st.lookupAddress(((Word) look).lexeme);
                if (id_addr == -1) {
                   error("errore: ID expr");
                }
                code.emit(OpCode.iload, id_addr);
                match(Tag.ID);
                break;
            default:
                error("error expr()");
        }
    }

    private void exprlist(OpCode opcode, int pm){
        switch(look.tag){
            case '+':
            case '-':
            case '*':
            case '/':
            case Tag.NUM:
            case Tag.ID:
            expr();
            if (opcode == OpCode.invokestatic)
                code.emit(opcode, pm);
            exprlistp(opcode, pm);
            break;
        default:
            error("error exprlist()");
        }
    }

    private void exprlistp(OpCode opcode, int pm){
        switch(look.tag){
            case ',':
                match(',');
                expr();
                code.emit(opcode, pm);
                exprlistp(opcode, pm);
                break;
            case ']':
            case ')':
                break;
            default:
                error("error exprlistp()");
        }
    }

    // command to execute jasmin: java -jar jasmin.jar Output.j
    public static void main(String[] args) {
        Lexer lex = new Lexer();
        String path = "C:/Users/HP/Desktop/ProgettoLFT/5_Bytecode/path.lft"; // il percorso del file da leggere
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            Translator tr = new Translator(lex, br);
            tr.prog();
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}