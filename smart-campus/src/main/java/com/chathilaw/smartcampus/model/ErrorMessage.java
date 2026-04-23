/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.chathilaw.smartcampus.model;

/**
 * Represents an error message returned by the API.
 * 
 * @author Chathila Wijesinghe
 */
public class ErrorMessage {

    private String errorMessage; // Stores the human-readable error description
    private int errorCode; // Stores the numeric HTTP status code or custom error code
    private String documentation; // Stores a link or reference to documentation about the error

    public ErrorMessage() {}

    public ErrorMessage(String errorMessage, int errorCode, String documentation) {
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
        this.documentation = documentation;
    }

    // Getter for the error message
    public String getErrorMessage() { return errorMessage; }
    // Setter for the error message
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }

    // Getter for the error code
    public int getErrorCode() { return errorCode; }
    // Setter for the error code
    public void setErrorCode(int errorCode) { this.errorCode = errorCode; }

    // Getter for the documentation link
    public String getDocumentation() { return documentation; } 
    // Setter for the documentation link
    public void setDocumentation(String documentation) { this.documentation = documentation; }
}
