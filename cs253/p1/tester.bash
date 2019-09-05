#!/bin/bash
# This script analyzes a file to check the
# accuracy of a text statistics program.
# This script relys on the unix utility "wc".
FILE=$1
OUTFILE="./expected.txt"
echo "words: `cat $FILE | wc -w`" > $OUTFILE
echo "chars: `cat $FILE | wc -c`" >> $OUTFILE
echo "lines: `cat $FILE | wc -l`" >> $OUTFILE
echo "digit 0: `grep -o 0 $FILE | wc -l`" >> $OUTFILE
echo "digit 1: `grep -o 1 $FILE | wc -l`" >> $OUTFILE
echo "digit 2: `grep -o 2 $FILE | wc -l`" >> $OUTFILE
echo "digit 3: `grep -o 3 $FILE | wc -l`" >> $OUTFILE
echo "digit 4: `grep -o 4 $FILE | wc -l`" >> $OUTFILE
echo "digit 5: `grep -o 5 $FILE | wc -l`" >> $OUTFILE
echo "digit 6: `grep -o 6 $FILE | wc -l`" >> $OUTFILE
echo "digit 7: `grep -o 7 $FILE | wc -l`" >> $OUTFILE
echo "digit 8: `grep -o 8 $FILE | wc -l`" >> $OUTFILE
echo "digit 9: `grep -o 9 $FILE | wc -l`" >> $OUTFILE

make --quiet

cat $FILE | ./homework > ./actual.txt
diff actual.txt expected.txt > /dev/null
if [ $? -eq 0 ]; then
    echo "PASS"
else
    echo "FAIL"
fi
rm actual.txt expected.txt
make clean --quiet
exit
