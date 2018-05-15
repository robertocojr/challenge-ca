package br.com.challenge.ca.controller;

import br.com.challenge.ca.entity.BankslipsEntity;
import br.com.challenge.ca.service.BankslipsService;
import br.com.challenge.ca.vo.BankslipsUpdateVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/rest/bankslips")
public class BankslipsController {

    @Autowired
    private BankslipsService bankslipsService;

    @GetMapping
    public List<BankslipsEntity> list() {
        return bankslipsService.getAll();
    }

    @GetMapping("{id}")
    public BankslipsEntity byId(@PathVariable(name = "id") final String code) {
        return bankslipsService.getByCode(code);
    }

    @PutMapping("{id}")
    public BankslipsUpdateVO update(@PathVariable("id") final String code,
                                    @RequestBody final BankslipsUpdateVO updateVO) {
        return bankslipsService.updateStatus(code, updateVO);
    }

    @PostMapping
    public BankslipsEntity add(@RequestBody BankslipsEntity bankslips) {

        return bankslipsService.add(bankslips);
    }

}
