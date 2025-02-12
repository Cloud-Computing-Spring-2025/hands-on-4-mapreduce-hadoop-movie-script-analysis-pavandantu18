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

        // Increment total lines processed counter
        context.getCounter("HadoopCounters", "Total Lines Processed").increment(1);

        // Extract character name and dialogue
        int colonIndex = line.indexOf(":");
        if (colonIndex == -1) {
            return;
        }

        String characterName = line.substring(0, colonIndex).trim();
        String dialogue = line.substring(colonIndex + 1).trim();

        // Count total characters in dialogue
        context.getCounter("HadoopCounters", "Total Characters Processed").increment(dialogue.length());

        // Tokenize dialogue into words
        StringTokenizer tokenizer = new StringTokenizer(dialogue);

        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken().toLowerCase().replaceAll("[^a-zA-Z]", "");
            if (!token.isEmpty()) {
                word.set(token);
                context.write(word, one);  // Emit word for frequency counting

                // Increment total words processed counter
                context.getCounter("HadoopCounters", "Total Words Processed").increment(1);
            }
        }

        // Track distinct characters speaking
        context.getCounter("HadoopCounters", "Number of Characters Speaking").increment(1);
    }
}
