JAVAC=javac

EightPuzzle.class: EightPuzzle.java EightPuzzleState.class Driver.class State.class RandomEight.class SearchFrontierStack.class
	$(JAVAC) $<

Action.class: Action.java
	$(JAVAC) $<

EightPuzzleState.class: EightPuzzleState.java State.class Action.class
	$(JAVAC) $<

State.class: State.java Action.class
	$(JAVAC) $<

RandomEight.class: RandomEight.java EightPuzzleState.class Action.class
	$(JAVAC) $<

Driver.class: Driver.java State.class SearchFrontierStorage.class
	$(JAVAC) $<

SearchFrontierStorage.class: SearchFrontierStorage.java
	$(JAVAC) $<

SearchFrontierStack.class: SearchFrontierStack.java SearchFrontierStorage.class
	$(JAVAC) $<

run: EightPuzzle.class
	java EightPuzzle

clean:
	rm -f *.class
