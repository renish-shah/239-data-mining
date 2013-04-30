package com.spamfilter.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.spamfilter.knn.KNNClassifier;
import com.spamfilter.naivebayes.filter.NaiveBayes;
import com.spamfilter.svm.SVMEngine;


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

	@RequestMapping(value = "/svmSS", method = RequestMethod.GET)
	public String showSVMSSResults(Model model) {

		model.addAttribute("message", svmEngine.runSVMEngine(true, true));
		return "spamfilter";
	}
	
	@RequestMapping(value = "/svmNN", method = RequestMethod.GET)
	public String showSVMNNResults(Model model) {

		model.addAttribute("message", svmEngine.runSVMEngine(false, false));
		return "spamfilter";
	}
	@RequestMapping(value = "/svmSN", method = RequestMethod.GET)
	public String showSVMSNResults(Model model) {

		model.addAttribute("message", svmEngine.runSVMEngine(true, false));
		return "spamfilter";
	}
	@RequestMapping(value = "/svmNS", method = RequestMethod.GET)
	public String showSVMNSResults(Model model) {

		model.addAttribute("message", svmEngine.runSVMEngine(false, true));
		return "spamfilter";
	}

	

	@RequestMapping(value = "/knnSpam", method = RequestMethod.GET)
	public String showKNNSpamResults(Model model) {

		KNNClassifier knnClassifier = new KNNClassifier();
		model.addAttribute("message", knnClassifier.runKnn("", "", true));

		// model.addAttribute("message", "Spring 3 MVC Hello World");
		return "spamfilter";
	}
	@RequestMapping(value = "/knnNonSpam", method = RequestMethod.GET)
	public String showKNNnonSpamResults(Model model) {

		KNNClassifier knnClassifier = new KNNClassifier();
		model.addAttribute("message", knnClassifier.runKnn("", "", false));

		// model.addAttribute("message", "Spring 3 MVC Hello World");
		return "spamfilter";
	}


	@RequestMapping(value = "/genSpamTrain", method = RequestMethod.GET)
	public String generateSpamTrainingFile(Model model) {

		if (svmEngine.generateTrainingArffFile(true))
			model.addAttribute("message",
					"Training File Generated Successfully!!!");
		else
			model.addAttribute("message",
					"Error in generating Training File!!!");

		return "welcome";
	}

	@RequestMapping(value = "/genSpamTest", method = RequestMethod.GET)
	public String generateSpamTestingFile(Model model) {

		if (svmEngine.generateTestingArffFile(true))
			model.addAttribute("message",
					"Testing File Generated Successfully!!!");
		else
			model.addAttribute("message", "Error in generating Testing File!!!");

		return "welcome";
	}
	
	@RequestMapping(value = "/genNonSpamTrain", method = RequestMethod.GET)
	public String genNonSpamTrainingFile(Model model) {

		if (svmEngine.generateTrainingArffFile(false))
			model.addAttribute("message",
					"Training File Generated Successfully!!!");
		else
			model.addAttribute("message",
					"Error in generating Training File!!!");

		return "welcome";
	}

	@RequestMapping(value = "/genNonSpamTest", method = RequestMethod.GET)
	public String genNonSpamTestingFile(Model model) {

		if (svmEngine.generateTestingArffFile(false))
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

	@RequestMapping(value = "/naiveTestSpam", method = RequestMethod.GET)
	public String showNBSpamResults(Model model) {
		NaiveBayes naiveBayes=new NaiveBayes();
		model.addAttribute("message", naiveBayes.checkNaiveBayes("spam"));
		return "spamfilter";
	}
	
	@RequestMapping(value = "/naiveTestNonSpam", method = RequestMethod.GET)
	public String showNBNonSpamResults(Model model) {
		NaiveBayes naiveBayes=new NaiveBayes();
		model.addAttribute("message", naiveBayes.checkNaiveBayes("nonspam"));
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