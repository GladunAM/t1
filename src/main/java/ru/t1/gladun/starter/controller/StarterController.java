package ru.t1.gladun.starter.controller;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.t1.gladun.starter.exception.WorkingProcessException;
import ru.t1.gladun.starter.service.LogTracking;


@RestController
public class StarterController {

    @LogTracking
    @GetMapping(value = "/mstime")
    public String threadSleep(@RequestParam Long ms1,
                              @RequestParam Long ms2,
                              @RequestParam Boolean weNeedError) throws InterruptedException {
        if (weNeedError) {
            throw new WorkingProcessException("You wanted an error, you got it");
        }
        Long ms3 = ms1 + ms2;
        Thread.sleep(ms3);
        return "Method has been working in " + ms3 + "ms or a little bit more";
    }

    @ExceptionHandler(WorkingProcessException.class)
    public ResponseEntity<Object> handleException(WorkingProcessException e) {
        return new ResponseEntity<Object>(
                "Problems on the server", new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
