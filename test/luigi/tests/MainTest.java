package luigi.tests;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import static org.junit.jupiter.api.Assertions.*;

@Order(3)
class MainTest {

    Main t;

    @BeforeAll
    static void setUp() {
        //Runs once before everything
        System.out.println("Executing @BeforeAll method");
    }

    @BeforeEach
    void beforeEachTestMethod() {
        //Reruns before every @Test case
        t = new Main();
        System.out.println("Executing @BeforeEach method");
    }


    @Test
    public void test_sum() {
        System.out.println("\nRunning test_sum\n");
        Assertions.assertEquals(2, t.sum(1));
    }

    @Test
    public void test_substract() {
        System.out.println("\nRunning test_substract\n");
        Assertions.assertEquals(0, t.subtract(1));
    }

    @Test
    public void test_IntegerDivisionByZero() {
        System.out.println("\nRunning test_IntegerDivisionByZero\n");

        // Arrange
        int dividend = 4;
        int divisor = 0;

        // Act & Assert
        assertThrows(ArithmeticException.class, () -> {
            // Act
            t.integerDivision(dividend, divisor);
        }, "Division by zero should have thrown ArithmeticException");
    }

    @DisplayName("Test Integer subtraction [minuend, subtrahend, expectedResult] ")
    @ParameterizedTest
//    @CsvSource({
//            "4,2,2",
//            "256, 2, 128",
//            "10, 5, 2"
//    })
    @CsvFileSource(resources = "/integerDivision.csv")
    public void test_IntegerDivision(int dividend, int divisor, int expectedResult) {
        System.out.println("\nRunning test_IntegerDivision\n");

        Assertions.assertEquals(expectedResult, t.integerDivision(dividend, divisor));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Luigi", "Caio", "Matheus"})
    public void valueSourceDemo(String name) {
        System.out.println(name);
        assertNotNull(name);
    }




    @AfterEach
    void afterEachTestMethod() {
        //Reruns after every @Test case (suppose you inserted a record in the Test case, now you can delete it or rollback)
        t = new Main();
        System.out.println("Executing @AfterEach method");
    }

    @AfterAll
    static void cleanUp() {
        System.out.println("Executing @AfterAll method");
    }
}