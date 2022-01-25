node {
   stage('Test') {
        bat "./gradlew clean"
        bat "./gradlew test "
   }
   stage('SonarQube Analysis') {
            withSonarQubeEnv() {
                bat "./gradlew sonarqube"
            }
       }
   stage("Build") {
        bat "./gradlew build"
        bat "./gradlew bootJar"

   }
   stage("Deploy") {
        bat "copy C:/ProgramData/nssm-2.24/win64/nssm.exe C:/ProgramData/Jenkins/.jenkins/workspace/CertificatesPipeline"
        //bat "cd C:/ProgramData/nssm-2.24/win64"
        bat "nssm start App"
   }

}