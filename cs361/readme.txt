#############################################################################
#                                                                           #
#                      NFA to DFA Conversion Project                        #
#                                                                           #
#                               CS 361                                      #
#                                                                           #
#                            Tyler Bevan                                    #
#                                                                           #
#############################################################################

The program files are:
	
	nfa2dfa.py	Main Class
	nfaLibrary.py	Library Class I wrote for state operations

To run this program:

	python3 nfa2dfa.py < input.file


Two test files are included with this program:

test.dat
	Contents:
		S
		F
		A B C
		SlF
		SaA
		AaA
		AaB
		BbB
		BbC
		CcC
		ClF
		-abc
		-aabbcc
		-aaaaa
		-acb
		-aaabbc

	Expected Output:
		SF
		SF BCF CF 
		A AB 
		SFaA
		AaAB
		ABaAB
		ABbBCF
		BCFbBCF
		BCFcCF
		CFcCF
		no
		yes
		no
		no
		yes



test2.dat
	Contents:
		S
		Z X
		A Y
		ZbZ
		ZcY
		ZcX
		XbA
		XaY
		YcA
		YaS
		SaA
		AcA
		SaZ
		AlZ
		SaS
		-a
		-ac
		-aaccbb
		-ba
		-abccca

	Expected Output:
		S
		AZS AZ Z YX 
		Y 
		SaAZS
		AZScAZ
		AZSbZ
		AZScYX
		AZSaAZS
		AZcAZ
		AZbZ
		AZcYX
		ZbZ
		ZcYX
		YXaS
		YXcAZ
		YXaY
		YXbAZ
		YaS
		YcAZ
		yes
		yes
		yes
		no
		no

