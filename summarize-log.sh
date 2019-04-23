#!/bin/sh

URL="https://0x0.st"
FILE=travisOutput.txt
SUMMARY=summary.txt

# remove the summary if it already exists
rm -f summary.txt

# set up info for summary
echo "Summarizing Travis Log for Test/Build Failures" >> ${SUMMARY}
echo "----------------------------------------------" >> ${SUMMARY}
echo "" >> ${SUMMARY}
echo "Branch: ${TRAVIS_BRANCH}" >> ${SUMMARY}
echo "Commit: ${TRAVIS_COMMIT} - ${TRAVIS_COMMIT_MESSAGE}" >> ${SUMMARY}
echo "" >> ${SUMMARY}
awk '/> Task :k9mail:compileDebugJavaWithJavac FAILED|> Task :k9mail:testDebugUnitTest/,/FAILURE: Build failed with an exception./' ${FILE} >> ${SUMMARY}

# print to an outside source to view summary
RESPONSE=$(curl -# -F "file=@${SUMMARY}" "${URL}")
echo "${RESPONSE}"