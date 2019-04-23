#!/bin/bash

FILE=travisOutput.txt

echo "Summarizing Travis Log for Test Failures"
awk 'FNR <= 50' ${FILE} | curl -sT - chunk.io
echo "Upload to chunk.io complete"