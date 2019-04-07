# VoldBench

Becnhmark on the nosql DB, Voldemort.

## Travis

[![Build Status](https://travis-ci.com/PamplemousseMR/VoldBench.svg?branch=master)](https://travis-ci.com/PamplemousseMR/VoldBench)


## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

- [Voldemort](https://www.project-voldemort.com/voldemort/quickstart.html) : A distributed database.

### Generation

This project use Voldemort 1.10.0, the library is available in folders "dist" and "lib" in the Voldemort project.

## Voldemort

### Build Voldemort

- git clone https://github.com/voldemort/voldemort.git
- git checkout 1.10.0
- .\gradlew build -x test

### Launch Voldemort server

- bin\voldemort-server.bat config\single_node_cluster

### Launch Voldemort client

- bin\voldemort-shell.bat test tcp://localhost:6666/

## Authors

* **MANCIAUX Romain** - *Initial work* - [PamplemousseMR](https://github.com/PamplemousseMR).
* **HANSER Florian** - *Initial work* - [ResnaHF](https://github.com/ResnaHF).

## License

This project is licensed under the GNU Lesser General Public License v3.0 - see the [LICENSE.md](LICENSE.md) file for details.
