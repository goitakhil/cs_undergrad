# Matrix - Vector Multiplication Testing Plan
Tyler Bevan
## Edge Cases
These cases should all return some meaningful error response
 * invalid input file names
 * unwritable output file locations
 * mismatched row or column lengths
```
1
2
1 2 3 4 5
1 2 3 4
1 2 3 4 5
```
 * incorrect vector length
```
2
2
1 1
1 1
1 2 3
```
 * non-numeric inputs
```
This
That
Not A Number
```
## Normal Cases
Each case is followed by the expected output
 * Zero input
```
2
2
0 0
0 0
0 0
``` 
```
0 0
```
 * Two Vectors
```
3
1
1 2 3
1 2 3
```
```
14
```
 * Square matrix
```
3
3
1 2 3
3 2 1
1 2 3
2 2 2
```
```
12 12 12
```
## Other Considerations
This program uses doubles as its data type, so there is a risk of over or 
underflow. This should be tested on both ends for a meaningful error.
