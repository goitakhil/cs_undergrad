#!/usr/bin/python3
# Written by Tyler Bevan
# for CS 361 Project
# 20 June, 2016

class State:
    '''
    Represents a state in a finite automaton.
    '''
    def __init__(self, stateId: str, isStart: bool, isFinal: bool):
        '''
        Constructor
        '''
        self.__stateID = stateId
        self.__isStart = isStart
        self.__isFinal = isFinal
        self.__transitions = []
        self.__closure = stateId
        
    def getStateID(self) -> str:
        '''
        Return the string StateID of the State.
        '''
        return(self.__stateID)
    
    def getIsStart(self) -> bool:
        '''
        Return true if the state is a start state.
        '''
        return(self.__isStart)
    
    def getIsFinal(self) -> bool:
        '''
        Return true if the state is final.
        '''
        return(self.__isFinal)
    
    def setIsFinal(self, isFinal: bool):
        '''
        Sets if the state is final.
        '''
        self.__isFinal = isFinal
    
    def addTransition(self, newTransition: str):
        '''
        Store a new transition.
        '''
        self.__transitions.append(newTransition)
        
    def getTransitions(self):
        '''
        Return the array of transitions.
        '''
        return self.__transitions
    
    def addClosure(self, newClosure: str):
        '''
        Add an empty transition.
        '''
        self.__closure += "," + newClosure
    
    def getClosure(self):
        '''
        Return the closure
        '''
        return self.__closure
       
def getState(states: list, stateID: str) -> State:
    '''
    Return the State object of a string stateID.
    '''
    for aState in states:
        if aState.getStateID() == stateID:
            return aState

def stateExists(states: list, stateID: str) -> bool:
    '''
    Return if a state is in the list.
    '''
    for aState in states:
        if aState.getStateID().strip() == stateID.strip():
            return True
    return False
    
