# RandomWordGen
A Random Word/Name Generator

#####USAGE:
Simply Instanciate a new RandomWordGen Object and call getNextName(int length) to get a Random Word.
You Need a List with some Sample Words. Use the NAMES.dic if you don't want to create one. 
The Length of the List can be at max around 7000 Words. If you're above, then it starts to take long time until the Sorting algorithm sorted the Strings.

#####Algorithm Description
The Algorithm is based on this Project: https://github.com/mafik/random-word-generator <br>
I'm  using Radix-Sort (because Quicksort, which was used in the original Project, has a Worst Case Runtime of O(n^2)) as a Sorting Algorithm, to sort the Word List. (to be able to use Binary Search afterwards, when generating names) 

0. The Algorithm for Generating Names starts, by simply choosing a random Letter.
1. search the best fitting Word for a Suffix of the Word which has been generated until now. The Suffix has a Random length.
2. Concatenate the found Word and the Generated one. (but leave away the first few letters, as they might be the same as the suffix)
3. continue with 1 and 2 until a Word with the minimum length has been generated.

#####Samples:
```
Eorisr Adadstie Eorisri Noshsa Wernri Uwatwerni Oiuhuna Oishri Cuwtwern Adeori Eoriona Lruise Adelaid Iuiana Mynnoni Eowooim 
Adelai Fvelristy Uwadela Oishade Ihhhui Uwanaal Vwrnver Bshlrhay Hriuian Ihhhaise Oisihu Uwarnit Werniui Uwanaalo 
Uwanash Ihhride Eoishsa Adeoris Iuhuha Adelaide Grederi Eowood Adelaide Adeladel Oishris Oisihrise Ihriona Uwanamo Adelai 
Eorelr UwadelZuwarn Lruish Oishasa Eorisut Bshled Adeori Eowoisha Uwanois Tumtwisha Uwtwnosh Iuwanoshe Werniky Kuiuihu Uwanal 
Eoishn Rhilom Vwaver Fveoris Adelaisty Qhilom Oishri Iuihhu Iuitwtr Eorisri Iuihuwa Oishnl Iuihri Eorimy Ihihdhu Fvelyn 
Eowwaldo Rhilom ZuwanaOishrisel Eoishri Xernri Adelris Wernos Oishai Eorirh Hrydni Uwtwer Ihrisky Ihhhris Eoriui Oinihh 
Jujadel Oishato Uwanadela Oihhrisel Eowoois Oiuihu
```
