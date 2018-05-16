package br.com.challenge.ca.vo;

import br.com.challenge.ca.enumeration.BankslipsStatusEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.math.BigDecimal;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BankslipsVO {

    @JsonProperty("id")
    public String code;

    @JsonProperty("due_date")
    @Temporal(TemporalType.DATE)
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", locale = "pt-BR", timezone = "Brazil/East")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Brazil/East")
    public Date dueDate;

    @JsonProperty("total_in_cents")
    public BigDecimal totalInCents;

    public String customer;

    public BankslipsStatusEnum status;

    public BankslipsVO() {
    }

    public BankslipsVO(Date dueDate, BigDecimal totalInCents, String customer, BankslipsStatusEnum status) {
        this.dueDate = dueDate;
        this.totalInCents = totalInCents;
        this.customer = customer;
        this.status = status;
    }

}
