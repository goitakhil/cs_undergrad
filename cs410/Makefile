
build:
	mvn clean compile assembly:single

run: build
	java -jar target/taskshell-1.0-SNAPSHOT-jar-with-dependencies.jar 'mysql://127.0.0.1/tasks?user=root&password=passw0rd&useSSL=false'

clean:
	mvn clean
	rm -rf target
