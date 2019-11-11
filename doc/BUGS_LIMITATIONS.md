# Teaching-HEIGVD-AMT-2019-Project-One

### Part 5. Known bugs and limitations

#### 5.1 Bugs 

Sometimes, the `launch_project.sh` script doesn't work as intended. In this case, the Arquillian tests fail.

One must :

* Launch the topology-testing by hand with docker-compose. 
* Go again on the root of the project and launch `mvn clean install -Dskiptest`. 

* Open `scripts` folder and execute `.populateDb.sh`
* Copy the `.war` file next to the Payara Dockerfile
* In the topology-prod folder, launch `docker-compose up --build`

