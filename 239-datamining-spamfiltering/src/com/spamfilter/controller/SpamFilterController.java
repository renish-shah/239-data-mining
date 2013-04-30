package com.spamfilter.controller;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spamfilter.knn.KNNClassifier;
import com.spamfilter.util.SVMEngine;

/**
 * @author RENISH
 * 
 *         Only GET and POST will return values PUT and DELETE will not.
 */
@Controller
/* @RequestMapping("/provider") */
public class SpamFilterController {

	protected static Logger logger = Logger.getLogger("controller");
	SVMEngine svmEngine = new SVMEngine();

	@RequestMapping(value = "/welcome", method = RequestMethod.GET)
	public String showWelcome() {

		// model.addAttribute("message", "Spring 3 MVC Hello World");
		return "welcome";
	}

	@RequestMapping(value = "/svm", method = RequestMethod.GET)
	public String showSVMResults(Model model) {

		model.addAttribute("message", svmEngine.runSVMEngine());
		return "spamfilter";
	}

	@RequestMapping(value = "/knn", method = RequestMethod.GET)
	public String showKNNResults(Model model) {

		KNNClassifier knnClassifier = new KNNClassifier();
		model.addAttribute("message", knnClassifier.runKnn("", ""));

		// model.addAttribute("message", "Spring 3 MVC Hello World");
		return "spamfilter";
	}

	@RequestMapping(value = "/genTrain", method = RequestMethod.GET)
	public String generateTrainingFile(Model model) {

		if (svmEngine.generateTrainingArffFile())
			model.addAttribute("message",
					"Training File Generated Successfully!!!");
		else
			model.addAttribute("message",
					"Error in generating Training File!!!");

		return "welcome";
	}

	@RequestMapping(value = "/genTest", method = RequestMethod.GET)
	public String generateTestingFile(Model model) {

		if (svmEngine.generateTestingArffFile())
			model.addAttribute("message",
					"Testing File Generated Successfully!!!");
		else
			model.addAttribute("message", "Error in generating Testing File!!!");

		return "welcome";
	}

	@RequestMapping(value = "/spamfilter", method = RequestMethod.GET)
	public String showSpamFilter(Model model) {

		model.addAttribute("message", "");
		return "spamfilter";
	}

	@RequestMapping(value = "/naive", method = RequestMethod.GET)
	public String showNaiveBaysianResults() {

		// model.addAttribute("message", "Spring 3 MVC Hello World");
		return "spamfilter";
	}

	// @RequestMapping(value = "/image", method = RequestMethod.POST, headers =
	// "Accept=application/xml, application/json")
	// public @ResponseBody
	// String receiveImageData(@RequestBody String imageData) {
	// logger.debug("Provider has received request to receive new ImageData");
	// // System.out.println("==="+encodedImage+"===");
	//
	// // String imagePath="E:\\personal\\sampleProductPick.jpg";
	// ImageDecode decode = new ImageDecode();
	// String imagePath=decode.decodeStringToImage(imageData);
	// System.out.println("Image Path in receiveImageData:"+imagePath);
	// if(phase_1.isCharacterInWindow(imagePath))
	// {
	// System.out.println(phase_1.getDataInImage());
	// return phase_1.getDataInImage();
	// }
	// else {
	//
	// System.out.println(phase_1.getDataInImage());
	// return "failure";
	// }
	// // decode.decodeStringToImage(imagePath);
	//
	// // Call service to here
	//
	// }
	//
	// @RequestMapping(value = "/persons", method = RequestMethod.GET, headers =
	// "Accept=application/xml, application/json")
	// public @ResponseBody
	// PersonList getPerson() {
	// logger.debug("Provider has received request to get all persons");
	// // Call service here
	// // personService=new PersonService();
	// // phase_1 = new Phase_1();
	// // phase_1.initializeSVMClassifier();
	// //phase_1.isCharacterInWindow();
	//
	// // phase_1.probableRegion();
	//
	// String dataObtained = phase_1.getDataInImage();
	//
	// System.out.println("\nCharacters extracted: "
	// + dataObtained.substring(0, dataObtained.length()-1) + ".");
	//
	// PersonList result = new PersonList();
	// result.setData(personService.getAll());
	//
	// return result;
	// // return personService.getAll();
	// }

}