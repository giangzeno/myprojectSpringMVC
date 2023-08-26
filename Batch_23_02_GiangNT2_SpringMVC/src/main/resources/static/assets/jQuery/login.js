var listInput = $('input');
var listFeedback = $('.feedback');
$(document).ready(
	function() {
		// Lắng nghe sự kiện click trên label
		$(".checkbox").click(function name() {
				// Kích hoạt sự kiện click cho checkbox tương ứng
				$("#rememberMe").prop("checked",
					!$("#rememberMe").prop("checked"));
			});
	})

$(".Login").click(function name() {
	var listFalax = new Array();
	if (checkLength(listInput[1].value)) {
		displayNone($(listFeedback[1]), false, 'Please provide a valid Project Name !!!')
		listFalax.push(false)
	} else {
		displayNone($(listFeedback[0]), true, '')
		listFalax.push(true)
	}
	
	if (checkLength(listInput[2].value)) {
		displayNone($(listFeedback[2]), false, 'Please provide a valid Project Name !!!')
		listFalax.push(false)
	} else {
		displayNone($(listFeedback[1]), true, '')
		listFalax.push(true)
	}
	if (!listFalax.includes(false)) {
		$("form").trigger("submit");

	}
});