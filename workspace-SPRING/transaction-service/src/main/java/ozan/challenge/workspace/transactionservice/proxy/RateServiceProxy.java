package ozan.challenge.workspace.transactionservice.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ozan.challenge.workspace.transactionservice.entity.Rate;

import java.util.List;

@FeignClient(name="rate-service")
public interface RateServiceProxy {


    @RequestMapping("rates/rate/pair/{source}/{target}")
    public List<Rate> getPairRate(@PathVariable(value = "source") String source, @PathVariable(value = "target") String target);


}

