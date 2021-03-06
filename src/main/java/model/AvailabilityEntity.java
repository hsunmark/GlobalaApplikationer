package model;

import javax.persistence.*;
import java.sql.Date;
import com.google.gson.*;

/**
 * Table holding a date-span where person is available.
 */
@Entity
@Table(name = "availability", schema = "recruitdb")
public class AvailabilityEntity {
    private long availabilityId;
    private String  fromDate;
    private String toDate;
    private transient PersonEntity person_fk;

    @Id
    @Column(name = "availability_id", nullable = false)
    public long getAvailabilityId() {
        return availabilityId;
    }

    public void setAvailabilityId(long availabilityId) {
        this.availabilityId = availabilityId;
    }

    @Basic
    @Column(name = "from_date", nullable = true)
    public String getFromDate() {
        return fromDate.toString();
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    @Basic
    @Column(name = "to_date", nullable = true)
    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AvailabilityEntity that = (AvailabilityEntity) o;

        if (availabilityId != that.availabilityId) return false;
        if (fromDate != null ? !fromDate.equals(that.fromDate) : that.fromDate != null) return false;
        if (toDate != null ? !toDate.equals(that.toDate) : that.toDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (availabilityId ^ (availabilityId >>> 32));
        result = 31 * result + (fromDate != null ? fromDate.hashCode() : 0);
        result = 31 * result + (toDate != null ? toDate.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "person_id")
    public PersonEntity getPerson_fk() {
        return person_fk;
    }

    public void setPerson_fk(PersonEntity person_fk) {
        this.person_fk = person_fk;
    }

}
