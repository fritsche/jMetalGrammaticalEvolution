
build:
	javac -d bin -sourcepath src -cp "lib/*" src/org/uma/jmetal/main/ExecuteAlgorithmCITOCommandLine.java 

run:
	java -cp "bin:lib/*" org.uma.jmetal.main.ExecuteAlgorithmCITOCommandLine -h
	