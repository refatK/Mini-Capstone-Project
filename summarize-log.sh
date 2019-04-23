#!/bin/sh

# defined variables
URL="https://0x0.st"
FILE=travisOutput.txt
SUMMARY=summary.txt

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
awk '/> Task :k9mail:compileDebugJavaWithJavac FAILED|> Task :k9mail:testDebugUnitTest/{flag=1} /FAILURE: Build failed with an exception./{flag=0} flag' ${FILE} >> ${SUMMARY}
echo "" >> ${SUMMARY}
echo "**************************" >> ${SUMMARY}

# print to an outside source to view summary
RESPONSE=$(curl -# -F "file=@${SUMMARY}" "${URL}")
echo "${RESPONSE}"

cd ${TRAVIS_BUILD_DIR}
ls
