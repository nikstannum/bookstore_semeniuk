import {prepareCsrfRequestHeaders} from "./util.js";

const headers = prepareCsrfRequestHeaders();
const $logoutReq = $(".nav-item-logout");

$logoutReq.on("click", req);
function req(e) {
	e.preventDefault();
	$.ajax({
			type: "POST",
			url: `/users/logout`,
			headers,
		});
};
