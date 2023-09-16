package com.TwentyOneCodes.madmathematics;

import java.util.ArrayList;

public class IntermediateQuestionGenerator {

    //Operations performed - cubes, squares, triple multiplication, avg, sqrt, prime numbers, simple equations
    StringBuilder finalQuestion = new StringBuilder();
    StringBuilder questionHint = new StringBuilder();
    ArrayList<Integer> options = new ArrayList<>();
    int theAnswer;

    public IntermediateQuestionGenerator(){
        generateQuestion();
    }

    private void generateQuestion() {
        int randomNumber = (int) (Math.random() * 7 + 1);
        if (randomNumber ==4) {
            randomNumber = (int) (Math.random() * 7 + 1);
        }
        switch (randomNumber) {
            case 1:
                generateCubes();
                break;
            case 2:
                generateSquares();
                break;
            case 3:
                generateTripleMultiplication();
                break;
            case 4:
                generateAverages();
                break;
            case 5:
                generateSQRT();
                break;
            case 6:
                generatePrimeNumbers();
                break;
            case 7:
                generateRandomEquation();
                break;
        }
    }

    private void generateRandomEquation() {
        int randomNumber = (int) ((Math.random() *6)+1);
        int FirstOperand = (int) ((Math.random() * 101) + 1);
        int SecondOperand = (int) ((Math.random() * 101) + 1);
        int ThirdOperand = (int) ((Math.random() * 51) + 1);
        int FourthOperand = (int) ((Math.random() * 6) + 1);
        switch(randomNumber){
            case 1:
                finalQuestion = new StringBuilder("What is the answer of the equation \n"+FirstOperand+ " + "+SecondOperand+" - "+ThirdOperand+" + "+FourthOperand);
                theAnswer = FirstOperand+SecondOperand-ThirdOperand+FourthOperand;
                break;

            case 2:
                finalQuestion = new StringBuilder("What is the answer of the equation \n"+FirstOperand+ " - "+SecondOperand+" - "+ThirdOperand+" + "+FourthOperand);
                theAnswer = FirstOperand-SecondOperand-ThirdOperand+FourthOperand;
                break;

            case 3:
                finalQuestion = new StringBuilder("What is the answer of the equation \n"+FirstOperand+ " + "+SecondOperand+" + "+ThirdOperand+" + "+FourthOperand);
                theAnswer = FirstOperand+SecondOperand+ThirdOperand+FourthOperand;
                break;

            case 4:
                finalQuestion = new StringBuilder("What is the answer of the equation \n"+FirstOperand+ " - "+SecondOperand+" - "+ThirdOperand+" - "+FourthOperand);
                theAnswer = FirstOperand-SecondOperand-ThirdOperand-FourthOperand;
                break;

            case 5:
                finalQuestion = new StringBuilder("What is the answer of the equation \n"+FirstOperand+ " + "+SecondOperand+" X "+FourthOperand);
                theAnswer = FirstOperand+SecondOperand*FourthOperand;
                break;

            case 6:
                finalQuestion = new StringBuilder("What is the answer of the equation \n"+FourthOperand+ " X "+SecondOperand+" - "+ThirdOperand+" X "+FourthOperand);
                theAnswer = FourthOperand*SecondOperand-ThirdOperand*FourthOperand;
                break;

        }
        for (int i = 0; i < 3; i++) {
            int randomOption = (int) ((Math.random() * 450) + 1);
            if (randomOption == theAnswer) {
                options.add((randomOption + 1));
            } else {
                options.add(randomOption);
            }
        }

        questionHint = new StringBuilder("Equations: \n\nEquations are common in Arithmetic. In mathematics, while solving Equations, we follow a common precedence called the 'BODMAS' rule\nB - BRACKETS\nO - ORDERS \nD - DIVISION\nM - MULTIPLICATION\nA - ADDITION\nS - SUBTRACTION.\n This means that an equation must be solved Brackets First, then the orders such as squares, followed by division or multiplication and at last addition or subtraction.\nNote: BODMAS rule is also known as the the PEDMAS rule \n\nExample: The Result of Two Numbers 10 x 5 + 10 is 60 \nExplanation: The multiplication sign has higher precedence according to the BODMAS rule\n Therefore, the multiplication sign is calculated first and then the product is of that number is added to the third number: =(10 x 5) + 10\n=50 +10\n=60\n Hence, the final result is 60.\n\nFor Larger Numbers, it helps to consider the options given to eliminate the obvious wrong answers.");
    }

