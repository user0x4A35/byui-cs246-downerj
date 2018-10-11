#!/bin/bash

if [ ! -d "class" ]; then mkdir "class"; fi

echo "Compiling..."
javac -d class -cp src src/*.java
