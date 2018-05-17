package br.com.challenge.ca.service;

import br.com.challenge.ca.exception.TaxInvalidParameterException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class TaxService {

    private final static Logger LOGGER = LoggerFactory.getLogger(TaxService.class);

    public BigDecimal incrementPercent(final BigDecimal value, final BigDecimal percent) {
        if (value == null || percent == null
                || value.compareTo(BigDecimal.ZERO) == -1 || percent.compareTo(BigDecimal.ZERO) == -1) {
            LOGGER.error("Valor invalido para calculo de taxas, value={}, percent={}", value, percent);
            throw new TaxInvalidParameterException();
        }

        BigDecimal result = value
                .multiply(percent.divide(new BigDecimal("100")))
                .add(value);

        return result;
    }

}
