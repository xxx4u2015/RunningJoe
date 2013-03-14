cd /d %~dp0
"%JAVA_HOME%bin\wsimport" -keep -d parsedwsdl http://grimfor.no-ip.org/runningjoe/web/ws/UserApi?wsdl
pause