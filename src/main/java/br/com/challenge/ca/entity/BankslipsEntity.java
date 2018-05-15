package br.com.challenge.ca.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "bankslips")
public class BankslipsEntity {

    @JsonIgnore
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @JsonProperty("id")
    @Column(name = "code")
    private String code;

    @JsonProperty("due_date")
    @JsonFormat(pattern = "yyyy-mm-dd")
    @NotNull
    @Temporal(TemporalType.DATE)
    @Column(name = "due_date")
    private Date dueDate;

    @JsonProperty("total_in_cents")
    @NotNull
    @Column(name = "total_in_cents")
    private BigDecimal totalInCents;

    @NotBlank
    @Column(name = "customer")
    private String customer;

    //TODO criar enum
    @NotBlank
    @Column(name = "status")
    private String status;

    private BankslipsEntity() {
        this.code = UUID.randomUUID().toString();
    }

    public BankslipsEntity(@NotNull Date dueDate, @NotNull BigDecimal totalInCents, @NotBlank String customer) {
        this.code = UUID.randomUUID().toString();
        this.dueDate = dueDate;
        this.totalInCents = totalInCents;
        this.customer = customer;
        this.status = "PENDING";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public BigDecimal getTotalInCents() {
        return totalInCents;
    }

    public void setTotalInCents(BigDecimal totalInCents) {
        this.totalInCents = totalInCents;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BankslipsEntity billet = (BankslipsEntity) o;

        if (id != null ? !id.equals(billet.id) : billet.id != null) return false;
        if (code != null ? !code.equals(billet.code) : billet.code != null) return false;
        if (dueDate != null ? !dueDate.equals(billet.dueDate) : billet.dueDate != null) return false;
        if (totalInCents != null ? !totalInCents.equals(billet.totalInCents) : billet.totalInCents != null)
            return false;
        if (customer != null ? !customer.equals(billet.customer) : billet.customer != null) return false;
        return status != null ? status.equals(billet.status) : billet.status == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (code != null ? code.hashCode() : 0);
        result = 31 * result + (dueDate != null ? dueDate.hashCode() : 0);
        result = 31 * result + (totalInCents != null ? totalInCents.hashCode() : 0);
        result = 31 * result + (customer != null ? customer.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "BankslipsEntity{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", dueDate=" + dueDate +
                ", totalInCents=" + totalInCents +
                ", customer='" + customer + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

}
