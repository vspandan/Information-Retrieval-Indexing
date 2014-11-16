To compile the source code and generate index dump

Run index.sh file from bin folder with two arguments
	1. File - To be parsed for creating index
	2. Path - To store index dump
	e.g., bash index.sh ~/sample.xml index

Run query.sh to search an index
	This script takes input from standard input with multiple lines
	1. First line gives number of queries
	2. Search item in next lines, each search item can have multiple fields
	3. Fields can be searched as plain text or specific to "title", "body", "catogery"
	
	e.g., printf "2\n b:german\n carmony indiana"|bash query.sh

	4. b:<word> searches for this word in body context, t:carmony<word> searches word in titles, c:<word> searches this word in catogeries 
	5. plain text will be searched in all indexes
	6. Print empty string if no matches found
 


