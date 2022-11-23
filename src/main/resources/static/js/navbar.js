const $logoutReq = $(".nav-item-logout");

$logoutReq.on("click", req);
function req(e) {
	e.preventDefault();
	$.post(`/users/logout`);
};
