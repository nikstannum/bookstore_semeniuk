const cssLink = $("<link>");
$("head").append(cssLink);
cssLink.attr({
	rel: "stylesheet",
	type: "text/css",
	href: "/css/style.css"
});

$.get("/api/books", processUsers);

function processUsers(books) {
	let i = 1;
	books.forEach(book => {
		const row = $(`<tr>
			<td>${i}</td>
			<td><a href="${book.id}">${book.title}</a></td>
			<td>${book.author}</td>
			<td><button id="${book.id}" class="buttonCart">add to cart</button></td>
			<td><a href="update?id=${book.id}"><input type ="submit" value="update"/></a></td>
		</tr>`);

		$("tbody").append(row);
		i++;
	});
	$(".buttonCart").on("click", function() {
		addCart(this.id)
	});
}

function addCart(id) {
	$.post("/api/cart", { "id": id });
}






