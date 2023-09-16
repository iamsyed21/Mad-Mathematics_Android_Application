package com.TwentyOneCodes.madmathematics;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

public class HardQuestionGenerator {


    //Operations performed- equations, BODMAS Logic, 4 operand Average, Percentages, Quad Multiplications, squares(40-50), SQRT(25-50)

    StringBuilder finalQuestion = new StringBuilder();
    ArrayList<Integer> options = new ArrayList<>();
    ArrayList<Double> doubleOptions = new ArrayList<>();
    StringBuilder questionHint = new StringBuilder();
    int theAnswer;
    double theDoubleAnswer;
    int questionType=0;

    public HardQuestionGenerator() {
        generateQuestion();
    }

    private void generateQuestion() {
        int randomNumber = (int) (Math.random() * 6 + 1);

        switch (randomNumber) {
            case 1:
                generateEquations();
                break;
            case 2:
                generateBODMAS();
                break;
            case 3:
                generateFourOperandAverage();
                break;
            case 4:
                generateQuadMultiplications();
                break;
            case 5:
                generateHardSquares();
                break;
            case 6:
                generateHardSQRT();
                break;
        }
    }

    private void generateHardSQRT() {
        int number = (int) ((Math.random() * 21) + 30);
        int squareNumber = number*number;
        finalQuestion = new StringBuilder("What is the Square Root √  of the number: " + squareNumber + " ? ");
        theAnswer = number;
        for (int i = 0; i < 6; i++) {
            int randomOption = (int) ((Math.random() * 21) + 30);
            if (randomOption == theAnswer) {
                options.add((randomOption + 1));
            } else {
                options.add(randomOption);
            }
        }
        questionHint = new StringBuilder("Square Root: \n\n Is a factor of a number that when multiplied by itself, gives the original number. \nNote: The factors of a number are defined as numbers that divide the original number evenly or exactly.\n\nThe Square root of a number is denoted by the Symbol '√' \n\nExample: The Square root of 100 is basically the factor of 100, which when multiplied to itself gives back the original number, 100. \n\nWhich is 10 x 10 = 100\nSquare root of √100 = 10. \n\nFor Larger Numbers, it helps to consider the options given to eliminate the obvious wrong answers.");
    }

    private void generateHardSquares() {
        int FirstOperand = (int) ((Math.random() * 26) + 15);

        finalQuestion = new StringBuilder("What is the Square of the Number: " + FirstOperand +"?");
        theAnswer = FirstOperand * FirstOperand;
        for (int i = 0; i < 6; i++) {
            int randomOption = (int) ((Math.random() * 1800) + 625);
            if (randomOption == theAnswer) {
                options.add((randomOption + 1));
            } else {
                options.add(randomOption);
            }
        }
        questionHint = new StringBuilder("Squaring: \n\nIt is the process of calculating the product of an Number with itself. It is basically multiplication of an number with itself. It is signified by the plus Symbol 'x' 0r '*'\n\nExample: The Square of the Numbers 10 is basically the Multiplication of the number 10 with itself\n\nWhich is 10 x 10 = 10. \n\n It is handy to remember squares of numbers 1 to 20 as they help us to guess the approximate squares of higher numbers.\n\nFor Larger Numbers, it helps to consider the options given to eliminate the obvious wrong answers.");
    }

    private void generateQuadMultiplications() {
        int FirstOperand = (int) ((Math.random() * 10) + 1);
        int SecondOperand = (int) ((Math.random() * 15) + 1);
        int ThirdOperand = (int) ((Math.random() * 10) + 1);
        int FourthOperand = (int) ((Math.random() * 15) + 1);

        finalQuestion = new StringBuilder("What is the product of the equation:\n(" + FirstOperand + " x " + SecondOperand + ") X (" + ThirdOperand +" x "+FourthOperand+")?");
        theAnswer= (FirstOperand * SecondOperand) * (ThirdOperand*FourthOperand);


        for (int i = 0; i < 6; i++) {
            int randomOption = (int) ((Math.random() * 1700) + 250);

            if (randomOption == theDoubleAnswer) {
                options.add(randomOption+1);
            } else {
                options.add(randomOption);
            }
        }

        questionHint = new StringBuilder("Multiplication: \n\nIs one of the basic operation of arithmetic. It is the process of calculating the product of two or more numbers. Multiplication is signified by the Symbols 'x' 0r '*'\n\nExample: The product of Two Numbers 10 x 5 is basically repeated addition the number '10' five time\n\nWhich is 10 x 5 = 50. \nPoint To Remember: The product inside the brackets is calculated first.\n\nFor Larger Numbers, it helps to consider the options given to eliminate the obvious wrong answers.");
    }


