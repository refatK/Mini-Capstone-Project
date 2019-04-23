#!/bin/sh

URL="https://0x0.st"
FILE=travisOutput.txt
SUMMARY=summary.txt

START_PAT="/> Task :k9mail:compileDebugJavaWithJavac FAILED|> Task :k9mail:testDebugUnitTest/"
END_PAT="/FAILURE: Build failed with an exception./"

# remove the summary if it already exists
rm -f summary.txt

# set up info for summary
echo "Summary of Travis Log for Test/Build Failures" >> ${SUMMARY}
echo "----------------------------------------------" >> ${SUMMARY}
echo "" >> ${SUMMARY}
echo "Branch: ${TRAVIS_BRANCH}" >> ${SUMMARY}
echo "Commit: ${TRAVIS_COMMIT} - ${TRAVIS_COMMIT_MESSAGE}" >> ${SUMMARY}
echo "Caused By: ${AUTHOR_NAME}" >> ${SUMMARY}
echo "" >> ${SUMMARY}
echo "" >> ${SUMMARY}
echo "--- FAILURE ---" >> ${SUMMARY}
echo "**************************" >> ${SUMMARY}
echo "" >> ${SUMMARY}
awk '${START_PAT}{flag=1} ${END_PAT}{flag=0} flag' ${FILE} >> ${SUMMARY}
echo "" >> ${SUMMARY}
echo "**************************" >> ${SUMMARY}

# print to an outside source to view summary
RESPONSE=$(curl -# -F "file=@${SUMMARY}" "${URL}")
echo "${RESPONSE}"
