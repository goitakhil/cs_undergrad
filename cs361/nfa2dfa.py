#!/usr/bin/python3
# Written by Tyler Bevan
# for CS 361 Project
# 20 June, 2016

def main():
    '''
    Read in a machine and test cases from stdin and process.
    '''
    # Import sys to use stdin and my nfaLibrary
    import sys
    from nfaLibrary import State
    from nfaLibrary import getState
    from nfaLibrary import stateExists
    

    # Parse the start state.
    nfaStates = [State(sys.stdin.readline().strip(), True, False)]
    
    # Parse final states.
    finalStates = sys.stdin.readline().split(" ")
    for newID in finalStates:
        if newID == nfaStates[0].getStateID() :
            nfaStates[0].setIsFinal(True)
        else:
            nfaStates.append(State(newID.strip(), False, True))
    
    # Parse other states.
    otherStates = sys.stdin.readline().split(" ")
    for newID in otherStates:
        nfaStates.append(State(newID.strip(), False, False))
    
    # Parse transitions.
    validChars = []
    notFoundString = True
    while notFoundString:
        currentString = sys.stdin.readline().strip() 
        if currentString[0] == '-':     # Check if we have found the first test case.
            firstTestString = currentString
            notFoundString = False
        else:
            charIndex = 1
            while currentString[charIndex].isupper(): # Find where the lower case letter is.
                charIndex += 1
            if currentString[charIndex] == 'l':       # Check for the empty transition.
                getState(nfaStates, currentString[0:charIndex]).addClosure(currentString[(charIndex + 1):]) 
            else:
                getState(nfaStates, currentString[0:charIndex]).addTransition(currentString[charIndex:]) 
                if not currentString[charIndex] in validChars:
                    validChars.append(currentString[charIndex])
    validChars.sort() # Valid characters are stored in an array of strings.
    
    # TODO: Convert the NFA to a DFA
    startState = State(nfaStates[0].getClosure(), True, False) # Create start state.
    dfaStates = [startState]
    currentIndex = 0
    while currentIndex < len(dfaStates) :  # Recursive loop since we don't know how many states there will be.
        workingState = dfaStates[currentIndex]
        for nfaState in workingState.getStateID().strip().split(","): # Seperate the nfa states in the dfa state
            for char in validChars:                                   # Check all valid characters
                charTarget = ""
                for transition in getState(nfaStates, nfaState).getTransitions():
                    if transition[0] == char:
                        if len(charTarget) == 0:
                            charTarget += getState(nfaStates, transition[1:]).getClosure()
                        elif not transition[1:] in charTarget:
                            charTarget += "," + getState(nfaStates, transition[1:]).getClosure()
                if charTarget == "":  # Check to see if the state exists
                    pass
                elif not stateExists(dfaStates, charTarget): # Create a new state if needed.
                    newState = State(charTarget, False, False)
                    workingState.addTransition(char + charTarget)
                    dfaStates.append(newState)
                else:
                    workingState.addTransition(char + charTarget) # Else, add a new transition to the state.
        currentIndex += 1
                
    for aState in dfaStates: # Set finality on each state.
        for nfaState in aState.getStateID().split(","):
            if getState(nfaStates, nfaState).getIsFinal() :
                aState.setIsFinal(True)
    
    # TODO: Print the DFA to the terminal nicely.
    print(startState.getStateID().replace(",",""))
    
    finalStateString = ""
    for state in dfaStates:
        if state.getIsFinal() == True :
            finalStateString += state.getStateID() + " "
    print(finalStateString.replace(",",""))

    otherStateString = ""
    for state in dfaStates:
        if state.getIsFinal() == False :
            if state.getIsStart() == False:
                otherStateString += state.getStateID() + " "
    print(otherStateString.replace(",",""))
    
    for aState in dfaStates:
        for aTransition in aState.getTransitions():
            print(aState.getStateID().replace(",","") + aTransition.replace(",",""))


    # TODO: Test a string for acceptance against the DFA.
    inputString = firstTestString
    originalInputString = "" + inputString
    
    if inputString == "-": # Special case for empty string.
        if currentState.getIsFinal() :
            print("yes")
        else:
            print("no")
    inputString = inputString[1:]
    if inputString == "":
        pass
    else:
        currentState = startState
        halted = False
        while not halted :	# Run the string through the machine.
            halted = True
            for aTransition in currentState.getTransitions():
                if aTransition[0] == inputString[0]:
                    currentState = getState(dfaStates, aTransition[1:]) # Move to new state
                    inputString = inputString[1:]                       # Consume String
                    halted = False
                    if inputString == "":
                        halted = True
                        break

        if len(inputString) > 0:
            print("no")
        elif currentState.getIsFinal() :
            print("yes")
        else:
            print("no")
    
    try:		# We use a try block to read stdin, only catches the 
        while True:     # expected IOException thrown by readline when the input is done.
            inputString = sys.stdin.readline().strip()
            if inputString == "":
                break
            currentState = startState

            if inputString == "-" or inputString == "-l": # Special case for empty string.
                if currentState.getIsFinal() :
                    print("yes")
                else:
                    print("no")
            inputString = inputString[1:]
            if inputString == "":
                continue

            currentState = startState
            halted = False
            while not halted :	# Run the string through the machine.
                halted = True
                for aTransition in currentState.getTransitions():
                    if aTransition[0] == inputString[0]:
                        currentState = getState(dfaStates, aTransition[1:]) # Move to new state
                        inputString = inputString[1:]                       # Consume String
                        halted = False
                        if inputString == "":
                            halted = True
                            break

            if len(inputString) > 0:
                print("no")
            elif currentState.getIsFinal() :
                print("yes")
            else:
                print("no")
    except IOException:
        pass
    
    exit()
    
    
if __name__ == "__main__":  # This is the actual run of the method.
    main()
    quit()
