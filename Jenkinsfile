node {
   stage('Test') {
        steps{
        bat "./gradlew.bat clean"
        }
        bat "./gradlew.bat test "
   }
   stage("Build") {
        bat "./gradlew.bat bootJar"
   }
   stage('SonarQube Analysis') {
        withSonarQubeEnv() {
            bat "./gradlew.bat sonarqube"
        }
   }
   stage("Deploy") {
        bat "java -jar web/build/libs/web-1.0-SNAPSHOT.jar"
   }
}