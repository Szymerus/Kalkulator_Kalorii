package com.szyzia.kal_kalk.controller;

import com.szyzia.kal_kalk.model.CalorieCalculatorForm;
import com.szyzia.kal_kalk.repository.CalorieCalculatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.text.DecimalFormat;

@Controller
public class CalorieCalculatorController {

    @Autowired
    private CalorieCalculatorRepository repository;

    @GetMapping("/calorie-calculator")
    public String showForm(Model model) {
        model.addAttribute("calorieCalculatorForm", new CalorieCalculatorForm());
        return "calorieCalculator";
    }

    @PostMapping("/calorie-calculator")
    public String calculate(@ModelAttribute CalorieCalculatorForm calorieCalculatorForm, Model model) {
        double bmr;
        if (calorieCalculatorForm.getGender().equalsIgnoreCase("male")) {
            bmr = 88.362 + (13.397 * calorieCalculatorForm.getWeight()) +
                    (4.799 * calorieCalculatorForm.getHeight()) - (5.677 * calorieCalculatorForm.getAge());
        } else {
            bmr = 447.593 + (9.247 * calorieCalculatorForm.getWeight()) +
                    (3.098 * calorieCalculatorForm.getHeight()) - (4.330 * calorieCalculatorForm.getAge());
        }

        double dailyCalories = bmr;
        switch (calorieCalculatorForm.getActivityLevel()) {
            case "sedentary":
                dailyCalories *= 1.2;
                break;
            case "light":
                dailyCalories *= 1.375;
                break;
            case "moderate":
                dailyCalories *= 1.55;
                break;
            case "active":
                dailyCalories *= 1.725;
                break;
            case "very active":
                dailyCalories *= 1.9;
                break;
        }


        DecimalFormat df = new DecimalFormat("#.#");
        String roundedResult = df.format(dailyCalories);
        String roundedResultToLoseWeight = df.format(dailyCalories - 300);
        String roundedResultToGainWeight = df.format(dailyCalories + 300);

        model.addAttribute("result", roundedResult);
        model.addAttribute("resultToLoseWeight", roundedResultToLoseWeight);
        model.addAttribute("resultToGainWeight", roundedResultToGainWeight);
        repository.save(calorieCalculatorForm);
        return "result";
    }
}
