package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import java.lang.Math;
import java.text.DecimalFormat;

import static java.lang.String.format;

public class CalcLogic {

    private  static final DecimalFormat df = new DecimalFormat("0.00");
    String s0, s1, s2, s3, s4;
    Boolean toggle = false;

    public CalcLogic() {
        this.s0 = this.s1 = this.s2 = this.s3 = this.s4 = "";
    }

    @FXML private Button Number0;
    @FXML private Button Number1;
    @FXML private Button Number2;
    @FXML private Button Number3;
    @FXML private Button Number4;
    @FXML private Button Number5;
    @FXML private Button Number6;
    @FXML private Button Number7;
    @FXML private Button Number8;
    @FXML private Button Number9;
    @FXML private Button decimal;
    @FXML private TextField display;
    @FXML private TextField display2;

    public void doCalcs(javafx.event.ActionEvent actionEvent){
        String s = ((Button)actionEvent.getSource()).getText();
        //useful only on power cases
        String y;

        if(toggle){
            if(s.charAt(0) == '1'){
                if(!s0.equals("") && s1.equals("")){
                    s0 = Double.toString(1 / Double.parseDouble(s0)); }
                else if(!s2.equals("") && s3.equals("")){
                    s2 = Double.toString(1 / Double.parseDouble(s2)); }
                else if(!s4.equals("")){
                    s4 = Double.toString(1 / Double.parseDouble(s4)); }
            }
            else if(s.charAt(0) == 's'){
                //TODO: the sin function returns a very long double, should take only few numbers
                if(!s0.equals("") && s1.equals("")){
                    s0 = df.format(Math.sin(Math.toRadians(Double.parseDouble(s0)))); }
                else if(!s2.equals("") && s3.equals("")){
                    s2 = df.format(Math.sin(Math.toRadians(Double.parseDouble(s2)))); }
                else if(!s4.equals("")){
                    s4 = df.format(Math.sin(Math.toRadians(Double.parseDouble(s4)))); }
            }
            else if(s.charAt(0) == 'c'){
                if(!s0.equals("") && s1.equals("")){
                    s0 = df.format(Math.cos(Math.toRadians(Double.parseDouble(s0)))); }
                else if(!s2.equals("") && s3.equals("")){
                    s2 = df.format(Math.cos(Math.toRadians(Double.parseDouble(s2)))); }
                else if(!s4.equals("")){
                    s4 = df.format(Math.cos(Math.toRadians(Double.parseDouble(s4)))); }
            }
            else if(s.charAt(0) == 'x'){
                if(s.charAt(1) == '²'){
                    if(!s0.equals("") && s1.equals("")){
                        s0 = Double.toString(Math.pow(Double.parseDouble(s0), 2)); }
                    else if(!s2.equals("") && s3.equals("")){
                        s2 = Double.toString(Math.pow(Double.parseDouble(s2), 2)); }
                    else if(!s4.equals("")){
                        s4 = Double.toString(Math.pow(Double.parseDouble(s4), 2)); }
                }
                else{
                    //TODO: y that should be the value of x^y takes the same ActionEvent while I would like to
                    // wait another button user input
                    y = (String.valueOf((Button)actionEvent.getSource()));
                    if(!s0.equals("") && s1.equals("")){
                        s0 = Double.toString(Math.pow(Double.parseDouble(s0), Double.parseDouble(y))); }
                    else if(!s2.equals("") && s3.equals("")){
                        s2 = Double.toString(Math.pow(Double.parseDouble(s2), Double.parseDouble(y))); }
                    else if(!s4.equals("")){
                        s4 = Double.toString(Math.pow(Double.parseDouble(s4), Double.parseDouble(y))); }
                }
            }
            changeBack();
        }
        else if ((s.charAt(0) >= '0' && s.charAt(0) <= '9') || s.charAt(0) == '.') {
            if (!s1.equals("") && s3.equals("")) {
                //s1 has an operator so the value is filled into s2
                s2 = s2 + s;
            } else if (!s3.equals((""))) {
                //s3 has operand so put in s4
                s4 = s4 + s;
            } else {
                //first inserted value
                s0 = s0 + s;
            }
        }
        else if (s.charAt(0) == 'C') {
            //clear all
            s0 = s1 = s2 = s3 = s4 = "";
        }
        else if (s.charAt(0) == 'D') {
            //clear the last filled character (idk how to do it kek)
            clearLastNotEmpty();
        }
        else if (s.charAt(0) == '=' && !s0.equals("")) {
            switchDisplay();
            s0 = Double.toString(getResult());
            s1 = s2 = s3 = s4 = "";

        }
        else {
            //branch of a operation sign input
            if ((s0.equals(""))) {
                //first value is a sign, do nothing
                return;
            } else if (s1.equals("")) {
                s1 = s1 + s;
            } else if (s3.equals("") && !s2.equals("")) {
                s3 = s3 + s;
            } else if(!s4.equals("")){
                //we filled operation so to ease the expression we resolve it
                switchDisplay();
                s0 = Double.toString(getResult());
                s1 = s;
                s2 = s3 = s4 = "";
            }
            else{
                if(s2.equals("")){
                    s1 = s;
                }
                else{
                    s3 = s;
                }
            }
        }
        display.setText(s0 + s1 + s2 + s3 + s4);
    }

