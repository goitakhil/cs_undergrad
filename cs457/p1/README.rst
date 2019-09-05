==============================================================================
P1 - Checkers
==============================================================================
Author: Tyler Bevan
Introduction to Artificial Intelligence
CS 457

Instructions
------------------------------------------------------------------------------
To compile run::
        make

This program runs as a player in the provided checkers program. ex::
        checkers random computer 3


Project Reflection
------------------------------------------------------------------------------
This project has been challenging in a number of ways. The first is that this 
is ancient C code that I’m not very fond of. However, once I understood how 
the provided macros worked I was able to make sense of the provided code. My 
player utilizes an Alpha-Beta pruned minimax tree with a material advantage 
ratio heuristic and iterative deepening. It plays fairly well on my computer,
reaching a depth of 7-10 in a 3 second game. I worked on this project solo, 
and spent 12-15 hours working on it. Most of that time was debugging, as 
designing the search tree itself took only 1-2 hours.

When I started the project I initially passed around pointers to the State
struct, however it turned out that by doing a memcpy on the entire State I was
introducing errors in the move list. I transitioned to passing the board
pointer only and generating the rest of the state locally. This solved a
couple problems, but my player still consistently played terribly.

Originally I was using a single function for both the min and max nodes of the
tree, with an enum to signal which mode it was in. In the process of debugging
I began to doubt the reliability of this system, so I replaced it with 
two separate functions.

This helped with the reliability of the player, but it still made lots of 
errors. I suspected that my application was generating the wrong moves at 
different levels. I added code to always generate scores based on my player’s
perspective, so that I wouldn’t optimize for my opponent. This also helped, 
but the player sometimes made moves that didn’t make sense. I drew the tree out
to understand where I was going wrong, and realized that I never swapped the 
player to generate moves for. My program assumed that the opponent never got a
turn. I added code to set the player based on whether I’m in a max or min node
and that fixed the problem.

I had issues with timing my turns at first. I verified that the clock code was
setting a clock point at the beginning of the turn and comparing it correctly
later on. I suspected that the clocks per second variable was incorrect. After
some consideration, I realized that my laptop uses a dynamic clock speed. This
could have been the reason I had problems. I simplified the timing code a bit
to avoid using the time struct, as I can cast a clock_t directly to a float.
My testing has not resulted in timeout problems on any systems with a static
clock speed.

In an effort to optimize the program, I used the powerful 'perf' tool. Using
it I identified that the most costly operations in my program were the score
function and the loop to generate all legal moves. Both of these functions
looped over all x and y on the board, a total of 64 iterations. However,
there are only 32 valid locations on the board. Because this is a
sufficiently small number, I created an array of all valid locations and
iterated over that list instead of using two for loops. This effectively
doubled the speed of the program, which buys me most of an extra iteration.

I also found that the function to convert a square id number to a x and y
coordinates was expensive, as it loops over the same 64 steps. I realized
that the output of this function could be pulled from the same lookup table
I used before. I was able to reduce this function from O(n^2) to O(1). This
function is used in several places, so this improvement helped greatly.

I would have liked to run this application using multiple threads to generate
the tree, as each branch can be generated independently by sacrificing some
pruning. This should have been a simple operation using openMP to run the for
loops in parallel. However, because this program uses pointers and public 
variables in many places, I had lots of mutual exclusion violations that 
resulted in incorrect choices. If I redesigned the entire program from scratch
I would design it to pass all variables by value, which would greatly simplify
the usage of muliple threads.

The heuristic I used is based on material advantage, with additional points
given to kings and edge locations. Edge locations are invulnerable to capture.
The score ranges from 0 to ~720000. A score even close to that high is
improbable in a normal game. To allow the player to make better choices in
the end of the game, any case that results in a win is worth 888888 points, 
and any loss is worth 0. This forces a victory to always be the most weighted
and a loss to be the least. 

I would have preferred to keep a running total of the score, which would 
nearly double the speed of my program. However, I did not have time to 
implement this before the assignment was due. Another improvement would be to 
use a hash table to store previous scores and avoid calculating them again.
This would be much more memory expensive than simply keeping the score up to
date.

In the end, my program seems to play fairly well. I tested it around 50 times
against the random player without a loss, and against the depth-5 player
around 20 times at 3 seconds. If I could further optimize the program I would
get another level of depth in the calculation. I tested my changes to the XY
function with a unit test, and verified that it is functionally identical to
the original version. Beyond those tests, all other errors would show up in 
normal operation of the program. If it can reliably beat the depth 5 player,
it is likely functionally correct, even if it isn't optimal or particularly 
accurate.

I am fairly pleased with this project. If I had much more time to dedicate to
this project, I would completely rewrite to program to facilitate parallel
processing of the tree. However, I am happy with how much I was able to
improve the existing code.

Tyler


