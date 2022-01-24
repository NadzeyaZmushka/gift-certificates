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
        bat "./gradlew.bat build"
        bat "./gradlew.bat bootJar"
   }

}