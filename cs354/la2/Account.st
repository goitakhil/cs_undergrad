"Account abstract class."

Object subclass: Account [
    |number customer balance|

    Account class [
        new: n customer: c balance: b [
            ^super new init: n customer: c balance: b
        ]
    ]

    init: n customer: c balance:b [
		customer := c.
		number := n.
        balance := b
    ]

    accrue: rate [
        ^self subclassResponsibility
    ]

    balance [
        ^balance
    ]

    deposit: amount [
        balance := balance + amount
    ]

    withdraw: amount [
        balance := balance - amount
    ]

    toString [
        |string|
        string := number,':',(customer toString),':',(balance asString).
        ^string
    ]

]
