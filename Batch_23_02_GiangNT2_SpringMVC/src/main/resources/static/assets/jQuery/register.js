var listInput = $('input');
var listFeedback = $('.feedback');

$(".REGISTER").click(function name(e) {
	e.preventDefault();
	var listFalax = new Array();
	if (checkNumber(listInput[1].value) || checkLength(listInput[1].value)) {
		displayNone($(listFeedback[0]), false, 'Please provide a valid Project ID !!!')
		listFalax.push(false)
	} else {
		displayNone($(listFeedback[0]), true, '')
		listFalax.push(true)
	}

	if (checkLength(listInput[2].value)) {
		displayNone($(listFeedback[1]), false, 'Please provide a valid Project Name !!!')
		listFalax.push(false)
	} else {
		displayNone($(listFeedback[1]), true, '')
		listFalax.push(true)
	}

	if (checkLength(listInput[3].value)) {
		displayNone($(listFeedback[2]), false, 'Please provide a valid Diffculty !!!')
		listFalax.push(false)
	} else {
		displayNone($(listFeedback[2]), true, '')
		listFalax.push(true)
	}

	if (checkNumber(listInput[4].value) || checkLength(listInput[3].value)) {
		displayNone($(listFeedback[3]), false, 'Please provide a valid Version !!!')
		listFalax.push(false)
	} else {
		displayNone($(listFeedback[3]), true, '')
		listFalax.push(true)
	}
	
	if (checkLength(listInput[5].value)) {
		displayNone($(listFeedback[4]), false, 'Please provide a valid Location !!!')
		listFalax.push(false)
	} else {
		displayNone($(listFeedback[4]), true, '')
		listFalax.push(true)
	}
	
	if ($("select").val() == '') {
		displayNone($(listFeedback[5]), false, 'Please provide a valid Department Name !!!')
		listFalax.push(false)
	} else {
		displayNone($(listFeedback[5]), true, '')
		listFalax.push(true)
	}

	if (!listFalax.includes(false)) {
		$("form").trigger("submit");
	}
})


if (feedbackregister === true || feedbackregister === false) {
	var icon = 'error';
	var text = 'Register Failure'
	if (feedbackregister) {
		icon = 'success'
		text = 'Register Successfully'
	}
	Swal.fire({
		icon: icon,
		title: text,
		showConfirmButton: false,
		timer: 1500
	})
}

if (feedbackValidate) {
	Swal.fire({
		title: 'Do you want to save the changes?',
		showDenyButton: true,
		showCancelButton: true,
		confirmButtonText: 'Register',
		denyButtonText: `Don't Register`,
		cancelButtonText: 'Back'
	}).then((result) => {
		if (result.isConfirmed) {
			var currentAction = $("form").attr("action");
            var newAction = currentAction + "/insert";
            $("form").attr("action", newAction);
			$("form").trigger("submit");
		} else if (result.isDenied) {
			window.location.href = '/home';
		}
	})
}