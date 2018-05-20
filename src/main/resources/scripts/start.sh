#!/bin/sh

pid=`ps aux | grep dataAnalysis-1.0.jar | grep -v grep | awk '{print $2}'`

if [ -n "$pid" ]; then
    echo "program dataAnalysis already started with pid ${pid}"
    exit 0
fi

echo "start program dataAnalysis"
nohup java -jar tag-index-backend-1.0.jar & 1>/dev/null
pid=`ps aux | grep dataAnalysis-1.0.jar | grep -v grep | awk '{print $2}'`
if [ -n "$pid" ]; then
    echo "program dataAnalysis successfully started with pid ${pid}"
else
    echo "program dataAnalysis start failed"
fi