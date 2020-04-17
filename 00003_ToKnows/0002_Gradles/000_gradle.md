Java to Know 


https://installvirtual.com/install-openjdk-8-on-mac-using-brew-adoptopenjdk/


-Dspring.profiles.active=nonsecure,local

~/.zshrc

~/.bash_profile


-Dspring.profiles.active=nonsecure, local

/usr/libexec/java_home -V 
export JAVA_HOME=`/usr/libexec/java_home -v 1.8`


export JAVA_HOME=/Library/Java/JavaVirtualMachines/adoptopenjdk-8.jdk/Contents/Home



./gradlew clean  

./gradlew BootJar 

 ./gradlew pitest  

 ./gradlew clean build 


——————————————————

cf login -a https://api.run-np.homedepot.com/      

 cf push -f development.manifest.yml -p build/libs/fast-integration-0.0.1-SNAPSHOT.jar

