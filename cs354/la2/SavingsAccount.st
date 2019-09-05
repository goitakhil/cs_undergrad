

Account subclass: SavingsAccount [
    |interest|

	SavingsAccount class [
		new [
			^super new init
		]
    ]

    accrue: rate [
        (interest = nil) ifTrue: [interest := 0.0].
		balance := balance + (balance * rate).
        interest := interest + (balance * rate)
    ]

]
