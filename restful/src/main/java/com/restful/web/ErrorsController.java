package com.restful.web;

import com.core.entity.ErrorResult;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ErrorsController implements ErrorController {

    private static final String NOT_FOUND = "404";
    private static final String ERROR_PATH = "/error";

    @RequestMapping(value = ERROR_PATH)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorResult handleError() {
        return ErrorResult.NOTFOUND();
    }

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }
}
