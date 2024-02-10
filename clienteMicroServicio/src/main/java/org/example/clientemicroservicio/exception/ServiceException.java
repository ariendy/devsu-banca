/*
 * Copyright © 2021 pExchange, LLC. All rights reserved.
 */

package org.example.clientemicroservicio.exception;

import org.springframework.http.HttpStatus;

public class ServiceException extends RuntimeException {

  private final HttpStatus status;

  public ServiceException(String message) {
    super(message);
    status = HttpStatus.BAD_REQUEST;
  }

  public ServiceException(String message, HttpStatus status) {
    super(message);
    this.status = status;
  }

  public ServiceException(String message, Throwable exception) {
    super(message, exception);
    status = HttpStatus.BAD_REQUEST;
  }

  public ServiceException(String message, HttpStatus status, Throwable exception) {
    super(message, exception);
    this.status = status;
  }

  public HttpStatus getStatus() {
    return status;
  }
}
