  /*
  * Random Word Generator - a Random Word/Name Generator
  * Copyright (C) 2015 masl123 (github.com)
  * 
  * This program is free software: you can redistribute it and/or modify
  *  it under the terms of the GNU Lesser General Public License as published by
  *  the Free Software Foundation, either version 3 of the License, or
  *  (at your option) any later version.
  *
  *  This program is distributed in the hope that it will be useful,
  *  but WITHOUT ANY WARRANTY; without even the implied warranty of
  *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  *  GNU Lesser General Public License for more details.
  *
  *  You should have received a copy of the GNU Lesser General Public License
  *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
  */

package com.masl.randWordGen;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;


public class RandomWordGen {

//	public static void main(String[] args) {
//		File file = new File(args[0]);
//		try {
//			RandomWordGen gen = new RandomWordGen(file);
//			System.out.println("generate Words");
//			for(int i = 0; i<100; i++){
//				System.out.println(gen.getNextName(6));
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
	
	private final char[] consonants = "pfgcrldhtnsqjkxbmwvz".toCharArray();
	private final char[] vovels = "aeiou".toCharArray();
	private List<String> wordlist;
	private Random rand;
	
	/**
	 * Creates a new Random Word Generator
	 * @param inFile File to the Word Dictionary File
	 * @throws IOException 
	 * 
	 * */
	public RandomWordGen(File inFile) throws IOException{
		wordlist = getWordList(inFile);
		wordlist = sort(wordlist);
		rand = new Random();
	}
	
	
	/**
	 * Creates a new Random Word Generator
	 * @param inFile File to the Word Dictionary File
	 * @param seed Seed for the Random Number Generator
	 * @throws IOException 
	 * */
	public RandomWordGen(File inFile, long seed) throws IOException{
		wordlist = getWordList(inFile);
		wordlist = sort(wordlist);
		rand = new Random(seed);
	}
	
	
	/**
	 * Loads the Words from a File and Splits them at Space or Newline.
	 * @param File to Load
	 * @return list of Words; not Sorted
	 * @throws IOException 
	 * */
	private List<String> getWordList(File inFile) throws IOException{
			BufferedReader reader = new BufferedReader(new FileReader(inFile));
			
			
			//there is a Simpler Method in Java 8, but because of Compatibility reasons I will not use it.
			List<String> lines = new LinkedList<String>(); 			
			String s;
			while((s = reader.readLine())!=null){
				lines.add(s);
			}

			try {
				reader.close();
			} catch (IOException e) {e.printStackTrace();}
			
			List<String> words = new ArrayList<String>(lines.size());

			while(!lines.isEmpty()){
			
				
				for(String word : lines.get(0).split("\\s")){
					words.add(word.toLowerCase());
				}
				lines.remove(0);//getting rid of lines
			}	
			return words;
	}
	
	/**
	 * Sorts a list of Strings with Radix-Sort
	 * */
	private List<String> sort(List<String> wordlist){
		List<String>[] buckets= new ArrayList[28];
		emptyBuckets(buckets);
		int n = getLongestWord();
		for(int i = 0; i<n; i++){
			while(!wordlist.isEmpty()){
				String word = wordlist.get(0);
				int charIndex = n-i-1; //sort after nTh Char, from right to left

				if(charIndex >= word.length()){ //length of Word is to small
					buckets[0].add(word);
				}else{
					char b = word.charAt(charIndex);
					if(b>='a' && b<='z'){ //see if its a Letter
						
						buckets[(b -'a')+ 1].add(word);
					}else{ //if not then its something else, like ä, ö, ü or ß
						buckets[buckets.length-1].add(word);
					}
				}
				wordlist.remove(0);
			}
			
			for(List<String> l : buckets){ //concatenate the Buckets
				wordlist.addAll(l);
			}
			
			emptyBuckets(buckets); //empty Buckets
		}
		return wordlist;
	}
	
	/**
	 * resets all Buckets
	 * */
	private void emptyBuckets(List<String>[] buckets){
		for(int i = 0 ; i<buckets.length; i++){
			buckets[i] = new ArrayList<String>(10);
		}
	}
	
	
	/**
	 * @return Longest String in Wordlist
	 * */
	private int getLongestWord(){
		int max = 0;
		for(String s : wordlist){
			if(s.length()>max){
				max = s.length();
			}
		}
		return max;
	}
	
	
	/**
	 * Generates a Random Name or Word
	 * 
	 * @param lenght minimum length of name
	 * @return returns a random Name
	 * */
	public String getNextName(int lenght){
		
		String name = rand.nextDouble()>0.7 ? (consonants[(int) (rand.nextDouble()*consonants.length)]+"").toUpperCase() : (vovels[(int) (rand.nextDouble()*vovels.length)]+"").toUpperCase();
		while(name.length()<lenght){
			int randSuffix = this.rand.nextInt(3)+1;
			String suffix = name.substring( (name.length()-randSuffix) > 0 ? (name.length()-randSuffix) : 0, name.length());
			String foundWord = find(suffix, 0 , wordlist.size());
			
			int upperbound = (int) ((rand.nextInt(foundWord.length())*0.8)+(foundWord.length()*0.2)); 
			upperbound++;

			foundWord = foundWord.substring(upperbound-1<suffix.length() ? 0 : suffix.length(), upperbound);
			name += foundWord;
		}
		return name;
	}
	
	/**
	 * Finds the best Match in a Sorted List of Strings
	 * */
	private String find(String s, int leftindex, int rightindex){
		
		int rightind = leftindex+((rightindex-leftindex)/2);
		
		if(rightindex -  leftindex == 1){
			return wordlist.get(rightind); //return best Match
		}

		int comp = s.compareToIgnoreCase(wordlist.get(rightind));

		if(comp > 0){ //go left
			return find(s, rightind, rightindex);
		}else if(comp < 0){//go Right
			return find(s, leftindex, rightind);
		}else{
			return wordlist.get(rightind); //return best match
		}
	}
}