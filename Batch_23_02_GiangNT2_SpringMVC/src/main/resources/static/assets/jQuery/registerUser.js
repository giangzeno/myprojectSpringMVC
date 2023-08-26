 var listInput = $('input');
    var listFeedback = $('.feedback');
    $(".Register").click(function (e) {
		e.preventDefault();
        var listFalax = new Array();
        if (checkLength(listInput[1].value)) {
            displayNone($(listFeedback[0]), false, 'Please provide a valid User Name !!!')
            listFalax.push(false)
        } else {
            displayNone($(listFeedback[0]), true, '')
            listFalax.push(true)
        }

        if (checkLength(listInput[2].value)) {
            displayNone($(listFeedback[1]), false, 'Please provide a valid PassWord !!!')
            listFalax.push(false)
        } else {
            displayNone($(listFeedback[1]), true, '')
            if (checkLength(listInput[3].value) || listInput[3].value !== listInput[2].value) {
                displayNone($(listFeedback[2]), false, 'Password mismatch !!!')
                listInput[3].value = ''
                listFalax.push(false)
            } else {
                displayNone($(listFeedback[2]), true, '')
                listFalax.push(true)
            }
        }
        if (checkLength(listInput[4].value) || checkEmail(listInput[4].value)) {
            displayNone($(listFeedback[3]), false, 'Please provide a valid Email !!!')
            listFalax.push(false)
        } else {
            displayNone($(listFeedback[3]), true, '')
            listFalax.push(true)
        }

        if (!listFalax.includes(false)) {
            $("form").trigger("submit");
        }
    })