
build:
	javac -d bin -sourcepath src -cp "lib/*" src/org/uma/jmetal/main/ExperimentGrammaticalEvolution.java 

run:
	java -cp "bin:lib/*" org.uma.jmetal.main.ExperimentGrammaticalEvolution experiment 100 600000 1
