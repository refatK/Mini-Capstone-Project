#!/bin/sh

URL="https://0x0.st"
FILE=travisOutput.txt
SUMMARY=summary.txt

echo "Summarizing Travis Log for Test Failures"
awk 'FNR <= 50 >"summary.txt"' ${FILE} | curl -sT - chunk.io

cat ${SUMMARY}
RESPONSE=$(curl -# -F "file=@${SUMMARY}" "${URL}")
echo "${RESPONSE}"