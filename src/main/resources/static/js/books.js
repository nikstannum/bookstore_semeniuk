const cssLink = $("<link>");
$("head").append(cssLink);
cssLink.attr({
	rel: "stylesheet",
	type: "text/css",
	href: "/css/style.css"
});

$(function() {

	const refresh = () => {
		const params = $(".query-string").text();
		$.get("/api/books?" + params, processBooks);
	};

	const updateQueryString = (page) => {
		$(".query-string").text(`page=${page.number}&size=${page.size}`)
	};

	const renderTable = (page) => {
		const $tbody = $("tbody");
		$tbody.empty();
		let i = 1;
		page.content.forEach(book => renderTableRow(book, $tbody, i++));
	};

	const renderTableRow = (book, $tbody, i) => {
		const $row = $(`
		<tr id="row-${book.id}">
			<td>${i}</td>
			<td><a href="books/${book.id}">${book.title}</a></td>
			<td>${book.author}</td>
			<td><button class="buttonCart">add to cart</button></td>
			<td><a href="/books/${book.id}/update"><input type ="submit" value="update"/></a></td>
			<td><button class="buttonDelete">delete</button></td>
		</tr>
		`);
		$row.find(".buttonCart").on("click", () => $.ajax({
			type: "POST",
			url: `/api/cart/${book.id}`,
			success: refresh
		}));
		$row.find(".buttonDelete").on("click", () => $.ajax({
			type: "DELETE",
			url: `/api/books/${book.id}`,
			success: refresh
		}));
		$tbody.append($row);
	};

	const renderPaginationButtons = (page) => {
		$(".pagination button").off();
		const prevPage = Math.max(0, page.number - 1);
		const lastPage = page.totalPages - 1;
		const nextPage = Math.min(lastPage, page.number + 1);
		$(".first").on("click", () => $.get(`/api/books?page=0&size=${page.size}`, processBooks));
		$(".prev").on("click", () => $.get(`/api/books?page=${prevPage}&size=${page.size}`, processBooks));
		$(".current").text(page.number + 1);
		$(".next").on("click", () => $.get(`/api/books?page=${nextPage}&size=${page.size}`, processBooks));
		$(".last").on("click", () => $.get(`/api/books?page=${lastPage}&size=${page.size}`, processBooks));
	}

	const processBooks = (page) => {
		updateQueryString(page);
		renderTable(page);
		renderPaginationButtons(page);
	};


	refresh();
	processBooks(page);

});
