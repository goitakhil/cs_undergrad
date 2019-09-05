
all: Interpreter.class

Interpreter.class: Parser.class Environment.class SyntaxException.class EvalException.class Interpreter.java
	javac Interpreter.java

Parser.class: Token.class Scanner.class NodeMulop.class NodeAddop.class NodeFact.class NodeExpr.class NodeUnary.class NodeTerm.class NodeAssn.class NodeStmt.class Node.class NodeFactExpr.class NodeFactId.class NodeFactNum.class Parser.java
	javac Parser.java

Token.class: Token.java
	javac Token.java

Node.class: Node.java
	javac Node.java

NodeStmt.class: Node.class NodeAssn.class NodeStmt.java
	javac NodeStmt.java

NodeAddop.class: Node.class EvalException.class NodeAddop.java
	javac NodeAddop.java

NodeMulop.class: Node.class EvalException.class NodeMulop.java
	javac NodeMulop.java

NodeAssn.class: Node.class NodeExpr.class NodeAssn.java
	javac NodeAssn.java

NodeExpr.class: Node.class NodeTerm.class NodeAddop.class NodeExpr.java
	javac NodeExpr.java

NodeTerm.class: Node.class NodeUnary.class NodeFact.class NodeMulop.class NodeTerm.java
	javac NodeTerm.java

NodeUnary.class: Node.class NodeUnary.java
	javac NodeUnary.java

NodeFact.class: Node.class NodeFact.java
	javac NodeFact.java

NodeFactExpr.class: NodeFact.class NodeFactExpr.java
	javac NodeFactExpr.java

NodeFactId.class: NodeFact.class NodeFactId.java
	javac NodeFactId.java

NodeFactNum.class: NodeFact.class NodeFactNum.java
	javac NodeFactNum.java

Scanner.class: Token.class SyntaxException.class Scanner.java
	javac Scanner.java

Environment.class: Environment.java
	javac Environment.java

EvalException.class: EvalException.java
	javac EvalException.java

SyntaxException.class: SyntaxException.java
	javac SyntaxException.java

default: all

clean:
	rm *.class
