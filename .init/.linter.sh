#!/bin/bash
cd /home/kavia/workspace/code-generation/grocery-browse-and-order-8781/grocery_ordering_app_frontend
./gradlew lint
LINT_EXIT_CODE=$?
if [ $LINT_EXIT_CODE -ne 0 ]; then
   exit 1
fi

