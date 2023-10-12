package com.example.backend.common.error;

import com.example.backend.common.error.code.ExceptionCode;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {
  private int status;
  private String message;
  private List<FieldError> fieldErrors;
  private List<ConstraintViolationError> violationErrors;

  private ErrorResponse(int status, String message) {
    this.status = status;
    this.message = message;
  }

  private ErrorResponse(final List<FieldError> fieldErrors,
      final List<ConstraintViolationError> violationErrors) {
    this.fieldErrors = fieldErrors;
    this.violationErrors = violationErrors;
  }
  private ErrorResponse(int status, String message,final List<FieldError> fieldErrors,
      final List<ConstraintViolationError> violationErrors) {
    this.status = status;
    this.message = message;
    this.fieldErrors = fieldErrors;
    this.violationErrors = violationErrors;
  }
  public static ErrorResponse of(BindingResult bindingResult) {
    return new ErrorResponse(FieldError.of(bindingResult), null);
  }

  public static ErrorResponse of(Set<ConstraintViolation<?>> violations) {
    return new ErrorResponse(null, ConstraintViolationError.of(violations));
  }

  public static ErrorResponse of(ExceptionCode exceptionCode) {
    return new ErrorResponse(exceptionCode.getStatus(), exceptionCode.getMessage());
  }

  public static ErrorResponse of(HttpStatus httpStatus) {
    return new ErrorResponse(httpStatus.value(), httpStatus.getReasonPhrase());
  }

  public static ErrorResponse of(HttpStatus httpStatus, String message) {
    return new ErrorResponse(httpStatus.value(), message);
  }
  public static void setToResponse(HttpServletResponse response, HttpStatus httpStatus, String message) throws IOException {
    ErrorResponse errorResponse = ErrorResponse.of(httpStatus);

    response.setStatus(httpStatus.value());
    response.setContentType("application/json;charset=UTF-8");

    ObjectMapper objectMapper = new ObjectMapper();
    String json = objectMapper.writeValueAsString(errorResponse);

    response.getWriter().write(json);
  }
  /* 잘못된 필드가 넘어 왔을 때*/
  @Getter
  @AllArgsConstructor
  public static class FieldError {
    private String field;
    private Object rejectedValue;
    private String reason;

    public static List<FieldError> of(BindingResult bindingResult) {
      final List<org.springframework.validation.FieldError> fieldErrors =
          bindingResult.getFieldErrors();
      return fieldErrors.stream()
          .map(error -> new FieldError(
              error.getField(),
              error.getRejectedValue() == null ?
                  "" : error.getRejectedValue().toString(),
              error.getDefaultMessage()))
          .collect(Collectors.toList());
    }
  }

  /* 유효성 위반할때 */
  @Getter
  @AllArgsConstructor
  public static class ConstraintViolationError {
    private String propertyPath;
    private Object rejectedValue;
    private String reason;

    public static List<ConstraintViolationError> of(
        Set<ConstraintViolation<?>> constraintViolations) {
      return constraintViolations.stream()
          .map(constraintViolation -> new ConstraintViolationError(
              constraintViolation.getPropertyPath().toString(),
              constraintViolation.getInvalidValue().toString(),
              constraintViolation.getMessage()
          )).collect(Collectors.toList());
    }
  }
}