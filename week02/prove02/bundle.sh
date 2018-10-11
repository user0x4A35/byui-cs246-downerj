#!/bin/bash

if [ ! -d "class" ] || [ ! -f "class/Game.class" ]; then bash ./compile.sh; fi

echo "Bundling classes..."
cp Manifest.mf class/
pushd class > /dev/null &&\
    jar -cmf Manifest.mf ../Prove02.jar *.class &&\
    rm -f Manifest.mf
    popd > /dev/null

