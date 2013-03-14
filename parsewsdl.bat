cd /d %~dp0
"%JAVA_HOME%bin\wsimport" -keep -d parsedwsdl http://grimfor.alwaysdata.net/RunningJoeServer/web/ws/UserApi?wsdl
pause