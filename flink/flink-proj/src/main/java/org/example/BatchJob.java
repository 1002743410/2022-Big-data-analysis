/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.example;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.core.fs.FileSystem;
import org.apache.flink.util.Collector;

/**
 Count the number of occurrences of each letter in the text file
 */
public class BatchJob {

	public static void main(String[] args) throws Exception {

		//The first step of the Flink program is to create a StreamExecutionEnvironment. This is an entry class, which can be used to set parameters, create data sources and submit tasks.
		ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

		//To facilitate the demonstration, output the results in the same file, and set the parallelism to 1.
		env.setParallelism(1);

		// execute program
		String input = null;
		String output = null;

		ParameterTool params = ParameterTool.fromArgs(args);

		try {
			input = params.getRequired("input");
			output = params.getRequired("output");
		} catch (RuntimeException e) {
			System.out.println("Argument Error");
			e.printStackTrace();

			return;
		}

		//Create a DataSet of string type.
		DataSet<String> text = env.readTextFile(input);
		//Parse the string data into words and times (represented by Tuple2<String, Integer>). The first field is a letter, and the second field is a number. The initial value of the number is set to 1.
		//A flatmap is implemented for parsing, because there may be multiple letters in a row of data.
		DataSet<Tuple2<String, Integer>> counts = text.flatMap(new Tokenizer()).groupBy(0).sum(1);
		counts.writeAsText(output, FileSystem.WriteMode.OVERWRITE);

		env.execute("Flink Batch Java API Skeleton");
	}

	public static class Tokenizer implements FlatMapFunction<String, Tuple2<String, Integer>> {
		@Override
		public void flatMap(String value, Collector<Tuple2<String, Integer>> out) throws Exception {

			String[] tokens = value.toLowerCase().split("\\W+");
			for (String token : tokens) {
				if (token.length() > 0) {
					for (char letter : token.toCharArray()) {
						System.out.println(letter);
						out.collect(new Tuple2<String, Integer>(letter + "", 1));
					}
				}
			}
		}
	}
}