package com.outfittery.stylistbooking.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Appointment {
  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  private String id;

  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "customer_id")
  @JsonManagedReference
  private Customer customer;

  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "stylist_id")
  @JsonManagedReference
  private Stylist stylist;

  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "time_slot_id")
  @JsonManagedReference
  private TimeSlot timeSlot;

  public Appointment() {}

  public String getId() {
    return this.id;
  }

  public Customer getCustomer() {
    return this.customer;
  }

  public Stylist getStylist() {
    return this.stylist;
  }

  public TimeSlot getTimeSlot() {
    return this.timeSlot;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setCustomer(Customer customer) {
    this.customer = customer;
  }

  public void setStylist(Stylist stylist) {
    this.stylist = stylist;
  }

  public void setTimeSlot(TimeSlot timeSlot) {
    this.timeSlot = timeSlot;
  }

  public boolean equals(Object o) {
    if (o == this) return true;
    if (!(o instanceof Appointment)) return false;
    final Appointment other = (Appointment) o;
    if (!other.canEqual((Object) this)) return false;
    final Object this$id = this.getId();
    final Object other$id = other.getId();
    if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
    final Object this$customer = this.getCustomer();
    final Object other$customer = other.getCustomer();
    if (this$customer == null ? other$customer != null : !this$customer.equals(other$customer))
      return false;
    final Object this$stylist = this.getStylist();
    final Object other$stylist = other.getStylist();
    if (this$stylist == null ? other$stylist != null : !this$stylist.equals(other$stylist))
      return false;
    final Object this$timeSlot = this.getTimeSlot();
    final Object other$timeSlot = other.getTimeSlot();
    if (this$timeSlot == null ? other$timeSlot != null : !this$timeSlot.equals(other$timeSlot))
      return false;
    return true;
  }

  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    final Object $id = this.getId();
    result = result * PRIME + ($id == null ? 43 : $id.hashCode());
    final Object $customer = this.getCustomer();
    result = result * PRIME + ($customer == null ? 43 : $customer.hashCode());
    final Object $stylist = this.getStylist();
    result = result * PRIME + ($stylist == null ? 43 : $stylist.hashCode());
    final Object $timeSlot = this.getTimeSlot();
    result = result * PRIME + ($timeSlot == null ? 43 : $timeSlot.hashCode());
    return result;
  }

  protected boolean canEqual(Object other) {
    return other instanceof Appointment;
  }

  public String toString() {
    return "";
  }
}
