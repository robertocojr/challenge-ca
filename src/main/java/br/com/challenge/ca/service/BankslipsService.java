package br.com.challenge.ca.service;

import br.com.challenge.ca.entity.BankslipsEntity;
import br.com.challenge.ca.exception.BankslipsNotFoundException;
import br.com.challenge.ca.exception.InvalidParameterException;
import br.com.challenge.ca.repository.BankslipsRepository;
import br.com.challenge.ca.vo.BankslipsUpdateVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public List<BankslipsEntity> getAll() {
        return bankslipsRepository.findAll();
    }

    public BankslipsEntity getByCode(String code) {
        if (!code.matches(uuidPattern)) {
            throw new InvalidParameterException();
        }
        Optional<BankslipsEntity> byCode = bankslipsRepository.findByCode(code);

        byCode.orElseThrow(BankslipsNotFoundException::new);

        return byCode.get();
    }

    public BankslipsEntity add(BankslipsEntity bankslips) {
        return bankslipsRepository.save(bankslips);
    }
}
