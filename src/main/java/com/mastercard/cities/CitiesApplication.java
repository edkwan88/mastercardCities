//*Ed Kwan*//

package com.mastercard.cities;

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

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.util.StringUtils;

@SpringBootApplication
public class CitiesApplication {
	
	@Bean(name="processedList")
	public List<Set<String>> setProcessedList(){
		return new ArrayList<Set<String>>();
	}
	public static void main(String[] args) {
		SpringApplication.run(CitiesApplication.class, args);
	}
	
/*	@Bean(name="inputFile")
	public String updateFile() {
		return inputFile;
	}
	*/

}
