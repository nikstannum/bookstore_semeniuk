

let searchParams = new URLSearchParams(window.location.search);
let id = searchParams.get("id");
let urlAddress = "/api/orders/" + id;
$.get(urlAddress, processDetails);





const data = $("#buttoms");
function processDetails(order) {
	let details = order.detailsDto;
	details.forEach((detail) => {
		const content = $(`
		<p>
		<button	id="${detail.id}" class="decrease">-</button></a>
		${detail.bookQuantity}
		<button	id="${detail.id}" class="increase">+</button></a>
		<p/>
		`);
		data.append(content);
	});
	$("button").on("click", function() {
		changeQuantity(this.id, order.id, this.className)
	});
}

function changeQuantity(detailId, orderId, status) {
	if(status == "decrease") {
		$.ajax({
			url: "/api/orders/decrease_quantity",
			method: "post",
			dataType: "json",
			data: {"text": orderId, "text":detailId},
		});
		
	} else {
	$.post("/api/orders/increase_quantity", detailId, orderId);
	}
}






