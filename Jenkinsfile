node {
   stage('test') {
       bat "./gradlew.bat test "
   }
   stage('SonarQube Analysis') {
    withSonarQubeEnv() {
      bat "./gradlew.bat sonarqube"
    }
  }
}