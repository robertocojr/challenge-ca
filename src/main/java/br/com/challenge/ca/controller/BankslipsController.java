package br.com.challenge.ca.controller;

import br.com.challenge.ca.service.BankslipsService;
import br.com.challenge.ca.vo.BankslipsUpdateVO;
import br.com.challenge.ca.vo.BankslipsVO;
import io.swagger.annotations.*;
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

    @ApiOperation(value = "Consulta todos boletos cadastrados.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok")
    })
    @GetMapping(produces = "application/json")
    public List<BankslipsVO> list() {
        return bankslipsService.getAll();
    }

    @ApiOperation(value = "Consulta de boleto filtrado por ID, com valor atualizado", response = BankslipsVO.class,
            notes = "Boletos vencidos tem acréscimo de 0.5%, após 10 dias do vencimento acréscimo de 1%")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 400, message = "Invalid id provided - it must be a valid UUID"),
            @ApiResponse(code = 404, message = "Bankslip not found with the specified id")
    })
    @GetMapping(value = "{id}", produces = "application/json")
    public BankslipsVO byId(@PathVariable(name = "id") final String code) {
        return bankslipsService.getByCode(code);
    }

    @ApiOperation(value = "Atualiza o status de um boleto",
            notes = "PAID para confirmar o pagamento<br>CANCELED para cancelar o boleto")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Bankslip updated"),
            @ApiResponse(code = 404, message = "Bankslip not found with the specified id"),
            @ApiResponse(code = 401, message = "Invalid status provided - it must be a valid status (PAID or CANCELED)")
    })
    @PutMapping("{id}")
    public void update(@PathVariable("id") final String code,
                       @RequestBody final BankslipsUpdateVO updateVO) {
        bankslipsService.updateStatus(code, updateVO);
    }

    @ApiOperation(value = "Inseri um novo boleto no banco", response = BankslipsVO.class,
            notes = "Todos os campos são obrigatórios.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Bankslip created"),
            @ApiResponse(code = 400, message = "Bankslip not provided in the request body"),
            @ApiResponse(code = 422, message = "Invalid bankslip provided.The possible reasons are: " +
                    "A field of the provided bankslip was null or with invalid values")
    })
    @PostMapping(produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public BankslipsVO add(@RequestBody(required = false) BankslipsVO bankslips) {

        return bankslipsService.add(bankslips);
    }

}
