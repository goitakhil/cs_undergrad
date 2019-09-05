#!/bin/bash

#Store an exit code for later.
EXITCODE=0

#Make the input program and library, export library path.
make

export LD_LIBRARY_PATH="${LD_LIBRARY_PATH}:../p3/lib"

#Make an output folder unless a file named output exists.
echo "Creating output folder"
if [ -d output ]; then
    rm -rf output;
    mkdir output;
else
    mkdir output;
fi

if [ $? -gt 0 ]; then
    echo "Error creating output file!";
    EXITCODE=1
    exit "$EXITCODE";
fi

#Process all the files.
for i in $(ls -Sr input/); do
    echo "Processing file:    input/$i"
    /usr/bin/time -f "std-list             %e" -- ./wf --std-list "input/$i" | sort > output/"$i"-std-list-results ;
    /usr/bin/time -f "self-organized-list  %e" -- ./wf --self-organized-list "input/$i" | sort > output/"$i"-self-organized-list-results ;
    diff "output/${i}-std-list-results" "output/${i}-self-organized-list-results" ;
    if [ $? -eq 0 ]; then
	echo "OK: std-list and self-organized-list match"
	echo ""
    else
	echo "FAIL: std-list and self-organized-list do not match"
	echo ""
	EXITCODE=1
    fi
done

#ALL DONE
echo "DONE!"
exit "$EXITCODE"

