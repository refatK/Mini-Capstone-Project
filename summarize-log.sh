#!/bin/sh

URL="https://0x0.st"
FILE=travisOutput.txt
SUMMARY=summary.txt

echo "Summarizing Travis Log for Test Failures"
head -50 ${FILE} > ${SUMMARY}

cat ${SUMMARY}
RESPONSE=$(curl -# -F "file=@${SUMMARY}" "${URL}")
echo "${RESPONSE}"