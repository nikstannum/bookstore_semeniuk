
$(function(){
const role = $(`meta[name="role"]`).attr("content");
document.querySelectorAll('input[type="radio"]').forEach(radio => {
	if (radio.id.toLowerCase().endsWith(role.toLowerCase())) {
		radio.checked = true;
	}
})
})
