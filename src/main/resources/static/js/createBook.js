$(function() {
	const $createBtn = $("button.create");
	$createBtn.on("click", sendData);

	function sendData(e) {
		e.preventDefault();
		const title = $("#input-title").val();
		const author = $("#input-author").val();
		const isbn = $("#input-isbn").val();
		const pages = $("#input-pages").val();
		const price = $("#input-price").val();
		const coverDto = $("input[name='coverDto']:checked").val();
		const book = { title, author, isbn, pages, price, coverDto };
		
		
		$.ajax({
			type: "POST",
			url: "/api/books",
			data: JSON.stringify(book),
			success: processCreated,
			error: processError,
			dataType: "json",
			contentType: "application/json"
		});
	}
	
	function processCreated(data, status, $XHR) {
		$(".error").remove();
		if ($XHR.status == 201) {
			const uri = $XHR.getResponseHeader("Location");
			window.location.href = uri;
		}
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
})
