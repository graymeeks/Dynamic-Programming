// Author: Gray Meeks
// Purpose: Provide the following information about two given strings:
//			
//			1) Length of longest common subsequence
//			2) One instance of a longest common subsequence
//			3) Length of longest common substring
//			4) A list of all distinct longest common substrings
//
// Input: stdin (prompted)
// Output: screen

import java.util.*;

public class LongestCommon 
{

	// Given two strings, solveLongestCommonSubstring prints two things:
	//		1) Length of longest common substring
	//		2) All distinct common substrings of that length.
	public static void solveLongestCommonSubstring(String x, String y)
	{
		// Designate strings to variables based on length
		// (necessary due to nature of traversal)
		String s1, s2;
		if (x.length() < y.length())
		{
			s1 = x;
			s2 = y;
		}
		else
		{
			s1 = y;
			s2 = x;
		}

		// Declare problem-relevant variables
		int longest = 0;
		int count = 0;
		Stack<String> strStack = new Stack<String>();
		int columns = s1.length() + 1;
		int rows = s2.length() + 1;
		int[] current = new int[columns];
		int[] previous = new int[columns];


		// Generate two array lists to keep track of substrings
		// throughout the problem
		ArrayList<String> prevSubstrings = new ArrayList<String>();
		ArrayList<String> currSubstrings = new ArrayList<String>();
		for (int i=0; i<columns; i++)
		{
			prevSubstrings.add("");
			currSubstrings.add("");
		}
		
		// Dynamically solve subproblems (with 'array shrink' improvement)
		for (int i=0; i<rows; i++)
		{
			for (int j=0; j<columns; j++)
			{
				// Case zero: base case
				if (i== 0 || j == 0)
				{
					current[j] = 0;
				}
				else
				{
					char c1 = s1.charAt(j-1);
					char c2 = s2.charAt(i-1);
					// Case one: current cell is part of common substring
					if (c1 == c2)
					{
						current[j] = previous[j-1] + 1;
						String soFar = prevSubstrings.get(j-1);
						soFar += c1;
						currSubstrings.set(j, soFar);
						// Subcase 1: longest but not setting a record
						if (current[j] == longest)
						{
							strStack.push(soFar);
							count++;
						}
						// Subcase two: setting record for 'new' longest
						else if (current[j] > longest)
						{
							longest = current[j];
							strStack.clear();
							strStack.push(soFar);
							count = 1;
						}
					}
					// Case two: current cell is not part of common substring
					else
					{
						current[j] = 0;
					}
				}
			}
			previous = current.clone();
			prevSubstrings = (ArrayList<String>)currSubstrings.clone();
		} // End dynamic traversal
	
		// Report back to user
		if (longest > 0)
		{
			ArrayList<String> printed = new ArrayList<String>();
			System.out.println("\nLength of longest common substring: " + longest);
			System.out.println("All distinct common substrings of length " + longest + ":\n");
			for (int i=0; i<count; i++)
			{
				// Print distinct substrings
				String curr = strStack.pop();
				if (!printed.contains(curr))
				{
					System.out.println(curr);
					printed.add(curr);
				}
			}
		}
		else
		{
			System.out.println("\nThere are no common substrings.");
		}
	} // end longest common substring
	
	
	// Given two strings, solveLongestCommonSubsequence provides the following information:
	//		1) Length of longest common subsequence
	//		2) One instance of a subsequence of that length
	public static void solveLongestCommonSubsequence(String s1, String s2)
	{
		// Declare problem-related variables
		int columns = s1.length() + 1;
		int rows = s2.length() + 1;
		int[][] dynamicArr = new int[rows][columns];
		
		// Dynamic array traversal
		for (int i=0; i<rows; i++)
		{
			for (int j=0; j<columns; j++)
			{
				// Case zero: base case
				if (i== 0 || j == 0)
				{
					dynamicArr[i][j] = 0;
				}
				else
				{
					char c1 = s1.charAt(j-1);
					char c2 = s2.charAt(i-1);
					// Case one: this cell extends a subsequence
					if (c1 == c2)
					{
						dynamicArr[i][j] = dynamicArr[i-1][j-1] + 1;
					}
					// Case two: this cell does not extend a subsequence
					else
					{
						dynamicArr[i][j] = Math.max(dynamicArr[i][j-1], dynamicArr[i-1][j]);
					}
				}
			}
		}
		// Report findings to user
		int ans = dynamicArr[rows-1][columns-1];
		if (ans > 0)
		{
			System.out.println("\nLength of longest common subsequence: "+ ans);
			System.out.println("A subsequence of length " + ans + ":\n");
			printLongest(s2, s2.length(), s1.length(), dynamicArr);
			System.out.println("\n");
		}
		else
		{
			System.out.println("\nThere are no common subsequences.");
		}
		return;
	} // end longest subsequence
	
	// Prints longest common subsequence given string two, 
	// length of string two, length of string one,
	// and the populated dynamic array.
	public static void printLongest(String s2, int m, int n, int[][] arr)
	{
		if (arr[m][n] == 0)
		{
			return;
		}
		if (arr[m][n] == arr[m-1][n])
		{
			printLongest(s2, m-1, n, arr);
		}
		else if (arr[m][n] == arr[m][n-1])
		{
			printLongest(s2, m, n-1, arr);
		}
		else
		{
			printLongest(s2, m-1, n-1, arr);
			System.out.print(s2.charAt(m-1));
		}
	} // end printLongest

	
	public static void main(String[] args) 
	{
		String s1 = null, s2 = null;
		Scanner kb = new Scanner(System.in);
		System.out.println("*****Longest Common Subsequence/Substring*****");
		
		// Prompt user
		System.out.println("\nEnter string 1:");
		while (s1 == null)
		{
			s1 = kb.nextLine();
		}
		System.out.println("\nEnter string 2:");
		while (s2 == null)
		{
			s2 = kb.nextLine();
		}
		
		// Dynamically solve longest common subsequence/substring problem
		solveLongestCommonSubsequence(s1, s2);
		solveLongestCommonSubstring(s1, s2);
		kb.close();
	} // end main

} // end class
