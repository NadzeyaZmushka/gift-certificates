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
//         bat "cd C:\\ProgramData\\nssm-2.24\\win64"
//         bat "copy ""C:\\ProgramData\\Jenkins\\.jenkins\\workspace\\Certificates Pipeline\\web\\build\\libs\\web-1.0-SNAPSHOT.jar"""
        //bat "cd C:/ProgramData/nssm-2.24/win64"
        bat "nssm start newapp"
   }

}