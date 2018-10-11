JAR_FILE="prove03.jar"
CLASS_DIR="."

if [ ! -f "$CLASS_DIR/Prove03.class" ]; then ./compile.sh; fi

(jar cmf Manifest.mf $JAR_FILE $CLASS_DIR/*.class $CLASS_DIR/twitter4j $CLASS_DIR/com) && \
    (rm -rf $CLASS_DIR/*.class)