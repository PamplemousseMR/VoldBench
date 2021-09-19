# VoldBench

Benchmark on the nosql DB, Voldemort compared to MySQL.

## CI

![Build Status](https://github.com/PamplemousseMR/VoldBench/actions/workflows/build.yml/badge.svg)

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

- [Voldemort](https://www.project-voldemort.com/voldemort/quickstart.html) : A distributed database.
- [MySQL](https://www.mysql.com/fr/) : A SQL server.

### Generation

This project use Voldemort 1.10.0, the library is available in folders "dist" and "lib" in the Voldemort project. It also use MySQL.

A MySQL and Voldemort server must be launch to execute a benchmark on it.

Compile the project with any IDE, needed libraries are on the "lib" folder. We also provide a "Makefile".

```
-h : Displays help for execution arguments.
-vdm-u <url> : The url of Voldemort server.
-vdm-p <port> : The port of Voldemort server.
-vdm-n <name> : The name of Voldemort server.
-sql-u <url> : The url of MySQL server.
-sql-c <classname> : The classname of MySQL server.
-sql-l <login> : The login of MySQL server.
-sql-p <password> : The password of MySQL server.
-l <loop> : Set the number of loop.
-s <size> : set the DB size.
```

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
