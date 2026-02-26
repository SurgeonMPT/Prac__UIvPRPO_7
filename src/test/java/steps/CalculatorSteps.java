package steps;

import calculator.Calculator;
import io.cucumber.java.ru.Дано;
import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Тогда;
import org.junit.Assert;

public class CalculatorSteps {

    private Calculator calculator;
    private double firstNumber;
    private double secondNumber;
    private double result;
    private String errorMessage;

    @Дано("калькулятор запущен")
    public void калькулятор_запущен() {
        calculator = new Calculator();
        errorMessage = null;
    }

    @Когда("пользователь вводит число {int}")
    public void пользователь_вводит_число(Integer number) {
        if (firstNumber == 0) {
            firstNumber = number;
        } else {
            secondNumber = number;
        }
    }

    @Когда("пользователь выбирает операцию {string}")
    public void пользователь_выбирает_операцию(String operation) {
        try {
            switch (operation) {
                case "сложение":
                    result = calculator.add(firstNumber, secondNumber);
                    break;
                case "вычитание":
                    result = calculator.subtract(firstNumber, secondNumber);
                    break;
                case "умножение":
                    result = calculator.multiply(firstNumber, secondNumber);
                    break;
                case "деление":
                    result = calculator.divide(firstNumber, secondNumber);
                    break;
                default:
                    errorMessage = "Неизвестная операция";
            }
        } catch (ArithmeticException e) {
            errorMessage = e.getMessage();
        }
    }

    @Тогда("результат должен быть равен {int}")
    public void результат_должен_быть_равен(Integer expected) {
        Assert.assertEquals(expected.intValue(), result, 0.001);
    }

    @Тогда("появляется предупреждение {string}")
    public void появляется_предупреждение(String expectedMessage) {
        Assert.assertEquals(expectedMessage, errorMessage);
    }
}
