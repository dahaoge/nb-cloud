#!/bin/sh
#---------------------------------------------------------------------------------------------------------
export LANG=zh_CN.UTF-8
JAVA_HOME=/usr/lib/jvm/java-1.8.0-openjdk-1.8.0.232.b09-0.el7_7.x86_64

JAR_NAME=nb-auth-1.0.0.jar
MainClass=cn.hao.nb.cloud.auth.AuthApplication
#CONFIG_FILE=./bootstrap.yml

RETVAL="0"

# See how we were called.
function start() {

    stop

    RUN_CMD="$JAVA_HOME/bin/java"
    RUN_CMD="$RUN_CMD -jar $JAR_NAME"
    RUN_CMD="$RUN_CMD -server -Xms512M -Xmx512M -XX:MaxPermSize=256M -XX:UseParallelGC"
    RUN_CMD="$RUN_CMD -Dfile.encoding=UTF-8"
#    RUN_CMD="$RUN_CMD --spring.config.location=$CONFIG_FILE"
    RUN_CMD="$RUN_CMD > /dev/null 2>&1 &"

    echo "command >> $RUN_CMD"

    eval $RUN_CMD
}

function stop() {
    pid=$(ps -ef | grep -v 'grep' | grep $JAR_NAME| awk '{printf $2 " "}')
    if [ "$pid" != "" ]; then
        echo -n "boot ( pid $pid) is running"
        echo
        echo -n $"Shutting down boot: "
        pid=$(ps -ef | grep -v 'grep' | grep $JAR_NAME | awk '{printf $2 " "}')
        if [ "$pid" != "" ]; then
            echo "kill boot process"
            kill -9 "$pid"
        fi
        else
             echo "boot is stopped"
        fi

    status
}

function status()
{
    pid=$(ps -ef | grep -v 'grep' | egrep $JAR_NAME| awk '{printf $2 " "}')
    #echo "$pid"
    if [ "$pid" != "" ]; then
        echo "query status, boot is running,pid is $pid"
    else
        echo "query status, boot is stopped"
    fi
}

function usage()
{
   echo "Usage: $0 {start|stop|restart|status}"
   RETVAL="2"
}

# See how we were called.
RETVAL="0"
case "$1" in
    start)
        start
        ;;
    stop)
        stop
        ;;
    restart)
        stop
        start
        ;;
    reload)
        RETVAL="3"
        ;;
    status)
        status
        ;;
    *)
      usage
      ;;
esac

exit $RETVAL
