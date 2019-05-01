all :
	rm -Rf build
	mkdir build
	javac -cp "VoldBench/lib/voldemort/*:VoldBench/lib/sql/*" ./VoldBench/src/Activity/Option.java -d build
	javac -cp "VoldBench/lib/voldemort/*:VoldBench/lib/sql/*:build" ./VoldBench/src/Benchmark/* -d build
	javac -cp "VoldBench/lib/voldemort/*:VoldBench/lib/sql/*:build" ./VoldBench/src/Activity/Main.java -d build
