package org.jpabook.jpashop.domain;

import javax.persistence.Embeddable;
import java.time.LocalDateTime;
import java.util.Objects;

@Embeddable
public class JobPeriod {

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public boolean isWorkNow() {
        if (endDate == null) {
            return false;
        }
        return true;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public JobPeriod() {
    }

    public JobPeriod(LocalDateTime startDate, LocalDateTime endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        JobPeriod jobPeriod = (JobPeriod) object;
        return Objects.equals(getStartDate(), jobPeriod.getStartDate()) && Objects.equals(getEndDate(), jobPeriod.getEndDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStartDate(), getEndDate());
    }
}
