# Teaching-HEIGVD-AMT-2019-Project-One

### Introduction

Welcome on our AMT project. The main objective of this is to apply the patterns and techniques presented during the lectures, and to create a simple multi-tiered application in Java EE.

### Requirements

You'll need to have `docker` and `docker-compose` and installed on your machine in order to launch the containers. You will also need `maven` and `python` to generate a .war file and run the scripts.

### Build the application

First, populate the database by launching the following script from the root of the project :

```bash
$ ./populateDB.sh
```

Then, launch the following script to build the containers:

```bash
$ ./build_image.sh
```



 