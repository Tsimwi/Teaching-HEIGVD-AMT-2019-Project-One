# Teaching-HEIGVD-AMT-2019-Project-One

### Part 4. Experiment

We were asked to test how the application was reacting on load. To do that we used JMeter.

We have done two series of tests :

- The first with paging, meaning that we displayed only 25 users per page. They are loaded from the DAO with an offset and limitation system. The offset will be our page number and the limit (set to 25) is hard coded in the SQL queries.
- The second is without paging, meaning that when we go in the "characters" menu, we will load all the characters of the database.

The goal is to show how pagination affects server performance and user experience. 

Note: to change the behaviors of the project (paging or not) we need to uncomment line 53 of `CharactersServlet`.

#### 4.1 With paging

Here is the command we used to launch the test.

```bash
JVM_ARGS='-Xms4096m -Xmx4096m' ./jmeter -n -t ../../Teaching-HEIGVD-AMT-2019-Project-One/jMeter/100Th_1It.jmx  -l ../../Teaching-HEIGVD-AMT-2019-Project-One/reportJmeter/100th_1it_pagined -e -o ../../Teaching-HEIGVD-AMT-2019-Project-One/reportJmeter/100th_1it_pagined_dir
```

With paging, there is no problem. We can see on the table below that we have an average time of 1032.32 ms to display the page.

![jmater_paging_stat](./img/jmater_paging_stat.png)

#### 4.2 Without paging

Here is the command we used to launch the test.

```bash
JVM_ARGS='-Xms4096m -Xmx4096m' ./jmeter -n -t ../../Teaching-HEIGVD-AMT-2019-Project-One/jMeter/100Th_1It.jmx  -l ../../Teaching-HEIGVD-AMT-2019-Project-One/reportJmeter/100th_1it_ -e -o ../../Teaching-HEIGVD-AMT-2019-Project-One/reportJmeter/100th_1it_dir
```

Without paging, there are some problems. The table show us that the server took 34'972.58 ms in average to respond. In addition to this, we have errors on the login page and on the character page. Error messages are `localhost:8080 failed to respond`. We think that it's because the server was too busy processing requests and database responses.

![jmater_no_paging_stat](./img/jmater_no_paging_stat.png)

![jmeter_error](./img/jmeter_error.png)

We launched `JConsole` to monitor resources used by the server while we are testing it without paging.

![jconsole_memory](./img/jconsole_memory.png)

We can see that for only 100 threads, 4Gb of memory is used. And this is only for displaying 100'000 characters. 