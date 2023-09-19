package luigi.tests;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertThrows;

@Order(2)
public class DemoRepeatedTest {

    private Main t;

    @BeforeEach
    void beforeEachTestMethod() {
        //Reruns before every @Test case
        t = new Main();
        System.out.println("\nExecuting @BeforeEach method");
    }

    @DisplayName("Division by Zero")
    @RepeatedTest(value=3, name = "{displayName}. Repetition {currentRepetition} of {totalRepetitions}")
    public void test_IntegerDivisionByZero(RepetitionInfo repetitionInfo, TestInfo testInfo) {

        System.out.println("\nRunning " + testInfo.getTestMethod().get().getName());

        int repetitionNumber = repetitionInfo.getCurrentRepetition(); //really useful for some test logics
        System.out.println("Repetition #" + repetitionNumber + " of " + repetitionInfo.getTotalRepetitions());


        // Arrange
        int dividend = 4;
        int divisor = 0;

        // Act & Assert
        assertThrows(ArithmeticException.class, () -> {
            // Act
            t.integerDivision(dividend, divisor);
        }, "Division by zero should have thrown ArithmeticException");
    }

    @AfterEach
    void afterEachTestMethod() {
        //Reruns after every @Test case (suppose you inserted a record in the Test case, now you can delete it or rollback)
        t = new Main();
        System.out.println("\nExecuting @AfterEach method\n");
    }
}
