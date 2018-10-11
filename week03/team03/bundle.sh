JAR_FILE="gsontest.jar"
CLASS_DIR="."

if [ ! -f "$CLASS_DIR/GSONTest.class" ]; then ./compile.sh; fi

(jar cmf Manifest.mf $JAR_FILE $CLASS_DIR/*.class $CLASS_DIR/com) && \
    (rm -rf $CLASS_DIR/*.class)