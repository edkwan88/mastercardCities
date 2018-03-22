package com.mastercard.cities.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

//*Ed Kwan*//
@Service("citiesService")
public class CitiesServiceImpl implements CitiesService{

	@Value("${readFromFile}")
	private String inputFile;

	@Autowired
	private List<Set<String>> processedList;
	
	@Override
	public String getConnections(String origin, String destination) {
		if (processedList.size()==0) {
			processedList = getProcessedList();
		}
		boolean found = false;
		int i=0;
		//case insensitive.
		origin = origin.toLowerCase();
		destination = destination.toLowerCase();
		while (!found && i<processedList.size()) {
			Set<String> routes = processedList.get(i);
			if (routes.contains(origin) && routes.contains(destination)) {
				found = true;
			} else {
				i++;
			}
		}
		return found?"yes" : "no";
	}

	public void setProcessedList(String file) {
		
		inputFile = file;
		processedList = getProcessedList();
	}
	public List<Set<String>> getProcessedList(){
		List<Set<String>> unprocessedList = new ArrayList<Set<String>>();
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource(inputFile).getFile());
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String st;
			while ((st = br.readLine()) != null) {
				int count = StringUtils.countOccurrencesOf(st, ",");
				if (count==1) {
					String[] tokens = st.split(Pattern.quote(","));
					tokens[0] = StringUtils.trimWhitespace(tokens[0]).toLowerCase();
					tokens[1] = StringUtils.trimWhitespace(tokens[1]).toLowerCase();
					Set<String> a = new HashSet<String>();
					a.add(tokens[0]);
					a.add(tokens[1]);
					unprocessedList.add(a);
				} else {
					//can make custom exception case for this.
					//For simplicity.  show bad line
					//and ignore this row.
					System.err.println("Bad Line input");
				}
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return processList(unprocessedList);
	}
	private List<Set<String>> processList(List<Set<String>> unprocessedList) {
		boolean dupesExist = true;
		int i = 0;
		int count = 0;
		//keep on shrinking until the unprocessed size doesnt change
		while (dupesExist && unprocessedList.size()>1) {
			int currentProcessSize = unprocessedList.size();
				HashSet<String> cities = (HashSet<String>) unprocessedList.get(0);

				//loop through cities inside each set of routes
				for (int j=i+1; j<unprocessedList.size(); j++) {
					Iterator<String> iter = cities.iterator();
					HashSet<String> currentSet = (HashSet<String>) unprocessedList.get(j);
					System.out.println("Current compare " + currentSet + " with " + cities);
					
					boolean added = false;
					while (iter.hasNext() && !added) {
						String city = (String) iter.next();
						//if any value in "currentSet" is in city, that means there is a route that can be added.
						//then we want to put it to the new tempList at index i
						//Then remove the duplicate route from templist
						//System.out.println( city);
						if (currentSet.contains(city)) {
							cities.addAll(currentSet);
							unprocessedList.remove(currentSet);
							j--;
							System.out.println("Found " + city + " --> New set:" + unprocessedList);
							added = true;
						}
					}
					System.out.println("--------------");
				}
				System.out.println("FINISHED " + ++count +" iterations");
				System.out.println("The unprocessed set is " + unprocessedList);
			if (currentProcessSize == unprocessedList.size()) {
				dupesExist = false;
			}
		}
		return unprocessedList;
	}
}
