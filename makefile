all :
	rm -Rf build
	javac -cp "VoldBench/lib/*" ./VoldBench/src/Activity/Option.java -d build
	javac -cp "VoldBench/lib/*:build" ./VoldBench/src/Benchmark/* -d build
	javac -cp "VoldBench/lib/*:build" ./VoldBench/src/Activity/Main.java -d build
