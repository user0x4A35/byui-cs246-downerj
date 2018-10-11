JAR_FILE="prove03-alt.jar"
CLASS_DIR="."

if [ ! -f "$CLASS_DIR/Main.class" ]; then ./compile.sh; fi

(jar cmf Manifest.mf $JAR_FILE $CLASS_DIR/*.class $CLASS_DIR/com) && \
    (rm -rf $CLASS_DIR/*.class)
