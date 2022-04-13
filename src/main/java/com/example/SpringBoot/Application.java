package com.example.SpringBoot;

import com.example.SpringBoot.Modules.CatalogModule;
import com.example.SpringBoot.Services.FileInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Scanner;

@SpringBootApplication
public class Application {

	FileInfoService fileInfoService = new FileInfoService();

	private final String pathToFile = System.getProperty("user.dir") + "\\testDirectory\\testMp3.mp3";

	public Application() {
		work();
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	private void work() {
		System.out.println("Path to file: " + pathToFile);

		File file = new File(pathToFile);
		if (!file.exists()) {
			System.out.println("File not exists");
			return;
		}

		Map<String, Method> descriptionsToFunctions = fileInfoService.getDescriptionsToFunctions(file);

		if (descriptionsToFunctions.size() == 0) {
			System.out.println("Can not read file data");
			return;
		}

		System.out.println("Possible functions:");
		String[] descriptions = descriptionsToFunctions.keySet().toArray(new String[0]);
		for (int i = 0; i < descriptions.length; i++) {
			System.out.println(i + " - " + descriptions[i]);
		}
		System.out.println();

		int functionNumber = new Scanner(System.in).nextInt();

		Method function = descriptionsToFunctions.get(descriptions[functionNumber]);

		try {
			String answer = (String) function.invoke(function.getDeclaringClass().newInstance(), file);
			System.out.println(answer);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
