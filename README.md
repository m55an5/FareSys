# FareSys

Assumption:
1. There is no need to add new bustops and routes through this system
2. Only working with 3 bus stops (We can add more, it would need to be hard coded in the main method)

   
## Gradle VERSION Gradle 6.6.1 (for compiling, unit testing and automation testing)

Run the following commands from within the project folder: /FareSys/

<b>To compile: </b>
./gradlew build

<b>To run Unit tests: </b>
./gradlew test

<b>To run Automation testing: </b>
./gradlew cucumber


Note: In case you are specifying a path to the .csv file, on MAC you may encounter (Operation not permitted) due to MAC OS security, you can follow this <a href=https://osxdaily.com/2018/10/09/fix-operation-not-permitted-terminal-error-macos/>link</a> for a quick fix. Works on Catalina. 


<b>Execution: </b>

java -jar ./build/libs/FareSys-1.0.0.jar csv_file_path

Examples

java -jar ./build/libs/FareSys-1.0.0.jar taps.csv

java -jar ./build/libs/FareSys-1.0.0.jar /User/abc/Downloads/taps.csv


## For Gradle Version 5.0 ( as tested on ubuntu)

<b>found an issue with gradle wrapper</b>

Run this in terminal (anywhere): gradle wrapper

<b>If</b> ./gradlew commands still dont run refer the following commands.




Run the following commands from within the project folder: /FareSys/

<b>To compile: </b>
gradle build

<b>To run Unit tests: </b>
gradle test

<b>To run Automation testing: </b>
gradle cucumber

<br \>

## Docker Comamnd - Execution and Running

<b>Building</b>
docker image build -t transit-manage-sys-build .


<b>Running</b>

Note: I have used volume mount for both "trips.csv" and "taps.csv" which are provided in the source code. Therefore you can run the following docker command while being able to make changes to "taps.csv" file locally and the running the following docker commands and be able to get the output of "trips.csv" in your local "trips.csv" file

<b>Run container with Volume mounts for taps.csv and trips.csv</b>
docker container run --name tms_machine --rm -v /Users/path/to/FareSys/taps.csv:/taps.csv -v /Users/path/to/FareSys/trips.csv:/trips.csv transit-manage-sys-build:latest


