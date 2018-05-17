package br.com.challenge.ca.service;

import br.com.challenge.ca.entity.BankslipsEntity;
import br.com.challenge.ca.enumeration.BankslipsStatusEnum;
import br.com.challenge.ca.enumeration.ErrorEnum;
import br.com.challenge.ca.exception.BankslipsInvalidFieldException;
import br.com.challenge.ca.exception.BankslipsNotFoundException;
import br.com.challenge.ca.exception.InvalidParameterException;
import br.com.challenge.ca.repository.BankslipsRepository;
import br.com.challenge.ca.vo.BankslipsUpdateVO;
import br.com.challenge.ca.vo.BankslipsVO;
import br.com.challenge.ca.vo.converter.BankslipsConverter;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

@Service
public class BankslipsService {

    private final static Logger LOGGER = LoggerFactory.getLogger(BankslipsService.class);

    private final String uuidPattern = "[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$";

    @Value("${bankslips.tax.due}")
    private String bankslipsTaxDue;

    @Value("${bankslips.tax.due.10}")
    private String bankslipsTaxDue10;

    @Autowired
    private BankslipsRepository bankslipsRepository;

    @Autowired
    private TaxService taxService;

    public void updateStatus(final String code, final BankslipsUpdateVO updateVO) {
        LOGGER.info("Iniciando atualizacao de status, code={}", code);
        if (BankslipsStatusEnum.PENDING.equals(updateVO.status)) {
            LOGGER.error("Status invalido, code={}, updateVO={}", code, updateVO);
            throw new InvalidParameterException(ErrorEnum.BANKSLIPS_INVALID_STATUS);
        }

        Optional<BankslipsEntity> byCode = bankslipsRepository.findByCode(code);

        byCode.orElseThrow(BankslipsNotFoundException::new);

        byCode.ifPresent(entity -> {
            entity.setStatus(updateVO.status);
            bankslipsRepository.save(entity);
            LOGGER.info("Finalizada atualizacao de status, code={}", code);
        });
    }

    public List<BankslipsVO> getAll() {
        LOGGER.info("Buscando todos boletos cadastrados");
        return BankslipsConverter.toVO(bankslipsRepository.findAll());
    }

    public BankslipsVO getByCode(String code) {
        LOGGER.info("Iniciando busca de boleto por codigo, code={}", code);
        if (!code.matches(uuidPattern)) {
            LOGGER.error("Codigo invalido, code={}", code);
            throw new InvalidParameterException(ErrorEnum.BANKSLIPS_INVALID_UUID);
        }
        Optional<BankslipsEntity> byCode = bankslipsRepository.findByCode(code);

        byCode.orElseThrow(BankslipsNotFoundException::new);

        LOGGER.info("Finalizada busca de boleto por codigo, code={}", code);
        return BankslipsConverter.toVO(updateValue(byCode.get()));
    }

    private BankslipsEntity updateValue(final BankslipsEntity bankslipsEntity) {
        LOGGER.info("Atualizando valor do boleto, code={}", bankslipsEntity.getCode());
        LocalDate dueDate = LocalDate.parse(DateFormatUtils.format(bankslipsEntity.getDueDate(), "yyyy-MM-dd"));

        Period between = Period.between(LocalDate.now(), dueDate);

        if (between.getDays() < -10) {

            bankslipsEntity.setTotalInCents(taxService
                    .incrementPercent(bankslipsEntity.getTotalInCents(), new BigDecimal(bankslipsTaxDue10)).setScale(0));
        } else if (between.getDays() < 0) {
            bankslipsEntity.setTotalInCents(taxService
                    .incrementPercent(bankslipsEntity.getTotalInCents(), new BigDecimal(bankslipsTaxDue)).setScale(0));
        }
        bankslipsEntity.setTotalInCents(bankslipsEntity.getTotalInCents().setScale(0));
        return bankslipsEntity;
    }

    public BankslipsVO add(final BankslipsVO bankslipsVO) {
        LOGGER.info("Criando novo boleto, bankslips={}", bankslipsVO);
        if (bankslipsVO == null) {
            throw new InvalidParameterException(ErrorEnum.BANKSLIPS_INVALID_BODY);
        }
        if (bankslipsVO.totalInCents == null || bankslipsVO.status == null || bankslipsVO.dueDate == null ||
                bankslipsVO.customer == null || bankslipsVO.customer.isEmpty()) {
            LOGGER.error("Falha na criacao por dados invalidos, bankslips={}", bankslipsVO);
            throw new BankslipsInvalidFieldException();
        }

        BankslipsEntity entity = BankslipsConverter.toEntity(bankslipsVO);
        BankslipsEntity bankslipsEntity = bankslipsRepository.save(entity);

        LOGGER.info("Finalizada criacao novo boleto, bankslips={}", bankslipsEntity);
        return BankslipsConverter.toVO(bankslipsEntity);
    }
}