    private void generatePrimeNumbers() {
        int [] primeNumbers = {3,5,7,11,13,17,19,23,29,31,37,41,43,47,53,59,61,67,71,73,79,83,89,97
                ,101,103,107,109,113,127,131,137,139,149,151,157,163,167,173,179
                ,181,191,193,197,199,211,223,227,229,233,239,241,251,257,263,269
                ,271,277,281,283,293,307,311,313,317,331,337,347,349,353,359,367
                ,373,379,383,389,397,401,409,419,421,431,433,439,443,449,457,461
                ,463,467,479,487,491,499};
        int randomNumber = (int) ((Math.random() *93)+0);
        finalQuestion = new StringBuilder("Which of the following Options Is a Prime Number ?");
        theAnswer = primeNumbers[randomNumber];

        for (int i = 0; i < 3; i++) {
            int randomOption = (int) ((Math.random() * 498) + 3);
            int randomSQRT = (int) Math.sqrt(randomOption);
            boolean isPrime = true;
            if (randomOption == theAnswer) {
                options.add((randomOption + 1));
            }
            for(int j=2; j<randomSQRT; j++){
                if(randomOption% j ==0)
                    isPrime = false;
            }
            if(isPrime){
                options.add(randomOption+1);
            }else{
                options.add(randomOption);
            }
        }
        questionHint = new StringBuilder("Prime Number: \n\n A prime number is a whole number than cannot be exactly divided by any whole number other than itself and 1\n\nExample: The number 3,5,7 are examples of prime numbers Because they can only be divided by 1 or by themselves respectively.\n\nIt is handy to remember prime numbers as many prime numbers as possible.\n\nPoints To remember:\n- 2 is the Only even prime number, which means any even number except two is not prime\n\nFor Larger Numbers, it helps to consider the options given to eliminate the obvious wrong answers.");

    }

    private void generateSQRT() {
        int number = (int) ((Math.random() * 30) + 1);
        int squareNumber = number*number;
        finalQuestion = new StringBuilder("What is the Square Root √ of the number: " + squareNumber + " ?");
        theAnswer = number;
        for (int i = 0; i < 3; i++) {
            int randomOption = (int) ((Math.random() * 30) + 1);
            if (randomOption == theAnswer) {
                options.add((randomOption + 1));
            } else {
                options.add(randomOption);
            }
        }

        questionHint = new StringBuilder("Square Root: \n\n Is a factor of a number that when multiplied by itself, gives the original number. \nNote: The factors of a number are defined as numbers that divide the original number evenly or exactly.\n\nThe Square root of a number is denoted by the Symbol '√' \n\nExample: The Square root of 100 is basically the factor of 100, which when multiplied to itself gives back the original number, 100. \n\nWhich is 10 x 10 = 100\nSquare root of √100 = 10. \n\nFor Larger Numbers, it helps to consider the options given to eliminate the obvious wrong answers.");

    }

