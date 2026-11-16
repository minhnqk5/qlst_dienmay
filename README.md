$project = "C:\Users\ADMIN\Downloads\qlst-dienmay-simple\qlst-dienmay"
$tomcat = "$env:USERPROFILE\Downloads\apache-tomcat-9.0.123-windows-x64\apache-tomcat-9.0.123"

$env:JAVA_HOME = "C:\Program Files\Java\jdk-25.0.2"
$env:Path = "$env:JAVA_HOME\bin;$env:Path"

cd $project
mvn clean package
if ($LASTEXITCODE -ne 0) {
    throw "Build thất bại; chưa deploy."
}

Remove-Item "$tomcat\webapps\qlst" -Recurse -Force -ErrorAction SilentlyContinue
Remove-Item "$tomcat\webapps\qlst.war" -Force -ErrorAction SilentlyContinue
Remove-Item "$tomcat\work\Catalina\localhost\qlst" -Recurse -Force -ErrorAction SilentlyContinue

Copy-Item "$project\target\qlst.war" "$tomcat\webapps\qlst.war" -Force

cd "$tomcat\bin"
.\catalina.bat run