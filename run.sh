#!/bin/bash

./gradlew clean

./gradlew runProduction

du -sh build/production/*