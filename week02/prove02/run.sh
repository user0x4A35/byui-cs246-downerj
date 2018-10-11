#!/bin/bash

if [ ! -d "class" ] || [ ! -f "class/Game.class" ]; then bash ./compile.sh; fi

echo "Running..."
pushd class > /dev/null
java Game
popd class > /dev/null
