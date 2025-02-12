### Project Overview – A brief explanation of what the project does.

This Hadoop MapReduce project analyzes a movie script dataset to extract insights such as:

Most Frequently Spoken Words by Characters
Total Dialogue Length per Character
Unique Words Used by Each Character
Hadoop Counters for Statistical Tracking

### Approach and Implementation – Explanation of the Mapper and Reducer logic.

The project consists of three MapReduce jobs, each performing a different analysis.

Mapper Logic:
CharacterWordMapper: Extracts words from dialogues, counts occurrences, and tracks counters.

DialogueLengthMapper: Computes the total dialogue length per character.
UniqueWordsMapper: Extracts unique words spoken by each character.

Reducer Logic:
Aggregates word frequencies, calculates total dialogue length, and consolidates unique words per character.

### Execution Steps – Include the commands used to build and run your project.

docker compose up -d
mvn install
mv target/*.jar input/code/
docker cp input/code/hands-on2-movie-script-analysis-1.0-SNAPSHOT.jar resourcemanager:/opt/hadoop-3.2.1/share/hadoop/mapreduce/
docker cp input/movie_dialogues.txt resourcemanager:/opt/hadoop-3.2.1/share/hadoop/mapreduce/
docker exec -it resourcemanager /bin/bash
cd /opt/hadoop-3.2.1/share/hadoop/mapreduce/

hadoop fs -mkdir -p /input/dataset

hadoop fs -put ./movie_dialogues.txt /input/dataset

hadoop jar /opt/hadoop-3.2.1/share/hadoop/mapreduce/hands-on2-movie-script-analysis-1.0-SNAPSHOT.jar com.movie.script.analysis.MovieScriptAnalysis /input/dataset/movie_dialogues.txt /output

hadoop fs -cat /output/*
hdfs dfs -get /output /opt/hadoop-3.2.1/share/hadoop/mapreduce/
exit 
docker cp resourcemanager:/opt/hadoop-3.2.1/share/hadoop/mapreduce/output/ shared-folder/output/

### Challenges Faced & Solutions – Mention any difficulties encountered and how they were resolved.

No challenges faced

### Sample Input and Output – Provide a test case example and its expected output.

JACK: The ship is sinking! We have to go now.
ROSE: I won’t leave without you.
JACK: We don’t have time, Rose!

Task1:
the 3
we 3
have 2
to 2
now 1
without 1

Task2:
JACK 54
ROSE 25

Task3:
JACK [the, ship, is, sinking, we, have, to, go, now, don’t, time, rose]
ROSE [i, won’t, leave, without, you]

