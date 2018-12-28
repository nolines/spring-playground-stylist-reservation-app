package com.outfittery.stylistbooking.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table
public class TimeSlot {
  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  private String id;

  private Integer time;

  @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @JoinColumn(name = "stylist_id")
  private Stylist stylist;

  @OneToOne(
    mappedBy = "timeSlot",
    fetch = FetchType.EAGER,
    cascade = CascadeType.ALL,
    orphanRemoval = true
  )
  private Appointment appointment;

  public TimeSlot() {}

  public TimeSlot(Integer time, Stylist stylist, Appointment appointment) {
    this.time = time;
    this.stylist = stylist;
    this.appointment = appointment;
  }

  public void setAppointment(Appointment appointment) {
    appointment.setTimeSlot(this);
    this.appointment = appointment;
  }

  public String getId() {
    return this.id;
  }

  public Integer getTime() {
    return this.time;
  }

  public Stylist getStylist() {
    return this.stylist;
  }

  public Appointment getAppointment() {
    return this.appointment;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setTime(Integer time) {
    this.time = time;
  }

  public void setStylist(Stylist stylist) {
    this.stylist = stylist;
  }

  public boolean equals(Object o) {
    if (o == this) return true;
    if (!(o instanceof TimeSlot)) return false;
    final TimeSlot other = (TimeSlot) o;
    if (!other.canEqual((Object) this)) return false;
    final Object this$id = this.getId();
    final Object other$id = other.getId();
    if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
    final Object this$time = this.getTime();
    final Object other$time = other.getTime();
    if (this$time == null ? other$time != null : !this$time.equals(other$time)) return false;
    final Object this$stylist = this.getStylist();
    final Object other$stylist = other.getStylist();
    if (this$stylist == null ? other$stylist != null : !this$stylist.equals(other$stylist)) return false;
    final Object this$appointment = this.getAppointment();
    final Object other$appointment = other.getAppointment();
    if (this$appointment == null ? other$appointment != null : !this$appointment.equals(other$appointment))
      return false;
    return true;
  }

  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    final Object $id = this.getId();
    result = result * PRIME + ($id == null ? 43 : $id.hashCode());
    final Object $time = this.getTime();
    result = result * PRIME + ($time == null ? 43 : $time.hashCode());
    final Object $stylist = this.getStylist();
    result = result * PRIME + ($stylist == null ? 43 : $stylist.hashCode());
    final Object $appointment = this.getAppointment();
    result = result * PRIME + ($appointment == null ? 43 : $appointment.hashCode());
    return result;
  }

  protected boolean canEqual(Object other) {
    return other instanceof TimeSlot;
  }

  public String toString() {
    return "TimeSlot(id=" + this.getId() + ", time=" + this.getTime() + ", stylist=" + this.getStylist().getName() + ", appointment=" + this.getAppointment() + ")";
  }
}