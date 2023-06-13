package com.simpletask.exception.handler;

import com.simpletask.exception.*;
import com.simpletask.payload.response.incorrect.*;
import com.simpletask.security.auth.UserExistsException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class AppExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(IncorrectStateException.class)
    public ResponseEntity<Object> handleIncorrectStateException(IncorrectStateException e, WebRequest request) {
        return new ResponseEntity<>(new IncorrectStateResponse(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(RewardNotTakenException.class)
    public ResponseEntity<Object> handleRewardNotTakenException(RewardNotTakenException e, WebRequest request) {
        return new ResponseEntity<>(new RewardNotTakenResponse(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<Object> handleTokenExpiredException(TokenExpiredException e, WebRequest request) {
        return new ResponseEntity<>(new JwtExpiredResponse(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserExistsException.class)
    public ResponseEntity<Object> handleUserExistsException(UserExistsException e, WebRequest request) {
        return new ResponseEntity<>(new UserExistsResponse(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(LevelDontMatchException.class)
    public ResponseEntity<Object> handleLevelDontMatchException(LevelDontMatchException e, WebRequest request) {
        return new ResponseEntity<>(new LevelDontMatchResponse(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<Object> handleTaskNotFoundException(TaskNotFoundException e, WebRequest request) {
        return new ResponseEntity<>(new TaskNotFoundResponse(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RewardNotFoundException.class)
    public ResponseEntity<Object> handleRewardNotFoundException(RewardNotFoundException e, WebRequest request) {
        return new ResponseEntity<>(new RewardNotFoundResponse(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException e, WebRequest request) {
        return new ResponseEntity<>(new UserNotFoundResponse(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(LinkedTasksFoundException.class)
    public ResponseEntity<Object> handleLinkedTasksFoundException(LinkedTasksFoundException e, WebRequest request) {
        return new ResponseEntity<>(new LinkedTasksFoundResponse(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(LinkedRewardFoundException.class)
    public ResponseEntity<Object> handleLinkedRewardsFoundException(LinkedRewardFoundException e, WebRequest request) {
        return new ResponseEntity<>(new LinkedRewardFoundResponse(), HttpStatus.BAD_REQUEST);
    }


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        List<String> errors = new ArrayList<String>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }

        ApiError apiError =
                new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
        return handleExceptionInternal(
                ex, apiError, headers, HttpStatus.BAD_REQUEST, request);
    }
}
