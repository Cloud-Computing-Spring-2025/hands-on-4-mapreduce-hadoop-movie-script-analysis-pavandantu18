package com.movie.script.analysis;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.StringTokenizer;

public class CharacterWordMapper extends Mapper<Object, Text, Text, IntWritable> {

    private final static IntWritable one = new IntWritable(1);
    private Text word = new Text();

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

        String dialogue = line.substring(colonIndex + 1).trim();

        // Tokenize the dialogue into words
        StringTokenizer tokenizer = new StringTokenizer(dialogue);

        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken().toLowerCase().replaceAll("[^a-zA-Z]", "");
            if (!token.isEmpty()) {
                word.set(token);
                context.write(word, one);  // Emit word with count 1
            }
        }
    }
}
