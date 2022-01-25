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
   stage("Build") {
        bat "copy C:/ProgramData/nssm-2.24/win64 C:/ProgramData/Jenkins/.jenkins/workspace/Certificates" "Pipeline"
        //bat "cd C:/ProgramData/nssm-2.24/win64"
        bat "nssm start App"
   }

}