    private void generateFourOperandAverage() {
        questionType =1;
        int FirstOperand = (int) ((Math.random() * 101) + 1);
        int SecondOperand = (int) ((Math.random() * 51) + 1);
        int ThirdOperand = (int) ((Math.random() * 101) + 1);
        int FourthOperand = (int) ((Math.random() * 51) + 1);

        finalQuestion = new StringBuilder("What is the approximate AVERAGE of numbers:\n" + FirstOperand + " + " + SecondOperand + " + " + ThirdOperand +" + "+FourthOperand+"?");
        double x = (FirstOperand + SecondOperand + ThirdOperand+ FourthOperand)/4;
        theDoubleAnswer = round(x);

        for (int i = 0; i < 6; i++) {
            double randomOption = round((Math.random() * 71)+ 20);

            if (randomOption == theDoubleAnswer) {
                doubleOptions.add((randomOption + 1));
            } else {
                doubleOptions.add(randomOption);
            }
        }
        questionHint = new StringBuilder("Average: \n\nAverage refers to the sum of a group of values divided by 'n', where 'n' is the number of values in the group. The average is also known as the Arithmetic Mean\n\nExample: The Average of Three Numbers 10, 5, 10 is basically addition these three numbers divided by the total amount of numbers '3' \n\nWhich is (10 + 5+ 10)/3 = 8. \n\nFor Larger Numbers, it helps to consider the options given to eliminate the obvious wrong answers.");

    }

    private void generateBODMAS() {
        questionType =1;
        int randomNumber = (int) ((Math.random() * 7) + 1);
        int FirstOperand = (int) ((Math.random() * 101) + 1);
        int SecondOperand = (int) ((Math.random() * 51) + 1);
        int ThirdOperand = (int) ((Math.random() * 26) + 1);
        int FourthOperand = (int) ((Math.random() * 11) + 1);
        switch (randomNumber) {
            case 1:
                finalQuestion = new StringBuilder("What is the answer of the equation\n(" + FirstOperand + " + " + SecondOperand + ") - (" + ThirdOperand + " + " + FourthOperand+" )");
                theDoubleAnswer = round((FirstOperand + SecondOperand) - (ThirdOperand + FourthOperand));
                break;

            case 2:
                finalQuestion = new StringBuilder("What is the answer of the equation\n(" + FirstOperand + " - " + SecondOperand + " - " + ThirdOperand + ") x " + FourthOperand);
                theDoubleAnswer = round((FirstOperand - SecondOperand - ThirdOperand)* FourthOperand);
                break;

            case 3:
                finalQuestion = new StringBuilder("What is the answer of the equation\n" + FirstOperand + " - (" + SecondOperand + " / " + FourthOperand + ") x " + FourthOperand);
                theDoubleAnswer = round(FirstOperand - (SecondOperand/FourthOperand)*ThirdOperand);
                break;

            case 4:
                finalQuestion = new StringBuilder("What is the answer of the equation\n(" + FirstOperand + "/" + SecondOperand + ") - (" + ThirdOperand + " X " + FourthOperand+")");
                theDoubleAnswer = round((FirstOperand / SecondOperand) - (ThirdOperand * FourthOperand));
                break;

            case 5:
                finalQuestion = new StringBuilder("What is the answer of the equation \n" + FirstOperand + " X (" + SecondOperand + " - " + FourthOperand + " + " + ThirdOperand+" )");
                theDoubleAnswer = round(FirstOperand * (SecondOperand - FourthOperand + ThirdOperand));
                break;

            case 6:
                finalQuestion = new StringBuilder("What is the answer of the equation\n(" + FourthOperand + " X " + SecondOperand + ") - (" + FourthOperand + " X " + ThirdOperand + ")");
                theDoubleAnswer = round((FourthOperand * SecondOperand) - (ThirdOperand * FourthOperand));
                break;

            case 7:
                finalQuestion = new StringBuilder("What is the answer of the equation\n(" + FourthOperand + " X " + SecondOperand + ") + (" + FourthOperand + " X " + ThirdOperand + ")");
                theDoubleAnswer = round((FourthOperand * SecondOperand) + (ThirdOperand * FourthOperand));
                break;
        }

        for (int i = 0; i < 6; i++) {
            double randomOption = round((Math.random() * 250)+ 1);

            if (randomOption == theDoubleAnswer) {
                doubleOptions.add((randomOption + 1));
            } else {
                doubleOptions.add(randomOption);
            }
        }
        questionHint = new StringBuilder("Equations: \n\nEquations are common in Arithmetic. In mathematics, while solving Equations, we follow a common precedence called the 'BODMAS' rule\nB - BRACKETS\nO - ORDERS \nD - DIVISION\nM - MULTIPLICATION\nA - ADDITION\nS - SUBTRACTION.\n This means that an equation must be solved Brackets First, then the orders such as squares, followed by division or multiplication and at last addition or subtraction.\nNote: BODMAS rule is also known as the the PEDMAS rule \n\nExample: The Result of Two Numbers 10 x 5 + 10 is 60 \nExplanation: The multiplication sign has higher precedence according to the BODMAS rule\n Therefore, the multiplication sign is calculated first and then the product is of that number is added to the third number: =(10 x 5) + 10\n=50 +10\n=60\n Hence, the final result is 60.\n\nFor Larger Numbers, it helps to consider the options given to eliminate the obvious wrong answers.");

    }

