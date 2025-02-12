#!/bin/bash

DEPLOY_DIR=~/App
echo -e "deploy path $DEPLOY_DIR"
BIN_DIR=$DEPLOY_DIR/bin
echo -e "bin path $BIN_DIR"
CONF_DIR=$DEPLOY_DIR/config
echo -e "config path $CONF_DIR"

#jvm 参数
JAVA_OPTS="-Xms4096m -Xmx4096m -XX:ParallelGCThreads=4 -XX:+HeapDumpOnOutOfMemoryError"

# jar包存放路径
JAR_PATH=$DEPLOY_DIR/lib
# jar包名称
JAR_NAME=efficacy-1.0-SNAPSHOT.jar
# 标准日志文件
STD_LOG=/dev/null

SERVER_NAME="shorturl"
SERVER_PORT="8080"

echo -e "Starting $SERVER_NAME ..."
nohup java $JAVA_OPTS -Xloggc:$DEPLOY_DIR/gclog.log -jar $JAR_PATH/$JAR_NAME >$STD_LOG 2>&1 &
echo -e "PORT1:$SERVER_PORT \r\c"

TIMEOUT=25
while [ $TIMEOUT -gt 1 ]; do
    echo -e "$TIMEOUT...\r\c"
    sleep 1
    PORT_EXIST=`netstat -antl | grep ":$SERVER_PORT" `
    if [ -n "$PORT_EXIST" ]; then
        PIDS=`jps | grep "$JAR_NAME" | awk '{print $1}'`
        echo -e "PID $PIDS"
        break
    fi
    ((TIMEOUT--))
done
echo "OK!"