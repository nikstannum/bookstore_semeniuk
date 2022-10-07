package com.belhard.dao.entity;

import java.math.BigDecimal;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@RequiredArgsConstructor
@Entity
@Table(name = "books")
@Where(clause = "deleted = false")
public class Book {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "book_id")
	private Long id;

	@Column(name = "title")
	private String title;

	@Column(name = "author")
	private String author;

	@Column(name = "isbn")
	private String isbn;

	@Column(name = "pages")
	private Integer pages;

	@Column(name = "price")
	private BigDecimal price;

	@Column(name = "cover_id")
	@Convert(converter = BookCoverConverter.class)
	private BookCover cover;

	@Transient
	@Column(name = "deleted")
	private boolean deleted;

	@OneToOne(mappedBy = "book", cascade = { CascadeType.DETACH, CascadeType.MERGE })
	private OrderInfo orderInfo;

	public enum BookCover {
		SOFT, HARD, SPECIAL
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
		Book other = (Book) obj;
		return id != null && Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Book [id=" + id + ", title=" + title + ", author=" + author + ", isbn=" + isbn + ", pages=" + pages
						+ ", price=" + price + ", cover=" + cover + "]";
	}

}
