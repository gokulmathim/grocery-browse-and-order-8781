.PHONY: build assemble check test clean

build:
\t./gradlew :grocery_ordering_app_frontend:app:build

assemble:
\t./gradlew :grocery_ordering_app_frontend:app:assembleDebug

check:
\t./gradlew :grocery_ordering_app_frontend:app:check

test:
\t./gradlew :grocery_ordering_app_frontend:app:test

clean:
\t./gradlew :grocery_ordering_app_frontend:app:clean
