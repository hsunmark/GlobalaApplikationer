package model;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by Henrik on 2016-02-25.
 */
@Entity
@Table(name = "availability", schema = "recruitdb")
public class AvailabilityEntity {
    private long availabilityId;
    private Long personId;
    private Date fromDate;
    private Date toDate;

    @Id
    @Column(name = "availability_id")
    public long getAvailabilityId() {
        return availabilityId;
    }

    public void setAvailabilityId(long availabilityId) {
        this.availabilityId = availabilityId;
    }

    @Basic
    @Column(name = "person_id")
    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    @Basic
    @Column(name = "from_date")
    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    @Basic
    @Column(name = "to_date")
    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AvailabilityEntity that = (AvailabilityEntity) o;

        if (availabilityId != that.availabilityId) return false;
        if (personId != null ? !personId.equals(that.personId) : that.personId != null) return false;
        if (fromDate != null ? !fromDate.equals(that.fromDate) : that.fromDate != null) return false;
        if (toDate != null ? !toDate.equals(that.toDate) : that.toDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (availabilityId ^ (availabilityId >>> 32));
        result = 31 * result + (personId != null ? personId.hashCode() : 0);
        result = 31 * result + (fromDate != null ? fromDate.hashCode() : 0);
        result = 31 * result + (toDate != null ? toDate.hashCode() : 0);
        return result;
    }
}
