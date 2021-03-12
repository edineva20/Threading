import de.dhl.jenkins.groovy.*;

pipeline {

    tools {
        jdk 'linux_jdk1.8.0_172'
        maven 'linux_M3'
    }
    agent {
        any
    }

    stages {
        stage('parallel compiles') {
          parallel {
            stage('SimpleApp') {
              steps {
                lock(resource: 'Lock') {
                  script {
                      HelloWorld.sayHello();
                  }
                  dir(path: 'SimpleApp') {
                    sh '${MAVEN_HOME}/bin/mvn -s ../maven/settings.xml clean compile'
                  }
                }
              }
            }
            stage('stateless') {
              steps {
                lock(resource: 'Lock') {
                  dir(path: 'ejb/stateless') {
                    sh '${MAVEN_HOME}/bin/mvn -s ../../maven/settings.xml clean compile'
                  }
                }
              }
            }
            stage('stateful') {
              steps {
                lock(resource: 'Lock') {
                  dir(path: 'ejb/stateful') {
                    sh '${MAVEN_HOME}/bin/mvn -s ../../maven/settings.xml clean compile'
                  }
                }
              }
            }
          stage('jms') {
              steps {
                lock(resource: 'Lock') {
                  dir(path: 'jms') {
                    sh '${MAVEN_HOME}/bin/mvn -s ../maven/settings.xml clean compile'
                  }
                }
              }
            }
          }
        }

    stage('test') {
      steps {
        dir(path: 'SimpleApp') {
          sh '${MAVEN_HOME}/bin/mvn test'
        }
        junit '**/surefire*/*.xml'
      }
    }
    stage('save_check_results') {
      agent { label 'linux'}
      steps {
        archiveArtifacts artifacts: '**/*surefire*/*.xml' , allowEmptyArchive: true
      }
    }

    stage('package') {
      steps {
        dir(path: 'SimpleApp') {
          sh '${MAVEN_HOME}/bin/mvn package'
        }
      }
    }

    stage('upload_artifacts') {
      steps {
        archiveArtifacts artifacts: '**/*.jar',  allowEmptyArchive: true
      }
    }
    }

    post {
    failure {
        echo 'I will always say Hello again!'
    }
    }
}
