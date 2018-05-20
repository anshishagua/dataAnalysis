#!/bin/sh

pid=`ps aux | grep dataAnalysis-1.0.jar | grep -v grep | awk '{print $2}'`

if [ -z "$pid" ]; then
 echo "program dataAnalysis not running"
 exit 0
fi

echo "stop program dataAnalysis with pid  ${pid}"
kill -15 $pid
echo "done"