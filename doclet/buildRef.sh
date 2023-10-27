# 请勿执行，用于参考
#!/bin/sh

echo "[REFERENCE GENERATOR] Booting up..."

# PROCESSING_SRC_PATH=./test
PROCESSING_SRC_PATH=../../processing4/core/src
PROCESSING_LIB_PATH=../../processing4/java/libraries
PROCESSING_VIDEO_PATH=../../processing-video
PROCESSING_SOUND_PATH=../../processing-sound

DOCLET_PATH=bin/main
SOURCE_PATH=../framework/bin/main
REFERENCES_OUT_PATH=../../libgdx-processing-website/content/references/translations/en
CLASS_PATH="$PROCESSING_SRC_PATH/../library/*:$PROCESSING_LIB_PATH/serial/library/*:$PROCESSING_LIB_PATH/io/library/*:$PROCESSING_LIB_PATH/net/library/*:$PROCESSING_VIDEO_PATH/library/*:$PROCESSING_SOUND_PATH/library/*"

# GENERATE REFERENCE ENTRIES AND INDEX THROUGH JAVADOC - BY DAVID WICKS

echo "[REFERENCE GENERATOR] Source Path :: $PROCESSING_SRC_PATH"
echo "[REFERENCE GENERATOR] Library Path :: $PROCESSING_LIB_PATH"

# You can pass one argument "sound" or "video" to generate those libraries separately
# or "processing" to generate the core without the sound and video libraries
# if there is no argument it will generate everything
if [ $# -eq 0 ]
  then
    echo "No arguments supplied, generating everything"
    echo "[REFERENCE GENERATOR] Removing previous version of the ref..."
    FOLDERS="$PROCESSING_SRC_PATH/processing/core/*.java \
        	$PROCESSING_SRC_PATH/processing/awt/*.java \
    			$PROCESSING_SRC_PATH/processing/data/*.java \
    			$PROCESSING_SRC_PATH/processing/event/*.java \
    			$PROCESSING_SRC_PATH/processing/opengl/*.java \
    			$PROCESSING_LIB_PATH/io/src/processing/io/*.java \
    			$PROCESSING_LIB_PATH/net/src/processing/net/*.java \
    			$PROCESSING_LIB_PATH/serial/src/processing/serial/*.java \
    			$PROCESSING_VIDEO_PATH/src/processing/video/*.java \
    			$PROCESSING_SOUND_PATH/src/processing/sound/*.java"
  elif [ $1 = "processing" ]
  then
    echo "Generating processing references"
    echo "[REFERENCE GENERATOR] Removing previous version of the ref..."
    FOLDERS="$PROCESSING_SRC_PATH/processing/core/*.java \
          $PROCESSING_SRC_PATH/processing/data/*.java \
          $PROCESSING_SRC_PATH/processing/event/*.java \
          $PROCESSING_SRC_PATH/processing/opengl/*.java \
          $PROCESSING_LIB_PATH/io/src/processing/io/*.java \
          $PROCESSING_LIB_PATH/net/src/processing/net/*.java \
          $PROCESSING_LIB_PATH/serial/src/processing/serial/*.java"
  elif [ $1 = "video" ]
  then
  	echo "Generating video library"
  	echo "[REFERENCE GENERATOR] Removing previous version of the ref..."
  	FOLDERS="$PROCESSING_VIDEO_PATH/src/processing/video/*.java"
  elif [ $1 = "sound" ]
  then
  	echo "Generating sound library"
  	echo "[REFERENCE GENERATOR] Removing previous version of the ref..."
  	FOLDERS="$PROCESSING_SOUND_PATH/src/processing/sound/*.java"
  else
    echo "Option '$1' not valid. Should be 'processing', 'sound' or 'video'"
fi

echo "[REFERENCE GENERATOR] Generating new javadocs..."
javadoc -doclet ProcessingWeblet \
        -docletpath $DOCLET_PATH \
        --source-path $SOURCE_PATH \
        --class-path $CLASS_PATH \
        -public \
	-templatedir ../templates \
	-examplesdir ../../content/api_en \
	-includedir ../../content/api_en/include \
	-imagedir images \
	-encoding UTF-8 \
  $FOLDERS \
	-noisy
