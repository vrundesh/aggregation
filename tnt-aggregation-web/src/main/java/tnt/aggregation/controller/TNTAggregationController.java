package tnt.aggregation.controller;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tnt.aggregation.model.Aggregation;
import tnt.aggregation.service.TNTAsyncService;

import java.util.List;
import java.util.concurrent.ExecutionException;



@Slf4j
@Api("tnt-aggregation")
@RestController
@RequiredArgsConstructor
public class TNTAggregationController {

    @Autowired
    private TNTAsyncService service;

    @RequestMapping(value = "/aggregation", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<Aggregation>  aggregatingInfo(@RequestParam List<String> pricing,
                    @RequestParam List<Integer> track, @RequestParam List<Integer> shipments) throws InterruptedException, ExecutionException {
       return new ResponseEntity<Aggregation>(service.getAllDataAggregated(pricing,track,shipments), HttpStatus.OK);
    }


}
