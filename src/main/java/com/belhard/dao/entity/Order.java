package com.belhard.dao.entity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.Hibernate;
import org.hibernate.annotations.Where;

import com.belhard.dao.entity.converter.OrderStatusConverter;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Where(clause = "deleted = false")
@Entity
@Table(name = "orders")
public class Order {

	@Id
	@Column(name = "order_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JoinColumn(name = "user_id")
	@ManyToOne(cascade = CascadeType.MERGE)
	private User user;

	@Column(name = "total_cost")
	private BigDecimal totalCost;

	@Column(name = "status_id")
	@Convert(converter = OrderStatusConverter.class)
	private Status status;

	@OneToMany(cascade = CascadeType.ALL)
	private List<OrderInfo> details;

	@Transient
	@Column(name = "deleted")
	private boolean deleted;

	public enum Status {
		PENDING, PAID, DELIVERED, CANCELED
	}

	@Override
	public int hashCode() {
		return getClass().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (Hibernate.getClass(this) != Hibernate.getClass(obj))
			return false;
		Order other = (Order) obj;
		return id != null && Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", user=" + user.getEmail() + ", totalCost=" + totalCost + ", status=" + status
						+ "]";
	}

}
