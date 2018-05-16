package br.com.challenge.ca.vo.converter;

import br.com.challenge.ca.entity.BankslipsEntity;
import br.com.challenge.ca.vo.BankslipsVO;

import java.util.List;
import java.util.stream.Collectors;

public class BankslipsConverter {

    public static BankslipsEntity toEntity(final BankslipsVO vo) {
        if (vo == null) {
            return new BankslipsEntity();
        }
        return new BankslipsEntity(vo.dueDate, vo.totalInCents, vo.customer, vo.status);
    }

    public static List<BankslipsVO> toVO(final List<BankslipsEntity> entities) {
        return entities.stream().map(BankslipsConverter::toVO).collect(Collectors.toList());
    }

    public static BankslipsVO toVO(final BankslipsEntity entity) {
        BankslipsVO vo = new BankslipsVO();
        if (entity != null) {
            vo.code = entity.getCode();
            vo.customer = entity.getCustomer();
            vo.dueDate = entity.getDueDate();
            vo.status = entity.getStatus();
            vo.totalInCents = entity.getTotalInCents();
        }

        return vo;
    }

}
