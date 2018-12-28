package com.outfittery.stylistbooking.model;

import lombok.AccessLevel;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class Stylist {

  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  private String id;

  private String name;

  @OneToMany(
    mappedBy = "stylist",
    fetch = FetchType.EAGER,
    cascade = CascadeType.ALL,
    orphanRemoval = true
  )
  @Setter(AccessLevel.NONE)
  private List<TimeSlot> timeSlots = new ArrayList<>();

  public Stylist() {
  }

  public String getId() {
    return this.id;
  }

  public String getName() {
    return this.name;
  }

  public List<TimeSlot> getTimeSlots() {
    return this.timeSlots;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public boolean equals(Object o) {
    if (o == this) return true;
    if (!(o instanceof Stylist)) return false;
    final Stylist other = (Stylist) o;
    if (!other.canEqual((Object) this)) return false;
    final Object this$id = this.getId();
    final Object other$id = other.getId();
    if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
    final Object this$name = this.getName();
    final Object other$name = other.getName();
    if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
    final Object this$timeSlots = this.getTimeSlots();
    final Object other$timeSlots = other.getTimeSlots();
    if (this$timeSlots == null ? other$timeSlots != null : !this$timeSlots.equals(other$timeSlots)) return false;
    return true;
  }

  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    final Object $id = this.getId();
    result = result * PRIME + ($id == null ? 43 : $id.hashCode());
    final Object $name = this.getName();
    result = result * PRIME + ($name == null ? 43 : $name.hashCode());
    final Object $timeSlots = this.getTimeSlots();
    result = result * PRIME + ($timeSlots == null ? 43 : $timeSlots.hashCode());
    return result;
  }

  protected boolean canEqual(Object other) {
    return other instanceof Stylist;
  }

  public String toString() {
    return "Stylist(id=" + this.getId() + ", name=" + this.getName() + ", timeSlots=" + this.getTimeSlots() + ")";
  }
}
