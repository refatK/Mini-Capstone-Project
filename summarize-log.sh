#!/bin/sh

URL="https://0x0.st"
FILE=travisOutput.txt
SUMMARY=summary.txt

echo "Summarizing Travis Log for Test Failures"
awk '/> Task :k9mail:compileDebugJavaWithJavac FAILED/,/FAILURE: Build failed with an exception./' file > ${SUMMARY}

RESPONSE=$(curl -# -F "file=@${SUMMARY}" "${URL}")
echo "${RESPONSE}"