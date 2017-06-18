#!/bin/bash
echo "Make Regular Rounds"
./MakeRounds.sh
echo "Make Subject PDFs"
./MakeSubjectPDFs.sh
echo "Make Ordered Rounds"
./OrderedRounds.sh
echo "Done!"
