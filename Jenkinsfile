node {
   stage('Test') {
        bat "./gradlew.bat clean"
        bat "./gradlew.bat test "
   }
   stage('SonarQube Analysis') {
            withSonarQubeEnv() {
                bat "./gradlew.bat sonarqube"
            }
       }
   stage("Build") {
        bat "./gradlew.bat bootJar"
   }

   stage("Deploy") {
        bat "java -jar web/build/libs/web-1.0-SNAPSHOT.jar"
   }
}