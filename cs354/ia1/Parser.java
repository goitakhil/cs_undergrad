
// This class is a recursive-descent parser,
// modeled after the programming language's grammar.
// It constructs and has-a Scanner for the program
// being parsed.

//import java.util.*;

public class Parser {

	private Scanner scanner;

	/**
	 * Passes a string to the scanner as a token.
	 * @param s
	 * @throws SyntaxException
	 */
	private void match(String s) throws SyntaxException {
		scanner.match(new Token(s));
	}

	/**
	 * Wrapper for scanner's curr method.
	 * @return
	 * @throws SyntaxException
	 */
	private Token curr() throws SyntaxException {
		return scanner.curr();
	}

	/**
	 * Wrapper for scanner's pos method.
	 * @return
	 */
	private int pos() {
		return scanner.pos();
	}

	/**
	 * Converts a * or / into a NodeMulop.
	 * @return
	 * @throws SyntaxException
	 */
	private NodeMulop parseMulop() throws SyntaxException {
		if (curr().equals(new Token("*"))) {
			match("*");
			return new NodeMulop(pos(), "*");
		}
		if (curr().equals(new Token("/"))) {
			match("/");
			return new NodeMulop(pos(), "/");
		}
		return null;
	}

	/**
	 * Converts a + or - into a NodeAddop.
	 * @return
	 * @throws SyntaxException
	 */
	private NodeAddop parseAddop() throws SyntaxException {
		if (curr().equals(new Token("+"))) {
			match("+");
			return new NodeAddop(pos(), "+");
		}
		if (curr().equals(new Token("-"))) {
			match("-");
			return new NodeAddop(pos(), "-");
		}
		return null;
	}

	/**
	 * Parses a factor into a NodeFact.
	 * @return
	 * @throws SyntaxException
	 */
	private NodeFact parseFact() throws SyntaxException {
		if (curr().equals(new Token("("))) {
			match("(");
			NodeExpr expr = parseExpr();
			match(")");
			return new NodeFactExpr(expr);
		}
		if (curr().equals(new Token("id"))) {
			Token id = curr();
			match("id");
			return new NodeFactId(pos(), id.lex());
		}
		Token num = curr();
		match("num");
		return new NodeFactNum(num.lex());
	}
	
	private NodeUnary parseUnary() throws SyntaxException {
		if (curr().equals(new Token("-"))){
			match("-");
			NodeUnary unary = parseUnary();
			return new NodeUnary(unary);
		} else {
			return null;
		}
	}

	/**
	 * Parses a Term into a NodeTerm.
	 * @return
	 * @throws SyntaxException
	 */
	private NodeTerm parseTerm() throws SyntaxException {
		NodeUnary unary = parseUnary();
		NodeFact fact = parseFact();
		NodeMulop mulop = parseMulop();
		if (mulop == null)
			return new NodeTerm(unary, fact, null, null);
		NodeTerm term = parseTerm();
		term.append(new NodeTerm(unary, fact, mulop, null));
		return term;
	}

	
	/**
	 * Parses an expression into a NodeExpr.
	 * @return
	 * @throws SyntaxException
	 */
	private NodeExpr parseExpr() throws SyntaxException {
		NodeTerm term = parseTerm();
		NodeAddop addop = parseAddop();
		if (addop == null)
			return new NodeExpr(term, null, null);
		NodeExpr expr = parseExpr();
		expr.append(new NodeExpr(term, addop, null));
		return expr;
	}

	/**
	 * Parses an assignment into a NodeAssn.
	 * @return
	 * @throws SyntaxException
	 */
	private NodeAssn parseAssn() throws SyntaxException {
		Token id = curr();
		match("id");
		match("=");
		NodeExpr expr = parseExpr();
		NodeAssn assn = new NodeAssn(id.lex(), expr);
		return assn;
	}

	/**
	 * Parses a statement into a NodeStmt.
	 * @return
	 * @throws SyntaxException
	 */
	private NodeStmt parseStmt() throws SyntaxException {
		NodeAssn assn = parseAssn();
		match(";");
		NodeStmt stmt = new NodeStmt(assn);
		return stmt;
	}

	/**
	 * Starting point for parsing a program.
	 * Recurses into the other methods in this class.
	 * @param program
	 * @return
	 * @throws SyntaxException
	 */
	public Node parse(String program) throws SyntaxException {
		scanner = new Scanner(program);
		scanner.next();
		return parseStmt();
	}

}
