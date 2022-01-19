node {
   stage('Test') {
       bat "./gradlew.bat test "
   }
   stage('SonarQube Analysis') {
        withSonarQubeEnv() {
      bat "./gradlew.bat sonarqube"
        }
   }
   stage("Deploy") {
        bat "java -jar web-1.0-SNAPSHOT.jar"
   }
}