package com.example.demo;

// import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.demo.logic.MathData;
import com.example.demo.logic.RTPCalculator;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		// SpringApplication.run(DemoApplication.class, args);

		int numberOfSpins = 1_000_000_000;
		RTPCalculator rtpCalculator = new RTPCalculator(MathData.betAmount, numberOfSpins);
		rtpCalculator.calculate();
	}

}
