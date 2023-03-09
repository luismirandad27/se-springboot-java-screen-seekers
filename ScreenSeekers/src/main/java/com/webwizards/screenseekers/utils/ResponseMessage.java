/**
 * Java Class: ResponseMessage
 * 
 * ------------
 * Description:
 * ------------ 
 * This class consists on the structure of a response message when a API method does not return any object
 * 
 * @author Luis Miguel MIranda
 * @version 1.0
 **/

package com.webwizards.screenseekers.utils;

public class ResponseMessage {
	
  private String message;

  public ResponseMessage(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

}
