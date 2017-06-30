package it.consoft.budget.cucumber.stepdefs;

import it.consoft.budget.BudgetApp;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.boot.test.context.SpringBootTest;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = BudgetApp.class)
public abstract class StepDefs {

    protected ResultActions actions;

}
