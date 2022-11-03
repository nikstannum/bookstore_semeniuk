//$.post("/api/users/all/data", getBook);


$(".formButton").on("click", $.get("/books/5"));

/*
function processForm() {
let book = {};
$.each($('form').serializeArray(), function(kv) {
  book[kv.name] = kv.value;
});
$.get("/books/5");
}*/

