package com.assignment.account.controller;
import com.assignment.account.exception.InvalidCurrencyException;
import com.assignment.account.service.RateServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/rates")
public class RateServiceController {

    @Autowired
    private RateServiceImpl rateService;

    @Value("${rate.service.url}")
    private String apiURL;

    @ApiOperation(value = "Get all rates", response = Object.class)
    @GetMapping("/ratesall")
    public Object getAllRates() {
        return rateService.getRates(apiURL);
    }

    @ApiOperation(value = "Get specific rate", response = Object.class)
    @GetMapping("/rate/{type}")
    public Object getSpecificRate(@PathVariable(value = "type") String type) throws InvalidCurrencyException {
        return rateService.getSpecificTypeRate(type,apiURL);
    }

    @ApiOperation(value = "Get pair rates", response = Object.class)
    @GetMapping("/rate/pair/{source}/{target}")
    public Object getPairRate(@PathVariable(value = "source") String source,@PathVariable(value = "target") String target) throws InvalidCurrencyException {
        return rateService.getPairRate(source,target,apiURL);
    }

}
