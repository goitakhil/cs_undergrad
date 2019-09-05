
Object subclass: Bank [
 |accounts|
 
 Bank class [
	new [
		^super new init
	]
 ]
 
 init [
	accounts := Set new
 ]
 
 
 add: account [
	accounts add: account
 ]
 
 accrue: rate [
	accounts do: [ :a | a accrue: rate ]
 ]
 
 toString [
	|str|
    str := ''.
	accounts do: [ :a |  (str := str,(a toString),((Character nl) asString)) ].
    ^str
 ]
 
]



|bank cust checking savings|
bank := Bank new.
cust := Customer new: 'Ann'.
checking := CheckingAccount new: '01001' customer: cust balance: 100.00 .
bank add: checking.
savings := SavingsAccount new: '01002' customer: cust balance: 100.00 .
bank add: savings.
bank accrue: 0.02 .
(bank toString) printNl.
