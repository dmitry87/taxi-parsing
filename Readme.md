# Getting Started

### Reference Documentation

The application can read parquet file from local file system. When Application is build you must run make graxt execute permissions to the jar file.
Please make sure you have
* java 17 installed to build and run the application
* maven installed to bulid the app ```brew maven install``` or search in a web for your OS approach
* To build the application run ```mvn clean package```
* Resources files are not included in the archive - you must download it https://www.nyc.gov/site/tlc/about/tlc-trip-record-data.page
* To run the app in the cloud it should be put into docker container.
```shell
chmod +x path_to_file/taxi-parsing-0.0.1-SNAPSHOT-jar-with-dependencies.jar
```
After this is done you may run the file. To see programm menu options please run:
```shell
java -jar path_to_file/taxi-parsing-0.0.1-SNAPSHOT-jar-with-dependencies.jar -h
```

To read multiple files from the classpath you may run:
```shell
java -jar path_to_file/taxi-parsing-0.0.1-SNAPSHOT-jar-with-dependencies.jar -fg path_to_file/green_tripdata_2023-11.parquet,path_to_file/green_tripdata_2023-10.parquet -fy path_to_file/yellow_tripdata_2023-10.parquet,path_to_file/yellow_tripdata_2023-11.parquet
```

To get data after some datetime you may run
```shell
java -jar path_to_file/taxi-parsing-0.0.1-SNAPSHOT-jar-with-dependencies.jar -fg path_to_file/green_tripdata_2023-11.parquet,path_to_file/green_tripdata_2023-10.parquet -fy path_to_file/yellow_tripdata_2023-10.parquet,path_to_file/yellow_tripdata_2023-11.parquet -s 2023-10-16T19:56:35
```

To get data in a window you must add end date:
```shell
java -jar path_to_file/taxi-parsing-0.0.1-SNAPSHOT-jar-with-dependencies.jar -fg path_to_file/green_tripdata_2023-11.parquet,path_to_file/green_tripdata_2023-10.parquet -fy path_to_file/yellow_tripdata_2023-10.parquet,path_to_file/yellow_tripdata_2023-11.parquet -s 2023-10-16T19:56:35 -e 2023-11-16T19:56:35
```

To get search within location ids you may update your parameters:
```shell
java -jar path_to_file/taxi-parsing-0.0.1-SNAPSHOT-jar-with-dependencies.jar -fg path_to_file/green_tripdata_2023-11.parquet,path_to_file/green_tripdata_2023-10.parquet -fy path_to_file/yellow_tripdata_2023-10.parquet,path_to_file/yellow_tripdata_2023-11.parquet -s 2023-10-16T19:56:35 -e 2023-11-16T19:56:35 -p 168 -d 125
```