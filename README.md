
cd C:\Users\ADMIN\Downloads\qlst-dienmay-simple
$env:JAVA_HOME = "C:\Program Files\Java\jdk-25.0.2"
$env:Path = "$env:JAVA_HOME\bin;$env:Path"
mvn clean package

Copy-Item ".\target\qlst.war" "$env:USERPROFILE\Downloads\apache-tomcat-9.0.123-windows-x64\apache-tomcat-9.0.123\webapps\qlst.war" -Force

Copy-Item ".\target\qlst.war" "$env:USERPROFILE\Downloads\apache-tomcat-9.0.123-windows-x64\apache-tomcat-9.0.123\webapps\qlst.war" -Force

cd "$env:USERPROFILE\Downloads\apache-tomcat-9.0.123-windows-x64\apache-tomcat-9.0.123\bin"
.\catalina.bat run





///////NẾU KO ĐƯỢC THÌ:

cd C:\Users\ADMIN\Downloads\qlst-dienmay-simple

$env:JAVA_HOME = "C:\Program Files\Java\jdk-25.0.2"
$env:Path = "$env:JAVA_HOME\bin;$env:Path"

mvn clean package
Copy-Item ".\target\qlst.war" "$env:USERPROFILE\Downloads\apache-tomcat-9.0.123-windows-x64\apache-tomcat-9.0.123\webapps\qlst.war" -Force
cd "$env:USERPROFILE\Downloads\apache-tomcat-9.0.123-windows-x64\apache-tomcat-9.0.123\bin"

$env:JAVA_HOME = "C:\Program Files\Java\jdk-25.0.2"
$env:Path = "$env:JAVA_HOME\bin;$env:Path"

.\catalina.bat run





http://localhost:8080/qlst

http://localhost:8080/qlst/register
http://localhost:8080/qlst/login

Tên đăng nhập: quanly
Mật khẩu: Quanly@123