    private void generateEquations () {
            int randomNumber = (int) ((Math.random() * 6) + 1);
            int FirstOperand = (int) ((Math.random() * 101) + 1);
            int SecondOperand = (int) ((Math.random() * 11) + 1);
            int ThirdOperand = (int) ((Math.random() * 511) + 1);
            int FourthOperand = (int) ((Math.random() * 51) + 1);
            switch (randomNumber) {
                case 1:
                    finalQuestion = new StringBuilder("What is the answer of the equation \n" + FirstOperand + " + " + SecondOperand + " - " + ThirdOperand + " + " + FourthOperand);
                    theAnswer = FirstOperand + SecondOperand + ThirdOperand + FourthOperand;
                    break;

                case 2:
                    finalQuestion = new StringBuilder("What is the answer of the equation \n" + FirstOperand + " - " + SecondOperand + " - " + ThirdOperand + " - " + FourthOperand);
                    theAnswer = FirstOperand - SecondOperand - ThirdOperand - FourthOperand;
                    break;

                case 3:
                    finalQuestion = new StringBuilder("What is the answer of the equation \n" + FirstOperand + " - " + SecondOperand + " - " + ThirdOperand + " + " + FourthOperand);
                    theAnswer = FirstOperand - SecondOperand - ThirdOperand + FourthOperand;
                    break;

                case 4:
                    finalQuestion = new StringBuilder("What is the answer of the equation \n" + FirstOperand + " + " + SecondOperand + " - " + ThirdOperand + " X " + FourthOperand);
                    theAnswer = FirstOperand + SecondOperand - ThirdOperand * FourthOperand;
                    break;

                case 5:
                    finalQuestion = new StringBuilder("What is the answer of the equation \n" + FirstOperand + " * " + SecondOperand + " - " + FourthOperand + " + " + ThirdOperand);
                    theAnswer = FirstOperand * SecondOperand - FourthOperand + ThirdOperand;
                    break;

                case 6:
                    finalQuestion = new StringBuilder("What is the answer of the equation \n" + FourthOperand + " X " + SecondOperand + " - " + ThirdOperand + " + " + FourthOperand + " + " + FirstOperand);
                    theAnswer = FourthOperand * SecondOperand - ThirdOperand + FourthOperand + FirstOperand;
                    break;

            }
            for (int i = 0; i < 6; i++) {
                int randomOption = (int) ((Math.random() * 450) + 1);
                if (randomOption == theAnswer) {
                    options.add((randomOption + 1));
                } else {
                    options.add(randomOption);
                }
            }

        questionHint = new StringBuilder("Equations: \n\nEquations are common in Arithmetic. In mathematics, while solving Equations, we follow a common precedence called the 'BODMAS' rule\nB - BRACKETS\nO - ORDERS \nD - DIVISION\nM - MULTIPLICATION\nA - ADDITION\nS - SUBTRACTION.\n This means that an equation must be solved Brackets First, then the orders such as squares, followed by division or multiplication and at last addition or subtraction.\nNote: BODMAS rule is also known as the the PEDMAS rule \n\nExample: The Result of Two Numbers 10 x 5 + 10 is 60 \nExplanation: The multiplication sign has higher precedence according to the BODMAS rule\n Therefore, the multiplication sign is calculated first and then the product is of that number is added to the third number: =(10 x 5) + 10\n=50 +10\n=60\n Hence, the final result is 60.\n\nFor Larger Numbers, it helps to consider the options given to eliminate the obvious wrong answers.");

        }

    private double round(double value){
            BigDecimal bd = BigDecimal.valueOf(value);
            bd = bd.setScale(2, RoundingMode.HALF_UP);
            return bd.doubleValue();
        }


}




