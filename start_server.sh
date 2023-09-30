#!/bin/bash
# 기존에 실행 중인 Java 프로세스 종료
pkill -f 'backend-0.0.1-SNAPSHOT.jar'

# 새로운 JAR 파일로 Java 애플리케이션 실행
TZ=Asia/Seoul nohup java -jar ~/backend-0.0.1-SNAPSHOT.jar > ~/backend.log 2>&1 &

