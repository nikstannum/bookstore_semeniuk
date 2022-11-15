$(function() {
	const $updateButton= $("button.update");
	$updateButton.on("click", sendData);

	function sendData(e) {
		e.preventDefault();
		const id = $("#input-id").val();
		const title = $("#input-title").val();
		const author = $("#input-author").val();
		const isbn = $("#input-isbn").val();
		const pages = $("#input-pages").val();
		const price = $("#input-price").val();
		const coverDto = $("input[name='coverDto']:checked").val();
		const book = {title, author, isbn, pages, price, coverDto };
		
		
		$.ajax({
			type: "PUT",
			url: `/api/books/${id}`,
			data: JSON.stringify(book),
			dataType: "json",
			contentType: "application/json"
		});
	}
})
