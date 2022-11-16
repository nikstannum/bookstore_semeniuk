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
			success: processUpdated,
			error: processError,
			dataType: "json",
			contentType: "application/json"
		});
	}
	function processUpdated(book) {
		$(".error").remove();
		window.location.href = `/books/${book.id}`;
	}

	function processError(response) {
		$(".error").remove();
		if (response.status == 422) {
			const validationResultDto = response.responseJSON;
			for (const field in validationResultDto.messages) {
				validationResultDto.messages[field].forEach(msg => {
					$("form").prepend($(`<div class="error">${field}: ${msg}</div>`));
				})
			}
		}
	}
});
