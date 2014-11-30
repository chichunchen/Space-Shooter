program:
	javac -sourcepath src -classpath bin \
		             src/*.java -d bin 

clean:
	rm bin/*.class
	rm bin/Records.txt
