/*
 * (C) Copyright 2018 Nuxeo (http://nuxeo.com/) and others.
 *
 * Contributors:
 *     Pierre-Gildas MILLON <pgmillon@nuxeo.com>
 */
properties([
        [$class: 'BuildDiscarderProperty', strategy: [$class: 'LogRotator', daysToKeepStr: '60', numToKeepStr: '60', artifactNumToKeepStr: '1']],
        disableConcurrentBuilds(),
        [$class: 'RebuildSettings', autoRebuild: false, rebuildDisabled: false]
])

node('SLAVE') {

    tool type: 'ant', name: 'ant-1.9'
    tool type: 'hudson.model.JDK', name: 'java-8-oracle'
    tool type: 'hudson.tasks.Maven$MavenInstallation', name: 'maven-3'

    timeout(30) {
        timestamps {
            stage('checkout') {
                checkout scm
            }

            stage('build and test') {
                sh "mvn -DskipTests -Pqa clean deploy"
            }
        }
    }
}