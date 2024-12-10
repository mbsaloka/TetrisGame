#!/bin/bash

mkdir -p bin
javac -d bin \
    src/audio/*.java \
    src/board/*.java \
    src/enums/*.java \
    src/main/*.java \
    src/mino/*.java \
    src/state/*.java \
    src/utility/*.java

cp -r ./Assets/* ./bin