program:
	javac -sourcepath src -classpath bin \
		             src/*.java -d bin 

clean:
	rm Space-Shooter.jar
	rm bin/*.class
#	rm bin/Records.txt
