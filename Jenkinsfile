node {
   stage('Test') {
        bat "gradle clean"
        bat "gradle test "
   }
   stage('SonarQube Analysis') {
            withSonarQubeEnv() {
                bat "gradle sonarqube"
            }
       }
   stage("Build") {
        bat "gradle build"
        bat "gradle bootJar"

   }
   stage("Deploy") {
        bat "copy "C:\\ProgramData\\nssm-2.24\\win64\\nssm.exe C:\\ProgramData\\Jenkins\\.jenkins\\workspace\\Certificates Pipeline""
        //bat "cd C:/ProgramData/nssm-2.24/win64"
        bat "nssm start App"
   }

}