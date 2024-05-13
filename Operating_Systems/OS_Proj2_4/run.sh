#!/bin/bash

runs=50000

for ((i=1; i<runs; i++ ))
do
    echo "Run number: $i"
    ./main
done