    private void clearLastNotEmpty() {
        if(s1.equals("")){
            s0 = "";
        }
        else if(s2.equals("")){
            s1 = "";
        }
        else if(s3.equals("")){
            s2 = "";
        }
        else if(s4.equals("")){
            s3 = "";
        }
        else{
            s4  = "";
        }
    }

    /**
     * just a function to split the screen values and see on the upper screen (display2) the previous operands, while
     * on the first screen the result
    */
    private void switchDisplay() {
        display2.setText(s0 + s1 + s2 + s3 + s4 + "=");
    }

    /**
     * simple single operation, pretty straight forward
     */
    private double singleOperationResult(String op1, String op2, String operation) {
        switch (operation) {
            case "+" -> {
                return Double.parseDouble(op1) + Double.parseDouble(op2);
            }
            case "-" -> {
                return Double.parseDouble(op1) - Double.parseDouble(op2);
            }
            case "*" -> {
                return Double.parseDouble(op1) * Double.parseDouble(op2);
            }
            default -> {
                return Double.parseDouble(op1) / Double.parseDouble(op2);
            }
        }
    }

    /**
     * this is the core operation site, here there's the handling of the single operation case or the multi operation,
     * where precedence is needed. The precedence is handled based on the operations' order.
     */
    private double getResult() {
        //single operation case
        if(s3.equals("")){
            s0 = Double.toString((singleOperationResult(s0, s2, s1)));
        }
        //case on no precedence betweeen two operation
        else if((s1.equals("*") || s1.equals("/")) || ((s1.equals("+") && s3.equals("+")) ||
                ((s1.equals("+") && s3.equals("-"))) || ((s1.equals("-") && s3.equals("+"))) ||
                ((s1.equals("-") && s3.equals("-"))))){
            s0 = Double.toString(singleOperationResult(Double.toString(singleOperationResult(s0, s2, s1)), s4, s3));
        }
        //second operand has a precedence because is a * or a /
        else{
            s0 = Double.toString(singleOperationResult(s0, Double.toString(singleOperationResult(s2, s4, s3)), s1));
        }
        return Double.parseDouble(s0);
    }

    public void otherOperations(ActionEvent actionEvent) {
        if(!toggle){
            changeText();
        }
        else{
            changeBack();
        }
    }

    private void changeText() {
        toggle = true;
        Number0.setText("sin");
        Number1.setText("x²");
        Number2.setText("radq");
        Number3.setText("xª");
        Number4.setText("pi");
        Number5.setText("eª");
        Number6.setText("2ª");
        Number7.setText("1/x");
        Number8.setText("n!");
        Number9.setText("ln");
        decimal.setText("cos");
    }

    private void changeBack(){
        toggle = false;
        Number0.setText("0");
        Number1.setText("1");
        Number2.setText("2");
        Number3.setText("3");
        Number4.setText("4");
        Number5.setText("5");
        Number6.setText("6");
        Number7.setText("7");
        Number8.setText("8");
        Number9.setText("9");
        decimal.setText(".");
    }
}
