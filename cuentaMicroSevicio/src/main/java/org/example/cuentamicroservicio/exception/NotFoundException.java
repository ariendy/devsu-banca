/*
 * Copyright © 2021 pExchange, LLC. All rights reserved.
 */

package org.example.cuentamicroservicio.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends RuntimeException {
  private final HttpStatus status;

  public NotFoundException(String message) {
    super(message);
    status = HttpStatus.NOT_FOUND;
  }

  public HttpStatus getStatus() {
    return status;
  }

}
