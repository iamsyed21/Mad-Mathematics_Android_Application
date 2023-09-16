package com.TwentyOneCodes.madmathematics;

import java.util.ArrayList;
import java.util.Random;

public class EasyQuestionGenerator {
    // operations performed + , - , *  , / and squares.
    StringBuilder finalQuestion = new StringBuilder();
    StringBuilder questionHint = new StringBuilder();
    ArrayList<Integer> options = new ArrayList<>();
    int theAnswer;


    public EasyQuestionGenerator() {
        generateQuestion();
    }


    private void generateQuestion() {
        int randomNumber = (int) (Math.random() * 5 + 1);
        if (randomNumber == 4) {
            randomNumber = (int) (Math.random() * 5 + 1);
        }
        switch (randomNumber) {
            case 1:
                generateAddition();
                break;
            case 2:
                generateSubraction();
                break;
            case 3:
                generateMultiplication();
                break;
            case 4:
                generateDivision();
                break;
            case 5:
                generateSquares();
                break;
        }
    }

    private void generateSquares() {
        int FirstOperand = (int) ((Math.random() * 22) + 1);
        int SecondOperand = FirstOperand;

        finalQuestion = new StringBuilder("What is the Square of the Number: " + FirstOperand +"?");
        theAnswer = FirstOperand * SecondOperand;
        for (int i = 0; i < 3; i++) {
            int randomOption = (int) ((Math.random() * 251) + 0);
            if (randomOption == theAnswer) {
                options.add((randomOption + 1));
            } else {
                options.add(randomOption);
            }

        }

        questionHint = new StringBuilder("Squaring: \n\nIt is the process of calculating the product of an Number with itself. It is basically multiplication of an number with itself. It is signified by the plus Symbol 'x' 0r '*'\n\nExample: The Square of the Numbers 10 is basically the Multiplication of the number 10 with itself\n\nWhich is 10 x 10 = 10. \n\nFor Larger Numbers, it helps to consider the options given to eliminate the obvious wrong answers.");

    }

    private void generateDivision() {
        int[] firstOperator = {10, 15, 20, 25, 30, 35, 40, 50, 60, 70, 80, 90, 100, 45, 55, 65, 75, 85, 95,};
        int[] secondOperator = {5, 10};
        int randomFromArr = (int) ((Math.random() * 20) + 1);
        int randomFromArr2 = (int) ((Math.random() * 2) + 0);


        finalQuestion = new StringBuilder("What is the Result of: \n" + firstOperator[randomFromArr] + "/" + secondOperator[randomFromArr2] + "?");
        theAnswer = firstOperator[randomFromArr] / secondOperator[randomFromArr2];
        for (int i = 0; i < 3; i++) {
            int randomOption = (int) ((Math.random() * 101) + 0);
            if (randomOption == theAnswer) {
                options.add((randomOption + 1));
            } else {
                options.add(randomOption);
            }
        }

        questionHint = new StringBuilder("Division: \n\nIs one of the basic operation of arithmetic. It is also the opposite of Multiplication. It is the progress of splitting a specific amount into equal parts. Division is signified by the division Symbol '/'\n\nExample: The result of dividing the Two Numbers 10 / 5 is basically dividing the number '10' five time\n\n Which is 10 / 5 = 2.\n\nPoints to Remember:\n- A Number divided by itself is always 1.\n- No Number can be divided by 0, any number divided by zero gives an Undefined Answer\n- A number divided by 1 results in the number itself.\n\nFor Larger Numbers, it helps to consider the options given to eliminate the obvious wrong answers.");

    }

    private void generateSubraction () {
            int FirstOperand = (int) ((Math.random() * 100) + 0);
            int SecondOperand = (int) ((Math.random() * 100) + 0);

            finalQuestion = new StringBuilder("What is the Difference of: \n" + FirstOperand + " - " + SecondOperand + "?");
            theAnswer = FirstOperand - SecondOperand;
            for (int i = 0; i < 3; i++) {
                int randomOption = (int) (new Random().nextInt(300) - 150);
                if (randomOption == theAnswer) {
                    options.add((randomOption + 1));
                } else {
                    options.add(randomOption);
                }

            }
        questionHint = new StringBuilder("Subtraction: \n\nIs one of the basic operation of arithmetic. It is the process of taking away a number from another number. Subtraction is signified by the Subtraction Symbol '-'\n\nExample: The Result of subtraction Two Numbers 10 - 5 is basically the difference of the two numbers\n\n Which is 10 - 5 = 5. \n\nPoints to Remember: \n- If a Bigger Number is being subtracted from an smaller number say\n5 - 10\n The result will be an Negative Number\nWhich is 5 - 10 = -5\n- Subtracting two Negative numbers is the same as adding a positive, the resulting number will be more than the original negative with the Negative Symbol '-'\n\nFor Larger Numbers, it helps to consider the options given to eliminate the obvious wrong answers.");
        }

    private void generateMultiplication () {
            int FirstOperand = (int) ((Math.random() * 20) + 1);
            int SecondOperand = (int) ((Math.random() * 10) + 1);

            finalQuestion = new StringBuilder("What is the Product of: \n" + FirstOperand + " x " + SecondOperand + "?");
            theAnswer = FirstOperand * SecondOperand;
            for (int i = 0; i < 3; i++) {
                int randomOption = (int) ((Math.random() * 149) + 1);
                if (randomOption == theAnswer) {
                    options.add((randomOption + 1));
                } else {
                    options.add(randomOption);
                }
            }

        questionHint = new StringBuilder("Multiplication: \n\nIs one of the basic operation of arithmetic. It is the process of calculating the product of two or more numbers. Multiplication is signified by the Symbols 'x' 0r '*'\n\nExample: The product of Two Numbers 10 x 5 is basically repeated addition the number '10' five time\n\nWhich is 10 x 5 = 50. \n\nFor Larger Numbers, it helps to consider the options given to eliminate the obvious wrong answers.");

        }

    private void generateAddition () {
            int numberOfOperands = (int) ((Math.random() * 2) + 2);
            int FirstOperand = (int) ((Math.random() * 100) + 0);
            int SecondOperand = (int) ((Math.random() * 100) + 0);

            if (numberOfOperands == 3) {
                int thirdOperand = (int) ((Math.random() * 10) + 0);
                finalQuestion = new StringBuilder("What is the Sum of \n" + FirstOperand + " + " + SecondOperand + " + " + thirdOperand + " ? ");
                theAnswer = FirstOperand + SecondOperand + thirdOperand;
            } else {
                finalQuestion = new StringBuilder("What is the Sum of \n" + FirstOperand + " + " + SecondOperand + "?");
                theAnswer = FirstOperand + SecondOperand;
            }

            for (int i = 0; i < 3; i++) {
                int randomOption = (int) ((Math.random() * 200) + 20);
                if (randomOption == theAnswer) {
                    options.add((randomOption + 1));
                } else {
                    options.add(randomOption);
                }
            }
        questionHint = new StringBuilder("ADDITION: \n\nIs one of the basic operation of arithmetic. It is Adding the value of two or more numbers to make a new total. Addition is signified by the plus Symbol '+'\n\nFor Example: The Addition of Two Numbers 10 and 5 is basically the total value of the two numbers\n\nwhich is 10 + 5 = 15. \n\nFor Larger Numbers, it helps to consider the options given to eliminate the obvious wrong answers.");

        }

}
