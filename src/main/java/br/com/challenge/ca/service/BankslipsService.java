package br.com.challenge.ca.service;

import br.com.challenge.ca.entity.BankslipsEntity;
import br.com.challenge.ca.enumeration.ErrorEnum;
import br.com.challenge.ca.exception.BankslipsInvalidFieldException;
import br.com.challenge.ca.exception.BankslipsNotFoundException;
import br.com.challenge.ca.exception.InvalidParameterException;
import br.com.challenge.ca.repository.BankslipsRepository;
import br.com.challenge.ca.vo.BankslipsUpdateVO;
import br.com.challenge.ca.vo.BankslipsVO;
import br.com.challenge.ca.vo.converter.BankslipsConverter;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

@Service
public class BankslipsService {

    private final String uuidPattern = "[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$";

    @Autowired
    private BankslipsRepository bankslipsRepository;

    public BankslipsUpdateVO updateStatus(final String code, final BankslipsUpdateVO updateVO) {
        Optional<BankslipsEntity> byCode = bankslipsRepository.findByCode(code);

        byCode.orElseThrow(BankslipsNotFoundException::new);

        byCode.ifPresent(entity -> {
            entity.setStatus(updateVO.status);
            bankslipsRepository.save(entity);
        });

        return updateVO;
    }

    public List<BankslipsVO> getAll() {
        return BankslipsConverter.toVO(bankslipsRepository.findAll());
    }

    public BankslipsVO getByCode(String code) {
        if (!code.matches(uuidPattern)) {
            throw new InvalidParameterException(ErrorEnum.BANKSLIPS_INVALID_UUID);
        }
        Optional<BankslipsEntity> byCode = bankslipsRepository.findByCode(code);

        byCode.orElseThrow(BankslipsNotFoundException::new);

        return BankslipsConverter.toVO(updateValue(byCode.get()));
    }

    private BankslipsEntity updateValue(final BankslipsEntity bankslipsEntity){
        LocalDate dueDate = LocalDate.parse(DateFormatUtils.format(bankslipsEntity.getDueDate(), "yyyy-MM-dd"));

        Period between = Period.between(LocalDate.now(), dueDate);

        if(between.getDays() < -10){
            bankslipsEntity.setTotalInCents(bankslipsEntity.getTotalInCents()
                    .multiply(new BigDecimal("0.01"))
                    .add(bankslipsEntity.getTotalInCents())
                    .setScale(0));
        } else if(between.getDays() < 0){
            bankslipsEntity.setTotalInCents(bankslipsEntity.getTotalInCents()
                    .multiply(new BigDecimal("0.005"))
                    .add(bankslipsEntity.getTotalInCents())
                    .setScale(0));
        }
        bankslipsEntity.setTotalInCents(bankslipsEntity.getTotalInCents().setScale(0));
        return bankslipsEntity;
    }

    public BankslipsVO add(final BankslipsVO bankslipsVO) {
        if (bankslipsVO == null) {
            throw new InvalidParameterException(ErrorEnum.BANKSLIPS_INVALID_BODY);
        }
        if (bankslipsVO.totalInCents == null || bankslipsVO.status == null || bankslipsVO.dueDate == null ||
                bankslipsVO.customer == null || bankslipsVO.customer.isEmpty()) {
            throw new BankslipsInvalidFieldException();
        }

        BankslipsEntity bankslipsEntity = BankslipsConverter.toEntity(bankslipsVO);
        return BankslipsConverter.toVO(bankslipsRepository.save(bankslipsEntity));
    }
}
