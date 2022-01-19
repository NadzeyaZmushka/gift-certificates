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
      step {
        bat "./gradlew.bat bootJar"
      }
      step {
        bat "java -jar web/build/libs/web-1.0-SNAPSHOT.jar"
      }
   }
}