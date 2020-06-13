package ozan.challenge.workspace.rateservice.controller;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.bind.annotation.*;
import ozan.challenge.workspace.rateservice.exception.InvalidCurrencyException;
import ozan.challenge.workspace.rateservice.service.RateServiceImpl;

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
