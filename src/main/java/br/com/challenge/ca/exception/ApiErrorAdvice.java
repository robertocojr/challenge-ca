package br.com.challenge.ca.exception;

import br.com.challenge.ca.vo.ErrorVO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@ControllerAdvice(annotations = RestController.class)
public class ApiErrorAdvice {

    @ExceptionHandler(value = { BankslipsNotFoundException.class })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorVO notFoundRequest(final BankslipsNotFoundException exception) {
        return buildErrorVO(exception);
    }

    @ExceptionHandler(value = { InvalidParameterException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorVO invalidParameterRequest(final InvalidParameterException exception) {
        return buildErrorVO(exception);
    }

    @ExceptionHandler(value = { BankslipsInvalidFieldException.class })
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ResponseBody
    public ErrorVO invalidFieldRequest(final BankslipsInvalidFieldException exception) {
        return buildErrorVO(exception);
    }

    private ErrorVO buildErrorVO(BaseException exception) {
        return new ErrorVO(exception.getError().getCode(), exception.getError().getMessage());
    }

}
