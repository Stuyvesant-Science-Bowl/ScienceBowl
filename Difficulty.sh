#!/bin/bash
javac -cp .:jar/opencsv-3.7.jar:jar/itextpdf-5.5.9.jar OrderedRounds3.java
java -cp .:jar/opencsv-3.7.jar:jar/itextpdf-5.5.9.jar OrderedRounds3 
rm OrderedRounds3.class
