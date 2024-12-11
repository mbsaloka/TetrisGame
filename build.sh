#!/bin/bash

mkdir -p bin
javac -d bin \
    src/audio/*.java \
    src/board/*.java \
    src/enums/*.java \
    src/main/*.java \
    src/mino/*.java \
    src/state/*.java \
    src/ui/*.java \
    src/utility/*.java

cp -r ./assets/* ./bin
