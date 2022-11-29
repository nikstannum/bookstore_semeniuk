import { prepareCsrfRequestHeaders } from "./util.js";


const cssLink = $("<link>");
$("head").append(cssLink);
cssLink.attr({
	rel: "stylesheet",
	type: "text/css",
	href: "/css/style.css"
});
$(function() {
	const refresh = () => {
		const params = $(`meta[name="query-string"]`).attr("content");
		$.get("/api/users?" + params, getMessages);
	}

	const headers = prepareCsrfRequestHeaders();

	const updateQueryString = (page) => {
		$(`meta[name="query-string"]`).attr("content", `page=${page.number}&size=${page.size}`);
	};

	const renderPaginationButtons = (page) => {
		$(".pagination button").off();
		const prevPage = Math.max(0, page.number - 1);
		const lastPage = page.totalPages - 1;
		const nextPage = Math.min(lastPage, page.number + 1);
		$(".first").on("click", () => $.get(`/api/users?page=0&size=${page.size}`, getMessages));
		$(".prev").on("click", () => $.get(`/api/users?page=${prevPage}&size=${page.size}`, getMessages));
		$(".current").text(page.number + 1);
		$(".next").on("click", () => $.get(`/api/users?page=${nextPage}&size=${page.size}`, getMessages));
		$(".last").on("click", () => $.get(`/api/users?page=${lastPage}&size=${page.size}`, getMessages));
	}

	const enableButton = $(`meta[name="ourRoleAdminOrManager"]`).attr("content");
	const renderTable = (page, messages) => {
		if (enableButton) {
			const optionalData = $(".optional");
			optionalData.remove();
			const addTh = $(`<th class="optional">block</th >
				<th class="optional">delete</th>`);
			const thead = $("thead tr");
			thead.append(addTh);
		};
		const $tbody = $("tbody");
		$tbody.empty();
		page.content.forEach(user => renderRow(user, messages));
	};
	const renderRow = (user, messages) => {
		const $row = $(`
			<tr >
				<td><a href="${user.id}">${user.firstName}</a></td>
				<td>${user.lastName}</td>
				<td>${user.email}</td>
				<td><a href="update?id=${user.id}"><input type ="submit" value="${messages.users_button_update}"/></a></td>
			</tr>`);
		if (enableButton) {
			const addTd = $(`<td><input id="${user.id}" class="block" type="submit" value="block"/></td >
				<td><input id="${user.id}" class="delete" type="submit" value="delete"/></td>`);
			$row.append(addTd);
		};
		$("tbody").append($row);
		$row.find(".block").on("click", () => $.ajax({
			type: "DELETE",
			headers: headers,
			url: `/api/users/${user.id}`,
			success: refresh,
		}))

	};

	const getMessages = (page) => {
		const processUsers = (messages) => {
			renderPaginationButtons(page);
			renderTable(page, messages);
			updateQueryString(page);
		}
		$.ajax({
			type: "POST",
			headers: headers,
			url: `/api/messages`,
			data: JSON.stringify(['users.button.update']),
			success: processUsers,
			dataType: "json",
			contentType: "application/json"
		})
	};



	refresh();
});