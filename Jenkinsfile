pipeline {
	agent any

	tools {
		maven 'M3'
		jdk 'JDK21'
	}

	stages {
		stage('Checkout') {
			steps {
				git branch: 'main',
				url: 'https://github.com/a-alrajhi/Practice01.git'
			}
		}

stage('Build')   { steps { sh 'mvn -Dmaven.compiler.release=21 clean compile' } }
stage('Test')    { steps { sh 'mvn -Dmaven.compiler.release=21 test' } }
stage('Package') { steps { sh 'mvn -Dmaven.compiler.release=21 -DskipTests package' } }

	}

	post {
		always {
			cleanWs()
		}
		success {
			echo 'Pipeline completed successfully!'
		}
		failure {
			echo 'Pipeline failed!'
		}
	}
}