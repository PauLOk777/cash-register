package com.paulok777.model.dto;

import java.util.Objects;

public class ReportDTO {
    private Long amount;
    private Long totalPrice;

    public ReportDTO(final Long amount, final Long totalPrice) {
        this.amount = amount;
        this.totalPrice = totalPrice;
    }

    public Long getAmount() {
        return this.amount;
    }

    public Long getTotalPrice() {
        return this.totalPrice;
    }

    public void setAmount(final Long amount) {
        this.amount = amount;
    }

    public void setTotalPrice(final Long totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReportDTO reportDTO = (ReportDTO) o;
        return Objects.equals(amount, reportDTO.amount) &&
                Objects.equals(totalPrice, reportDTO.totalPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, totalPrice);
    }

    public String toString() {
        return "ReportDTO(amount=" + getAmount() + ", totalPrice=" + getTotalPrice() + ")";
    }

    public static ReportDTO.ReportDTOBuilder builder() {
        return new ReportDTO.ReportDTOBuilder();
    }

    public static class ReportDTOBuilder {
        private Long amount;
        private Long totalPrice;

        ReportDTOBuilder() {
        }

        public ReportDTO.ReportDTOBuilder amount(final Long amount) {
            this.amount = amount;
            return this;
        }

        public ReportDTO.ReportDTOBuilder totalPrice(final Long totalPrice) {
            this.totalPrice = totalPrice;
            return this;
        }

        public ReportDTO build() {
            return new ReportDTO(this.amount, this.totalPrice);
        }

        public String toString() {
            return "ReportDTO.ReportDTOBuilder(amount=" + this.amount + ", totalPrice=" + this.totalPrice + ")";
        }
    }
}
