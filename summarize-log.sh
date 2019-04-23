#!/bin/sh

URL="https://0x0.st"
FILE=travisOutput.txt
SUMMARY=summary.txt

echo "Summarizing Travis Log for Test/Build Failures"
awk '/> Task :k9mail:compileDebugJavaWithJavac FAILED|> Task :k9mail:testDebugUnitTest/,/FAILURE: Build failed with an exception./' ${FILE} > ${SUMMARY}

RESPONSE=$(curl -# -F "file=@${SUMMARY}" "${URL}")
echo "${RESPONSE}"