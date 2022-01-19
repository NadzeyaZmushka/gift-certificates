node {
   stage('test') {
       steps {
       start "./gradlew.bat test "
       }
   }
   stage('SonarQube Analysis') {
    withSonarQubeEnv() {
      start "./gradlew.bat sonarqube"
    }
  }
}