package br.com.challenge.ca.unit;

import br.com.challenge.ca.exception.TaxInvalidParameterException;
import br.com.challenge.ca.service.TaxService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;

@RunWith(SpringJUnit4ClassRunner.class)
public class TaxServiceTest {

    private TaxService taxService = new TaxService();

    @Test
    public void incrementPercent(){
        BigDecimal result = taxService.incrementPercent(BigDecimal.TEN, BigDecimal.ONE);

        Assert.assertEquals(new BigDecimal("10.10"), result);
    }

    @Test
    public void incrementPercentValueZero(){
        BigDecimal result = taxService.incrementPercent(BigDecimal.ONE, BigDecimal.ZERO);

        Assert.assertEquals(BigDecimal.ONE, result);
    }

    @Test(expected = TaxInvalidParameterException.class)
    public void incrementPercentValueNull(){
        taxService.incrementPercent(null, BigDecimal.ONE);
    }

    @Test(expected = TaxInvalidParameterException.class)
    public void incrementPercentPercentNull(){
        taxService.incrementPercent(BigDecimal.ONE, null);
    }

    @Test(expected = TaxInvalidParameterException.class)
    public void incrementPercentValueNegative(){
        taxService.incrementPercent(new BigDecimal("-100"), BigDecimal.ONE);
    }

}