    private void generateAverages() {
        int numberOfOperands = (int) ((Math.random() * 2) + 2);
        int FirstOperand = (int) ((Math.random() * 101) + 1);
        int SecondOperand = (int) ((Math.random() * 51) + 1);

        if (numberOfOperands == 3) {
            int thirdOperand = (int) ((Math.random() * 51) + 1);
            finalQuestion = new StringBuilder("What is the approximate AVERAGE of \n" + FirstOperand + " + " + SecondOperand + " + " + thirdOperand + " ? ");
            theAnswer = (FirstOperand + SecondOperand + thirdOperand)/3;
        } else {
            finalQuestion = new StringBuilder("What is the approximate AVERAGE of \n" + FirstOperand + " + " + SecondOperand + "?");
            theAnswer = (FirstOperand + SecondOperand)/2;
        }

        for (int i = 0; i < 3; i++) {
            int randomOption = (int) ((Math.random() * 101) + 0);
            if (randomOption == theAnswer) {
                options.add((randomOption + 10));
            } else {
                options.add(randomOption);
            }
        }

        questionHint = new StringBuilder("Average: \n\nAverage refers to the sum of a group of values divided by 'n', where 'n' is the number of values in the group. The average is also known as the Arithmetic Mean\n\nExample: The Average of Three Numbers 10, 5, 10 is basically addition these three numbers divided by the total amount of numbers '3' \n\nWhich is (10 + 5+ 10)/3 = 8. \nNote: In this Round, the decimal point is rounded off to its nearest value.\n\nFor Larger Numbers, it helps to consider the options given to eliminate the obvious wrong answers.");
    }

    private void generateTripleMultiplication() {
        int FirstOperand = (int) ((Math.random() * 16) + 1);
        int SecondOperand = (int) ((Math.random() * 11) + 1);
        int thirdOperand = (int) ((Math.random() * 11) + 1);


        finalQuestion = new StringBuilder("What is the Product of \n" + FirstOperand + " X " + SecondOperand + " X " + thirdOperand + " ? ");
        theAnswer = FirstOperand * SecondOperand * thirdOperand;


        for (int i = 0; i < 3; i++) {
            int randomOption = (int) ((Math.random() * 581) + 20);
            if (randomOption == theAnswer) {
                options.add((randomOption + 1));
            } else {
                options.add(randomOption);
            }
        }
        questionHint = new StringBuilder("Multiplication: \n\nIs one of the basic operation of arithmetic. It is the process of calculating the product of two or more numbers. Multiplication is signified by the Symbols 'x' 0r '*'\n\nExample: The product of Two Numbers 10 x 5 is basically repeated addition the number '10' five time\n\nWhich is 10 x 5 = 50. \n\nFor Larger Numbers, it helps to consider the options given to eliminate the obvious wrong answers.");
    }

    private void generateSquares() {
        int FirstOperand = (int) ((Math.random() * 24) + 2);

        finalQuestion = new StringBuilder("What is the Square of the Number: " + FirstOperand +"?");
        theAnswer = FirstOperand * FirstOperand;
        for (int i = 0; i < 3; i++) {
            int randomOption = (int) ((Math.random() * 601) + 300);
            if (randomOption == theAnswer) {
                options.add((randomOption + 1));
            } else {
                options.add(randomOption);
            }
        }
        questionHint = new StringBuilder("Squaring: \n\nIt is the process of calculating the product of an Number with itself. It is basically multiplication of an number with itself. It is signified by the plus Symbol 'x' 0r '*'\n\nExample: The Square of the Numbers 10 is basically the Multiplication of the number 10 with itself\n\nWhich is 10 x 10 = 10. \n\n It is handy to remember squares of numbers 1 to 20 as they help us to guess the approximate squares of higher numbers.\n\nFor Larger Numbers, it helps to consider the options given to eliminate the obvious wrong answers.");
    }

    private void generateCubes() {
        int FirstOperand = (int) ((Math.random() * 11) + 2);
        finalQuestion = new StringBuilder("What is the Cube of the Number: " + FirstOperand +"?");
        theAnswer = FirstOperand * FirstOperand * FirstOperand;
        for (int i = 0; i < 3; i++) {
            int randomOption = (int) ((Math.random() * 600) + 20);
            if (randomOption == theAnswer) {
                options.add((randomOption + 1));
            } else {
                options.add(randomOption);
            }

        }

        questionHint = new StringBuilder("Cubes: \n\nA Cube Number is the result when an number has been multiplied by itself twice. It is basically multiplication of an number with itself two times. \n\nExample: The Cube of the Numbers 10 is basically the Multiplication of the number 10 three times\n\nWhich is 10 x 10 x 10= 1000. \n\nFor Larger Numbers, it helps to consider the options given to eliminate the obvious wrong answers.");

    }

}
