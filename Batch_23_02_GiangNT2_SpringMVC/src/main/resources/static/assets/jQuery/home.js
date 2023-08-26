$(document).ready(function() {
	var listFeedback = $('.feedback');
	$(".REGISTER").click(function name(e) {
		e.preventDefault();
		var listInput = $('input');
		var listFalax = new Array();
		if (checkNumber(listInput[1].value)) {
			displayNone($(listFeedback[0]), false, 'Please provide a valid Project ID !!!')
			listFalax.push(false)
		} else {
			displayNone($(listFeedback[0]), true, '')
			listFalax.push(true)
		}

		if (!listFalax.includes(false)) {
			$("form:first").trigger("submit");
		}
	})

	if (feedbackdelete === true || feedbackdelete === false) {
		var icon = 'error';
		var text = 'Delete Failure'
		if (feedbackdelete) {
			icon = 'success'
			text = 'Delete Successfully'
		}
		Swal.fire({
			icon: icon,
			title: text,
			showConfirmButton: false,
			timer: 1500
		})
		setTimeout(function() {
			history.pushState(null, null, '/home');;
		}, 1500);
	}

	$(".delete").click(function name() {
		Swal.fire({
			title: 'Are you sure?',
			text: "You won't be able to revert this!",
			icon: 'warning',
			showCancelButton: true,
			confirmButtonColor: '#3085d6',
			cancelButtonColor: '#d33',
			confirmButtonText: 'Yes, delete it!',
			cancelButtonText: 'Back'
		}).then((result) => {
			if (result.isConfirmed) {
				window.location.href = $(this).attr("url");
			}
		})
	})
	
	$(".update").click(function(){
		window.location.href = $(this).attr("url");
	})



	if (feedbackUpdate === true || feedbackUpdate === false) {
		var icon = 'error';
		var text = 'Update Failure'
		if (feedbackUpdate) {
			icon = 'success'
			text = 'Update Successfully'
		}
		Swal.fire({
			icon: icon,
			title: text,
			showConfirmButton: false,
			timer: 1500
		})
		setTimeout(function() {
			history.pushState(null, null, '/home');;
		}, 1500);
	}
});

var listA = $(".hip");
for (let index = 0; index < listA.length; index++) {
	if ($(listA[index]).text() == indexPage) {
		$(listA[index]).addClass("indexPage");
		$(listA[index]).attr("href","#");
	}
}
