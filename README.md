# MS3-Challenge
MS3 Coding Challenge README

Approach Summary

Given Customer X's request for a database to built for their 1000+ clients I approached building a Java application as follows. 
First I decided to create a new Maven project in Eclipse because Maven is designed for project management and automation, 
but I primarily concerned with using as a build tool because of its ability to integrate with other open source libraries and is 
perfectly suited for this request since Maven can write test cases while analyzing and filtering metadata. Maven provides us with 
a POM.XML extension that provides detailed information about current project including plugins and configuration files. Knowing 
that I needed to build an SQLLite DB from Customer X's give CSV I created a <dependency></dependency> tag for SQLLite version 
3.16.1 in the POM.XML file with the <dependencies></dependencies> tags, which Maven proceeded to download the framework necessary 
for me use in conjuncture with the main and test cases of the Java code. After setting up the POM file, I decided that the best 
approach would be to make an Object Oriented Dynamic based application. First I set up a method to connect to the SQLLite library. 
The finds the absolute path in my host machine and prints out if a connection to the DB was found else provide a feedback error in 
the form of a printStackTrace() throwback. Once the connection has been established a Table in the Database needs to be created to 
store Customer X's data, so I designed a create_Table method to setup such a Table. Using SQL syntax CREATE TABLE to create the 
table I store this value in a variable in a string and format the table using the A,B,C,D,... as headers since our data in the CSV 
does not included detailed headers to identify our column data. Furthermore, I formatted each header with a set data type and did 
not assign a Primary Key(because our data lacks a unique identifier need to establish one). Following the creation of a the 
Table, I coded a method designed to parse each row of the CSV File and add the data to the Table in the Database, within this 
method it should be able to scan in the CSV File, interpret the data, and add it to the DB in a secure and efficient manner. 
I decided to make use of a buffer to read in from the CSV and make use of prepared statements so that SQLInjection would fail 
if someone tried to hack into the DB. Additionally I also make us of the Java JCD Batch library which will take large chucks of 
data, in our case rows from the table and process the data and update the DB as a block instead of going line 
by line, effectively reducing execution time of our program. Finally I develop a method to close the established connection 
with the SQLLite library which also end and saves the data written from our CSV to our Database. Despite not getting to the 
point in the process where statistics about the Database are written to a log file or each record was checked for verification. 
I would approached this by designing another method that would accepted the arguments from the add_To_DB method and written a 
loop structure such that each row of data was processed as it was add to the DB. Since each row was now parameterized and 
delimited in our add_To_DB method I would make a variable say called count which would be the count of column for that record 
where the data was not null and of proper type say Integer for column G for example. I would then compare this variable against 
a variable called tot which would be the total number of columns as initialized in our create table method, determined by our 
headers.  Row data that does not match the total is the coded with a timestamp and labeled as "failed". Then write in a log 
file the number of written data by creating a variable called written =  which would simple be a count of how many times our 
add_TO_DB loop ran, write the number of "failed" in as determined by the above approach and then define the # of success as 
any row of data not failed but within the total amount of time that data was add to the DB.


Run the code

- Use any IDE that supports Java libraries, specifically Maven projects, I recommend Eclipse
- Have Git installed on your local machine, if its Linux you can easily use the CLI to install get using, ex apt get install Git. 
For Mac and Windows you'll need to download Git from the website, git-scm.com/downloads, or download Github Desktop at https://desktop.github.com/
- Start a new Maven project in the chosen IDE, in Eclipse go to File > New > Project > Search for Maven > Click Maven project create a simple maven project,
when prompted give it a Group ID and Artifact ID leave all others fields the same. Click Finish.
- Go to my link for the Git repository, download the repository folder as a zip, then unzip after completion
- Copy the Pom.xml file over to your Maven > Pom.xml file in Eclipse
- Go into the src folder > main > java f and copy the CSV_To_DB into the main src/main/java package in Eclipse > under the default package 
- In the code make sure  you change the path locations for CSV File and the SQLLite library that Maven downloaded  as a dependency, this is because my path locations
are local  to my host. Please make sure you  have the CSV File on your machine. Below is code you need to change, change each path to one that matches with those on your local machine.
Connection conn = connect("C:/Users/Master/.m2/repository/org/xerial/sqlite-jdbc/3.16.1/sqlite-jdbc-3.16.1-sources.jar");
add_To_DB(conn, "C:\\Users\\Master\\Downloads\\ms3Interview (2).csv");
- You should now be able to run and test the code
