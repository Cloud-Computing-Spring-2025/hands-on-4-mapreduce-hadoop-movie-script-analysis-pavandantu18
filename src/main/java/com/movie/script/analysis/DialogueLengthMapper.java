package com.movie.script.analysis;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class DialogueLengthMapper extends Mapper<Object, Text, Text, IntWritable> {

    private final static IntWritable wordCount = new IntWritable();
    private Text character = new Text();

    @Override
    public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        String line = value.toString().trim();

        // Ignore empty lines
        if (line.isEmpty()) {
            return;
        }

        // Extract character name and dialogue
        int colonIndex = line.indexOf(":");
        if (colonIndex == -1) {
            return;
        }

        String characterName = line.substring(0, colonIndex).trim();
        String dialogue = line.substring(colonIndex + 1).trim();

        // Calculate dialogue length (number of characters in dialogue)
        int length = dialogue.length();

        character.set(characterName);
        wordCount.set(length);
        context.write(character, wordCount);  // Emit (Character, Dialogue Length)
    }
}
