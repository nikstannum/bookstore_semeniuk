 
$.get("/api/users/all/data", processUsers);

const cssLink = $("<link>");
  $("head").append(cssLink);
  cssLink.attr({
    rel:  "stylesheet",
    type: "text/css",
    href: "/css/style.css"
  });


function processUsers(users) {
	users.forEach(user => {
		const row = $(`<tr>
		
			<td><a href="${user.id}">${user.firstName}</a></td>
			<td>${user.lastName}</td>
			<td>${user.email}</td>
			<td><a href="update?id=${user.id}"><input type ="submit" value="<spring:message code="users.button.update"/>"/></a></td>
		
		</tr>`);
		$("tbody").append(row);
	});
}