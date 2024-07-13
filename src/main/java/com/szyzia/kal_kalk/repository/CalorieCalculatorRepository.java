package com.szyzia.kal_kalk.repository;

import com.szyzia.kal_kalk.model.CalorieCalculatorForm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CalorieCalculatorRepository extends JpaRepository<CalorieCalculatorForm, Long> {
}