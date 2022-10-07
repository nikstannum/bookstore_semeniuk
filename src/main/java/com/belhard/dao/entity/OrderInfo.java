package com.belhard.dao.entity;

import java.math.BigDecimal;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.Hibernate;
import org.hibernate.annotations.Where;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@RequiredArgsConstructor
@Table(name = "order_infos")
@Where(clause = "deleted = false")
public class OrderInfo {

	@Id
	@Column(name = "order_infos_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "order_id")
	private Long orderId;

	@OneToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE })
	@JoinColumn(name = "book_id")
	private Book book;

	@Column(name = "book_quantity")
	private Integer bookQuantity;

	@Column(name = "book_price")
	private BigDecimal bookPrice;

	@ManyToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH })
	private Order order;

	@Transient
	@Column(name = "deleted")
	private boolean deleted;

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
		OrderInfo other = (OrderInfo) obj;
		return id != null && Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "OrderInfo [id=" + id + ", orderId=" + orderId + ", book=" + book.getTitle() + ", bookQuantity="
						+ bookQuantity + ", bookPrice=" + bookPrice + "]";
	}

}
