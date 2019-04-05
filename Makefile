JAVA_ARGS	:=
MAIN		:= A51CipherTest

BIN			:= bin
SRC			:= src
SRC_FILE	:= sources.list

all: clean compile run

clean:
	rm -rf $(BIN)/

compile:
	@mkdir -p $(BIN)
	@find ./$(SRC) -name "*.java" > $(BIN)/$(SRC_FILE)
	javac -d ./$(BIN) @$(BIN)/$(SRC_FILE) $(JAVA_ARGS)

run:
	java -classpath ./$(BIN) $(MAIN) ${ARGS}
