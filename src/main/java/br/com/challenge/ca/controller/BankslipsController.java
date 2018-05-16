package br.com.challenge.ca.controller;

import br.com.challenge.ca.service.BankslipsService;
import br.com.challenge.ca.vo.BankslipsUpdateVO;
import br.com.challenge.ca.vo.BankslipsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping(path = "/rest/bankslips")
public class BankslipsController {

    @Autowired
    private BankslipsService bankslipsService;

    @GetMapping
    public List<BankslipsVO> list() {
        return bankslipsService.getAll();
    }

    @GetMapping("{id}")
    public BankslipsVO byId(@PathVariable(name = "id") final String code) {
        return bankslipsService.getByCode(code);
    }

    @PutMapping("{id}")
    public BankslipsUpdateVO update(@PathVariable("id") final String code,
                                    @RequestBody final BankslipsUpdateVO updateVO) {
        return bankslipsService.updateStatus(code, updateVO);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BankslipsVO add(@RequestBody(required = false) BankslipsVO bankslips) {

        return bankslipsService.add(bankslips);
    }

}
