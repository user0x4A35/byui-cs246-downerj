SRC_ROOT="src"
GSON_ROOT="."
CLASS_DIR="."

javac -cp "$SRC_ROOT:$GSON_ROOT" -d $CLASS_DIR $SRC_ROOT/*.java
