@ECHO OFF
call compile
cd out\production\FlowFSM
jar cvf FlowFSM.jar *.class
cd ..\..\..